package gutizia.scripts.farmer.tasks;

import gutizia.scripts.farmer.GutFarmer;
import gutizia.tasks.Task;
import gutizia.util.resources.PropertyUtil;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;

import java.util.logging.Logger;

import static gutizia.util.resources.PropertyUtil.fruitTrees;
import static gutizia.util.resources.PropertyUtil.herbs;
import static gutizia.util.resources.PropertyUtil.hops;
import static gutizia.util.resources.PropertyUtil.trees;
import static gutizia.util.resources.PropertyUtil.allotments;
import static gutizia.util.resources.PropertyUtil.flowers;
import static gutizia.util.trackers.FarmingTracker.farmingTracker;

public class Wait extends Task {
    private final static Logger LOGGER = Logger.getLogger("Wait");

    public Wait(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        long nextReadyPatch = 0;
        String nextReadyPatchName = "";
        if (farmingTracker.isHerbsActivated() && herbs.getProperty(PropertyUtil.LOGIN_DONE).equals("true")) {
            if (Long.parseLong(herbs.getProperty("next.harvest")) < nextReadyPatch) {
                nextReadyPatch = Long.parseLong(herbs.getProperty("next.harvest"));
                nextReadyPatchName = "herbs";
            }

        } else if (farmingTracker.isFlowersActivated() && flowers.getProperty(PropertyUtil.LOGIN_DONE).equals("true")) {
            if (Long.parseLong(flowers.getProperty("next.harvest")) < nextReadyPatch) {
                nextReadyPatch = Long.parseLong(flowers.getProperty("next.harvest"));
                nextReadyPatchName = "flowers";
            }

        }  else if (farmingTracker.isAllotmentsActivated() && allotments.getProperty(PropertyUtil.LOGIN_DONE).equals("true")) {
            if (Long.parseLong(allotments.getProperty("next.harvest")) < nextReadyPatch) {
                nextReadyPatch = Long.parseLong(allotments.getProperty("next.harvest"));
                nextReadyPatchName = "allotments";
            }

        } else if (farmingTracker.isHopsActivated() && hops.getProperty(PropertyUtil.LOGIN_DONE).equals("true")) {
            if (Long.parseLong(hops.getProperty("next.harvest")) < nextReadyPatch) {
                nextReadyPatch = Long.parseLong(hops.getProperty("next.harvest"));
                nextReadyPatchName = "hops";
            }

        } else if (farmingTracker.isFruitTreesActivated() && fruitTrees.getProperty(PropertyUtil.LOGIN_DONE).equals("true")) {
            if (Long.parseLong(fruitTrees.getProperty("next.harvest")) < nextReadyPatch) {
                nextReadyPatch = Long.parseLong(fruitTrees.getProperty("next.harvest"));
                nextReadyPatchName = "fruit trees";
            }

        } else if (farmingTracker.isTreesActivated() && trees.getProperty(PropertyUtil.LOGIN_DONE).equals("true")) {
            if (Long.parseLong(trees.getProperty("next.harvest")) < nextReadyPatch) {
                nextReadyPatch = Long.parseLong(trees.getProperty("next.harvest"));
                nextReadyPatchName = "trees";
            }
        }
        final long waitTime = nextReadyPatch + Random.nextInt(60000, 300000);
        ((GutFarmer)ctx.controller.script()).setWaiting(true);
        LOGGER.info("next run: " + nextReadyPatchName + ", ready in: " + waitTime + " ms.");
        Condition.wait(() -> ctx.controller.isStopping() || System.currentTimeMillis() > waitTime, 1000, ((int)waitTime / 1000));
        ((GutFarmer)ctx.controller.script()).setWaiting(false);
    }

    @Override
    public boolean activate() {
        return !ctx.game.loggedIn();
    }
}