package gutizia.tasks.loot;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.util.settings.LootSettings;
import org.powerbot.script.Tile;

import java.util.ArrayList;

import static gutizia.util.combat.PlayerCombat.playerCombat;
import static gutizia.util.managers.LootManager.lootManager;

public class LootItemsToLoot extends SuperTask {

    static int itemsToLootSizeThreshold = LootSettings.getNextRandomLootThreshold();
    static Tile currentLootTile = Tile.NIL;

    public LootItemsToLoot() {
        setTaskInfo(new TaskInfo(
                () -> (ctx.inventory.size() + lootManager.getLootList().size() >= 28 ||
                        lootManager.getLootList().size() >= itemsToLootSizeThreshold) && playerCombat.getTarget().isDead(),
                () -> ctx.inventory.isFull() || !playerCombat.getTarget().isDead() || lootManager.getLootList().isEmpty(),
                "LootItems",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new GetToLootTile());
        tasks.add(new NewLoot());
        return tasks;
    }
}
