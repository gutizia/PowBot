package gutizia.util.trackers;

import java.util.ArrayList;
import java.util.Map;

public abstract class SinglePatchTracker extends Tracker {
    private final Map<Integer,Integer> produceLevelRequirement;
    private final int amountOfPatches;

    private ArrayList<Integer> seedsUsed = new ArrayList<>();
    private int[] priority;

    abstract Map<Integer, Integer> initProduceLevelRequirement();
    abstract int initAmountOfPatches();

    public SinglePatchTracker() {
        this.amountOfPatches = initAmountOfPatches();
        this.produceLevelRequirement = initProduceLevelRequirement();
    }

    public int getAmountOfPatches() {
        return amountOfPatches;
    }

    public int[] getPriority() {
        return priority;
    }

    public int[] getSeedsUsed() {
        int[] seeds = new int[seedsUsed.size()];
        for (int i = 0; i < seedsUsed.size(); i++) {
            seeds[i] = seedsUsed.get(i);
        }
        return seeds;
    }

    public Map<Integer, Integer> getProduceLevelRequirement() {
        return produceLevelRequirement;
    }

    public void setPriority(int[] priority) {
        this.priority = priority;
    }

    public void setSeedsUsed(ArrayList<Integer> seedsUsed) {
        this.seedsUsed = seedsUsed;
    }
}
