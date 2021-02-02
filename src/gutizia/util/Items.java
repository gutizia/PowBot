package gutizia.util;

import gutizia.scripts.Script;
import gutizia.util.managers.BankManager;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;
import java.util.Objects;

public class Items {
    private ArrayList<Item> items;

    public final static Items INVENTORY = new Items(ClientContext.ctx().inventory.items());
    public final static Items EMPTY_INVENTORY = new Items(
            new Item(-1), new Item(-1), new Item(-1), new Item(-1), new Item(-1), new Item(-1), new Item(-1),
            new Item(-1), new Item(-1), new Item(-1), new Item(-1), new Item(-1), new Item(-1), new Item(-1),
            new Item(-1), new Item(-1), new Item(-1), new Item(-1), new Item(-1), new Item(-1), new Item(-1),
            new Item(-1), new Item(-1), new Item(-1), new Item(-1), new Item(-1), new Item(-1), new Item(-1));

    public Items() {
        items = new ArrayList<>();
    }

    public Items(Item... items) {
        this.items = new ArrayList<>();
        for (Item item : items) {
            addItem(item);
        }
    }

    private Items(org.powerbot.script.rt4.Item[] invItems) {
        items = new ArrayList<>();
        for (org.powerbot.script.rt4.Item i : invItems) {
            items.add(new Item(i.id(), i.name(), i.stackSize()));
        }
    }

    public void addItem(Item item) {
        if (contains(item.getId()) && item.getId() != -1) {
            Item toRemove = null;
            for (Item i : items) {
                if (i.getId() == item.getId()) {
                    toRemove = i;
                    item.setAmount(item.getAmount() + i.getAmount());
                    break;
                }
            }
            items.remove(toRemove);
        }
        items.add(item);
    }

    public void removeItem(String name, int amount) {
        Item toRemove = null;
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                toRemove = item;
                break;
            }
        }
        if (toRemove != null) {
            items.remove(toRemove);
            if (amount < toRemove.getAmount()) {
                toRemove.setAmount(toRemove.getAmount() - amount);
                items.add(toRemove);
            }
        }
    }

    public void removeItem(String name) {
        removeItem(name, Integer.MAX_VALUE);
    }

    public void removeItem(int itemId, int amount) {
        Item toRemove = null;
        for (Item item : items) {
            if (item.getId() == itemId) {
                toRemove = item;
                break;
            }
        }
        if (toRemove != null) {
            items.remove(toRemove);
            if (amount < toRemove.getAmount()) {
                toRemove.setAmount(toRemove.getAmount() - amount);
                items.add(toRemove);
            }
        }
    }

    public void removeItem(int itemId) {
        removeItem(itemId, Integer.MAX_VALUE);
    }

    public void addUniqueItems(Items newItems) {
        for (Item item : newItems.getItems()) {
            if (!contains(item.getId())) {
                items.add(item);
            }
        }
    }

    public boolean contains(int itemId) {
        for (Item item : items) {
            if (item.getId() == itemId) {
                return true;
            }
        }
        return false;
    }

    public Item getItem(int itemId) {
        for (Item item : items) {
            if (item.getId() == itemId) {
                return item;
            }
        }
        return new Item(-1);
    }

    public boolean containsAll(int... itemIds) {
        for (int itemId : itemIds) {
            if (!contains(itemId)) {
                return false;
            }
        }
        return true;
    }

    public boolean containsAll(Items items) {
        for (Item item : items.getItems()) {
            Item i = getItem(item.getId());
            if (i.getId() != item.getId() || i.getAmount() < item.getAmount()) {
                return false;
            }
        }
        return true;
    }

    public boolean contains(String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int[] getIds() {
        int[] itemIds = new int[items.size()];
        int i = 0;
        for (Item item : items) {
            itemIds[i++] = item.getId();
        }
        return itemIds;
    }

    private ArrayList<Integer> getSharedIds() {
        ArrayList<Integer> integers = new ArrayList<>();
        for (Item item : items) {
            integers.add(item.getSharedId());
        }
        return integers;
    }

    public ArrayList<String> getDisplayInfo() {
        ArrayList<String> strings = new ArrayList<>();
        for (Item item : items) {
            strings.add(item.getId() + "(" + item.getName() + " )" + ": " + item.getAmount());
        }
        return strings;
    }

    public boolean haveAllInInventory() {
        Items invItems = new Items(ClientContext.ctx().inventory.items());
        return matches(invItems);
    }

    /**
     * checks if the items in this object matches the desired Items's list of items
     * it has to have all the same items, and if item is stackable more or equal quantity
     * no specific order needed
     * @param desiredItems the list of items you want to have
     * @return returns true only if all items match and in the right quantity
     */
    public boolean matches(Items desiredItems) {
        System.out.println("checking if two Items is matching...");
        if (!this.getSharedIds().containsAll(desiredItems.getSharedIds())) {
            System.out.println("this.getSharedIds did not containAll of desiredItems.getSharedIds");
            return false;
        }
        if (!desiredItems.getSharedIds().containsAll(this.getSharedIds())) {
            System.out.println("desiredItems.getSharedIds did not containAll of this.getSharedIds");
            return false;
        }
        if (!containsAll(desiredItems)) {
            System.out.println("this.items did not contain all items for desiredItems");
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}
