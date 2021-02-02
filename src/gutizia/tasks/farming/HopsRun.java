package gutizia.tasks.farming;

import gutizia.scripts.farmer.GutFarmer;
import gutizia.tasks.Activatable;
import gutizia.tasks.Task;
import gutizia.tasks.farming.farmPatch.FarmPatch;
import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.constants.Items;
import gutizia.util.constants.Runes;
import gutizia.util.constants.Tools;
import gutizia.util.managers.CameraManager;
import gutizia.util.resources.PropertyUtil;
import gutizia.util.resources.Requirements;
import gutizia.util.skills.farming.Farming;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;
import java.util.logging.Logger;

import static gutizia.tasks.TaskChanger.taskChanger;
import static gutizia.util.managers.CameraManager.cameraManager;
import static gutizia.util.resources.PropertyUtil.NEXT_HARVEST;
import static gutizia.util.resources.PropertyUtil.hops;
import static gutizia.util.trackers.FarmingTracker.farmingTracker;
import static gutizia.util.trackers.HopsRunTracker.hopsRunTracker;

public class HopsRun extends Requirements {
    private final static Logger LOGGER = Logger.getLogger("HopsRun");
    private Farming farming = new Farming(ctx);

    public HopsRun(ClientContext ctx) {
        super(ctx);
    }

    private Task finish = new Task(ctx) {
        @Override
        public void execute() {
            LOGGER.info("finishing hops run");
            hopsRunTracker.setDoingRun(false);
            hops.saveFarmingTime();
            ((GutFarmer)ctx.controller.script()).setStandardTasks();
        }

        @Override
        public boolean activate() {
            if (hopsRunTracker.getPatchOrder().isEmpty()) {
                return farming.hopsDone();
            }
            return false;
        }
    };

    private Activatable getToFarmSpot = () -> {
        if (!hopsRunTracker.haveGottenItems()) {
            return false;
        }

        if (!hopsRunTracker.getPatchOrder().isEmpty()) {
            if (farming.getFarmSpotYouAreAt().equals(Farming.FarmingSpot.NONE)) {
                return true;
            }
            return farming.hopsDone();
        }
        return false;
    };

    @Override
    public void execute() {
        LOGGER.info("starting hops run");
        taskChanger.setTasks(getTasks());
        hopsRunTracker.reset();

        if (!(cameraManager.getZoom() >= CameraManager.Zoom.MID.getRange()[0] &&
                cameraManager.getZoom() <= CameraManager.Zoom.MID.getRange()[1])) {
            cameraManager.scrollZoom(CameraManager.Zoom.MID);
        }
        hopsRunTracker.setDoingRun(true);
    }

    protected ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new GetToFarmSpot(ctx, getToFarmSpot));
        tasks.add(new GetStoredCompost(ctx));
        tasks.add(new FreeInventorySpace(ctx));
        tasks.add(new GetFarmingItems(ctx, requiredItems, Farming.FarmRun.HOPS_RUN));
        tasks.add(new FarmPatch(ctx));
        tasks.add(finish);
        return tasks;
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

        item.add(Tools.MAGIC_SECATEURS);
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

        return (farmingTracker.isHopsActivated() && hops.getProperty(PropertyUtil.LOGIN_DONE).equals("true") &&
                System.currentTimeMillis() > Long.parseLong(hops.getProperty(NEXT_HARVEST)));
    }
}
