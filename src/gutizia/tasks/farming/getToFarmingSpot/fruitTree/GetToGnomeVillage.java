package gutizia.tasks.farming.getToFarmingSpot.fruitTree;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.skills.farming.Farming;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.managers.TeleportManager.teleportManager;
import static gutizia.util.resources.Traversing.traversing;


public class GetToGnomeVillage extends GetToFarmSpot {
    private Tile[] pathToPatch = new Tile[] {new Tile(2453,3089,0),new Tile(2455,3076,0),new Tile(2469,3074,0),new Tile(2484,3080,0),new Tile(2497,3087,0),
            new Tile(2501,3099,0),new Tile(2496,3111,0),new Tile(2496,3126,0),new Tile(2493,3139,0),new Tile(2497,3153,0),new Tile(2494,3166,0),
            new Tile(2488,3178,0)};

    public GetToGnomeVillage(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        traversing.walkPath(pathToPatch,4);
    }

    @Override
    public boolean activate() {
        return teleportManager.isInCastleWars() || traversing.isInRange(pathToPatch,8);
    }

}