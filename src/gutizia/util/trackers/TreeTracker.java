package gutizia.util.trackers;

import gutizia.util.constants.Items;
import gutizia.util.constants.LevelRequirements;

import java.util.HashMap;
import java.util.Map;

public class TreeTracker extends SinglePatchTracker {
    public final static TreeTracker treeTracker = new TreeTracker();

    private TreeTracker() {
        super();
    }

    @Override
    Map<Integer, Integer> initProduceLevelRequirement() {
        Map<Integer,Integer> map = new HashMap<>();
        map.put(Items.OAK_SAPLING,LevelRequirements.OAK_SAPLING);
        map.put(Items.WILLOW_SAPLING,LevelRequirements.WILLOW_SAPLING);
        map.put(Items.MAPLE_SAPLING,LevelRequirements.MAPLE_SAPLING);
        map.put(Items.YEW_SAPLING,LevelRequirements.YEW_SAPLING);
        map.put(Items.MAGIC_SAPLING,LevelRequirements.MAGIC_SAPLING);

        return map;
    }

    @Override
    int initAmountOfPatches() {
        return 4; // TODO make dynamic
    }

    // TODO finish this
    @Override
    void setPatchOrder() {

    }

}
