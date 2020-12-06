package gutizia.util.combat;

import gutizia.util.constants.Bounds;
import gutizia.util.constants.Supplies;
import gutizia.util.constants.Items;
import gutizia.util.constants.Npcs;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;

public class Combat extends ClientAccessor {
    private Target myCurrentTarget = Target.NONE;

    private ArrayList<Integer> itemsToLoot;
    private ArrayList<Tile> lootTiles = new ArrayList<>();

    private int killsBeforeLoot;
    private int lootItemsTimes = 0;

    public Combat(ClientContext ctx, ArrayList<Integer> itemsToLoot) {
        super(ctx);
        this.itemsToLoot = itemsToLoot;
    }

    public void engageTarget(final Npc target, boolean usingMagic) {
        if (!target.inViewport()) {
            ctx.camera.turnTo(target);
        }

        target.interact(false,"Attack");

        // wait until you either attack the target
        if (!usingMagic) {
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return target.tile().distanceTo(ctx.players.local()) < 3;
                }
            },600,10);
        }

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return isFightingATarget();
            }
        },670,6);
    }

    private int getTargetSize(Npc target) {
        if (target.id() == Npcs.COW_IDS[0] || target.id() == Npcs.COW_IDS[1] || target.id() == Npcs.COW_IDS[2]) {
            return 2;
        } else {
            return 1;
        }
    }

    public boolean isFightingATarget() {
        Actor interactingWith = ctx.players.local().interacting();
        return (interactingWith.valid() && interactingWith.combatLevel() > 0 && interactingWith.interacting().equals(ctx.players.local()));
    }



    void saveTileForLoot(Tile tile, int size) {
        switch (size) {
            case 1:
                lootTiles.add(tile);
                break;

            case 2:
                lootTiles.add(new Tile(tile.x() - 1,tile.y() - 1, tile.floor()));
                break;
        }
    }

    //  3/6 chance to be 2 kills, 2/6 chance to be 3 kills, 1/6 chance to be 4 kills
    private void setKillsBeforeLoot() {
        java.util.Random random = new java.util.Random();
        int myNumber = (random.nextInt(6));
        myNumber++;

        if (myNumber > (28 - ctx.inventory.size())) {
            killsBeforeLoot = 28 - ctx.inventory.size();
        }

        switch (myNumber) {
            case 1:
                killsBeforeLoot = 2;
            case 2:
                killsBeforeLoot = 2;
                return;
            case 3:
                killsBeforeLoot = 2;
                return;
            case 4:
                killsBeforeLoot = 3;
                return;
            case 5:
                killsBeforeLoot = 3;
                return;
            case 6:
                killsBeforeLoot = 4;
                return;
        }
        killsBeforeLoot = myNumber;
    }

    public void lootItems() {
        int tileRange = 8;

        int[] itemsToLootArray = new int[itemsToLoot.size()];
        Iterator<Integer> iterator = itemsToLoot.iterator();
        for (int i = 0; i < itemsToLootArray.length;i++) {
            itemsToLootArray[i] = iterator.next();
        }

        for (final Tile tile : lootTiles) {
            if (ctx.inventory.select().size() == 28) {
                break;
            }
            for (final GroundItem item : ctx.groundItems.select().at(tile).id(itemsToLootArray)) {
                if (ctx.inventory.select().size() == 28) {
                    break;
                }
                if (tile.distanceTo(ctx.players.local()) > tileRange) {
                   ctx.movement.step(tile);
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() {
                            return tile.distanceTo(ctx.players.local()) < 4;
                        }
                    },600,10);
                }

                Condition.sleep(300);
                do {
                    if (!item.inViewport()) {
                        ctx.camera.turnTo(item);
                    }

                    item.interact(false, "Take", item.name());

                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() {
                            return ctx.players.local().inMotion() || !item.valid();
                        }
                    },600,4);
                }
                while (!ctx.players.local().inMotion() && ctx.players.local().tile() != item.tile() && item.inViewport() && item.valid());


                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() {
                        return !item.valid();
                    }
                },600,6);
            }
        }
        setKillsBeforeLoot();

        if (lootItemsTimes >= 3) {
            lootTiles = new ArrayList<>();
            lootItemsTimes = 0;

        } else {
            lootItemsTimes++;
        }
    }



    public void checkIfToLoot() {
        if (lootTiles.size() >= killsBeforeLoot) {
            lootItems();
        }
    }

    public boolean drinkWhine() {
        final int whineAmount = ctx.inventory.select().size();
        ctx.inventory.select().id(Supplies.JUG_OF_WINE).poll().interact("Drink");
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return whineAmount != ctx.inventory.select().size();
            }
        },600,6);
        return whineAmount != ctx.inventory.select().size();
    }

    public void protectFromMelee(boolean activate) {
        while (activate != ctx.prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE)) {
            ctx.prayer.prayer(Prayer.Effect.PROTECT_FROM_MELEE, activate);
        }

        while (!ctx.game.tab().equals(Game.Tab.INVENTORY)) {
            ctx.game.tab(Game.Tab.INVENTORY);
        }
    }

    public void setItemsToLoot(ArrayList<Integer> itemsToLoot) {
           this.itemsToLoot = itemsToLoot;
    }
}
