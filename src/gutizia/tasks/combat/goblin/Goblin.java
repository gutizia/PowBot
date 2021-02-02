package gutizia.tasks.combat.goblin;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.tasks.bank.lumbridge.DepositItemsInLumbridge;
import gutizia.tasks.traverse.Traverse;
import gutizia.util.TraverseUtil;
import gutizia.util.combat.Target;
import gutizia.util.constants.Areas;
import gutizia.util.constants.Paths;
import gutizia.util.settings.CombatSettings;

import java.util.ArrayList;

import static gutizia.util.combat.TargetSelector.targetSelector;

public class Goblin extends SuperTask {

    public Goblin() {
        setTaskInfo(new TaskInfo(
                () -> targetSelector.getTargetToKill().equals(Target.GOBLINS) &&
                        (TraverseUtil.getClosestTileDistance(Paths.LUMBRIDGE_GOBLINS, ctx.players.local().tile()) < 10 ||
                                Areas.GOBLIN.containsOrIntersects(ctx.players.local()) ||
                Areas.LUMBRIDGE_COURTYARD.containsOrIntersects(ctx.players.local())),
                () -> !targetSelector.getTargetToKill().equals(Target.GOBLINS) &&
                        Areas.LUMBRIDGE_COURTYARD.contains(ctx.players.local()) ||
                        Areas.LUMBRIDGE_BANK.contains(ctx.players.local()),
                "Goblin",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new GetToCourtyard());
        tasks.add(new DepositItemsInLumbridge());
        tasks.add(new Traverse(() -> Areas.LUMBRIDGE_COURTYARD.contains(ctx.players.local()), Paths.LUMBRIDGE_GOBLINS, false));
        tasks.addAll(CombatSettings.getCombatTasks());
        return tasks;
    }
}
