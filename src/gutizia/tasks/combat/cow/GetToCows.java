package gutizia.tasks.combat.cow;

import gutizia.tasks.*;
import gutizia.tasks.traverse.ClickOnMinimap;
import gutizia.tasks.traverse.Traverse;
import gutizia.util.InteractOptions;
import gutizia.util.constants.Areas;
import gutizia.util.constants.Interactives;
import gutizia.util.constants.Paths;
import gutizia.util.constants.Tiles;
import org.powerbot.script.Tile;

import java.util.ArrayList;

public class GetToCows extends SuperTask {

    private final static Tile TILE_INSIDE_PEN = new Tile(3258, 3268, 0);

    public GetToCows() {
        setTaskInfo(new TaskInfo(
                () -> !Areas.LUMBRIDGE_COW_PEN.containsOrIntersects(ctx.players.local()),
                () -> Areas.LUMBRIDGE_COW_PEN.containsOrIntersects(ctx.players.local()),
                "GetToCows",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        if (Areas.LUMBRIDGE_COURTYARD.contains(ctx.players.local())) {
            tasks.add(new Traverse(() -> Paths.LUMBRIDGE_COW_PEN[Paths.LUMBRIDGE_COW_PEN.length -1].distanceTo(ctx.players.local()) > 6,
                    Paths.LUMBRIDGE_COW_PEN, false));
        } else {
            tasks.add(new WebWalk(ctx, () -> Paths.LUMBRIDGE_COW_PEN[Paths.LUMBRIDGE_COW_PEN.length -1].distanceTo(ctx.players.local()) > 6,
                    Paths.LUMBRIDGE_COW_PEN[Paths.LUMBRIDGE_COW_PEN.length -1]));
        }
        tasks.add(new Interact(ctx, () -> !Areas.LUMBRIDGE_COW_PEN.contains(ctx.players.local()) &&
                Tiles.LUMBRIDGE_COW_PEN_GATE.distanceTo(ctx.players.local()) > Interactives.GATE.getInteractRange(), Interactives.GATE,
                new InteractOptions("Open", true, false, false, false, false)));
        tasks.add(new ClickOnMinimap(ctx, () -> TILE_INSIDE_PEN.distanceTo(ctx.players.local()) > 4, TILE_INSIDE_PEN));
        return tasks;
    }
}
