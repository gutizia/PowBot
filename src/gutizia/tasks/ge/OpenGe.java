package gutizia.tasks.ge;

import gutizia.tasks.Activatable;
import gutizia.tasks.Task;
import gutizia.util.ge.GrandExchange;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.Npc;

public class OpenGe extends Task {

    private Activatable activatable;

    public OpenGe(Activatable activatable) {
        this.activatable = activatable;
    }

    @Override
    public void execute() {
        Npc npc = ctx.npcs.select().action("Exchange").nearest().poll();

        npc.interact("Exchange");
        Condition.wait(() -> ctx.widgets.component(GrandExchange.W_MAIN, 0).visible(), 1000, 10);

        if (!GrandExchange.isOffersSet() && ctx.widgets.widget(GrandExchange.W_MAIN).valid()) {
            Condition.sleep(1000);
            GrandExchange.initOffers();
        }
    }

    @Override
    public boolean activate() {
        if (!GrandExchange.AREA.containsOrIntersects(ctx.players.local()) || ctx.widgets.widget(GrandExchange.W_MAIN).valid()) {
            return false;
        }

        return activatable.activate();
    }
}
