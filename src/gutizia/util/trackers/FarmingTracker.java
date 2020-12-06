package gutizia.util.trackers;

public class FarmingTracker {
    public final static FarmingTracker farmingTracker = new FarmingTracker();

    private boolean herbsActivated;
    private boolean flowersActivated;
    private boolean allotmentsActivated;
    private boolean hopsActivated;
    private boolean treesActivated;
    private boolean fruitTreesActivated;
    private boolean usingRunes;

    private int expGained = 0;
    private int moneyMade = 0;

    private int herbsFarmed = 0;
    private int flowersFarmed = 0;
    private int allotmentsFarmed = 0;
    private int hopsFarmed = 0;
    private int fruitsFarmed = 0;

    private int treesPlanted = 0;
    private int fruitTreesPlanted = 0;
    private int treesCheckedHealthOf = 0;
    private int fruitTreesCheckedHealthOf = 0;

    private FarmingTracker() {}

    public boolean isUsingRunes() {
        return usingRunes;
    }

    public void setUsingRunes(boolean usingRunes) {
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

    public int getExpGained() {
        return expGained;
    }

    public int getAllotmentsFarmed() {
        return allotmentsFarmed;
    }

    public int getFlowersFarmed() {
        return flowersFarmed;
    }

    public int getFruitsFarmed() {
        return fruitsFarmed;
    }

    public int getFruitTreesCheckedHealthOf() {
        return fruitTreesCheckedHealthOf;
    }

    public int getFruitTreesPlanted() {
        return fruitTreesPlanted;
    }

    public int getHerbsFarmed() {
        return herbsFarmed;
    }

    public int getHopsFarmed() {
        return hopsFarmed;
    }

    public int getMoneyMade() {
        return moneyMade;
    }

    public int getTreesCheckedHealthOf() {
        return treesCheckedHealthOf;
    }

    public int getTreesPlanted() {
        return treesPlanted;
    }

    public void increaseExpGained(int increment) {
       expGained += increment;
    }

    public void increaseMoneyMade(int increment) {
        moneyMade += increment;
    }

    public void increaseHerbsFarmed() {
        herbsFarmed++;
    }

    public void increaseFlowersFarmed() {
        flowersFarmed++;
    }

    public void increaseAllotmentsFarmed() {
        allotmentsFarmed++;
    }

    public void increaseHopsFarmed() {
        hopsFarmed++;
    }

    public void increaseFruitsFarmed() {
        fruitsFarmed++;
    }

    public void increaseFruitTreesPlanted() {
        fruitTreesPlanted++;
    }

    public void increaseFruitTreesCheckedHealthOf() {
        fruitTreesCheckedHealthOf++;
    }

    public void increaseTreesCheckedHealthOf() {
        treesCheckedHealthOf++;
    }

    public void increaseTreesPlanted() {
        treesPlanted++;
    }

    public int getProduceGained() {
        return fruitsFarmed + herbsFarmed + flowersFarmed + allotmentsFarmed + hopsFarmed;
    }
}
