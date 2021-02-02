package gutizia.tasks.quests.cooks.assistant;

import gutizia.tasks.*;
import gutizia.util.InteractOptions;
import gutizia.util.constants.Interactives;
import gutizia.util.constants.Items;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;

import static gutizia.util.Inventory.inventory;

public class GetFlour extends SuperTask {

    private Tile millTile = new Tile(3165, 3306, 0);
    private Tile topOfMillTile = new Tile(3165, 3306, 2);

    public GetFlour(ClientContext ctx) {
        super(ctx);
        setTaskInfo(new TaskInfo(
                () -> ctx.inventory.select().id(Items.POT_OF_FLOUR).isEmpty() && !ctx.inventory.select().id(Items.POT).isEmpty(),
                () -> !ctx.inventory.select().id(Items.POT_OF_FLOUR).isEmpty(),
                "GetFlour",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();


        tasks.add(new WebWalk(ctx, () -> inventory.getItems().contains(Items.WHEAT), millTile));
        tasks.add(new Interact(ctx, () -> !topOfMillTile.matrix(ctx).reachable() &&
                new Tile(millTile.x(), millTile.y(), ctx.players.local().tile().floor()).matrix(ctx).reachable(), Interactives.LADDER,
                new InteractOptions("Climb-up", false, false, false,false, false)));
        //tasks.add(new );
        return tasks;
    }
}
