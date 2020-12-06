package gutizia.tasks.traverse;

import gutizia.tasks.Task;
import gutizia.util.Interact;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class OpenGate extends Task {

    private Tile gateTile;

    public OpenGate(ClientContext ctx, Tile gateTile) {
        super(ctx);
        this.gateTile = gateTile;
    }

    @Override
    public void execute() {
        Interact.interact(ctx, ctx.objects.poll(), true, "Open", true);
    }

    @Override
    public boolean activate() {
        return !ctx.objects.select(15).at(gateTile).name("Gate").action("Open").isEmpty();
    }
}
