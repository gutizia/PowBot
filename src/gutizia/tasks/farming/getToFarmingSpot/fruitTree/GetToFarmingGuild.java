package gutizia.tasks.farming.getToFarmingSpot.fruitTree;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.skills.farming.Farming;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.managers.TeleportManager.teleportManager;
import static gutizia.util.resources.Traversing.traversing;

public class GetToFarmingGuild extends GetToFarmSpot {
    private final Tile[] pathToPatch = new Tile[] {new Tile(1249, 3732, 0), new Tile(1249, 3742, 0), new Tile(1243, 3756, 0)};

    public GetToFarmingGuild(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        traversing.walkPath(pathToPatch, 6);
    }

    @Override
    public boolean activate() {
        return teleportManager.isInFarmingTeleportAreaOutside() || teleportManager.isInFarmingTeleportAreaInside() ||
                traversing.isInRange(pathToPatch,8);
    }

}