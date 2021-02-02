package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Activatable;
import gutizia.tasks.Task;
import gutizia.util.Interact;
import gutizia.util.managers.CameraManager;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.GameObject;

import java.util.concurrent.Callable;

import static gutizia.util.managers.CameraManager.cameraManager;
import static gutizia.util.overlay.TaskOverlay.taskOverlay;

public class BoardRaft extends Task {

    private final Area area = new Area(new Tile(2511,3492,0),new Tile(2514,3496,0));

    private Activatable activatable;

    BoardRaft(Activatable activatable) {
        this.activatable = activatable;
    }

    @Override
    public void execute() {
        taskOverlay.setActiveTask("boarding raft");

        ctx.movement.step(new Tile(2514,3495,0));

        GameObject gate = ctx.objects.select(20).within(area).name("Gate").nearest().poll();
        cameraManager.turnCamera(CameraManager.Direction.WEST);

        Interact.interact(ctx, gate, false, "Open", false);

        GameObject logRaft = ctx.objects.select(10).name("Log raft").nearest().poll();

        logRaft.interact("Board");
        Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return ctx.players.local().tile().y() < 3485;
                }
            },600,10);
    }

    @Override
    public boolean activate() {
        if (!WaterfallQuest.ALMERA_HOUSE.containsOrIntersects(ctx.players.local())) {
            return false;
        }
        return activatable.activate();

        /*
        // after have gotten glarials urn and amulet
        return (ctx.varpbits.varpbit(65) == 4 && // correct part of quest
        ctx.inventory.select().id(endingItems).size() == endingItems.length) && // have items to get into waterfall dungeon
        almeraHouse.containsOrIntersects(ctx.players.local()); // around almera's house
         */
    }
}
