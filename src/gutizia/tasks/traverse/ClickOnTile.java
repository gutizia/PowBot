package gutizia.tasks.traverse;

import gutizia.tasks.Activatable;
import gutizia.tasks.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.TileMatrix;

import static gutizia.util.combat.PlayerCombat.playerCombat;
import static gutizia.util.overlay.TaskOverlay.taskOverlay;


public class ClickOnTile extends Task {
    private Activatable activatable;
    private TileMatrix matrix;
    private boolean leftClick;
    private boolean hop;

    public ClickOnTile(ClientContext ctx, Activatable activatable, TileMatrix matrix) {
        super(ctx);
        this.activatable = activatable;
        this.matrix = matrix;
        this.leftClick = false;
        this.hop = false;
    }

    public ClickOnTile(ClientContext ctx, Activatable activatable, TileMatrix matrix, boolean leftClick, boolean hop) {
        super(ctx);
        this.activatable = activatable;
        this.matrix = matrix;
        this.leftClick = leftClick;
        this.hop = hop;
    }

    @Override
    public void execute() {
        taskOverlay.setActiveTask("ClickOnTile");
        if (leftClick) {
            if (hop) {
                ctx.input.hop(matrix.centerPoint());
                Condition.sleep(30);
                ctx.input.click(true);
            } else {
                matrix.click();
            }
        } else {
            if (hop) {
                ctx.input.hop(matrix.centerPoint());
            }
            matrix.interact(false, "Walk here");
        }
        playerCombat.setAttacking(false);
    }

    @Override
    public boolean activate() {
        if (ctx.movement.destination().equals(matrix.tile()) || ctx.players.local().tile().equals(matrix.tile())) {
            return false;
        }
        return activatable.activate();
    }
}
