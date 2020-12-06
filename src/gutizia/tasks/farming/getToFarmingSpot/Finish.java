package gutizia.tasks.farming.getToFarmingSpot;

import gutizia.util.skills.farming.Farming;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.tasks.TaskChanger.taskChanger;
import static gutizia.util.trackers.FruitTreeTracker.fruitTreeTracker;
import static gutizia.util.trackers.HerbRunTracker.herbRunTracker;
import static gutizia.util.trackers.HopsRunTracker.hopsRunTracker;
import static gutizia.util.trackers.TreeTracker.treeTracker;

public class Finish extends GetToFarmSpot {

    public Finish(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        if (hopsRunTracker.isDoingRun())
            hopsRunTracker.removeFromPatchOrder(0);

        if (herbRunTracker.isDoingRun())
            herbRunTracker.removeFromPatchOrder(0);

        if (fruitTreeTracker.isDoingRun())
            fruitTreeTracker.removeFromPatchOrder(0);

        taskChanger.setTasksPrevious(ctx);
    }

    @Override
    public boolean activate() {
        Farming.FarmingSpot farmingSpot = farming.getFarmSpotYouAreAt();

        if (hopsRunTracker.isDoingRun()) {
            return hopsRunTracker.getPatchOrder().get(0).equals(farmingSpot);
        }

        if (herbRunTracker.isDoingRun()) {
            return herbRunTracker.getPatchOrder().get(0).equals(farmingSpot);
        }

        if (fruitTreeTracker.isDoingRun()) {
            return fruitTreeTracker.getPatchOrder().get(0).equals(farmingSpot);
        }

        if (treeTracker.isDoingRun()) {
            return treeTracker.getPatchOrder().get(0).equals(farmingSpot);
        }

        return false;
    }
}
