package gutizia.tasks.farming.getToFarmingSpot.herbs;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.constants.Paths;
import org.powerbot.script.rt4.ClientContext;

public class GetToArdougne extends GetToFarmSpot {

    public GetToArdougne(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        traversing.walkPath(Paths.ARDOUNGE_HERB_PATCH, 5);
    }

    @Override
    public boolean activate() {
        return traversing.isInRange(Paths.ARDOUNGE_HERB_PATCH,8) || teleportManager.isInFishingGuildTeleportArea();
    }
}
