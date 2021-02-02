package gutizia.util;

import gutizia.scripts.Script;

public class Inventory {

    private Items items;

    public final static Inventory inventory = new Inventory();

    private Inventory() {
        items = Items.INVENTORY;
    }

    public Items getItems() {
        return items;
    }

    public void addNewItem(Item item, int index) {
        items.getItems().add(index, new Item(item.getId(), item.getName(), item.getAmount()));
    }

    public void addNewItem(org.powerbot.script.rt4.Item item) {
        if (items.getItems().size() >= 28) {
            Script.stopScript("error! trying to add more items into inventory than allowed");
            return;
        }

        items.getItems().add(item.inventoryIndex(), new Item(item.id(), item.name(), item.stackSize()));
    }

    public void removeOldItem(int index) {
        items.getItems().remove(index);
    }

    public void removeOldItem(org.powerbot.script.rt4.Item item) {
        if (item.inventoryIndex() != -1) {
            items.getItems().remove(item.inventoryIndex());
        }
    }
}
