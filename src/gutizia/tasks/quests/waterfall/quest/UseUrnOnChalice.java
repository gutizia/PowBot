package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;
import gutizia.util.Interact;
import gutizia.util.constants.Items;
import gutizia.util.constants.Widgets;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.GameObject;

import java.util.concurrent.Callable;

import static gutizia.util.overlay.TaskOverlay.taskOverlay;
import static gutizia.util.managers.ChatManager.chatManager;

public class UseUrnOnChalice extends Task {

    @Override
    public void execute() {
        taskOverlay.setActiveTask("place urn");

        GameObject chalice = ctx.objects.select(10).name("Chalice").at(new Tile(2604,9911,0)).poll();

        if (!ctx.inventory.select().name("Glarial's urn").isEmpty()) {
            Interact.use(ctx, ctx.inventory.poll());
            chalice.interact("Use",chalice.name());

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return !ctx.components.select(Widgets.CHAT_BOX_QUEST_ANNOUNCEMENT).textContains("click here to continue").visible().isEmpty();
                }
            },800,3);

            chatManager.finishDialog();

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return ctx.inventory.select().name("Glarial's urn").isEmpty();
                }
            },600,5);
        }
    }

    @Override
    public boolean activate() {
        return ctx.varpbits.varpbit(65) == 8 && // correct stage of quest
                WaterfallQuest.SECOND_CHALICE_AREA.containsOrIntersects(ctx.players.local()) &&
                !ctx.inventory.select().id(Items.GLARIALS_URN).isEmpty(); // still have urn
    }
}
