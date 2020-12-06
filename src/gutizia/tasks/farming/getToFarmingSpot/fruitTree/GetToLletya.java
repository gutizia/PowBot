package gutizia.tasks.farming.getToFarmingSpot.fruitTree;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.skills.farming.Farming;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.trackers.FruitTreeTracker.fruitTreeTracker;

public class GetToLletya extends GetToFarmSpot {

    public GetToLletya(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        // TODO add this later
    }

    @Override
    public boolean activate() {
        return fruitTreeTracker.getPatchOrder().get(0).equals(Farming.FarmingSpot.LLETYA_FT) && // next spot is Lletya fruit tree
                !PatchAreas.lletyaFTArea.containsOrIntersects(ctx.players.local()); // and not at Lletya fruit tree
    }

}