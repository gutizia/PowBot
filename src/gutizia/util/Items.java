package gutizia.util;

import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;
import java.util.Objects;

public class Items {
    private ArrayList<Item> items;

    public Items() {
        items = new ArrayList<>();
    }

    public Items(org.powerbot.script.rt4.Item[] invItems) {
        items = new ArrayList<>();
        for (org.powerbot.script.rt4.Item i : invItems) {
            items.add(new Item(i.id(), i.name(), i.stackSize()));
        }
    }

    public void addItem(Item item) {
        if (contains(item.getId())) {
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

    public ArrayList<String> getDisplayInfo() {
        ArrayList<String> strings = new ArrayList<>();
        for (Item item : items) {
            strings.add(item.getId() + "(" + item.getName() + " )" + ": " + item.getAmount());
        }
        return strings;
    }

    public boolean haveAllInInventory(ClientContext ctx) {
        Items invItems = new Items(ctx.inventory.items());
        return this.equals(invItems);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Items items1 = (Items) o;
        return Objects.equals(items, items1.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}
