package gutizia.tasks.farming.getToFarmingSpot.herbs;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.constants.Paths;
import org.powerbot.script.rt4.ClientContext;

public class GetToCatherby extends GetToFarmSpot {

    public GetToCatherby(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        traversing.walkPath(Paths.CATHERBY_HERB_PATCH, 5);
    }

    @Override
    public boolean activate() {
        return teleportManager.isInCamelotTeleportArea() || traversing.isInRange(Paths.CATHERBY_HERB_PATCH,8);
    }
}