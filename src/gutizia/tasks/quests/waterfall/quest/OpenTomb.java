package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;
import gutizia.util.Interact;
import gutizia.util.constants.Items;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;

import java.util.concurrent.Callable;

import static gutizia.util.overlay.TaskOverlay.taskOverlay;

public class OpenTomb extends Task {

    @Override
    public void execute() {
        taskOverlay.setActiveTask("Open Tomb");

        final GameObject tombstone = ctx.objects.select(10).name("Glarial's Tombstone").nearest().poll();
        Item pebble = ctx.inventory.select().id(Items.PEBBLE).poll();

        Interact.use(ctx, pebble);
        tombstone.interact("Use");

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return ctx.players.local().tile().distanceTo(tombstone) > 10;
            }
        },900,10);
    }

    @Override
    public boolean activate() {
        return !ctx.objects.select(15).name("Glarial's Tombstone").isEmpty() && // within at least 15 tiles around
                !ctx.inventory.select().id(Items.PEBBLE).isEmpty() && // have pebble
                ctx.inventory.select().id(Items.GLARIALS_AMULET, Items.GLARIALS_URN).size() < 2; // don't have both glarials items
    }
}
