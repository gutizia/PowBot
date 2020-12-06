package gutizia.tasks.farming.getToFarmingSpot.herbs;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.constants.Paths;
import org.powerbot.script.rt4.ClientContext;

public class GetToFalador extends GetToFarmSpot {

    public GetToFalador(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        traversing.walkPath(Paths.FALADOR_HERB_PATCH, 5);
    }

    @Override
    public boolean activate() {
        return teleportManager.isInDraynorTeleportArea() || traversing.isInRange(Paths.FALADOR_HERB_PATCH,8);
    }
}