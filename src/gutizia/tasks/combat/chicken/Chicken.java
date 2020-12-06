package gutizia.tasks.combat.chicken;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.util.TraverseUtil;
import gutizia.util.combat.Target;
import gutizia.util.constants.Areas;
import gutizia.util.constants.Paths;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;

import static gutizia.util.combat.TargetSelector.targetSelector;

public class Chicken extends SuperTask {

    public Chicken(ClientContext ctx) {
        super(ctx);
        setTaskInfo(new TaskInfo(
                () -> targetSelector.getTargetToKill(ctx).equals(Target.CHICKENS) &&
                        TraverseUtil.getClosestTileDistance(Paths.LUMBRIDGE_CHICKEN_PEN, ctx.players.local().tile()) < 10,
                () -> !targetSelector.getTargetToKill(ctx).equals(Target.CHICKENS) &&
                        Areas.LUMBRIDGE_COURTYARD.contains(ctx.players.local()) ||
                        Areas.LUMBRIDGE_BANK.contains(ctx.players.local()),
                "Chicken",
                false
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new GetToChickens(ctx));
        tasks.add(new GetToCourtyard(ctx));
        // kill chicken
        // loot
        return tasks;
    }
}
