package gutizia.tasks.farming;

import gutizia.tasks.Task;
import gutizia.util.Interact;
import gutizia.util.constants.Items;
import gutizia.util.constants.Runes;
import gutizia.util.constants.Tools;
import gutizia.util.managers.BankManager;
import gutizia.util.skills.farming.Farming.FarmRun;
import gutizia.util.skills.farming.Farming;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Item;

import java.util.ArrayList;
import java.util.logging.Logger;

import static gutizia.util.trackers.CompostTracker.compostTracker;
import static gutizia.util.trackers.FarmingTracker.farmingTracker;
import static gutizia.util.trackers.FruitTreeTracker.fruitTreeTracker;
import static gutizia.util.trackers.HerbRunTracker.herbRunTracker;
import static gutizia.util.trackers.HopsRunTracker.hopsRunTracker;
import static gutizia.util.trackers.TreeTracker.treeTracker;

/**
 * duplicate class to trim useless code
 */
public class GetFarmingItems extends Task {
    private final static Logger LOGGER = Logger.getLogger("GetFarmingItems");
    private BankManager bankManager = new BankManager(ctx);

    private Farming.Patch patch;
    private FarmRun farmRun;
    private ArrayList<ArrayList<Integer>> requiredItems;
    private boolean gotItems = false;

    public GetFarmingItems(ClientContext ctx, ArrayList<ArrayList<Integer>> requiredItems, FarmRun farmRun) {
        super(ctx);
        this.farmRun = farmRun;
        this.requiredItems = requiredItems;
    }

    @Override
    public void execute() {
        LOGGER.info("getting items for doing " + farmRun);
        int[] itemsRequired = new int[requiredItems.size()];
        int i = 0;
        for (ArrayList<Integer> item : requiredItems) {
            itemsRequired[i] = item.get(0);
            i++;
        }

        if (bankManager.openABank()) {
            if (ctx.bank.opened()) {
                ctx.bank.depositInventory();

                Condition.wait(() -> ctx.inventory.size() == 0,200,6);

                switch (farmRun) {
                    case HERB_RUN:
                        patch = Farming.Patch.HERB;
                        if (farmingTracker.isHerbsActivated()) setSeeds(withdrawSeeds());
                        patch = Farming.Patch.FLOWER;
                        if (farmingTracker.isFlowersActivated()) setSeeds(withdrawSeeds());
                        patch = Farming.Patch.ALLOTMENT1;
                        if (farmingTracker.isAllotmentsActivated()) setSeeds(withdrawSeeds());
                        break;

                    case HOPS_RUN:
                        patch = Farming.Patch.HOPS;
                        if (farmingTracker.isHopsActivated()) setSeeds(withdrawSeeds());
                        break;

                    case FRUIT_TREE_RUN:
                        patch = Farming.Patch.FRUIT_TREE;
                        if (farmingTracker.isFruitTreesActivated()) setSeeds(withdrawSeeds());
                        break;
                }

                bankManager.withdrawItems(requiredItems);

                // TODO find out what to do with this. can only hold 1000 of each compost panels, and sometimes you don't even want compost for the up coming run
                bankManager.withdrawAllNoted(Items.COMPOST);
                bankManager.withdrawAllNoted(Items.SUPERCOMPOST);
                bankManager.withdrawAllNoted(Items.ULTRACOMPOST);
                if (compostTracker.isUsingBottomlessBucket()) {
                    ctx.bank.withdraw(Items.BOTTOMLESS_BUCKET,1);
                }

                ctx.bank.close();
            }

            if (!ctx.bank.opened()) {

                Interact.equipOutfit(ctx, new int[]{Tools.MAGIC_SECATEURS});

                if (ctx.inventory.select().id(itemsRequired).size() == itemsRequired.length) {
                    if (farmingTracker.isUsingRunes()) {
                        boolean haveEnoughRunes = true;
                        for (Item rune : ctx.inventory.id(Runes.AIR,Runes.EARTH,Runes.LAW)) {
                            if (rune.stackSize() < getPatches()) {
                                haveEnoughRunes = false;
                                break;
                            }
                        }
                        gotItems = haveEnoughRunes;
                    } else {
                        gotItems = ctx.inventory.id(Items.TELEPORT_TO_HOUSE_TABLET).poll().stackSize() >= getPatches();
                    }
                }
            }
            if (gotItems) {
                setGottenItems();
            } else {
                LOGGER.info("did not get all items needed for run...");
            }
        }
    }

