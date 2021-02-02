package gutizia.tasks.farming.getToFarmingSpot.fruitTree;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.skills.farming.Farming;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.managers.TeleportManager.teleportManager;
import static gutizia.util.resources.Traversing.traversing;

public class GetToCatherby extends GetToFarmSpot {
    private Tile[] pathToPatch = new Tile[] {new Tile(2766,3474,0),new Tile(2779,3473,0),
            new Tile(2793,3472,0),new Tile(2803,3471,0),new Tile(2812,3470,0),new Tile(2816,3466,0),new Tile(2825,3462,0),
            new Tile(2829,3452,0),new Tile(2828,3441,0),new Tile(2839,3437,0),new Tile(2850,3432,0),new Tile(2857,3433,0)};

    public GetToCatherby(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        traversing.walkPath(pathToPatch,4);
    }

    @Override
    public boolean activate() {
        return teleportManager.isInCamelotTeleportArea() || traversing.isInRange(pathToPatch,8);
    }

}