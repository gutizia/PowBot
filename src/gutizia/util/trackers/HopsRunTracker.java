package gutizia.util.trackers;

import gutizia.util.constants.Items;
import gutizia.util.constants.LevelRequirements;
import gutizia.util.skills.farming.Farming;
import org.powerbot.script.Random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HopsRunTracker extends SinglePatchTracker {
    public final static HopsRunTracker hopsRunTracker = new HopsRunTracker();

    private HopsRunTracker() {
        super();
    }

    @Override
    Map<Integer, Integer> initProduceLevelRequirement() {
        Map<Integer,Integer> map = new HashMap<>();
        map.put(Items.BARLEY_SEED,LevelRequirements.BARLEY);
        map.put(Items.HAMMERSTONE,LevelRequirements.HAMMERSTONE);
        map.put(Items.ASGARNIAN_SEED,LevelRequirements.ASGARNIAN_HOPS);
        map.put(Items.JUTE_SEED,LevelRequirements.JUTE);
        map.put(Items.YANILLIAN_SEED,LevelRequirements.YANILLIAN_HOPS);
        map.put(Items.KRANDORIAN_SEED,LevelRequirements.KRANDORIAN_HOPS);
        map.put(Items.WILDBLOOD_SEED,LevelRequirements.WILDBLOOD);
        return map;
    }

    @Override
    int initAmountOfPatches() {
        return 4;
    }

    @Override
    void setPatchOrder() {
        patchOrder = new ArrayList<>();
        ArrayList<Integer> storedIntegers = new ArrayList<>();
        int random;

        for (int i = 0; i < 4;i++) {
            do {
                random = Random.nextInt(1,5);
            } while (storedIntegers.contains(random));
            storedIntegers.add(random);

            switch (random) {
                case 1:
                    patchOrder.add(Farming.FarmingSpot.CHAMP_GUILD_HOPS);
                    break;
                case 2:
                    patchOrder.add(Farming.FarmingSpot.ENTRANA_HOPS);
                    break;
                case 3:
                    patchOrder.add(Farming.FarmingSpot.CAMELOT_HOPS);
                    break;
                case 4:
                    patchOrder.add(Farming.FarmingSpot.YANILLE_HOPS);
                    break;
            }
        }
    }
}
