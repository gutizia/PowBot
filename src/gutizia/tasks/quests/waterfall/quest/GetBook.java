package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;
import gutizia.util.Interact;
import gutizia.util.constants.Items;
import gutizia.util.managers.CameraManager;
import gutizia.util.resources.Traversing;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.GameObject;

import static gutizia.util.managers.CameraManager.cameraManager;
import static gutizia.util.Inventory.inventory;
import static gutizia.util.overlay.TaskOverlay.taskOverlay;
import static gutizia.util.resources.Traversing.traversing;

public class GetBook extends Task {

    private final Tile[] path = new Tile[] {new Tile(2533,3425,0),new Tile(2522,3432,0)};
    private final Tile doorTile = new Tile(2521,3432,0);
    private final Tile goalTile = new Tile(2518,3431,0);
    private final int[] doorBounds = new int[] {0, 20, -212, -12, 16, 120};
    private final int doorID = 1543;

    @Override
    public void execute() {
        taskOverlay.setActiveTask("getting book");

        traversing.walkPath(path,4);

        cameraManager.turnCamera(CameraManager.Direction.WEST);

        traversing.walkPastDoor(doorID,doorTile,goalTile,doorBounds);

        GameObject staircase = ctx.objects.select(10).name("Staircase").nearest().poll();
        Interact.interact(ctx, staircase, false, "Climb-up", false);
        Condition.wait(() -> ctx.players.local().tile().floor() == 1, 600, 10);

        ctx.movement.step(new Tile(2519,3426,1));

        cameraManager.turnCamera(CameraManager.Direction.EAST);

        GameObject bookcase = ctx.objects.select(10).name("Bookcase").at(new Tile(2520,3427,1)).poll();

        bookcase.interact("Search");
        Condition.wait(() -> inventory.getItems().contains(Items.BOOK_ON_BAXTORIAN), 600, 6);
    }

    @Override
    public boolean activate() {
        return !inventory.getItems().contains(Items.BOOK_ON_BAXTORIAN) && // don't have book
                new Tile(2527,3413,0).distanceTo(ctx.players.local()) < 8; // within lowerRiverIsland of where you end up after swimming down river
    }
}
