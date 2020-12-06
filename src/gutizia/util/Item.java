package gutizia.util;

import java.util.List;

public class Item {
    private int id;
    private DegradeableItem degradeableItem = null;
    private String name;
    private int amount;

    public Item(int id, String name, int amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        initSharedId();
    }

    public Item(int id) {
        this(id, "no name", 1);
    }

    public Item(int id, int amount) {
        this(id, "no name", amount);
    }

    public Item(int id, String name) {
        this(id, name, 1);
    }

    private void initSharedId() {
        degradeableItem = MultiIdItems.getDegradeableItemContaining(id);
    }

    public static int indexOf(List<Item> list, int itemId) {
        int i = 0;
        for (Item item : list) {
            if (item.id == itemId) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    /**
     *
     * @return returns the shared id of the degradeable item, if no degradeable item returns regular id
     */
    public int getSharedId() {
        if (degradeableItem != null) {
            return degradeableItem.getSharedId();
        }
        return id;
    }

    public int getId() {
        return id;
    }

    /**
     *
     * @return returns the name of the degradeable item, if no degradeable item returns regular name
     */
    public String getSharedName() {
        if (degradeableItem != null) {
            return degradeableItem.getName();
        }
        return name;
    }

    public String getName() {
        return name;
    }

    public DegradeableItem getDegradeableItem() {
        return degradeableItem;
    }

    public void setName(String name) {
        this.name = name;
    }
}
