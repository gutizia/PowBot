package gutizia.tasks.farming.getToFarmingSpot.herbs;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.Interact;
import gutizia.util.constants.Paths;
import gutizia.util.managers.CameraManager;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import static gutizia.util.managers.TeleportManager.teleportManager;
import static gutizia.util.resources.Traversing.traversing;

public class GetToHosidius extends GetToFarmSpot {
    private final Tile doorTile = new Tile(1249, 3723, 0);
    private final int[] bounds = {-68, 104, -332, -36, 16, 40};

    public GetToHosidius(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        if (teleportManager.isInFarmingTeleportAreaInside()) {
            GameObject door = ctx.objects.select(doorTile, 3).at(doorTile).name("Door").poll();
            if (Interact.interact(ctx, door, false,"Open", false)) {
                Condition.wait(() -> ctx.players.local().tile().y() <= 3722, 600, 4);
            }
        }

        if (teleportManager.isInFarmingTeleportAreaOutside()) {
            traversing.walkPath(Paths.HOSIDIUS_HERB_PATCH, 5);
        }
    }

    @Override
    public boolean activate() {
        return teleportManager.isInHosidiusTeleportArea() || traversing.isInRange(Paths.HOSIDIUS_HERB_PATCH,8);
    }
}