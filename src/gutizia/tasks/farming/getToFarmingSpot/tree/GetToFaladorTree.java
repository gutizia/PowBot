package gutizia.tasks.farming.getToFarmingSpot.tree;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class GetToFaladorTree extends GetToFarmSpot {

    public GetToFaladorTree(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        if (teleportManager.isInFaladorParkTeleportArea()) {
            ctx.movement.step(new Tile(3004, 3375, 0));
        }
    }

    @Override
    public boolean activate() {
        return !PatchAreas.faladorTreeArea.containsOrIntersects(ctx.players.local());
    }
}
