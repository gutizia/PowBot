package gutizia.tasks.farming.getToFarmingSpot.tree;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class GetToLumbridgeTree extends GetToFarmSpot {
    private final Tile[] pathToPatch = new Tile[] {new Tile(3214, 3216, 0), new Tile(3206, 3214, 0), new Tile(3197, 3219, 0), new Tile(3194, 3229, 0)};

    public GetToLumbridgeTree(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        traversing.walkPath(pathToPatch, 5);
    }

    @Override
    public boolean activate() {
        return !PatchAreas.lumbridgeTreeArea.containsOrIntersects(ctx.players.local());
    }
}
