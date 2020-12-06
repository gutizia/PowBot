package gutizia.tasks.traverse;

import gutizia.tasks.Task;
import gutizia.util.TraverseUtil;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class ClickNextTile extends Task {

    public ClickNextTile(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        ctx.movement.step(TraverseUtil.getNextTile(ctx));
    }

    @Override
    public boolean activate() {
        return !ctx.players.local().inMotion() || ctx.movement.destination().equals(Tile.NIL) ||
                ctx.movement.destination().distanceTo(ctx.players.local()) < TraverseUtil.getNextTileLimit();
    }
}
