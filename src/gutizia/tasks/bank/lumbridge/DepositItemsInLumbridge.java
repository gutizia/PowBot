package gutizia.tasks.bank.lumbridge;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.tasks.bank.WithdrawItems;
import gutizia.tasks.traverse.Traverse;
import gutizia.util.Items;
import gutizia.util.constants.Areas;
import gutizia.util.constants.Paths;
import gutizia.util.settings.CombatSettings;

import java.util.ArrayList;

public class DepositItemsInLumbridge extends SuperTask {

    public DepositItemsInLumbridge() {
        setTaskInfo(new TaskInfo(
                () -> (Areas.LUMBRIDGE_BANK.containsOrIntersects(ctx.players.local()) && !ctx.inventory.isEmpty())
                        || (Areas.LUMBRIDGE_COURTYARD.containsOrIntersects(ctx.players.local()) && ctx.inventory.isFull()),
                () -> Areas.LUMBRIDGE_COURTYARD.containsOrIntersects(ctx.players.local()) && ctx.inventory.isEmpty(),
                "DepositItems",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new GetToLumbridgeBank());
        tasks.add(new WithdrawItems(Items.EMPTY_INVENTORY, true));
        tasks.add(new GetToCourtyard());
        tasks.add(new Traverse(() -> Areas.LUMBRIDGE_COURTYARD.contains(ctx.players.local()), Paths.LUMBRIDGE_GOBLINS, false));
        tasks.addAll(CombatSettings.getCombatTasks());
        return tasks;
    }
}
