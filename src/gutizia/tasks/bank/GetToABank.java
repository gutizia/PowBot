package gutizia.tasks.bank;

import gutizia.tasks.Activatable;
import gutizia.tasks.Task;
import org.powerbot.script.rt4.ClientContext;

public class GetToABank extends Task {

    private Activatable activatable;

    public GetToABank(Activatable activatable) {
        this.activatable = activatable;
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean activate() {
        if (ctx.bank.nearest().tile().distanceTo(ctx.players.local()) > 10) {
            return false;
        }
        return activatable.activate();
    }
}
