package gutizia.util;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.Npc;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Interact {
    public final static int MAX_INTERACT_DISTANCE = 16;

    public static void clickOnTile(ClientContext ctx, Tile tile, boolean leftClick) {
        clickOnTile(ctx, tile, leftClick, false);
    }

    public static void clickOnTile(ClientContext ctx, Tile tile, boolean leftClick, boolean hop) {
        TileMatrix tileMatrix = tile.matrix(ctx);
        Tile initDest = ctx.movement.destination();

        if (!tileMatrix.inViewport()) {
            ctx.movement.step(tile);
        }

        if (leftClick) {
            if (hop) {
                ctx.input.hop(tileMatrix.centerPoint());
                ctx.input.click(true);
            }
            tileMatrix.click();
        } else {
            if (hop) {
                ctx.input.hop(tileMatrix.centerPoint());
            }
            tileMatrix.interact(false, "Walk here");
        }

        Condition.wait(() -> !ctx.movement.destination().equals(initDest) ||
                ctx.players.local().tile().equals(tile), 20, 10);
    }

    public static boolean clickOnTile(ClientContext ctx, Tile tile) {
        TileMatrix tileMatrix = tile.matrix(ctx);
        Tile initDest = ctx.movement.destination();

        if (!tileMatrix.inViewport()) {
            ctx.camera.turnTo(tileMatrix);
        }

        tileMatrix.interact("Walk here");
        Condition.wait(() -> !ctx.movement.destination().equals(initDest), 100, 8);

        if (!ctx.movement.destination().equals(tile)) {
            return tileMatrix.interact("Walk here");
        }
        return false;
    }

    public static boolean interact(ClientContext ctx, final GameObject object, boolean rightClick, String action, boolean ignoreDistance) {
        if (!ignoreDistance) {
            if (object.tile().distanceTo(ctx.players.local()) > 8) {
                ctx.movement.step(object);
                Condition.wait(() -> object.tile().distanceTo(ctx.players.local()) < 4);
            }
        }

        if (!object.inViewport()) {
            if (!ignoreDistance) {
                ctx.movement.step(object);
            }
            ctx.camera.turnTo(object);
        }

        removeSelectedItem(ctx);

        return object.interact(rightClick,action,object.name());
    }

    public static boolean interact(ClientContext ctx, final Npc npc, boolean rightClick, String action, boolean ignoreDistance) {
        if (!ignoreDistance) {
            if (npc.tile().distanceTo(ctx.players.local()) > 8) {
                ctx.movement.step(npc);
                Condition.wait(() -> npc.tile().distanceTo(ctx.players.local()) < 4);
            }
        }

        if (!npc.inViewport()) {
            if (!ignoreDistance) {
                ctx.movement.step(npc);
            }
            ctx.camera.turnTo(npc);
        }

        removeSelectedItem(ctx);

        return npc.interact(rightClick,action,npc.name());
    }

    public static void removeSelectedItem(ClientContext ctx) {
        if (ctx.inventory.selectedItem().id() != -1) {
            if (!ctx.game.tab().equals(Game.Tab.INVENTORY)) {
                ctx.game.tab(Game.Tab.INVENTORY, true);
            }
            ctx.inventory.selectedItem().interact("Cancel");
        }
    }

    public static boolean actionContains(String[] strings, String action) {
        for (String s : strings) {
            if (s == null || s.isEmpty()) {
                continue;
            }
            if (action.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean actionContains(String[] strings, String... actions) {
        for (String s : strings) {
            if (s == null || s.isEmpty()) {
                continue;
            }
            for (String action : actions) {
                if (action.equals(s)) {
                    return true;
                }
            }
        }
        return false;
    }


    // TODO remove later
    public static void climbLadder(ClientContext ctx, final GameObject ladder, boolean climbUp) {
        while (ctx.players.local().tile().distanceTo(ladder.tile()) < 20) {
            if (climbUp) {
                ladder.interact(false,"Climb-up",ladder.name());

            } else {
                ladder.interact(false,"Climb-down",ladder.name());
            }

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return ctx.players.local().tile().distanceTo(ladder.tile()) > 20;
                }
            },600,8);
        }
    }

    public static void climbLadder(ClientContext ctx, final GameObject ladder, boolean climbUp,Tile startTile) {
        while (ctx.players.local().tile().distanceTo(ladder.tile()) < 20 && ctx.players.local().tile().floor() == startTile.floor()) {
            if (climbUp) {
                ladder.interact(false,"Climb-up",ladder.name());

            } else {
                ladder.interact(false,"Climb-down",ladder.name());
            }
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return ctx.players.local().tile().distanceTo(ladder.tile()) > 20;
                }
            },600,8);
        }
    }

    // TODO remove later
    public static void climbLadder(ClientContext ctx, final GameObject ladder, boolean climbUp, int[] bounds,Tile startTile) {
        ladder.bounds(bounds);
        while (ctx.players.local().tile().distanceTo(ladder.tile()) < 20 && ctx.players.local().tile().floor() == startTile.floor()) {
            if (climbUp) {
                ladder.interact(false,"Climb-up",ladder.name());

            } else {
                ladder.interact(false,"Climb-down",ladder.name());
            }
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return ctx.players.local().tile().distanceTo(ladder.tile()) > 20;
                }
            },600,8);
        }
    }

    public static void equipOutfit(ClientContext ctx, int[] items) {
        if (!ctx.game.tab().equals(Game.Tab.INVENTORY)) {
            ctx.game.tab(Game.Tab.INVENTORY, true);
        }
        for (Item item : ctx.inventory.select().id(items)) {
            item.bounds(-12,20,-12,20,0,0);
            String[] actions = item.inventoryActions();
            for (String string : actions) {

                if (!wear(string,item)) {
                    wield(string,item);
                }
            }
        }
        Condition.wait(() -> ctx.inventory.select().id(items).isEmpty(), 200, 5);
    }
    public static boolean wield(String string, Item item) {

        try {

            if (string.equals("Wield")) {

                item.interact("Wield");
                return true;
            }

        } catch (NullPointerException e) {
            // not
        }
        return false;
    }
    public static boolean wear(String string, Item item) {

        try {

            if (string.equals("Wear")) {

                item.interact("Wear");
                return true;
            }
        } catch (NullPointerException e) {
            // not
        }
        return false;
    }

    // TODO remove later
    public static boolean haveItemsInInventory(ClientContext ctx, ArrayList<ArrayList<Integer>> items) {
        ArrayList<ArrayList<Integer>> missingItems = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> missingItem = new ArrayList<Integer>();

        for (ArrayList<Integer> item : items) {
            boolean foundMatchingID = false;

            for (Item inventoryItem : ctx.inventory.select()) {
                if (item.get(0).equals(inventoryItem.id())) {
                    if (inventoryItem.stackSize() < item.get(1)) { // if you have the item, but too few, add it to missingItems list...
                        missingItem.add(item.get(0));
                        missingItem.add(item.get(1) - inventoryItem.stackSize());
                        missingItems.add(missingItem);
                        missingItem = new ArrayList<Integer>();
                    }
                    foundMatchingID = true;
                    break;
                }
            }
            if (!foundMatchingID) {
                missingItem.add(item.get(0));
                missingItem.add(item.get(1));
                missingItems.add(missingItem);
                missingItem = new ArrayList<Integer>();
            }
        }

        return missingItems.isEmpty();
    }

    public static void dropItems(ClientContext ctx, int id) {
        if (!ctx.game.tab().equals(Game.Tab.INVENTORY)) {
            ctx.game.tab(Game.Tab.INVENTORY, true);
        }
        for (Item item : ctx.inventory.select().id(id)) {
            ctx.inventory.drop(item,true);
        }
    }

    public static void use(ClientContext ctx , Item item) {
        if (!ctx.game.tab().equals(Game.Tab.INVENTORY)) {
            ctx.game.tab(Game.Tab.INVENTORY, true);
        }
        while (ctx.inventory.selectedItem().id() != item.id() && item.valid() && !ctx.controller.isStopping() && ctx.game.loggedIn()) {
            removeSelectedItem(ctx);

            item.interact(false,"Use",item.name());
            Condition.sleep(Random.nextInt(340,600));
        }
    }

    public void climbLadder(ClientContext ctx, final GameObject ladder, boolean climbUp, int[] bounds) {
        ladder.bounds(bounds);
        if (climbUp) {
            ladder.interact(false,"Climb-up",ladder.name());

        } else {
            ladder.interact(false,"Climb-down",ladder.name());
        }

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return ctx.players.local().tile().distanceTo(ladder.tile()) > 20;
            }
        },600,8);
    }

    public void climbStairs(ClientContext ctx, GameObject staircase,boolean climbUp, int[] bounds) {
        staircase.bounds(bounds);

        final int floorLevel = ctx.players.local().tile().floor();

        while (ctx.players.local().tile().floor() == floorLevel) {
            if (climbUp) {
                staircase.interact(false,"Climb-up",staircase.name());

            } else {
                staircase.interact(false,"Climb-down",staircase.name());
            }
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return ctx.players.local().tile().floor() != floorLevel;
                }
            },600,8);
        }
    }

}
