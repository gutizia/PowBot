package gutizia.tasks.quests.cooks.assistant;

import gutizia.tasks.*;
import gutizia.util.InteractOptions;
import gutizia.util.constants.Interactives;
import gutizia.util.constants.Items;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;

import static gutizia.util.Inventory.inventory;

public class GetWheat extends SuperTask {

    private Tile wheatTile = new Tile(3161, 3294, 0);

    public GetWheat(ClientContext ctx) {
        super(ctx);
        setTaskInfo(new TaskInfo(
                () -> !inventory.getItems().contains(Items.WHEAT),
                () -> inventory.getItems().contains(Items.WHEAT),
                "GetWheat",
                false
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new WebWalk(ctx, () -> !wheatTile.matrix(ctx).reachable() && wheatTile.distanceTo(ctx.players.local()) > 8, wheatTile));
        tasks.add(new Interact(ctx, () -> !inventory.getItems().contains(Items.WHEAT) && wheatTile.matrix(ctx).reachable(), Interactives.WHEAT,
                new InteractOptions("Pick", false, false, false, false, false)));
        return tasks;
    }
}
