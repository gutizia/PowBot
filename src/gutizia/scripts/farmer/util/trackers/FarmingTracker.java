package gutizia.scripts.farmer.util.trackers;

public class FarmingTracker {
    public final static FarmingTracker farmingTracker = new FarmingTracker();

    private boolean herbsActivated;
    private boolean flowersActivated;
    private boolean allotmentsActivated;
    private boolean hopsActivated;
    private boolean treesActivated;
    private boolean fruitTreesActivated;
    private boolean usingRunes;

    public boolean isUsingRunes() {
        return usingRunes;
    }

    public void setUsingRunes(boolean usingRunes) {
        System.out.println("setting usingRunes to: " + usingRunes);
        this.usingRunes = usingRunes;
    }

    public void setHerbsActivated(boolean herbsActivated) {
        this.herbsActivated = herbsActivated;
    }

    public void setFlowersActivated(boolean flowersActivated) {
        this.flowersActivated = flowersActivated;
    }

    public void setAllotmentsActivated(boolean allotmentsActivated) {
        this.allotmentsActivated = allotmentsActivated;
    }

    public void setFruitTreesActivated(boolean fruitTreesActivated) {
        this.fruitTreesActivated = fruitTreesActivated;
    }

    public void setHopsActivated(boolean hopsActivated) {
        this.hopsActivated = hopsActivated;
    }

    public void setTreesActivated(boolean treesActivated) {
        this.treesActivated = treesActivated;
    }

    public boolean isHerbsActivated() {
        return herbsActivated;
    }

    public boolean isFlowersActivated() {
        return flowersActivated;
    }

    public boolean isAllotmentsActivated() {
        return allotmentsActivated;
    }

    public boolean isFruitTreesActivated() {
        return fruitTreesActivated;
    }

    public boolean isHopsActivated() {
        return hopsActivated;
    }

    public boolean isTreesActivated() {
        return treesActivated;
    }
}
