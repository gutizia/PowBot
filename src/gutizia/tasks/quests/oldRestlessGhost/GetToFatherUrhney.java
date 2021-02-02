package gutizia.tasks.quests.oldRestlessGhost;

import gutizia.util.managers.CameraManager;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.managers.CameraManager.cameraManager;
import static gutizia.util.resources.Traversing.traversing;

public class GetToFatherUrhney extends OldRestlessGhost {

    GetToFatherUrhney(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        //overlay.changeStatus("getting to father Urhney");
        final  Tile[] LUMBRIDGE_CHURCH_SWAMP_HOUSE = new Tile[] {new Tile(3236,3209,0), new Tile(3242,3199,0),new Tile(3241,3186,0),new Tile(3234,3180,0),
                new Tile(3221,3176,0),new Tile(3212,3169,0),new Tile(3201,3164,0),new Tile(3191,3160,0),new Tile(3177,3160,0),new Tile(3165,3165,0),
                new Tile(3152,3169,0),new Tile(3147,3171,0)};
        final int doorID = 15056;
        final int[] doorBounds = {8, 108, -212, -12, 112, 132};
        final Tile doorTile = new Tile(3147,3172,0);
        final Tile goalTile = new Tile(3147,3175,0);

        if (church.containsOrIntersects(ctx.players.local())) {
            final Tile churchDoorTile = new Tile(3238,3210,0);
            final Tile churchGoalTile = new Tile(3235,3209,0);
            final int churchDoorID = 1540;
            final int[] churchDoorBounds = {-4, 8, -220, -16, 12, 112};

            traversing.walkPastDoor(churchDoorID,churchDoorTile,churchGoalTile,churchDoorBounds);
        }

        traversing.walkPath(LUMBRIDGE_CHURCH_SWAMP_HOUSE,5);
        cameraManager.turnCamera(CameraManager.Direction.NORTH);
        traversing.walkPastDoor(doorID,doorTile,goalTile,doorBounds);
    }

    @Override
    public boolean activate() {
        return ctx.varpbits.varpbit(107) == 1 && // correct state of quest
                !shack.containsOrIntersects(ctx.players.local()); // not already in shack
    }
}
