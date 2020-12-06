package gutizia.tasks.farming;

import gutizia.scripts.farmer.GutFarmer;
import gutizia.tasks.Task;
import gutizia.util.constants.Components;
import gutizia.util.constants.Items;
import gutizia.util.constants.Widgets;
import gutizia.util.skills.farming.Farming;
import gutizia.util.trackers.HerbRunTracker;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;
import java.util.logging.Logger;

import static gutizia.util.trackers.CompostTracker.compostTracker;
import static gutizia.util.trackers.HerbRunTracker.herbRunTracker;
import static gutizia.util.trackers.HopsRunTracker.hopsRunTracker;

public class GetStoredCompost extends Task {
    private final static Logger LOGGER = Logger.getLogger("GetStoredCompost");
    private Farming farming = new Farming(ctx);

    public GetStoredCompost(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        LOGGER.info("getting compost");
        farming.openFarmingEquipmentStore();
        farming.depositFarmingItems();

        if (compostTracker.isUsingBottomlessBucket()) {
            getBottomlessBucket();
        }

        ArrayList<ArrayList<Integer>> compostToWithdraw = getCompostToUse();

        if (missingNeededCompost(compostToWithdraw)) {
            LOGGER.info("missing compost you need!");
            if (GutFarmer.active) {
                LOGGER.info("stopping script...");
                ctx.controller.stop();
            }
            farming.closeFarmingEquipmentStore();
            return;
        }

        LOGGER.info("withdrawing composts:");
        for (ArrayList<Integer> item : compostToWithdraw) {
            LOGGER.info(Integer.toString(item.get(0)) + "," + Integer.toString(item.get(1)));
            while (!ctx.widgets.component(Widgets.CHAT_BOX, Components.CHAT_BOX_ENTER_AMOUNT).visible()) {
                ctx.widgets.component(Widgets.FARMING_EQUIPMENT_STORE, item.get(0)).interact("Remove-X");
                Condition.wait(() -> ctx.widgets.component(Widgets.CHAT_BOX, Components.CHAT_BOX_ENTER_AMOUNT).visible(), 600, 6);
            }
            Condition.sleep(Random.nextInt(200, 400));

            ctx.input.send(Integer.toString(item.get(1)) + "\n");

            Condition.sleep(Random.nextInt(300, 500));
        }

        farming.closeFarmingEquipmentStore();
    }

    private void getBottomlessBucket() {
        int storeComponent = 15;
        int invComponent = 8;
        int textComponent = 10;

        // checking if have bottomless bucket available at all
        if (ctx.widgets.component(Widgets.FARMING_EQUIPMENT_STORE,storeComponent).visible()) {
            if (ctx.widgets.component(Widgets.FARMING_EQUIPMENT_STORE, storeComponent, textComponent).text().charAt(0) != '1' && // if don't have bottomless bucket in store
                    ctx.widgets.component(Widgets.FARMING_EQUIPMENT_STORE_INVENTORY, invComponent, textComponent).text().charAt(0) == '0') { // and don't have it in inventory
                LOGGER.info("did not find bottomless bucket, setting using bottomless bucket to false...");
                compostTracker.setUsingBottomlessBucket(false);
                return;
            }

            if (ctx.widgets.component(Widgets.FARMING_EQUIPMENT_STORE,storeComponent,textComponent).text().charAt(0) == '1') {
                ctx.widgets.component(Widgets.FARMING_EQUIPMENT_STORE,storeComponent,textComponent).click();
            }
        }

    }

