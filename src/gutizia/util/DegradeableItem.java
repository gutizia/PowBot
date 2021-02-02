package gutizia.util;

public class DegradeableItem {
    private int[] allIds;
    private String name;

    DegradeableItem(int[] allIds, String name) {
        this.allIds = allIds;
        this.name = name;
    }

    /**
     * checks if the given id is contained within stored ids for the specific item
     * @param id the id of the item to check
     * @return returns true if match is found
     */
    public boolean contains(int id) {
        for (int i : allIds) {
            if (i == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return returns the id of the item in it's unused state or least broken state
     */
    public int getSharedId() {
        return allIds[0];
    }

    /**
     *
     * @param id the id of the item you want to check is usable or not (not in a broken state)
     * @return returns true only if the id is recognized in a list of item ids for that item that is not the broken state
     */
    public boolean isUsable(int id) {
        if (allIds[allIds.length - 1] == id) {
            return false;
        }
        for (int i : allIds) {
            if (id == i) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }
}
