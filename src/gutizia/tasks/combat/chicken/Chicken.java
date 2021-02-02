package gutizia.tasks.combat.chicken;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.tasks.bank.lumbridge.DepositItemsInLumbridge;
import gutizia.util.TraverseUtil;
import gutizia.util.combat.Target;
import gutizia.util.constants.Areas;
import gutizia.util.constants.Paths;
import gutizia.util.settings.CombatSettings;

import java.util.ArrayList;

import static gutizia.util.combat.TargetSelector.targetSelector;

public class Chicken extends SuperTask {

    public Chicken() {
        setTaskInfo(new TaskInfo(
                () -> targetSelector.getTargetToKill().equals(Target.CHICKENS) &&
                        (TraverseUtil.getClosestTileDistance(Paths.LUMBRIDGE_CHICKEN_PEN, ctx.players.local().tile()) < 10 ||
                                Areas.LUMBRIDGE_CHICKEN_PEN.containsOrIntersects(ctx.players.local())),
                () -> !targetSelector.getTargetToKill().equals(Target.CHICKENS) &&
                        Areas.LUMBRIDGE_COURTYARD.contains(ctx.players.local()) ||
                        Areas.LUMBRIDGE_BANK.contains(ctx.players.local()),
                "Chicken",
                false
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new GetToCourtyard());
        tasks.add(new DepositItemsInLumbridge());
        tasks.add(new GetToChickens());
        tasks.addAll(CombatSettings.getCombatTasks());
        return tasks;
    }
}
