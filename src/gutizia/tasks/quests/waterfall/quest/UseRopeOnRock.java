package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;
import gutizia.util.Interact;
import gutizia.util.managers.CameraManager;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;

import static gutizia.util.managers.CameraManager.cameraManager;
import static gutizia.util.overlay.TaskOverlay.taskOverlay;

public class UseRopeOnRock extends Task {

    @Override
    public void execute() {
        taskOverlay.setActiveTask("using rope on rock");

        ctx.movement.step(new Tile(2512,3476,0));

        cameraManager.turnCamera(CameraManager.Direction.SOUTH);

        GameObject gameObject = ctx.objects.select(15).name("Rock").at(new Tile(2512,3468,0)).poll();
        Item rope = ctx.inventory.select().name("Rope").poll();

        if (!gameObject.inViewport()) {
            ctx.camera.turnTo(gameObject);
        }

        Interact.use(ctx, rope);
        gameObject.interact(false,"Use",gameObject.name());

        Condition.wait(() -> !WaterfallQuest.UPPER_RIVER_ISLAND.containsOrIntersects(ctx.players.local()), 600, 6);
    }

    @Override
    public boolean activate() {
        return ctx.varpbits.varpbit(WaterfallQuest.VARBIT) == 4 && // correct stage of quest
                WaterfallQuest.UPPER_RIVER_ISLAND.containsOrIntersects(ctx.players.local()); // at small island
    }
}
