package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;
import gutizia.util.combat.Combat;
import gutizia.util.constants.Items;
import gutizia.util.managers.CameraManager;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.GameObject;

import java.util.concurrent.Callable;

import static gutizia.util.managers.CameraManager.cameraManager;
import static gutizia.util.managers.ChatManager.chatManager;
import static gutizia.util.overlay.TaskOverlay.taskOverlay;
import static gutizia.util.resources.Traversing.traversing;

public class GetPebble extends Task {

    private final Tile[] path = new Tile[] {new Tile(2542,9557,0),new Tile(2530,9555,0),new Tile(2521,9563,0),new Tile(2515,9573,0)};
    private final int[] doorBounds = new int[] {12, 128, -208, -12, 132, 112};
    private final Tile doorTile = new Tile(2515,9575,0);
    private final int doorID = 1991;
    private Combat combat = new Combat(ctx, null);

    @Override
    public void execute() {
        taskOverlay.setActiveTask("get key");

        combat.protectFromMelee(true);

        ctx.movement.step(new Tile(2544,9564,0));
        cameraManager.turnCamera(CameraManager.Direction.EAST);

        GameObject crates = ctx.objects.select().name("Crate").at(new Tile(2548,9565,0)).poll();

        if (ctx.combat.healthPercent() < 60) {
            LOGGER.info("drinking whine");
            combat.drinkWhine();
        }

        while (ctx.inventory.select().name("Key").isEmpty()) {
            crates.interact(false,"Search",crates.name());
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return !ctx.players.local().inMotion() || !ctx.inventory.select().name("Key").isEmpty();
                }
            },1200,9);
        }

        if (ctx.combat.healthPercent() < 60) {
            LOGGER.info("drinking whine");
            combat.drinkWhine();
        }

        LOGGER.info("opening gate");

        traversing.walkPath(path,8);
        cameraManager.turnCamera(CameraManager.Direction.NORTH);

        if (ctx.combat.healthPercent() < 60) {
            LOGGER.info("drinking whine");
            combat.drinkWhine();
        }

        GameObject door = ctx.objects.select(15).id(doorID).at(doorTile).poll();
        door.bounds(doorBounds);
        while (ctx.players.local().tile().y() < 9576) {
            door.interact("Open");
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return null;
                }
            },600,7);
        }

        combat.protectFromMelee(false);

        LOGGER.info("getting pebble by talking to Golrie");

        chatManager.startConversation("Golrie");
        chatManager.finishDialog();
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return !ctx.components.textContains("click here to continue").isEmpty();
            }
        },600,40);
        chatManager.finishDialog();
    }

    @Override
    public boolean activate() {
        final Tile minTile = new Tile(2508,9552,0);
        final Tile maxTile = new Tile(2552,9584,0);
        final Tile tile = ctx.players.local().tile();

        if (tile.x() >= minTile.x() && tile.x() <= maxTile.x()) { // withing x axis of maze dungeon
            if (tile.y() >= minTile.y() && tile.y() <= maxTile.y()) { // within y axis of maze dungeon
                return tile.floor() == maxTile.floor() && // same floor as dungeon
                    ctx.inventory.select().id(Items.PEBBLE).isEmpty(); // don't have pebble
            }
        }
        return false;
    }
}
