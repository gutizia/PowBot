package gutizia.tasks;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.overlay.TaskOverlay.taskOverlay;

public class WebWalk extends Task {

    private Activatable activatable;
    private Tile tile;

    public WebWalk(ClientContext ctx, Activatable activatable, Tile tile) {
        super(ctx);
        this.activatable = activatable;
        this.tile = tile;
    }

    @Override
    public void execute() {
        taskOverlay.setActiveTask("webWalk");
        ctx.movement.walkTo(tile);
    }

    @Override
    public boolean activate() {
        return activatable.activate();
    }
}
