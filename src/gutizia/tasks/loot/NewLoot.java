package gutizia.tasks.loot;

import gutizia.tasks.Task;
import gutizia.util.settings.LootSettings;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.GroundItem;

import java.util.ArrayList;

import static gutizia.util.managers.LootManager.lootManager;
import static gutizia.util.overlay.TaskOverlay.taskOverlay;

public class NewLoot extends Task {

    @Override
    public void execute() {
        taskOverlay.setActiveTask("Loot");
        ArrayList<GroundItem> toRemove = new ArrayList<>();
        ArrayList<GroundItem> groundItems = lootManager.getGroundItemsOnTile(LootItemsToLoot.currentLootTile);
        for (GroundItem groundItem : groundItems) {
            if (groundItem.interact(false,"Take", groundItem.name())) {
                toRemove.add(groundItem);
                Condition.wait(() -> !groundItem.valid(), 600, 16);
            }
        }
        for (GroundItem groundItem : toRemove) {
            lootManager.removeItemToLoot(groundItem);
        }
        if (toRemove.equals(groundItems)) {
            lootManager.removeLootTile(LootItemsToLoot.currentLootTile);
            LootItemsToLoot.currentLootTile = Tile.NIL;
            LootItemsToLoot.itemsToLootSizeThreshold = LootSettings.getNextRandomLootThreshold();
        }
    }

    @Override
    public boolean activate() {
        return !LootItemsToLoot.currentLootTile.equals(Tile.NIL);
    }
}
