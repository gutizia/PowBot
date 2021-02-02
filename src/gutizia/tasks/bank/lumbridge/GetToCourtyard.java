package gutizia.tasks.bank.lumbridge;

import gutizia.tasks.Interact;
import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.tasks.traverse.ClickOnMinimap;
import gutizia.util.InteractOptions;
import gutizia.util.constants.Areas;
import gutizia.util.constants.Interactives;
import org.powerbot.script.Tile;

import java.util.ArrayList;

class GetToCourtyard extends SuperTask {

    private Tile tile = new Tile(3221, 3219, 0);

    GetToCourtyard() {
        setTaskInfo(new TaskInfo(
                () -> Areas.LUMBRIDGE_BANK.containsOrIntersects(ctx.players.local()),
                () -> Areas.LUMBRIDGE_COURTYARD.containsOrIntersects(ctx.players.local()),
                "GetToCourtyard",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Interact(
                ctx, () -> new Tile(3210, 3219, ctx.players.local().tile().floor()).distanceTo(ctx.players.local()) < 25 &&
                ctx.players.local().tile().floor() != 0, Interactives.STAIRCASE,
                new InteractOptions("Climb-down", false, false, true, false, false)
        ));
        tasks.add(new ClickOnMinimap(ctx, () -> tile.matrix(ctx).reachable() && !ctx.players.local().inMotion(), tile));
        return tasks;
    }
}
