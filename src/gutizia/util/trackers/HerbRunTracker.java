package gutizia.util.trackers;

import gutizia.util.constants.Items;
import gutizia.util.constants.LevelRequirements;
import gutizia.util.skills.farming.Farming;
import org.powerbot.script.Random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HerbRunTracker extends Tracker {
    public final static HerbRunTracker herbRunTracker = new HerbRunTracker();

    private Map<Integer,Integer> herbs;
    private Map<Integer,Integer> flowers;
    private Map<Integer,Integer> allotments;

    private ArrayList<Integer> herbSeedUsed = new ArrayList<>();
    private ArrayList<Integer> flowerSeedUsed = new ArrayList<>();
    private ArrayList<Integer> allotmentSeedUsed = new ArrayList<>();

    private int[] herbPriority;
    private int[] flowerPriority;
    private int[] allotmentPriority;

    private HerbRunTracker() {
        Map<Integer,Integer> temp = new HashMap<Integer, Integer>();
        temp.put(Items.GUAM_SEED,LevelRequirements.GUAM);
        temp.put(Items.MARRENTILL_SEED,LevelRequirements.MARRENTILL);
        temp.put(Items.TARROMIN_SEED,LevelRequirements.TARROMIN);
        temp.put(Items.HARRALANDER_SEED,LevelRequirements.HARRALANDER);
        temp.put(Items.RANARR_SEED,LevelRequirements.RANARR);
        temp.put(Items.TOADFLAX_SEED,LevelRequirements.TOADFLAX);
        temp.put(Items.IRIT_SEED,LevelRequirements.IRIT_LEAF);
        temp.put(Items.AVANTOE_SEED,LevelRequirements.AVANTOE);
        temp.put(Items.KWUARM_SEED,LevelRequirements.KWUARM);
        temp.put(Items.SNAPDRAGON_SEED,LevelRequirements.SNAPDRAGON);
        temp.put(Items.CADANTINE_SEED,LevelRequirements.CADANTINE);
        temp.put(Items.LANTADYME_SEED,LevelRequirements.LANTADYME);
        temp.put(Items.DWARF_WEED_SEED,LevelRequirements.DWARF_WEED);
        temp.put(Items.TORSTOL_SEED,LevelRequirements.TORSTOL);

        herbs = temp;
        temp = new HashMap<>();


        temp.put(Items.POTATO_SEED,LevelRequirements.POTATO);
        temp.put(Items.ONION_SEED,LevelRequirements.ONION);
        temp.put(Items.CABBAGE_SEED,LevelRequirements.CABBAGE);
        temp.put(Items.TOMATO_SEED,LevelRequirements.TOMATO);
        temp.put(Items.SWEETCORN_SEED,LevelRequirements.SWEETCORN);
        temp.put(Items.STRAWBERRY_SEED,LevelRequirements.STRAWBERRY);
        temp.put(Items.WATERMELON_SEED,LevelRequirements.WATERMELON);
        temp.put(Items.SNAPE_GRASS_SEED,LevelRequirements.SNAPE_GRASS);

        allotments = temp;
        temp = new HashMap<>();

        temp.put(Items.MARIGOLD_SEED,LevelRequirements.MARIGOLD);
        temp.put(Items.ROSEMARY_SEED,LevelRequirements.ROSEMARY);
        temp.put(Items.NASTURTIUM_SEED,LevelRequirements.NASTURTIUM);
        temp.put(Items.WOAD_LEAF_SEED,LevelRequirements.WOAD_LEAF);
        temp.put(Items.LIMPWURT_SEED,LevelRequirements.LIMPWURT);
        temp.put(Items.WHITE_LILY_SEED,LevelRequirements.WHITE_LILY);

        flowers = temp;
    }

    /**
     * sets a random patchOrder in which to do the herb run (always starts at catherby)
     * only adds farming guild to list if have high enough farming level
     */
    void setPatchOrder() {
        patchOrder = new ArrayList<>();
        ArrayList<Integer> storedIntegers = new ArrayList<>();
        int random;
        patchOrder.add(Farming.FarmingSpot.CATHERBY_HAF);

        for (int i = 0; i < 5;i++) {
            do {
                random = Random.nextInt(1,6);
            } while (storedIntegers.contains(random));
            storedIntegers.add(random);

            switch (random) {
                case 1:
                    patchOrder.add(Farming.FarmingSpot.FALADOR_HAF);
                    break;
                case 2:
                    patchOrder.add(Farming.FarmingSpot.ARDOUGNE_HAF);
                    break;
                case 3:
                    //patchOrder.add(Farming.FarmingSpot.CANIFIS_HAF); // TODO ADD SUPPORT FOR IT LATER
                    break;
                case 4:
                    patchOrder.add(Farming.FarmingSpot.HOSIDIUS_HAF);
                    break;
                case 5:
                    /*if (ctx.skills.realLevel(Constants.SKILLS_FARMING) >= 65) { // TODO ADD SUPPORT FOR IT LATER
                        patchOrder.add(Farming.FarmingSpot.FARMING_GUILD);
                    } */
                    break;
            }
        }
    }

    public ArrayList<Farming.FarmingSpot> getOrder() {
        return patchOrder;
    }

    public void setGottenItems(boolean gottenItems) {
        this.gottenItems = gottenItems;
    }

    public int[] getHerbSeedUsed() {
        int[] seeds = new int[herbSeedUsed.size()];
        for (int i = 0; i < herbSeedUsed.size(); i++) {
            seeds[i] = herbSeedUsed.get(i);
        }
        return seeds;
    }

    public int[] getFlowerSeedUsed() {
        int[] seeds = new int[flowerSeedUsed.size()];
        for (int i = 0; i < flowerSeedUsed.size(); i++) {
            seeds[i] = flowerSeedUsed.get(i);
        }
        return seeds;
    }

    public int[] getAllotmentSeedUsed() {
        int[] seeds = new int[allotmentSeedUsed.size()];
        for (int i = 0; i < allotmentSeedUsed.size(); i++) {
            seeds[i] = allotmentSeedUsed.get(i);
        }
        return seeds;
    }

    public void setHerbSeedUsed(ArrayList<Integer> herbSeedUsed) {
        this.herbSeedUsed = herbSeedUsed;
    }

    public void setFlowerSeedUsed(ArrayList<Integer> flowerSeedUsed) {
        this.flowerSeedUsed = flowerSeedUsed;
    }

    public void setAllotmentSeedUsed(ArrayList<Integer> allotmentSeedUsed) {
        this.allotmentSeedUsed = allotmentSeedUsed;
    }

    public Map<Integer, Integer> getAllotments() {
        return allotments;
    }

    public Map<Integer, Integer> getFlowers() {
        return flowers;
    }

    public Map<Integer, Integer> getHerbs() {
        return herbs;
    }

    public int getHerbPatchesAmount() {
        return 4; // TODO make dynamic
    }

    public int getFlowerPatchesAmount() {
        return 4; // TODO make dynamic
    }

    public int getAllotmentPatchesAmount() {
        return 4 * 2; // 2 patches on each farming spot TODO make dynamic
    }

    public void setHerbPriority(int[] herbPriority) {
        this.herbPriority = herbPriority;
    }

    public void setFlowerPriority(int[] flowerPriority) {
        this.flowerPriority = flowerPriority;
    }

    public void setAllotmentPriority(int[] allotmentPriority) {
        this.allotmentPriority = allotmentPriority;
    }

    public int[] getHerbPriority() {
        return herbPriority;
    }

    public int[] getFlowerPriority() {
        return flowerPriority;
    }

    public int[] getAllotmentPriority() {
        return allotmentPriority;
    }
}