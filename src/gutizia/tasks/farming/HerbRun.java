package gutizia.tasks.farming;

import gutizia.scripts.farmer.GutFarmer;
import gutizia.tasks.Activatable;
import gutizia.tasks.farming.farmPatch.FarmPatch;
import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.resources.PropertyUtil;
import gutizia.util.skills.farming.Farming.FarmRun;
import gutizia.tasks.Task;
import gutizia.util.constants.*;
import gutizia.util.managers.*;
import gutizia.util.resources.Requirements;
import gutizia.util.skills.farming.Farming;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;
import java.util.logging.Logger;

import static gutizia.util.managers.CameraManager.cameraManager;
import static gutizia.util.resources.PropertyUtil.*;
import static gutizia.util.trackers.FarmingTracker.farmingTracker;
import static gutizia.util.trackers.HerbRunTracker.herbRunTracker;
import static gutizia.tasks.TaskChanger.taskChanger;

public class HerbRun extends Requirements {
    private final static Logger LOGGER = Logger.getLogger("herbs");
    private Farming farming = new Farming(ctx);

    private Task finish = new Task(ctx) {
        @Override
        public void execute() {
            LOGGER.info("finishing herb run");
            herbRunTracker.setDoingRun(false);

            if (farmingTracker.isHerbsActivated()) {
                herbs.saveFarmingTime();
            }
            if (farmingTracker.isFlowersActivated()) {
                flowers.saveFarmingTime();
            }
            if (farmingTracker.isAllotmentsActivated()) {
                allotments.saveFarmingTime();
            }
            ((GutFarmer)ctx.controller.script()).setStandardTasks();
        }

        @Override
        public boolean activate() {
            if (herbRunTracker.getOrder().isEmpty()) {
                return doneAtThisFarmSpot();
            }
            return false;
        }
    };

    private Activatable getToFarmSpot = () -> {
        if (!herbRunTracker.haveGottenItems()) {
            return false;
        }

        if (!herbRunTracker.getOrder().isEmpty()) {
            if (farming.getFarmSpotYouAreAt().equals(Farming.FarmingSpot.NONE)) {
                return true;
            }

            return doneAtThisFarmSpot();
        }
        return false;
    };

    public HerbRun(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        LOGGER.info("starting herb run");
        taskChanger.setTasks(getTasks());
        herbRunTracker.reset();

        if (!(cameraManager.getZoom() >= CameraManager.Zoom.MID.getRange()[0] &&
                cameraManager.getZoom() <= CameraManager.Zoom.MID.getRange()[1])) {
            cameraManager.scrollZoom(CameraManager.Zoom.MID);
        }
        herbRunTracker.setDoingRun(true);
    }

    protected ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new GetToFarmSpot(ctx, getToFarmSpot));
        tasks.add(new GetStoredCompost(ctx));
        tasks.add(new FreeInventorySpace(ctx));
        tasks.add(new GetFarmingItems(ctx, requiredItems, FarmRun.HERB_RUN));
        tasks.add(new FarmPatch(ctx));
        tasks.add(finish);
        return tasks;
    }

    private boolean doneAtThisFarmSpot() {
        boolean haveHerbSeeds = !ctx.inventory.select().id(Items.HERB_SEEDS).isEmpty();
        boolean haveFlowerSeeds = !ctx.inventory.select().id(Items.FLOWER_SEEDS).isEmpty();
        boolean haveAllotmentSeeds = !ctx.inventory.select().id(Items.ALLOTMENT_SEEDS).isEmpty();

        return haveHerbSeeds == herbDone(haveHerbSeeds) &&
                haveFlowerSeeds == flowerDone(haveFlowerSeeds) &&
                haveAllotmentSeeds == allotmentDone(haveAllotmentSeeds);
    }

    private boolean herbDone(boolean haveSeeds) {
        if (!haveSeeds) {
            return false;
        }
        return farming.herbDone();
    }

    private boolean allotmentDone(boolean haveSeeds) {
        if (!haveSeeds) {
            return false;
        }
        return farming.allotment1Done() && farming.allotment2Done();
    }

    private boolean flowerDone(boolean haveSeeds) {
        if (!haveSeeds) {
            return false;
        }
        return farming.flowerDone();
    }

    @Override
    protected void setRequiredStats() {

    }

    @Override
    protected void setRequiredItems() {
        ArrayList<Integer> item = new ArrayList<>();

        item.add(Tools.SPADE);
        item.add(1);
        requiredItems.add(item);
        item = new ArrayList<>();

        item.add(Tools.SEED_DIBBER);
        item.add(1);
        requiredItems.add(item);
        item = new ArrayList<>();

        item.add(Tools.RAKE);
        item.add(1);
        requiredItems.add(item);
        item = new ArrayList<>();

        if (farmingTracker.isUsingRunes()) {
            item.add(Runes.AIR);
            item.add(10);
            requiredItems.add(item);
            item = new ArrayList<>();

            item.add(Runes.EARTH);
            item.add(10);
            requiredItems.add(item);
            item = new ArrayList<>();

            item.add(Runes.LAW);
            item.add(10);
            requiredItems.add(item);
        } else {
            item.add(Items.TELEPORT_TO_HOUSE_TABLET);
            item.add(10);
            requiredItems.add(item);
        }
    }

    @Override
    public boolean activate() {
        if (!ctx.game.loggedIn()) {
            return false;
        }

        if (farmingTracker.isHerbsActivated() && herbs.getProperty(PropertyUtil.LOGIN_DONE).equals("true")
                && System.currentTimeMillis() > Long.parseLong(herbs.getProperty(NEXT_HARVEST))) {
            return true;
        }
        if (farmingTracker.isFlowersActivated() && flowers.getProperty(PropertyUtil.LOGIN_DONE).equals("true")
                && System.currentTimeMillis() > Long.parseLong(flowers.getProperty(NEXT_HARVEST))) {
            return true;
        }
        return (farmingTracker.isAllotmentsActivated() && allotments.getProperty(PropertyUtil.LOGIN_DONE).equals("true")
                && System.currentTimeMillis() > Long.parseLong(allotments.getProperty(NEXT_HARVEST)));
    }
}
