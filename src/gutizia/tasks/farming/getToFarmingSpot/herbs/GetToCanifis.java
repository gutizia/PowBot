package gutizia.tasks.farming.getToFarmingSpot.herbs;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.skills.farming.Farming;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.trackers.HerbRunTracker.herbRunTracker;

public class GetToCanifis extends GetToFarmSpot {

    public GetToCanifis(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        // TODO add route through canifis and through ectophial
    }

    @Override
    public boolean activate() {
        return herbRunTracker.getOrder().get(0).equals(Farming.FarmingSpot.CANIFIS_HAF) && // next spot is Canifis herb
                !PatchAreas.canifis.containsOrIntersects(ctx.players.local()); // and not at Canifis herb
    }
}