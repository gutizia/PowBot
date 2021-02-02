package gutizia.tasks.combat.chicken;

import gutizia.tasks.*;
import gutizia.tasks.traverse.ClickOnMinimap;
import gutizia.tasks.traverse.Traverse;
import gutizia.util.InteractOptions;
import gutizia.util.TraverseUtil;
import gutizia.util.constants.Areas;
import gutizia.util.constants.Interactives;
import gutizia.util.constants.Paths;
import gutizia.util.constants.Tiles;
import org.powerbot.script.Tile;

import java.util.ArrayList;

public class GetToChickens extends SuperTask {

    private final static Tile TILE = new Tile(3230, 3298, 0);

    public GetToChickens() {
        setTaskInfo(new TaskInfo(
                () -> !Areas.LUMBRIDGE_CHICKEN_PEN.contains(ctx.players.local()),
                () -> Areas.LUMBRIDGE_CHICKEN_PEN.contains(ctx.players.local()),
                "GetToChickens",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Traverse(() -> TraverseUtil.getClosestTileDistance(Paths.LUMBRIDGE_CHICKEN_PEN, ctx.players.local().tile()) < 10 &&
                !Areas.LUMBRIDGE_CHICKEN_PEN.containsOrIntersects(ctx.players.local()) &&
                Paths.LUMBRIDGE_CHICKEN_PEN[Paths.LUMBRIDGE_CHICKEN_PEN.length - 1].distanceTo(ctx.players.local()) > 8,
                Paths.LUMBRIDGE_CHICKEN_PEN, false));
        tasks.add(new Interact(ctx, () -> !Areas.LUMBRIDGE_CHICKEN_PEN.contains(ctx.players.local()) &&
                Tiles.LUMBRIDGE_CHICKEN_PEN_GATE.distanceTo(ctx.players.local()) <= Interactives.GATE.getInteractRange(), Interactives.GATE,
                new InteractOptions("Open", true, false, false, false, false)));
        tasks.add(new ClickOnMinimap(ctx, () -> TILE.distanceTo(ctx.players.local()) > 4 &&
                !gutizia.util.Interact.actionContains(ctx.objects.select(10).at(new Tile(3236, 3295, 0)).name("Gate").poll().actions(), "Open"), TILE));
        return tasks;
    }
}
