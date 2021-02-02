package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;
import gutizia.util.Interact;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.GameObject;

import static gutizia.util.overlay.TaskOverlay.taskOverlay;

public class SwimDownRiver extends Task {

    @Override
    public void execute() {
        taskOverlay.setActiveTask("swim down river");
        GameObject river = ctx.objects.select(10).name("River").nearest().poll();
        Interact.interact(ctx, river, true, "Swim", false);
        Condition.wait(() -> ctx.players.local().tile().y() < 3470, 600, 13);
    }

    @Override
    public boolean activate() {
        return WaterfallQuest.UPPER_RIVER_ISLAND.containsOrIntersects(ctx.players.local()); // at small island
    }
}
