package gutizia.tasks.loot;

import gutizia.tasks.Task;
import org.powerbot.script.Tile;

import static gutizia.util.managers.LootManager.lootManager;
import static gutizia.util.overlay.TaskOverlay.taskOverlay;

public class GetToLootTile extends Task {

    @Override
    public void execute() {
        taskOverlay.setActiveTask("GetToLootTile");
        LootItemsToLoot.currentLootTile = getClosestTile();

        if (LootItemsToLoot.currentLootTile.distanceTo(ctx.players.local()) > 4) {
            ctx.movement.step(LootItemsToLoot.currentLootTile);
            ctx.camera.turnTo(LootItemsToLoot.currentLootTile);
        }
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
        return !lootManager.getLootTiles().isEmpty() && LootItemsToLoot.currentLootTile.equals(Tile.NIL);
    }
}
