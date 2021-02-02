package gutizia.tasks.farming.getToFarmingSpot.tree;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.resources.Traversing.traversing;

public class GetToGnomeStrongholdTree extends GetToFarmSpot {
    private final Tile treeTeleportSpot = new Tile(2461, 3444, 0);
    private final Tile[] pathToPatchGate = new Tile[] {new Tile(2456, 3396, 0), new Tile(2449, 3404, 0),
            new Tile(2442, 3409, 0), new Tile(2437, 3417, 0)};
    private final Tile[] pathToPatchTree = new Tile[] {new Tile(2459, 3434, 0), new Tile(2452, 3429, 0),
            new Tile(2446, 3421, 0), new Tile(2437, 3417, 0)};

    public GetToGnomeStrongholdTree(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        if (ctx.players.local().tile().equals(treeTeleportSpot)) {
            traversing.walkPath(pathToPatchTree,4);
            return;
        }
        traversing.walkPath(pathToPatchGate,4);
    }

    @Override
    public boolean activate() {
        return !PatchAreas.gnomeStrongholdTreeArea.containsOrIntersects(ctx.players.local());
    }

}