    /**
     * finds out which composts to deposit from farming Storage
     * @return list of composts to withdraw
     */
    private ArrayList<ArrayList<Integer>> getCompostToUse() {
        ArrayList<ArrayList<Integer>> composts = new ArrayList<>();
        ArrayList<Integer> normalCompostList = new ArrayList<>();
        ArrayList<Integer> superCompostList = new ArrayList<>();
        ArrayList<Integer> ultraCompostList = new ArrayList<>();
        int compost = 0;
        int superCompost = 0;
        int ultraCompost = 0;

        if (herbRunTracker.isDoingRun()) {
            if (!ctx.inventory.select().id(Items.HERB_SEEDS).isEmpty()) {
                switch (compostTracker.getCompostForSeed(ctx.inventory.poll().id())) {
                    case Items.COMPOST:
                        compost++;
                        break;

                    case Items.SUPERCOMPOST:
                        superCompost++;
                        break;

                    case Items.ULTRACOMPOST:
                        ultraCompost++;
                        break;
                }
            }

            if (!ctx.inventory.select().id(Items.FLOWER_SEEDS).isEmpty()) {
                switch (compostTracker.getCompostForSeed(ctx.inventory.poll().id())) {
                    case Items.COMPOST:
                        compost++;
                        break;

                    case Items.SUPERCOMPOST:
                        superCompost++;
                        break;

                    case Items.ULTRACOMPOST:
                        ultraCompost++;
                        break;
                }
            }

            if (!ctx.inventory.select().id(Items.ALLOTMENT_SEEDS).isEmpty()) {
                switch (compostTracker.getCompostForSeed(ctx.inventory.poll().id())) {
                    case Items.COMPOST:
                        compost += 2;
                        break;

                    case Items.SUPERCOMPOST:
                        superCompost += 2;
                        break;

                    case Items.ULTRACOMPOST:
                        ultraCompost += 2;
                        break;
                }
            }

        } else if (hopsRunTracker.isDoingRun()) {
            if (!ctx.inventory.select().id(Items.HOPS_SEEDS).isEmpty()) {
                switch (compostTracker.getCompostForSeed(ctx.inventory.poll().id())) {
                    case Items.COMPOST:
                        compost += 1;
                        break;

                    case Items.SUPERCOMPOST:
                        superCompost += 1;
                        break;

                    case Items.ULTRACOMPOST:
                        ultraCompost += 1;
                        break;
                }
            }
        }

        if (compost > 0) {
            normalCompostList.add(Components.FOLDER_STORE_COMPOST);
            normalCompostList.add(compost);
            composts.add(normalCompostList);
        }

        if (superCompost > 0) {
            superCompostList.add(Components.FOLDER_STORE_SUPERCOMPOST);
            superCompostList.add(superCompost);
            composts.add(superCompostList);
        }

        if (ultraCompost > 0) {
            ultraCompostList.add(Components.FOLDER_STORE_ULTRACOMPOST);
            ultraCompostList.add(ultraCompost);
            composts.add(ultraCompostList);
        }

        if (compostTracker.isUsingBottomlessBucket()) {
            String bottomlessBucketCompostType = compostTracker.getBlCompostType();
            LOGGER.info("bottomless bucket compost panels = " + bottomlessBucketCompostType + ", removing it from list");

            switch (bottomlessBucketCompostType) {
                case "normal":
                    composts.remove(normalCompostList);
                    break;

                case "super":
                    composts.remove(superCompostList);
                    break;

                case "ultra":
                    composts.remove(ultraCompostList);
                    break;
            }
        }

        return composts;
    }

    /**
     * checks for missing composts and updates {@link HerbRunTracker} of which compost was missing
     * @param requiredCompost arrayList of all compost needed for that farm spot (componentFolder of compost,quantity)
     * @return returns true if you have stored less compost than required
     */
    private boolean missingNeededCompost(ArrayList<ArrayList<Integer>> requiredCompost) {
        if (!ctx.widgets.widget(Widgets.FARMING_EQUIPMENT_STORE).valid()) {
            return false; // TODO replace with exception
        }

        final int quantity = 10;
        boolean missingCompost = false;
        for (ArrayList<Integer> compost : requiredCompost) {
            String text = ctx.widgets.component(Widgets.FARMING_EQUIPMENT_STORE,compost.get(0),quantity).text();

            int storedCompost = Integer.parseInt(text.split("/")[0]);
            if (compost.get(1) > storedCompost) {
                switch (compost.get(0)) {
                    case 17: // normal compost
                        LOGGER.info("@@@@@@@@ don't have anymore normal compost!!!");
                        compostTracker.setHaveNormalCompost(false);
                        break;
                    case 18: // super compost
                        LOGGER.info("@@@@@@@@ don't have anymore super compost!!!");
                        compostTracker.setHaveSuperCompost(false);
                        break;
                    case 19: // ultra compost
                        LOGGER.info("@@@@@@@@ don't have anymore ultra compost!!!");
                        compostTracker.setHaveUltraCompost(false);
                        break;
                }

                missingCompost = true;
            }
        }

        return missingCompost;
    }

    @Override
    public boolean activate() {
        if (farming.getFarmSpotYouAreAt().equals(Farming.FarmingSpot.NONE)) {
            return false;
        }

        int item = -1;
        if (herbRunTracker.isDoingRun() && herbRunTracker.haveGottenItems()) {
            if (!farming.herbDone() && !ctx.inventory.select().id(Items.HERB_SEEDS).isEmpty()) {
                item = compostTracker.getCompostForSeed(ctx.inventory.poll().id()); // gets corresponding compost to the seed in your inventory
            }

            if ((!farming.allotment1Done() || !farming.allotment2Done()) && !ctx.inventory.select().id(Items.ALLOTMENT_SEEDS).isEmpty()) {
                item = compostTracker.getCompostForSeed(ctx.inventory.poll().id());
            }

            if (!farming.flowerDone() && !ctx.inventory.select().id(Items.FLOWER_SEEDS).isEmpty()) {
                item = compostTracker.getCompostForSeed(ctx.inventory.poll().id());
            }
        }

        if (hopsRunTracker.isDoingRun() && hopsRunTracker.haveGottenItems()) {
            if (!farming.hopsDone() && !ctx.inventory.select().id(Items.HOPS_SEEDS).isEmpty()) {
                item = compostTracker.getCompostForSeed(ctx.inventory.poll().id());
            }
        }

        return (compostTracker.haveCompost(item) && ctx.inventory.select().id(item).isEmpty());
    }
}
