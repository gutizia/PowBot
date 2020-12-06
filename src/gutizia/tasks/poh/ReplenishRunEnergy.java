package gutizia.tasks.poh;

import gutizia.tasks.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

public class ReplenishRunEnergy extends Task {

    private final int threshold;

    public ReplenishRunEnergy(ClientContext ctx) {
        super(ctx);
        this.threshold = 0;
    }

    public ReplenishRunEnergy(ClientContext ctx, int threshold) {
        super(ctx);
        this.threshold = threshold;
    }

    @Override
    public void execute() {
        GameObject pool = ctx.objects.select(10).name("Ornate rejuvenation pool").poll();

        if (!pool.inViewport()) {
            ctx.camera.turnTo(pool);
        }

        pool.interact("Drink");

        Condition.wait(() -> ctx.movement.energyLevel() == 100, 1200, 6);
    }

    @Override
    public boolean activate() {
        return false;
    }
}
