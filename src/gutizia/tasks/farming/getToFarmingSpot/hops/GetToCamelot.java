package gutizia.tasks.farming.getToFarmingSpot.hops;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.managers.TeleportManager;
import gutizia.util.resources.Traversing;
import gutizia.util.skills.farming.Farming;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.managers.TeleportManager.teleportManager;
import static gutizia.util.managers.WorldManager.worldManager;
import static gutizia.util.resources.Traversing.traversing;
import static gutizia.util.trackers.HopsRunTracker.hopsRunTracker;

public class GetToCamelot extends GetToFarmSpot {

    private final Tile[] pathToPatch = new Tile[] {new Tile(2749,3478,0),new Tile(2739,3482,0),new Tile(2737,3491,0),new Tile(2729,3500,0),
            new Tile(2720,3504,0),new Tile(2710,3506,0),new Tile(2697,3510,0),new Tile(2688,3515,0),new Tile(2678,3521,0),
            new Tile(2672,3523,0)};

    public GetToCamelot(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        traversing.walkPath(pathToPatch,5);
    }

    @Override
    public boolean activate() {
        return traversing.isInRange(pathToPatch,8) || teleportManager.isInCamelotTeleportArea();
    }
}
