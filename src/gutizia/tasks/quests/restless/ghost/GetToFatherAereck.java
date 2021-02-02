package gutizia.tasks.quests.restless.ghost;

import gutizia.tasks.Task;
import gutizia.util.managers.CameraManager;
import org.powerbot.script.Tile;

import static gutizia.util.managers.CameraManager.cameraManager;
import static gutizia.util.managers.TeleportManager.teleportManager;
import static gutizia.util.overlay.TaskOverlay.taskOverlay;
import static gutizia.util.resources.Traversing.traversing;

public class GetToFatherAereck extends Task {

    @Override
    public void execute() {
        taskOverlay.setActiveTask("get to Aereck");
        final Tile doorTile = new Tile(3238,3210,0);
        final Tile goalTile = new Tile(3242,3206,0);
        final int doorID = 1540;
        final int[] doorBounds = {-4, 8, -220, -16, 12, 112};

        if (!traversing.isInLumbridgeCourtyard()) {
            teleportManager.teleportToLumbridge(false);
        }

        if (traversing.isInLumbridgeCourtyard()) {
            traversing.walkPath(RestlessGhost.LUMBRIDGE_CHURCH,4);
            cameraManager.turnCamera(CameraManager.Direction.EAST);
            traversing.walkPastDoor(doorID,doorTile,goalTile,doorBounds);
        }
    }

    @Override
    public boolean activate() {
        return !RestlessGhost.CHURCH.containsOrIntersects(ctx.players.local()) && // not in church
                ctx.varpbits.varpbit(107) == 0; // have not started quest yet
    }
}
