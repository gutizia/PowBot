package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;
import gutizia.util.combat.Combat;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.GameObject;

import java.util.concurrent.Callable;

import static gutizia.util.overlay.TaskOverlay.taskOverlay;

public class GetKey extends Task {

    private final int key = 298;

    private Combat combat = new Combat(ctx, null);

    @Override
    public void execute() {
        taskOverlay.setActiveTask("getting key");
        final Area doorArea = new Area(new Tile(2579,9873,0),new Tile(2585,9880));

        combat.protectFromMelee(true);

        while (ctx.players.local().tile().x() < 2582) {
            if (!ctx.objects.select(20).within(doorArea).name("Large door").action("Open").isEmpty()) { // if doors are closed
                LOGGER.info("key door is closed");

                final GameObject door = ctx.objects.poll();

                ctx.movement.step(new Tile(2575,9875,0));

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
                LOGGER.info("key door is open");

                ctx.movement.step(new Tile(2590,9885,0));

                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() {
                        return !ctx.players.local().inMotion() || !ctx.objects.select(10).within(doorArea).name("Large door").action("Open").isEmpty();
                    }
                },300,20);
            }
        }

        if (ctx.combat.healthPercent() < 70) {
            LOGGER.info("drinking whine");
            combat.drinkWhine();
        }

        GameObject crate = ctx.objects.select(20).name("Crate").at(new Tile(2589,9888,0)).poll();

        while (ctx.inventory.select().id(key).isEmpty()) {
            if (!crate.inViewport()) {
                ctx.camera.turnTo(crate);
            }

            crate.interact("Search",crate.name());
            Condition.sleep(Random.nextInt(900,1400));
        }

        if (ctx.combat.healthPercent() < 70) {
            LOGGER.info("drinking whine");
            combat.drinkWhine();
        }
    }

    @Override
    public boolean activate() {
        return ctx.inventory.select().id(key).isEmpty() && // dont have key in inventory
                ctx.players.local().tile().distanceTo(new Tile(2575,9862,0)) <= 20; // if 20 or less tiles away from entrance to waterfall dungeon
    }
}
