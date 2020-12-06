package gutizia.tasks.farming.getToFarmingSpot.tree;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class GetToFarmingGuildTree extends GetToFarmSpot {
    private final Tile[] pathToPatch = new Tile[] {new Tile(1245, 3730, 0), new Tile(1236, 3733, 0)};

    public GetToFarmingGuildTree(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        traversing.walkPath(pathToPatch,5);
    }

    @Override
    public boolean activate() {
        return !PatchAreas.farmingGuildTreeArea.containsOrIntersects(ctx.players.local());
    }
}
