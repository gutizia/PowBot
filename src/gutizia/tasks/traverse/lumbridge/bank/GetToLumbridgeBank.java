package gutizia.tasks.traverse.lumbridge.bank;

import gutizia.tasks.Interact;
import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.tasks.traverse.ClickOnMinimap;
import gutizia.util.InteractOptions;
import gutizia.util.constants.Areas;
import gutizia.util.constants.Interactives;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;

public class GetToLumbridgeBank extends SuperTask {

    public GetToLumbridgeBank(ClientContext ctx) {
        super(ctx);
        setTaskInfo(new TaskInfo(
                () -> Areas.LUMBRIDGE_COURTYARD.contains(ctx.players.local()),
                () -> Areas.LUMBRIDGE_BANK.contains(ctx.players.local()),
                "GetToLumbridgeBank",
                false
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Interact(
                ctx, () -> new Tile(3210, 3219, ctx.players.local().tile().floor()).distanceTo(ctx.players.local()) < 25 &&
                ctx.players.local().tile().floor() != 2, Interactives.STAIRCASE,
                new InteractOptions("Climb-up", false, false, true, false, false)
        ));
        tasks.add(new ClickOnMinimap(ctx, () -> new Tile(3208, 3220, 2).matrix(ctx).reachable() && !ctx.players.local().inMotion(), new Tile(3208, 3220, 2)));
        return tasks;
    }
}
