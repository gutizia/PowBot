package gutizia.util.trackers;

import gutizia.util.skills.farming.Farming;

import java.util.ArrayList;

public abstract class Tracker {
    ArrayList<Farming.FarmingSpot> patchOrder;
    boolean gottenItems;
    private long elapsedTime;
    private boolean doingRun;

    abstract void setPatchOrder();

    public void reset() {
        setPatchOrder();
        gottenItems = false;
    }

    public long getElapsedTime() {
        elapsedTime = System.currentTimeMillis() - elapsedTime;
        return elapsedTime;
    }

    public boolean isDoingRun() {
        return doingRun;
    }

    public void setDoingRun(boolean doingRun) {
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
}
