package gutizia.scripts.farmer.util.trackers;

import gutizia.util.constants.Items;
import gutizia.util.constants.LevelRequirements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public enum Patch {
    HERB, FLOWER, ALLOTMENT, FRUIT_TREE, TREE, HOPS;

    private ArrayList<Integer> seedsUsed = new ArrayList<>();
    private int[] priority;

    private int produceFarmed = 0;
    private int runProduceFarmed = 0;

    private int treesPlanted = 0;
    private int runTreesPlanted = 0;

    private int checkedHealthOf = 0;
    private int runCheckedHealthOf = 0;

    Patch() {}

    void resetRunValues() {
        this.runProduceFarmed = 0;
    }

    public int[] getSeedsUsed() {
        int[] seeds = new int[seedsUsed.size()];
        for (int i = 0; i < seedsUsed.size(); i++) {
            seeds[i] = seedsUsed.get(i);
        }
        return seeds;
    }

    public int[] getPriority() {
        return priority;
    }

    public int getProduceFarmed() {
        return produceFarmed;
    }

    public int getRunProduceFarmed() {
        return runProduceFarmed;
    }

    public void setPriority(int[] priority) {
        this.priority = priority;
    }

    public void setSeedsUsed(ArrayList<Integer> seedsUsed) {
        this.seedsUsed = seedsUsed;
    }

    public Map<Integer, Integer> getProduceLevelRequirement() {
        Map<Integer, Integer> map = new HashMap<>();
        switch (this) {
            case HERB:
                map.put(Items.GUAM_SEED,LevelRequirements.GUAM);
                map.put(Items.MARRENTILL_SEED,LevelRequirements.MARRENTILL);
                map.put(Items.TARROMIN_SEED,LevelRequirements.TARROMIN);
                map.put(Items.HARRALANDER_SEED,LevelRequirements.HARRALANDER);
                map.put(Items.RANARR_SEED,LevelRequirements.RANARR);
                map.put(Items.TOADFLAX_SEED,LevelRequirements.TOADFLAX);
                map.put(Items.IRIT_SEED,LevelRequirements.IRIT_LEAF);
                map.put(Items.AVANTOE_SEED,LevelRequirements.AVANTOE);
                map.put(Items.KWUARM_SEED,LevelRequirements.KWUARM);
                map.put(Items.SNAPDRAGON_SEED,LevelRequirements.SNAPDRAGON);
                map.put(Items.CADANTINE_SEED,LevelRequirements.CADANTINE);
                map.put(Items.LANTADYME_SEED,LevelRequirements.LANTADYME);
                map.put(Items.DWARF_WEED_SEED,LevelRequirements.DWARF_WEED);
                map.put(Items.TORSTOL_SEED,LevelRequirements.TORSTOL);
                break;

            case FLOWER:
                map.put(Items.MARIGOLD_SEED,LevelRequirements.MARIGOLD);
                map.put(Items.ROSEMARY_SEED,LevelRequirements.ROSEMARY);
                map.put(Items.NASTURTIUM_SEED,LevelRequirements.NASTURTIUM);
                map.put(Items.WOAD_LEAF_SEED,LevelRequirements.WOAD_LEAF);
                map.put(Items.LIMPWURT_SEED,LevelRequirements.LIMPWURT);
                map.put(Items.WHITE_LILY_SEED,LevelRequirements.WHITE_LILY);
                break;

            case ALLOTMENT:
                map.put(Items.POTATO_SEED,LevelRequirements.POTATO);
                map.put(Items.ONION_SEED,LevelRequirements.ONION);
                map.put(Items.CABBAGE_SEED,LevelRequirements.CABBAGE);
                map.put(Items.TOMATO_SEED,LevelRequirements.TOMATO);
                map.put(Items.SWEETCORN_SEED,LevelRequirements.SWEETCORN);
                map.put(Items.STRAWBERRY_SEED,LevelRequirements.STRAWBERRY);
                map.put(Items.WATERMELON_SEED,LevelRequirements.WATERMELON);
                map.put(Items.SNAPE_GRASS_SEED,LevelRequirements.SNAPE_GRASS);
                break;

            case FRUIT_TREE:
                map.put(Items.APPLE_SAPLING,LevelRequirements.APPLE);
                map.put(Items.BANANA_SAPLING,LevelRequirements.BANANA);
                map.put(Items.ORANGE_SAPLING,LevelRequirements.ORANGE);
                map.put(Items.CURRY_SAPLING,LevelRequirements.CURRY);
                map.put(Items.PINEAPPLE_SAPLING,LevelRequirements.PINEAPPLE);
                map.put(Items.PAPAYA_SAPLING,LevelRequirements.PAPAYA);
                map.put(Items.PALM_TREE_SAPLING,LevelRequirements.PALM_TREE);
                map.put(Items.DRAGONFRUIT_SAPLING,LevelRequirements.DRAGONFRUIT);
                break;

            case TREE:
                map.put(Items.OAK_SAPLING,LevelRequirements.OAK_SAPLING);
                map.put(Items.WILLOW_SAPLING,LevelRequirements.WILLOW_SAPLING);
                map.put(Items.MAPLE_SAPLING,LevelRequirements.MAPLE_SAPLING);
                map.put(Items.YEW_SAPLING,LevelRequirements.YEW_SAPLING);
                map.put(Items.MAGIC_SAPLING,LevelRequirements.MAGIC_SAPLING);
                break;

            case HOPS:
                map.put(Items.BARLEY_SEED,LevelRequirements.BARLEY);
                map.put(Items.HAMMERSTONE_SEED,LevelRequirements.HAMMERSTONE);
                map.put(Items.ASGARNIAN_SEED,LevelRequirements.ASGARNIAN_HOPS);
                map.put(Items.JUTE_SEED,LevelRequirements.JUTE);
                map.put(Items.YANILLIAN_SEED,LevelRequirements.YANILLIAN_HOPS);
                map.put(Items.KRANDORIAN_SEED,LevelRequirements.KRANDORIAN_HOPS);
                map.put(Items.WILDBLOOD_SEED,LevelRequirements.WILDBLOOD);
                break;
        }

        return map;
    }
    public int getAmountOfPatches() {
        switch (this) {
            case HERB: case FLOWER: case HOPS: case FRUIT_TREE:
                return 4;

            case ALLOTMENT:
                return 8;

            case TREE:
                return 5;
        }
        return 0;
    }

    public void updateProduceFarmed() {
        this.produceFarmed++;
        this.runProduceFarmed++;
    }

    public void updateTreesPlanted() {
        this.treesPlanted++;
        this.runTreesPlanted++;
    }

    public void updateCheckedHealthOf() {
        this.checkedHealthOf++;
        this.runCheckedHealthOf++;
    }

    public int getTreesPlanted() {
        return treesPlanted;
    }

    public int getRunTreesPlanted() {
        return runTreesPlanted;
    }

    public int getCheckedHealthOf() {
        return checkedHealthOf;
    }

    public int getRunCheckedHealthOf() {
        return runCheckedHealthOf;
    }

    public Map<Integer, Integer[]> getProtectItemMap() {
        Map<Integer, Integer[]> map = new HashMap<>();
        switch (this) {
            case TREE:
                map.put(Items.OAK_SAPLING, new Integer[] {Items.BASKET_OF_TOMATOES, 1});
                map.put(Items.WILLOW_SAPLING, new Integer[] {Items.BASKET_OF_APPLES, 1});
                map.put(Items.MAPLE_SAPLING, new Integer[] {Items.BASKET_OF_ORANGES, 1});
                map.put(Items.YEW_SAPLING, new Integer[] {Items.CACTUS_SPINE, 10});
                map.put(Items.MAGIC_SAPLING, new Integer[] {Items.COCONUT, 25});
                break;

            case FRUIT_TREE:
                map.put(Items.APPLE_SAPLING, new Integer[] {Items.SWEETCORN, 9});
                map.put(Items.BANANA_SAPLING, new Integer[] {Items.BASKET_OF_APPLES, 4});
                map.put(Items.ORANGE_SAPLING, new Integer[] {Items.BASKET_OF_STRAWBERRIES, 3});
                map.put(Items.CURRY_SAPLING, new Integer[] {Items.BASKET_OF_BANANAS, 5});
                map.put(Items.PINEAPPLE_SAPLING, new Integer[] {Items.WATERMELON, 10});
                map.put(Items.PAPAYA_SAPLING, new Integer[] {Items.PINEAPPLE, 10});
                map.put(Items.PALM_TREE_SAPLING, new Integer[] {Items.PAPAYA_FRUIT, 15});
                map.put(Items.DRAGONFRUIT_SAPLING, new Integer[] {Items.COCONUT, 15});
                break;
        }
        return map;
    }
}
