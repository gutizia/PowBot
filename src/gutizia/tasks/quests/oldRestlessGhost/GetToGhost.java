package gutizia.tasks.quests.oldRestlessGhost;

import gutizia.util.managers.CameraManager;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.managers.CameraManager.cameraManager;
import static gutizia.util.resources.Traversing.traversing;
import static gutizia.util.managers.TeleportManager.teleportManager;

public class GetToGhost extends OldRestlessGhost {

    GetToGhost(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        //overlay.changeStatus("getting to ghost");
        final Tile[] path = new Tile[] {new Tile(3233,3218,0),new Tile(3236,3208,0),new Tile(3240,3201,0),new Tile(3244,3193,0)};
        final Tile doorTile = new Tile(3247,3193,0);
        final Tile goalTile = new Tile(3248,3193,0);
        final int doorID = 1535;
        final int[] doorBounds = {-4, 12, -208, -20, 8, 120};

        teleportManager.teleportToLumbridge(false);

        traversing.walkPath(path,5);

        cameraManager.turnCamera(CameraManager.Direction.EAST);
        traversing.walkPastDoor(doorID,doorTile,goalTile,doorBounds);
    }

    @Override
    public boolean activate() {
        if (ctx.varpbits.varpbit(107) == 4 && // correct state of quest
                !coffinRoom.containsOrIntersects(ctx.players.local())) { // not in coffin room
            return true;
        }

        if (!coffinRoom.containsOrIntersects(ctx.players.local()) && // if not in coffin room
                ctx.varpbits.varpbit(107) == 2) { // ready to get to ghost)
            return true;
        }
        return false;
    }
}
