package gutizia.util.listeners;


import java.util.EventListener;

public interface InventoryListener extends EventListener {

    void onInventoryChange(InventoryEvent inventoryEvent);
}