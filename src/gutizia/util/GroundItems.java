package gutizia.util;

import org.powerbot.script.rt4.GroundItem;

import java.util.ArrayList;

public class GroundItems {
    private ArrayList<GroundItem> groundItems;

    public GroundItems() {
        groundItems = new ArrayList<>();
    }

    public void addGroundItem(GroundItem groundItem) {
        groundItems.add(groundItem);
    }

    public void addItem(GroundItem item) {
        if (!contains(item)) {
            groundItems.add(item);
        }
    }

    public boolean contains(GroundItem groundItem) {
        for (GroundItem item : groundItems) {
            if (item.equals(groundItem)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<GroundItem> getGroundItems() {
        return groundItems;
    }
}
