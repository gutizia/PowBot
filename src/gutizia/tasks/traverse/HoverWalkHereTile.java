package gutizia.tasks.traverse;

import gutizia.tasks.Activatable;
import gutizia.tasks.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.MenuCommand;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.TileMatrix;

public class HoverWalkHereTile extends Task {

    private Activatable activatable;
    private TileMatrix tileMatrix;

    public HoverWalkHereTile(ClientContext ctx, Activatable activatable, Tile tile) {
        super(ctx);
        this.activatable = activatable;
        this.tileMatrix = tile.matrix(ctx);
    }

    @Override
    public void execute() {
        tileMatrix.click(false);
        Condition.wait(ctx.menu::opened, 25, 8);

        if (ctx.menu.opened()) {
            ctx.menu.hover((MenuCommand menuCommand) -> menuCommand.action.equals("Walk here"));
        }
    }

    @Override
    public boolean activate() {
        return activatable.activate();
    }
}
