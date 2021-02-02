package gutizia.tasks.combat.goblin;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.tasks.traverse.Traverse;
import gutizia.util.TraverseUtil;
import gutizia.util.combat.Target;
import gutizia.util.constants.Areas;
import gutizia.util.constants.Paths;

import java.util.ArrayList;

import static gutizia.util.combat.TargetSelector.targetSelector;

class GetToCourtyard extends SuperTask {

    GetToCourtyard() {
        setTaskInfo(new TaskInfo(
                () -> !Areas.LUMBRIDGE_COURTYARD.contains(ctx.players.local()) && ctx.inventory.isFull() ||
                        !targetSelector.getTargetToKill().equals(Target.GOBLINS),
                () -> Areas.LUMBRIDGE_COURTYARD.contains(ctx.players.local()),
                "GetToCourtyard",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Traverse(() -> TraverseUtil.getClosestTileDistance(Paths.LUMBRIDGE_GOBLINS, ctx.players.local().tile()) < 14 ||
                Areas.GOBLIN.containsOrIntersects(ctx.players.local()), Paths.LUMBRIDGE_GOBLINS, true));
        return tasks;
    }
}
