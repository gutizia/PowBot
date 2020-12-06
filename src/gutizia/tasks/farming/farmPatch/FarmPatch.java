package gutizia.tasks.farming.farmPatch;

import gutizia.tasks.Task;
import gutizia.tasks.farming.FreeInventorySpace;
import gutizia.util.constants.Bounds;
import gutizia.util.constants.Items;
import gutizia.util.database.Database;
import gutizia.util.skills.farming.Farming;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Item;

import java.util.ArrayList;
import java.util.logging.Logger;

import static gutizia.util.managers.SettingsManager.settingsManager;
import static gutizia.util.trackers.CompostTracker.compostTracker;
import static gutizia.util.trackers.FarmingTracker.farmingTracker;
import static gutizia.util.trackers.FruitTreeTracker.fruitTreeTracker;
import static gutizia.util.trackers.HerbRunTracker.herbRunTracker;
import static gutizia.tasks.TaskChanger.taskChanger;
import static gutizia.util.trackers.HopsRunTracker.hopsRunTracker;
import static gutizia.util.trackers.TreeTracker.treeTracker;

public class FarmPatch extends Task {
    final static Logger LOGGER = Logger.getLogger("FarmPatch");
    Farming farming = new Farming(ctx);

    static Farming.Patch patch;
    static int seed;
    static int initialSeedAmount;
    static boolean usedCompost;
    private static int expGained;
    private static int produceGained;
    static boolean updatedPatchTracker = true; // a safety check to avoid multiple counts of same patch when tracking alive/dead patches
    private static int farmLevel;


    private Task finish = new Task(ctx) {
        @Override
        public void execute() {
            LOGGER.info("finishing patch: " + patch);
            new Database().updateRun(settingsManager.getLogin(), getProduceName(),herbRunTracker.getElapsedTime(),
                    farmingTracker.getProduceGained() - produceGained,farmingTracker.getExpGained() - expGained);

            if (ctx.skills.realLevel(Constants.SKILLS_FARMING) > farmLevel) {
                new Database().updateFarmLevel(settingsManager.getLogin(), ctx.skills.realLevel(Constants.SKILLS_FARMING));
            }

            taskChanger.setTasksPrevious(ctx);
        }

        @Override
        public boolean activate() {
            if (compostTracker.isUsingCompost() && !usedCompost) {
                return false;
            }

            switch (patch) {
                case HERB:
                    return farming.herbDone();

                case FLOWER:
                    return farming.flowerDone();

                case ALLOTMENT1:
                    return farming.allotment1Done();

                case ALLOTMENT2:
                    return farming.allotment2Done();

                case HOPS:
                    return farming.hopsDone();

                case FRUIT_TREE:
                    return farming.fruitTreeDone();

                default:
                    LOGGER.info("finish task did not recognize patch: " + patch);
                    return false;
            }
        }
    };

    public FarmPatch(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        taskChanger.setTasks(getTasks());
        Item itemSeed = farming.getSeed(patch);
        seed = itemSeed.id();
        initialSeedAmount = itemSeed.stackSize();
        updatedPatchTracker = false;
        usedCompost = fruitTreeTracker.isDoingRun() || treeTracker.isDoingRun();
        expGained = farmingTracker.getExpGained();
        produceGained = farmingTracker.getProduceGained();
        farmLevel = ctx.skills.realLevel(Constants.SKILLS_FARMING);
        LOGGER.info("starting FarmPatch task on patch: " + patch + ", with seed: " + seed);
    }

    protected ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();

        tasks.add(new RakePatch(ctx));
        tasks.add(new PlantPatch(ctx));
        tasks.add(new FreeInventorySpace(ctx));

        if (herbRunTracker.isDoingRun() || hopsRunTracker.isDoingRun()) {
            tasks.add(new ClearPatch(ctx));
            tasks.add(new HarvestPatch(ctx));
            tasks.add(new FertilizePatch(ctx));

        } else if (fruitTreeTracker.isDoingRun() || treeTracker.isDoingRun()) {
            tasks.add(new PayToRemoveTree(ctx));
            tasks.add(new PlantSapling(ctx));
            tasks.add(new CheckHealth(ctx));

            if (fruitTreeTracker.isDoingRun()) {
                tasks.add(new PickProduceFromPatch(ctx));
            }
        }
        tasks.add(finish);

        return tasks;
    }

    int[] getBounds(String objectName) {
        switch (objectName) {
            case "Apple tree":
                return Bounds.APPLE_TREE;

            default:
                LOGGER.info("ERROR! did not recognize game object name: " + objectName);
                LOGGER.info("returning default bounds");
                return new int[] {-32, 32, -64, 0, -32, 32};
        }
    }

    private String getProduceName() {
        switch (patch) {
            case HERB:
                return "herbs";

            case FLOWER:
                return "flowers";

            case ALLOTMENT1: case ALLOTMENT2:
                return "allotments";

            case HOPS:
                return "hops";

            case FRUIT_TREE:
                return "fruit trees";

            case TREE:
                return "trees";

            case CACTUS:
                return "cacti";

            default:
                LOGGER.info("ERROR! getProduceName() did not recognize patch: " + patch);
                return "null";
        }
    }

    @Override
    public boolean activate() {
        if (farming.getFarmSpotYouAreAt().equals(Farming.FarmingSpot.NONE)) {
            return false;
        }

        if (herbRunTracker.isDoingRun() && herbRunTracker.haveGottenItems()) {
            if (!ctx.inventory.select().id(Items.HERB_SEEDS).isEmpty()) { // have herb seed in inventory
                if (!farming.herbDone()) { // haven't already done herb
                    patch = Farming.Patch.HERB;
                    return true;
                }
            }
            if (!ctx.inventory.select().id(Items.FLOWER_SEEDS).isEmpty()) { // have flower seed in inventory
                if (!farming.flowerDone()) { // haven't already done flowers
                    patch = Farming.Patch.FLOWER;
                    return true;
                }
            }
            if (!ctx.inventory.select().id(Items.ALLOTMENT_SEEDS).isEmpty()) { // have allotment seeds in inventory
                if (!farming.allotment1Done()) { // haven't already done allotment 1
                    patch = Farming.Patch.ALLOTMENT1;
                    return true;
                }
                if (!farming.allotment2Done()) { // haven't already done allotment 2
                    patch = Farming.Patch.ALLOTMENT2;
                    return true;
                }
            }

        } else if (hopsRunTracker.isDoingRun()) {
            if (hopsRunTracker.haveGottenItems() && !ctx.inventory.select().id(Items.HOPS_SEEDS).isEmpty()) { // have hops seeds in inventory
                if (!farming.hopsDone()) { // haven't already done hops
                    patch = Farming.Patch.HOPS;
                    return true;
                }
            }

        } else if (fruitTreeTracker.isDoingRun()) {
            if (fruitTreeTracker.haveGottenItems() && !ctx.inventory.select().id(Items.FRUIT_TREE_SAPLINGS).isEmpty()) { // have fruit tree saplings in inventory
                if (!farming.fruitTreeDone()) { // haven't already done fruit tree
                    patch = Farming.Patch.FRUIT_TREE;
                    return true;
                }
            }

        } else if (treeTracker.isDoingRun()) {
            if (treeTracker.haveGottenItems() && !ctx.inventory.select().id(Items.TREE_SAPLINGS).isEmpty()) {
                if (!farming.treeDone()) {
                    patch = Farming.Patch.TREE;
                    return true;
                }
            }
        }

        return false;
    }
}
