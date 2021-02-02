package gutizia.util.ge;

import gutizia.tasks.Task;
import gutizia.tasks.ge.OpenGe;

public class CollectOffer extends Task {

    @Override
    public void execute() {
        if (GrandExchange.AREA.containsOrIntersects(ctx.players.local())) {
            new OpenGe(() -> true).execute();
        }

    }

    @Override
    public boolean activate() {
        return ctx.bank.nearest().tile().distanceTo(ctx.players.local()) < 10;
    }
}
