package gutizia.tasks.farming;

import gutizia.scripts.farmer.GutFarmer;
import gutizia.tasks.Activatable;
import gutizia.tasks.Task;
import gutizia.tasks.farming.farmPatch.FarmPatch;
import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.constants.Items;
import gutizia.util.constants.LevelRequirements;
import gutizia.util.constants.Runes;
import gutizia.util.constants.Tools;
import gutizia.util.managers.CameraManager;
import gutizia.util.resources.PropertyUtil;
import gutizia.util.resources.Requirements;
import gutizia.util.skills.farming.Farming.FarmRun;
import gutizia.util.skills.farming.Farming;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;

import java.util.ArrayList;
import java.util.logging.Logger;

import static gutizia.tasks.TaskChanger.taskChanger;
import static gutizia.util.managers.CameraManager.cameraManager;
import static gutizia.util.resources.PropertyUtil.NEXT_HARVEST;
import static gutizia.util.resources.PropertyUtil.fruitTrees;
import static gutizia.util.trackers.FarmingTracker.farmingTracker;
import static gutizia.util.trackers.FruitTreeTracker.fruitTreeTracker;

public class FruitTreeRun extends Requirements {
    private final static Logger LOGGER = Logger.getLogger("FruitTreeRun");
    private Farming farming = new Farming(ctx);

    private Task finish = new Task(ctx) {
        @Override
        public void execute() {
            LOGGER.info("finishing fruit tree run");
            fruitTreeTracker.setDoingRun(false);
            fruitTrees.saveFarmingTime();
            ((GutFarmer)ctx.controller.script()).setStandardTasks();
        }

        @Override
        public boolean activate() {
            if (fruitTreeTracker.getPatchOrder().isEmpty()) {
                return farming.fruitTreeDone();
            }
            return false;
        }
    };

    private Activatable getToFarmingSpot = () -> {
        if (!fruitTreeTracker.haveGottenItems()) {
            return false;
        }

        if (!fruitTreeTracker.getPatchOrder().isEmpty()) {
            if (farming.getFarmSpotYouAreAt().equals(Farming.FarmingSpot.NONE)) {
                return true;
            }
            return farming.fruitTreeDone();
        }
        return false;
    };

    public FruitTreeRun(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        LOGGER.info("starting fruit tree run");
        taskChanger.setTasks(getTasks());
        fruitTreeTracker.reset();

        if (!(cameraManager.getZoom() >= CameraManager.Zoom.MID.getRange()[0] &&
                cameraManager.getZoom() <= CameraManager.Zoom.MID.getRange()[1])) {
            cameraManager.scrollZoom(CameraManager.Zoom.MID);
        }
        fruitTreeTracker.setDoingRun(true);
    }

    @Override
    protected ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new FarmPatch(ctx));
        tasks.add(new GetToFarmSpot(ctx, getToFarmingSpot));
        tasks.add(new GetStoredCompost(ctx)); // TODO add if statement where condition is if want to use compost for additional exp
        tasks.add(new FreeInventorySpace(ctx));
        tasks.add(new GetFarmingItems(ctx, requiredItems, FarmRun.FRUIT_TREE_RUN));
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

        item.add(Tools.RAKE);
        item.add(1);
        requiredItems.add(item);
        item = new ArrayList<>();

        item.add(Items.COINS);
        item.add(5000);
        requiredItems.add(item);
        item = new ArrayList<>();

        if (ctx.skills.realLevel(Constants.SKILLS_MAGIC) >= LevelRequirements.TELEPORT_TO_HOUSE) {
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

        return (farmingTracker.isFruitTreesActivated() && fruitTrees.getProperty(PropertyUtil.LOGIN_DONE).equals("true")
                && System.currentTimeMillis() > Long.parseLong(fruitTrees.getProperty(NEXT_HARVEST)));
    }
}
