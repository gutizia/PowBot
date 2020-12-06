package gutizia.tasks.combat.chicken;

import gutizia.tasks.Interact;
import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.tasks.traverse.Traverse;
import gutizia.util.InteractOptions;
import gutizia.util.TraverseUtil;
import gutizia.util.combat.Target;
import gutizia.util.constants.Areas;
import gutizia.util.constants.Interactives;
import gutizia.util.constants.Paths;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import java.util.ArrayList;

import static gutizia.util.combat.TargetSelector.targetSelector;

class GetToCourtyard extends SuperTask {

    GetToCourtyard(ClientContext ctx) {
        super(ctx);
        setTaskInfo(new TaskInfo(
                () -> !Areas.LUMBRIDGE_COURTYARD.contains(ctx.players.local()) && ctx.inventory.isFull() ||
                        !targetSelector.getTargetToKill(ctx).equals(Target.CHICKENS),
                () -> Areas.LUMBRIDGE_COURTYARD.contains(ctx.players.local()),
                "GetToCourtyard",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Interact(ctx, () -> Areas.LUMBRIDGE_CHICKEN_PEN.contains(ctx.players.local()), Interactives.LUMBRIDGE_CHICKEN_PEN_GATE,
                new InteractOptions("Open", true, false, false, false, false)));
        tasks.add(new Traverse(ctx, () -> TraverseUtil.getClosestTileDistance(Paths.LUMBRIDGE_CHICKEN_PEN, ctx.players.local().tile()) < 14 &&
                !gutizia.util.Interact.actionContains(((GameObject)Interactives.LUMBRIDGE_CHICKEN_PEN_GATE.getInteractive()).actions(), "Open"), Paths.LUMBRIDGE_CHICKEN_PEN, true));
        return tasks;
    }
}
