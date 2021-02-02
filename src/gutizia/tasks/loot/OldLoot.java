package gutizia.tasks.loot;

import gutizia.tasks.Task;
import gutizia.util.settings.LootSettings;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.GroundItem;

import java.util.ArrayList;

import static gutizia.util.combat.PlayerCombat.playerCombat;
import static gutizia.util.managers.LootManager.lootManager;
import static gutizia.util.overlay.TaskOverlay.taskOverlay;

public class OldLoot extends Task {
    private int itemsToLootSizeThreshold = LootSettings.getNextRandomLootThreshold();

    @Override
    public void execute() {
        taskOverlay.setActiveTask("Loot");

        for (int i = 0; i < lootManager.getLootTiles().size(); i++) {
            Tile tile = getClosestTile();
            lootManager.removeLootTile(tile);

            if (tile.distanceTo(ctx.players.local()) > 4) {
                ctx.movement.step(tile);
                ctx.camera.turnTo(tile);
            }
            ArrayList<GroundItem> toRemove = new ArrayList<>();
            for (GroundItem groundItem : lootManager.getGroundItemsOnTile(tile)) {
                if (groundItem.interact("Take")) {
                    toRemove.add(groundItem);
                    Condition.wait(() -> !groundItem.valid(), 600, 16);
                }
            }
            for (GroundItem groundItem : toRemove) {
                lootManager.removeItemToLoot(groundItem);
            }
        }

        itemsToLootSizeThreshold = LootSettings.getNextRandomLootThreshold();
    }

    private Tile getClosestTile() {
        double min = Integer.MAX_VALUE;
        Tile t = Tile.NIL;
        for (Tile tile : lootManager.getLootTiles()) {
            if (tile.distanceTo(ctx.players.local()) < min) {
                min = tile.distanceTo(ctx.players.local());
                t = tile;
            }
        }
        return t;
    }

    @Override
    public boolean activate() {
        if (ctx.inventory.isFull() || !playerCombat.getTarget().isDead() || lootManager.getLootTiles().isEmpty()) {
            return false;
        }

        return ctx.inventory.size() + lootManager.getLootList().size() >= 28 || lootManager.getLootList().size() >= itemsToLootSizeThreshold;
    }
}
