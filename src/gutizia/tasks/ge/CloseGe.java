package gutizia.tasks.ge;

import gutizia.tasks.Activatable;
import gutizia.tasks.Task;
import gutizia.util.ge.GrandExchange;
import org.powerbot.script.Condition;

public class CloseGe extends Task {

    private Activatable activatable;

    public CloseGe(Activatable activatable) {
        this.activatable = activatable;
    }

    @Override
    public void execute() {
        ctx.widgets.component(GrandExchange.W_MAIN, 2, 11).click();
        Condition.wait(() -> ctx.widgets.widget(GrandExchange.W_MAIN).valid(), 1300, 2);
    }

    @Override
    public boolean activate() {
        if (!ctx.widgets.widget(GrandExchange.W_MAIN).valid()) {
            return false;
        }

        return activatable.activate();
    }
}
