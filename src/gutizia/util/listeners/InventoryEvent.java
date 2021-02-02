package gutizia.util.listeners;

import org.powerbot.script.rt4.Item;

import java.util.EventObject;

public class InventoryEvent extends EventObject {

    private final int inventorySlot;
    private final Item oldItem;
    private final Item newItem;
    private final int invIndex;

    public InventoryEvent(int inventorySlot, Item oldItem, Item newItem, int invIndex) {
        super(inventorySlot);
        this.inventorySlot = inventorySlot;
        this.oldItem = oldItem;
        this.newItem = newItem;
        this.invIndex = invIndex;
    }

    public int getInventorySlot() {
        return inventorySlot;
    }

    public Item getOldItem() {
        return oldItem;
    }

    public Item getNewItem() {
        return newItem;
    }

    public int getInvIndex() {
        return invIndex;
    }
}
