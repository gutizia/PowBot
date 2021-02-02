package gutizia.tasks.bank;

import gutizia.scripts.Script;
import gutizia.tasks.Task;
import gutizia.util.Item;
import gutizia.util.Items;
import gutizia.util.managers.BankManager;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.Inventory.inventory;

public class WithdrawItems extends Task {

    private Items items;
    private boolean mustMatch;

    public WithdrawItems(Items items, boolean mustMatch) {
        this.items = items;
        this.mustMatch = mustMatch;
    }

    @Override
    public void execute() {
        if (ctx.bank.open()) {
            ctx.bank.depositInventory();
            BankManager bankManager = new BankManager(ClientContext.ctx());
            int itemId = -1;
            int amount = 1;
            for (Item item : items.getItems()) {
                if (item.getId() == -1) {
                    continue;
                }
                if (item.hasMultipleIds()) {
                    Script.stopScript("haven't implemented withdrawing multiple ids yet, contact me and maybe i'll give a fuck and do something about it");
                }
                if (item.getAmount() > 1) {
                    bankManager.withdrawItem(item.getId(), item.getAmount());
                    itemId = -1;
                    continue;
                }

                if (item.getId() == itemId) {
                    amount++;
                    System.out.println("another " + item.getName() +  "(" + item.getId() + ")" + " found. (new total: " + amount + ")");
                    continue;
                }

                if (itemId != -1) {
                    System.out.println("withdrawing item: " + "(" + itemId + "," + amount + ")");
                    bankManager.withdrawItem(itemId, amount);
                }

                System.out.println("new item found: " + item.getName() + "(" + item.getId() + ")");
                itemId = item.getId();
                amount = 1;
            }
            System.out.println("got all items: " + items.haveAllInInventory());
        }
        ctx.bank.close();
    }

    @Override
    public boolean activate() {
        if (ctx.bank.nearest().tile().distanceTo(ctx.players.local()) > 10 || !ctx.bank.nearest().tile().matrix(ctx).reachable()) {
            return false;
        }

        if (mustMatch) {
            return !items.matches(inventory.getItems());

        } else {
            return !inventory.getItems().containsAll(items);
        }
    }
}
