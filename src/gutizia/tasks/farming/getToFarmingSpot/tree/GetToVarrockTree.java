package gutizia.tasks.farming.getToFarmingSpot.tree;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.resources.Traversing.traversing;


public class GetToVarrockTree extends GetToFarmSpot {
    private final Tile[] pathToPatch = new Tile[] {new Tile(3213, 3433, 0), new Tile(3219, 3445, 0), new Tile(3224, 3452, 0), new Tile(3228, 3457, 0)};

    public GetToVarrockTree(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        traversing.walkPath(pathToPatch, 5);
    }

    @Override
    public boolean activate() {
        return !PatchAreas.varrockTreeArea.containsOrIntersects(ctx.players.local());
    }
}