    private ArrayList<Integer> withdrawSeeds() {
        LOGGER.info("getting seeds/saplings for " + patch);
        int patchesRemaining = getPatches();
        int[] allSeeds = getAllSeeds();
        ArrayList<Integer> seeds = new ArrayList<>();

        if (ctx.bank.select().id(allSeeds).isEmpty()) {
            LOGGER.info("no preferred seed/sapling and no seeds/saplings found in bank");
            deactivatePatchType();
            return null; // todo alter return value
        }

        for (Integer seed : getAvailableSeeds()) {
            int amountWithdrawn = ctx.bank.withdrawAmount(seed,patchesRemaining * getSeedsPerPatch(seed));

            Item seedItem = ctx.inventory.select().id(seed).poll();
            if (seedItem == ctx.inventory.nil()) continue;
            seeds.add(seedItem.id());

            if (seedItem.stackSize() >= (patchesRemaining * getSeedsPerPatch(seed))) {
                LOGGER.info("withdrew all seeds/saplings needed");
                return seeds;
            } else {
                int rest;
                if ((rest = amountWithdrawn % getSeedsPerPatch(seed)) != 0) {
                    LOGGER.info("withdrew " + rest + " too many of " + seedItem.name() + ". Depositing the extras now...");
                    ctx.bank.deposit(seedItem.id(),rest);
                }
                patchesRemaining -= seedItem.stackSize() / getSeedsPerPatch(seed);
            }
        }

        if (patchesRemaining > 0) {
            LOGGER.info("was not able to get enough seeds/sapling for an entire run...");
        }
        return seeds;
    }

    private void deactivatePatchType() {
        switch (patch) {
            case HERB:
                farmingTracker.setHerbsActivated(false);
                break;

            case FLOWER:
                farmingTracker.setFlowersActivated(false);
                break;

            case ALLOTMENT1: case ALLOTMENT2:
                farmingTracker.setAllotmentsActivated(false);
                break;

            case HOPS:
                farmingTracker.setHopsActivated(false);
                break;

            case FRUIT_TREE:
                farmingTracker.setFruitTreesActivated(false);
                break;

            case TREE:
                farmingTracker.setTreesActivated(false);
                break;
        }
    }

    private void setGottenItems() {
        LOGGER.info("successfully got all items needed");
        switch (farmRun) {
            case HERB_RUN:
                herbRunTracker.setGottenItems(true);
                break;

            case HOPS_RUN:
                hopsRunTracker.setGottenItems(true);
                break;

            case FRUIT_TREE_RUN:
                fruitTreeTracker.setGottenItems(true);
                break;

            case TREE_RUN:
                treeTracker.setGottenItems(true);
                break;

            default:
                LOGGER.info("ERROR! setGottenItems() did not recognize farm run: " + farmRun);
        }
    }

    private void setSeeds(ArrayList<Integer> seeds) {
        switch (patch) {
            case HERB:
                herbRunTracker.setHerbSeedUsed(seeds);
                break;

            case FLOWER:
                herbRunTracker.setFlowerSeedUsed(seeds);
                break;

            case ALLOTMENT1: case ALLOTMENT2:
                herbRunTracker.setAllotmentSeedUsed(seeds);
                break;

            case HOPS:
                hopsRunTracker.setSeedsUsed(seeds);
                break;

            case FRUIT_TREE:
                fruitTreeTracker.setSeedsUsed(seeds);
                break;

            default:
                LOGGER.info("ERROR! setSeeds() did not recognize patch: " + patch);
        }
    }

