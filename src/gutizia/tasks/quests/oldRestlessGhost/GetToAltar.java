package gutizia.tasks.quests.oldRestlessGhost;

import gutizia.util.constants.Paths;
import gutizia.util.managers.CameraManager;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.managers.CameraManager.cameraManager;
import static gutizia.util.resources.Traversing.traversing;

public class GetToAltar extends OldRestlessGhost {

    GetToAltar(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        //overlay.changeStatus("getting to altar");
        Tile doorTile = new Tile(3247,3193,0);
        Tile goalTile = new Tile(3242,3201,0);
        int doorID = 1535;
        int[] doorBounds = {-4, 12, -208, -20, 8, 120};

        cameraManager.turnCamera(CameraManager.Direction.WEST);
        traversing.walkPastDoor(doorID,doorTile,goalTile,doorBounds);

        traversing.walkPathReverse(LUMBRIDGE_CHURCH,4);
        traversing.walkPath(Paths.LUMBRIDGE_DRAYNOR_SOUTHERN_CROSSROADS,3);
        traversing.walkPath(Paths.DRAYNOR_SOUTHERN_CROSSROADS_WIZARDS_TOWER,4);

        doorID = 23972;
        doorTile = new Tile(3109,3167,0);
        goalTile = new Tile(3109,3164,0);

        traversing.walkPastDoor(doorID,doorTile,goalTile);

        doorTile = new Tile(3107,3162,0);
        goalTile = new Tile(3105,3161,0);

        traversing.walkPastDoor(doorID,doorTile,goalTile);
        ctx.objects.select().name("Ladder").nearest().poll().interact(false,"Climb-down");
        Condition.sleep(2000);

        ctx.movement.step(new Tile(3108,9559,0));

        doorBounds = new int[] {-4, 8, -224, -24, 8, 112};
        doorTile = new Tile(3111,9559,0);
        goalTile = new Tile(3117,9566,0);
        doorID = 1535;

        traversing.walkPastDoor(doorID,doorTile,goalTile,doorBounds);

    }

    @Override
    public boolean activate() {
        return !altarRoom.containsOrIntersects(ctx.players.local()) && // not in altar room
                ctx.varpbits.varpbit(107) == 3; // at correct state of quest
    }
}

