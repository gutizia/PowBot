package gutizia.tasks.traverse;

import gutizia.tasks.Activatable;
import gutizia.tasks.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;

public class SetRun extends Task {

    private boolean run;
    private Activatable activatable = null;

    public SetRun(ClientContext ctx, boolean run) {
        super(ctx);
        this.run = run;
    }

    public SetRun(ClientContext ctx, Activatable activatable, boolean run) {
        super(ctx);
        this.run = run;
        this.activatable = activatable;
    }

    @Override
    public void execute() {
        Condition.sleep(Random.nextGaussian(200, 400, 330));
        ctx.input.hop(ctx.widgets.widget(Constants.MOVEMENT_MAP).component(Constants.MOVEMENT_RUN_ENERGY - 1).centerPoint());
        Condition.wait(() -> ctx.menu.containsAction("Toggle Run"), 20, 10);
        ctx.input.click(true);
    }

    @Override
    public boolean activate() {
        if (activatable != null) {
            return activatable.activate();
        }

        return ctx.movement.running() != run;
    }
}