    private ArrayList<Integer> getAvailableSeeds() {
        int level = ctx.skills.realLevel(Constants.SKILLS_FARMING);
        ArrayList<Integer> seeds = new ArrayList<>();
        int[] prio;
        switch (patch) {
            case HERB:
                prio = herbRunTracker.getHerbPriority();
                seeds.ensureCapacity(prio.length);
                for (int i = 0; i < prio.length; i++) {
                    if (level >= herbRunTracker.getHerbs().get(Items.HERB_SEEDS[i])) {
                        seeds.add(prio[i], Items.HERB_SEEDS[i]);
                    } else {
                        seeds.add(prio[i], -1);
                    }
                }
                break;

            case FLOWER:
                prio = herbRunTracker.getFlowerPriority();
                seeds.ensureCapacity(prio.length);
                for (int i = 0; i < prio.length; i++) {
                    if (level >= herbRunTracker.getFlowers().get(Items.FLOWER_SEEDS[i])) {
                        seeds.add(prio[i], Items.FLOWER_SEEDS[i]);
                    } else {
                        seeds.add(prio[i], -1);
                    }
                }
                break;

            case ALLOTMENT1: case ALLOTMENT2:
                prio = herbRunTracker.getAllotmentPriority();
                seeds.ensureCapacity(prio.length);
                for (int i = 0; i < prio.length; i++) {
                    if (level >= herbRunTracker.getAllotments().get(Items.ALLOTMENT_SEEDS[i])) {
                        seeds.add(prio[i], Items.ALLOTMENT_SEEDS[i]);
                    } else {
                        seeds.add(prio[i], -1);
                    }
                }
                break;

            case HOPS:
                prio = hopsRunTracker.getPriority();
                seeds.ensureCapacity(prio.length);
                for (int i = 0; i < prio.length; i++) {
                    if (level >= hopsRunTracker.getProduceLevelRequirement().get(Items.HOPS_SEEDS[i])) {
                        seeds.add(prio[i], Items.HOPS_SEEDS[i]);
                    } else {
                        seeds.add(prio[i], -1);
                    }
                }
                break;

            case FRUIT_TREE:
                prio = fruitTreeTracker.getPriority();
                seeds.ensureCapacity(prio.length);
                for (int i = 0; i < prio.length; i++) {
                    if (level >= fruitTreeTracker.getProduceLevelRequirement().get(Items.FRUIT_TREE_SAPLINGS[i])) {
                        seeds.add(prio[i], Items.FRUIT_TREE_SAPLINGS[i]);
                    } else {
                        seeds.add(prio[i], -1);
                    }
                }
                break;

            case TREE:
                prio = treeTracker.getPriority();
                seeds.ensureCapacity(prio.length);
                for (int i = 0; i < prio.length; i++) {
                    if (level >= treeTracker.getProduceLevelRequirement().get(Items.TREE_SAPLINGS[i])) {
                        seeds.add(prio[i], Items.TREE_SAPLINGS[i]);
                    } else {
                        seeds.add(prio[i], -1);
                    }
                }
                break;
        }
        return seeds;
    }

    private int getPatches() {
        switch (patch) {
            case HERB:
                return herbRunTracker.getHerbPatchesAmount();

            case FLOWER:
                return herbRunTracker.getFlowerPatchesAmount();

            case ALLOTMENT1: case ALLOTMENT2:
                return herbRunTracker.getAllotmentPatchesAmount();

            case HOPS:
                return hopsRunTracker.getAmountOfPatches();

            case FRUIT_TREE:
                return fruitTreeTracker.getAmountOfPatches();

            case TREE:
                return treeTracker.getAmountOfPatches();

            default:
                LOGGER.info("ERROR! getPatches did not recognize patch: " + patch);
                return 0;
        }
    }

    private int[] getAllSeeds() {
        switch (patch) {
            case HERB:
                return Items.HERB_SEEDS;

            case FLOWER:
                return Items.FLOWER_SEEDS;

            case ALLOTMENT1: case ALLOTMENT2:
                return Items.ALLOTMENT_SEEDS;

            case HOPS:
                return Items.HOPS_SEEDS;

            case FRUIT_TREE:
                return Items.FRUIT_TREE_SAPLINGS;

            default:
                LOGGER.info("ERROR! getAllSeeds did not recognize patch: " + patch);
                return null;
        }
    }

    private int getSeedsPerPatch(int seed) {
        switch (patch) {
            case HERB: case FLOWER: case FRUIT_TREE: case TREE:
                return 1;

            case ALLOTMENT1: case ALLOTMENT2:
                return 3;

            case HOPS:
                return (seed == Items.JUTE_SEED ? 3 : 4);

            default:
                LOGGER.info("ERROR! getSeedsPerPatch did not recognize patch: " + patch);
                return 1;
        }
    }

    @Override
    public boolean activate() {
        switch (farmRun) {
            case HERB_RUN:
                return !herbRunTracker.getPatchOrder().isEmpty() && !herbRunTracker.haveGottenItems();

            case HOPS_RUN:
                return !hopsRunTracker.getPatchOrder().isEmpty() && !hopsRunTracker.haveGottenItems();

            case FRUIT_TREE_RUN:
                return !fruitTreeTracker.getPatchOrder().isEmpty() && !fruitTreeTracker.haveGottenItems();

            default:
                LOGGER.info("ERROR! activate() not recognize farm run: " + farmRun);
                ctx.controller.stop();
                return false;
        }
    }
}
