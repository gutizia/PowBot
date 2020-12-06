package gutizia.tasks.farming.getToFarmingSpot.tree;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class GetToTaverlyTree extends GetToFarmSpot {
    private final Tile[] pathToPatch = new Tile[] {new Tile(2898, 3544, 0), new Tile(2898, 3534, 0), new Tile(2907, 3523, 0),
            new Tile(2906, 3514, 0), new Tile(2906, 3501, 0), new Tile(2906, 3489, 0), new Tile(2901, 3480 , 0),
            new Tile(2898, 3470, 0), new Tile(2897, 3456, 0), new Tile(2909, 3455, 0), new Tile(2920, 3453, 0),
            new Tile(2929, 3446, 0), new Tile(2935, 3440, 0)};

    public GetToTaverlyTree(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        traversing.walkPath(pathToPatch, 5);
    }

    @Override
    public boolean activate() {
        return !PatchAreas.taverlyTreeArea.containsOrIntersects(ctx.players.local());
    }
}
