package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Activatable;
import gutizia.tasks.Task;
import gutizia.util.Interact;
import gutizia.util.managers.CameraManager;
import gutizia.util.managers.POHManager;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.GameObject;

import static gutizia.util.managers.CameraManager.cameraManager;
import static gutizia.util.managers.POHManager.pohManager;
import static gutizia.util.overlay.TaskOverlay.taskOverlay;
import static gutizia.util.resources.Traversing.traversing;

class GetToAlmera extends Task {

    private final Tile[] FISHING_GUILD_ALMERA = new Tile[] {new Tile(2601,3391,0),new Tile(2590,3392,0),new Tile(2579,3401,0),new Tile(2570,3412,0),
            new Tile(2566,3425,0),new Tile(2559,3435,0),new Tile(2556,3444,0),new Tile(2556,3456,0),new Tile(2553,3467,0),new Tile(2543,3476,0),new Tile(2531,3484,0),
            new Tile(2529,3495,0)};
    private final int[] doorBounds = {4, 20, -216, 0, 4, 116};
    private final Tile doorTile = new Tile(2525,3495,0);
    private final Tile goalTile = new Tile(2521,3495,0);
    private Activatable activatable;

    GetToAlmera(Activatable activatable) {
        this.activatable = activatable;
    }

    @Override
    public void execute() {
        final int doorID = 1540;
        taskOverlay.setActiveTask("GetToAlmera");
        pohManager.teleportToHouse();
        pohManager.viewHouseAdvertisement();
        pohManager.getInsideHostHouse();
        pohManager.replenishAtPool();
        pohManager.useOrnateJewelleryBox(POHManager.Jewellery.FISHING_GUILD);

        traversing.walkPath(FISHING_GUILD_ALMERA,5);

        GameObject gate = ctx.objects.select().name("Gate").nearest().poll();
        cameraManager.turnCamera(CameraManager.Direction.WEST);

        Interact.interact(ctx, gate, false, "Open", false);

        traversing.walkPastDoor(doorID,doorTile,goalTile,doorBounds);
    }

    @Override
    public boolean activate() {
        return activatable.activate();
    }
}
