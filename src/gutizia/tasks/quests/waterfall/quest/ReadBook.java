package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;
import gutizia.util.constants.Items;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.Item;

import java.util.concurrent.Callable;

import static gutizia.util.Inventory.inventory;
import static gutizia.util.overlay.TaskOverlay.taskOverlay;

public class ReadBook extends Task {

    @Override
    public void execute() {
        taskOverlay.setActiveTask("reading book");

        Item book = ctx.inventory.select().name("Book on baxtorian").poll();

        while (!ctx.widgets.component(26,3).visible()) {
            book.interact("Read");

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return !ctx.components.select().textContains("Book on Baxtorian").isEmpty();
                }
            },600,4);
        }
        ctx.movement.step(ctx.players.local().tile());
    }

    @Override
    public boolean activate() {
        return ctx.varpbits.varpbit(65) == 2 && // correct stage of quest
                !inventory.getItems().contains(Items.BOOK_ON_BAXTORIAN); // have book in inventory
    }
}
