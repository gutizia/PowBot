package gutizia.tasks.traverse;

import gutizia.tasks.Activatable;
import gutizia.tasks.Task;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.combat.PlayerCombat.playerCombat;

public class ClickOnMinimap extends Task {
    private Activatable activatable;
    private Tile tile;

    public ClickOnMinimap(ClientContext ctx, Activatable activatable, Tile tile) {
        super(ctx);
        this.activatable = activatable;
        this.tile = tile;
    }

    @Override
    public void execute() {
        ctx.movement.step(tile);
        playerCombat.setAttacking(false);
    }

    @Override
    public boolean activate() {
        return activatable.activate();
    }
}
