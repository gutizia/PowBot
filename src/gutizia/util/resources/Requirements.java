package gutizia.util.resources;

import gutizia.util.managers.BankManager;
import gutizia.tasks.Task;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * parent class for all things that have requirements
 * first set required stats/levels
 * then set owned items
 * then get missing stats/items
 */

public abstract class Requirements extends Task {
    private final static Logger LOGGER = Logger.getLogger(Requirements.class.getName());

    public Requirements(ClientContext ctx) {
        super(ctx);
        setRequiredItems();
        setRequiredStats();
    }

    protected ArrayList<ArrayList<Integer>> ownedItems = new ArrayList<ArrayList<Integer>>(); // item id, quantity owned
    protected ArrayList<ArrayList<Integer>> missingItems = new ArrayList<ArrayList<Integer>>(); // item id, quantity missing
    protected ArrayList<ArrayList<Integer>> requiredItems = new ArrayList<ArrayList<Integer>>(); // item id, quantity required

    protected ArrayList<ArrayList<Integer>> requiredStats = new ArrayList<ArrayList<Integer>>(); // skill id, levels required
    protected ArrayList<ArrayList<Integer>> missingStats = new ArrayList<ArrayList<Integer>>(); // skill id, levels missing

    /**
     * puts all items you have in your bank into list of owned items
     * this should always be called after banking all items in inventory/equipment
     * requiredItems list of all items needed and how many of them are needed
     * sets a list of all items you own that are in the requiredItems list
     */
    protected void setOwnedItems () {
        int[] itemIDS = new int[requiredItems.size()];
        ownedItems = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> item;

        for (int i = 0;i < requiredItems.size();i++) {
            ArrayList<Integer> requiredItem = requiredItems.get(i);
            itemIDS[i] = requiredItem.get(0);
        }

        for (Item bankItem : ctx.bank.select().id(itemIDS)) {
            item = new ArrayList<Integer>();
            item.add(bankItem.id());
            item.add(bankItem.stackSize());
            ownedItems.add(item);
        }
    }

    /**
     * loops through all required items and checks if they match with owned items
     * ownedItems the items the bot already owns
     * requiredItems the items the bot requires to perform their task
     * sets the items that the bot needs, but don't already have. (itemID,quantity)
     */
    protected void setMissingItems() {
        missingItems = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> missingItem = new ArrayList<Integer>();

        for (ArrayList<Integer> requiredItem : requiredItems) {
            boolean foundMatchingID = false;

            for (ArrayList<Integer> ownedItem : ownedItems) {
                if (requiredItem.get(0).equals(ownedItem.get(0))) {
                    if (ownedItem.get(1) < requiredItem.get(1)) { // if you have the item, but too few, add it to missingItems list...
                        missingItem.add(requiredItem.get(0));
                        missingItem.add(requiredItem.get(1) - ownedItem.get(1));
                        missingItems.add(missingItem);
                        missingItem = new ArrayList<Integer>();
                    }
                    foundMatchingID = true;
                    break;
                }
            }
            if (!foundMatchingID) {
                missingItem.add(requiredItem.get(0));
                missingItem.add(requiredItem.get(1));
                missingItems.add(missingItem);
                missingItem = new ArrayList<Integer>();

            }
        }
    }

    /**
     * checks if you have the required levels in each stat in required stats list.
     * if requirements aren't met, the differential in levels, and the stat is added into the missingStats list
     */
    protected void setMissingStats() {
        missingStats = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> missingStat = new ArrayList<Integer>();

        for (ArrayList<Integer> requiredStat : requiredStats) {
            if (ctx.skills.realLevel(requiredStat.get(0)) < requiredStat.get(1)) { // if player's level in that stat is lower tha required level, add it to list
                missingStat.add(requiredStat.get(0));
                missingStat.add(requiredStat.get(1) - ctx.skills.realLevel(requiredStat.get(0)));
                missingStats.add(missingStat);
                missingStat = new ArrayList<Integer>();
            }
        }
    }

    protected void getRequiredItemsFromBank() {
        BankManager bankManager = new BankManager(ctx);
        bankManager.openABank();
        ctx.bank.depositInventory();

        setOwnedItems();
        setMissingItems();

        if (!missingItems.isEmpty()) {
            LOGGER.info("missing some items:");
            for (ArrayList<Integer> item : missingItems) {
                LOGGER.info(Integer.toString(item.get(0)));
            }
        }

        bankManager.withdrawItems(requiredItems);
        ctx.bank.close();
    }

    protected boolean haveAllRequiredItemsInInventory() {
        ArrayList<ArrayList<Integer>> missingItems = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> missingItem = new ArrayList<Integer>();


        for (ArrayList<Integer> requiredItem : requiredItems) {
            boolean foundMatchingID = false;

            for (Item inventoryItem : ctx.inventory.select()) {
                if (requiredItem.get(0).equals(inventoryItem.id())) {
                    if (inventoryItem.stackSize() < requiredItem.get(1)) { // if you have the item, but too few, add it to missingItems list...
                        missingItem.add(requiredItem.get(0));
                        missingItem.add(requiredItem.get(1) - inventoryItem.stackSize());
                        missingItems.add(missingItem);
                        missingItem = new ArrayList<Integer>();
                    }
                    foundMatchingID = true;
                    break;
                }
            }
            if (!foundMatchingID) {
                missingItem.add(requiredItem.get(0));
                missingItem.add(requiredItem.get(1));
                missingItems.add(missingItem);
                missingItem = new ArrayList<Integer>();
            }
        }
        if (!missingItems.isEmpty()) {
            LOGGER.info("missing items from inventory:");
            for (ArrayList<Integer> item : missingItems) {
                LOGGER.info(Integer.toString(item.get(0)) + "," + Integer.toString(item.get(1)));
            }
        }

        return missingItems.isEmpty();
    }

    abstract protected void setRequiredItems();
    abstract protected void setRequiredStats();
    abstract protected ArrayList<Task> getTasks();

    protected int[] getItemIDs(ArrayList<ArrayList<Integer>> itemList) {
        int[] itemIDs = new int[itemList.size()];

        for (int i = 0;i < itemIDs.length;i++) {
            itemIDs[i] = itemList.get(i).get(0);
        }
        return itemIDs;
    }
}