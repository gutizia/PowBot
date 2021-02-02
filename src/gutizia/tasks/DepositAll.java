package gutizia.tasks;

import gutizia.util.Interact;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;

public class DepositAll extends Task {

    @Override
    public void execute() {
        if (!ctx.bank.opened()) {
            ctx.bank.open();
        }

        ctx.bank.depositInventory();

        ctx.bank.close();
    }

    @Override
    public boolean activate() {
        return ctx.bank.nearest().tile().distanceTo(ctx.players.local()) < Interact.MAX_INTERACT_DISTANCE && !ctx.inventory.isEmpty();
    }
}
