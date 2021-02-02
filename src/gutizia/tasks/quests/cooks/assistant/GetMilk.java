package gutizia.tasks.quests.cooks.assistant;

import gutizia.tasks.*;
import gutizia.util.InteractOptions;
import gutizia.util.constants.Interactives;
import gutizia.util.constants.Items;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;

class GetMilk extends SuperTask {

    private Tile tile = new Tile(3255, 3272, 0);

    GetMilk(ClientContext ctx) {
        super(ctx);
        setTaskInfo(new TaskInfo(
                () -> ctx.inventory.select().id(Items.BUCKET_OF_MILK).isEmpty() && !ctx.inventory.select().id(Items.EMPTY_BUCKET).isEmpty(),
                () -> !ctx.inventory.select().id(Items.BUCKET_OF_MILK).isEmpty(),
                "GetMilk",
                false
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new WebWalk(ctx, () -> !tile.matrix(ctx).reachable() || tile.distanceTo(ctx.players.local()) > 6, tile));
        tasks.add(new Interact(ctx, () -> tile.matrix(ctx).reachable() && !ctx.npcs.select().name("Dairy cow").isEmpty(), Interactives.DAIRY_COW,
                new InteractOptions("Milk", true, true, true, false, false)));

        return tasks;
    }
}
