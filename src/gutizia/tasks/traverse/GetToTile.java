package gutizia.tasks.traverse;

import gutizia.tasks.Activatable;
import gutizia.tasks.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class GetToTile extends Task {

    private Activatable activatable;
    private Tile tile;

    public GetToTile(ClientContext ctx, Activatable activatable, Tile tile) {
        super(ctx);
        this.activatable = activatable;
        this.tile = tile;
    }

    @Override
    public void execute() {
        ctx.movement.step(tile);
        Condition.wait(() -> ctx.players.local().tile().equals(tile) || ctx.players.local().inMotion(), 600, 3);
        Condition.wait(() -> ctx.players.local().tile().equals(tile) || !ctx.players.local().inMotion(), 600, 10);
    }

    @Override
    public boolean activate() {
        return activatable.activate();
    }
}
