package gutizia.scripts.farmer.util.trackers;


import gutizia.util.skills.farming.Farming;

import java.util.ArrayList;

public abstract class Run {
    ArrayList<Farming.FarmingSpot> patchOrder = new ArrayList<>();
    private boolean gottenItems = false;
    private long elapsedTime;
    private boolean doingRun;

    private static int totalExpGained;
    private int expGained;
    private int runExpGained;

    private static int totalMoneyMade;
    private int moneyMade;
    private int runMoneyMade;

    private int runsDone = 0;

    abstract void setPatchOrder();
    abstract void reset();

    Run() {}

    void resetRunValues() {
        this.elapsedTime = System.currentTimeMillis();
        this.runExpGained = 0;
        this.runMoneyMade = 0;
        setPatchOrder();
    }

    public long getElapsedTime() {
        if (doingRun) {
            return System.currentTimeMillis() - elapsedTime;
        }
        return 0;
    }

    public boolean isDoingRun() {
        return doingRun;
    }

    public void setDoingRun(boolean doingRun) {
        if (this.doingRun && !doingRun) {
            runsDone++;
        }
        this.doingRun = doingRun;
    }

    public void setGottenItems(boolean gottenItems) {
        this.gottenItems = gottenItems;
    }

    public boolean haveGottenItems() {
        return gottenItems;
    }

    public ArrayList<Farming.FarmingSpot> getPatchOrder() {
        return patchOrder;
    }

    public void removeFromPatchOrder(int index) {
        patchOrder.remove(index);
    }

    public static int getTotalExpGained() {
        return totalExpGained;
    }

    public int getExpGained() {
        return expGained;
    }

    public int getRunExpGained() {
        return runExpGained;
    }

    public static int getTotalMoneyMade() {
        return totalMoneyMade;
    }

    public int getMoneyMade() {
        return moneyMade;
    }

    public int getRunMoneyMade() {
        return runMoneyMade;
    }

    public int getRunsDone() {
        return runsDone;
    }

    public void updateExpGained(int exp) {
        Run.totalExpGained += exp;
        this.expGained += exp;
        this.runExpGained += exp;
    }

    public void updateMoneyMade(int money) {
        Run.totalMoneyMade += money;
        this.moneyMade += money;
        this.runMoneyMade += money;
    }
}
