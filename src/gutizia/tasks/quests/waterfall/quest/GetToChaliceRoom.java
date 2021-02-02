package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;
import gutizia.util.combat.Combat;
import gutizia.util.managers.CameraManager;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.GameObject;

import java.util.concurrent.Callable;

import static gutizia.util.managers.CameraManager.cameraManager;
import static gutizia.util.overlay.TaskOverlay.taskOverlay;
import static gutizia.util.resources.Traversing.traversing;

public class GetToChaliceRoom extends Task {

    private Tile[] pathToDoor = new Tile[] {new Tile(2576,9875,0),new Tile(2564,9880,0)};
    private final int[] closedDoorBounds = new int[] {12, 120, -216, -8, 136, 116};
    private final Area doorArea = new Area(new Tile(2562,9879,0),new Tile(2568,9884,0));
    private final Area betweenDoors = new Area(new Tile(2566,9901,0),new Tile(2569,9894,0));
    private final int[] doorBounds = new int[] {12, 124, -224, 0, 116, 104};
    private final int key = 298;

    private Combat combat = new Combat(ctx, null);

    @Override
    public void execute() {
        taskOverlay.setActiveTask("get to chalice room");
        traversing.walkPath(pathToDoor,6);

        if (ctx.combat.healthPercent() < 60) {
            LOGGER.info("drinking whine");
            combat.drinkWhine();
        }

        cameraManager.turnCamera(CameraManager.Direction.NORTH);

        while (ctx.players.local().tile().y() <= 9893) {
            if (!ctx.objects.select(20).within(doorArea).name("Large door").action("Open").isEmpty() && ctx.players.local().tile().y() < 9882) { // if doors are closed and not passed door

                LOGGER.info("fire giant door is closed");

                final GameObject door = ctx.objects.poll();
                door.bounds(closedDoorBounds);

                if (!door.inViewport()) {
                    ctx.camera.turnTo(door);
                }

                door.interact(false,"Open",door.name());

                Condition.wait(new Callable<Boolean>() { // wait until door is opened
                    @Override
                    public Boolean call() {
                        return ctx.objects.select(15).within(doorArea).name("Large door").action("Open").isEmpty();
                    }
                },300,20);

            } else {
                LOGGER.info("fire giant door is open");

                GameObject door = ctx.objects.select(16).name("Door").at(new Tile(2568,9893,0)).poll();

                if (door.tile().distanceTo(ctx.players.local()) > 8) {
                    ctx.movement.step(door);
                }

                door.bounds(doorBounds);

                if (!door.inViewport()) {
                    ctx.camera.turnTo(door);
                }

                door.interact("Open",door.name());

                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() {
                        return ctx.players.local().tile().y() > 9893;
                    }
                },300,20);
            }
        }

        LOGGER.info("inside between doors");
        combat.protectFromMelee(false);

        if (ctx.combat.healthPercent() < 60) {
            LOGGER.info("drinking whine");
            combat.drinkWhine();
        }

        GameObject door = ctx.objects.select(10).name("Door").at(new Tile(2566,9901,0)).poll();

        door.bounds(doorBounds);

        if (!door.inViewport()) {
            ctx.camera.turnTo(door);
        }

        while (betweenDoors.contains(ctx.players.local())) {
            door.interact("Open", door.name());
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return betweenDoors.contains(ctx.players.local());
                }
            }, 600, 5);

            if (ctx.combat.healthPercent() < 60) {
                LOGGER.info("drinking whine");
                combat.drinkWhine();
            }
        }
    }

    @Override
    public boolean activate() {
        return !ctx.inventory.select().id(key).isEmpty() && // have key
                WaterfallQuest.WATERFALL_DUNGEON.containsOrIntersects(ctx.players.local()); // and in waterfall dungeon
    }
}
