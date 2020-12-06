package gutizia.util.trackers;

import gutizia.util.constants.Items;
import gutizia.util.constants.LevelRequirements;
import gutizia.util.skills.farming.Farming;
import org.powerbot.script.Random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FruitTreeTracker extends SinglePatchTracker {
    public final static FruitTreeTracker fruitTreeTracker = new FruitTreeTracker();

    private FruitTreeTracker() {
        super();
    }

    @Override
    Map<Integer, Integer> initProduceLevelRequirement() {
        Map<Integer,Integer> map = new HashMap<>();
        map.put(Items.APPLE_SAPLING,LevelRequirements.APPLE);
        map.put(Items.BANANA_SAPLING,LevelRequirements.BANANA);
        map.put(Items.ORANGE_SAPLING,LevelRequirements.ORANGE);
        map.put(Items.CURRY_SAPLING,LevelRequirements.CURRY);
        map.put(Items.PINEAPPLE_SAPLING,LevelRequirements.PINEAPPLE);
        map.put(Items.PAPAYA_SAPLING,LevelRequirements.PAPAYA);
        map.put(Items.PALM_TREE_SAPLING,LevelRequirements.PALM_TREE);
        map.put(Items.DRAGONFRUIT_SAPLING,LevelRequirements.DRAGONFRUIT);
        return map;
    }

    @Override
    int initAmountOfPatches() {
        return 4; // TODO make dynamic
    }

    @Override
    void setPatchOrder() {
        patchOrder = new ArrayList<>();
        ArrayList<Integer> storedIntegers = new ArrayList<>();
        int random;

        for (int i = 0; i < 5;i++) {
            do {
                random = Random.nextInt(1,7);
            } while (storedIntegers.contains(random));
            storedIntegers.add(random);

            switch (random) {
                case 1:
                    patchOrder.add(Farming.FarmingSpot.GNOME_STRONGHOLD_FT);
                    break;
                case 2:
                    patchOrder.add(Farming.FarmingSpot.CATHERBY_FT);
                    patchOrder.add(Farming.FarmingSpot.BRIMHAVEN_FT);
                    break;
                case 3:
                    patchOrder.add(Farming.FarmingSpot.GNOME_VILLAGE_FT);
                    break;
                case 4:
                    //patchOrder.add(Farming.FarmingSpot.LLETYA_FT);
                    break;
                case 5:
                    //patchOrder.add(Farming.FarmingSpot.FARMING_GUILD_FT);
                    break;

            }
        }
    }
}
