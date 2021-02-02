package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;
import gutizia.util.Interact;
import gutizia.util.constants.Items;
import gutizia.util.managers.POHManager;
import org.powerbot.script.Area;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.GameObject;

import static gutizia.util.managers.POHManager.pohManager;
import static gutizia.util.overlay.TaskOverlay.taskOverlay;
import static gutizia.util.resources.Traversing.traversing;

public class GetToMazeDungeon extends Task {

    private final static Tile[] path = new Tile[] {new Tile(2453,3089,0),new Tile(2455,3076,0),new Tile(2469,3074,0),new Tile(2484,3080,0),new Tile(2497,3087,0),
            new Tile(2501,3099,0),new Tile(2496,3111,0),new Tile(2496,3126,0),new Tile(2493,3139,0),new Tile(2497,3153,0),new Tile(2494,3166,0),
            new Tile(2496,3179,0),new Tile(2503,3191,0),new Tile(2518,3187,0),new Tile(2519,3175),new Tile(2534,3175,0),new Tile(2545,3174,0),
            new Tile(2544,3162,0),new Tile(2533,3157,0)};

    @Override
    public void execute() {
        taskOverlay.setActiveTask("get to dung");

        pohManager.teleportToHouse();
        pohManager.viewHouseAdvertisement();
        pohManager.getInsideHostHouse();
        pohManager.replenishAtPool();
        pohManager.useOrnateJewelleryBox(POHManager.Jewellery.CASTLE_WARS);
        traversing.walkPath(path,4);

        GameObject ladder = ctx.objects.select(10).name("Ladder").nearest().poll();

        Interact.climbLadder(ctx, ladder,false);
    }

    @Override
    public boolean activate() {
        final Tile minTile = new Tile(2508,9552,0);
        final Tile maxTile = new Tile(2552,9584,0);
        final Area area = new Area(minTile,maxTile);

        return ctx.varpbits.varpbit(65) == 3 && // correct stage of quest
                ctx.inventory.select().id(Items.PEBBLE).isEmpty() && // don't have pebble already
                !area.containsOrIntersects(ctx.players.local()); // not in dungeon
    }
}
