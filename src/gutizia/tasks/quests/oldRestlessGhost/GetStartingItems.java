package gutizia.tasks.quests.oldRestlessGhost;

import gutizia.util.constants.Runes;
import org.powerbot.script.rt4.ClientContext;

public class GetStartingItems extends OldRestlessGhost {

    GetStartingItems(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        //overlay.changeStatus("getting starting items");
        bankManager.openABank();
        ctx.bank.depositInventory();
        ctx.bank.depositEquipment();
        bankManager.withdrawItems(requiredItems);
        ctx.bank.close();
    }

    @Override
    public boolean activate() {
        int[] items = new int[] {Runes.AIR, Runes.EARTH, Runes.LAW};
        return ctx.inventory.select().id(items).size() != 3 && // if don't have teleportation runes
                ctx.inventory.select().size() > 20; // if have more than 20 items in inventory
    }
}
