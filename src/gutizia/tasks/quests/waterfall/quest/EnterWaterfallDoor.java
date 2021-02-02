package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;
import gutizia.util.constants.Items;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;

import java.util.concurrent.Callable;

import static gutizia.util.managers.CameraManager.cameraManager;
import static gutizia.util.overlay.TaskOverlay.taskOverlay;

public class EnterWaterfallDoor extends Task {

    @Override
    public void execute() {
        taskOverlay.setActiveTask("opening door");

        if (!ctx.inventory.select().id(Items.GLARIALS_AMULET).isEmpty()) {
            ctx.inventory.poll().click("Equip");
            Condition.wait(() -> ctx.inventory.select().id(Items.GLARIALS_AMULET).isEmpty(), 600, 4);
        }

        if (ctx.inventory.select().id(Items.GLARIALS_AMULET).isEmpty()) {
            cameraManager.turnCamera(320, 30, 94, 99);

            ctx.objects.select(10).name("Door").nearest().poll().interact("Open");
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return !ctx.players.local().tile().equals(new Tile(2511, 3463, 0));
                }
            }, 600, 5);
        }
    }

    @Override
    public boolean activate() {
        return ctx.players.local().tile().equals(new Tile(2511,3463,0));
    }
}
