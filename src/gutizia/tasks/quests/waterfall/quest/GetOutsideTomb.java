package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;
import gutizia.util.constants.Items;
import gutizia.util.managers.POHManager;
import org.powerbot.script.Tile;

import static gutizia.util.managers.POHManager.pohManager;
import static gutizia.util.overlay.TaskOverlay.taskOverlay;
import static gutizia.util.resources.Traversing.traversing;

public class GetOutsideTomb extends Task {

    final Tile[] path = new Tile[] {new Tile(2601,3391,0),new Tile(2590,3392,0),new Tile(2579,3401,0),new Tile(2570,3412,0),
            new Tile(2566,3425,0),new Tile(2559,3435,0),new Tile(2556,3444,0)};

    @Override
    public void execute() {
        taskOverlay.setActiveTask("get to Tomb");

        pohManager.teleportToHouse();
        pohManager.viewHouseAdvertisement();
        pohManager.getInsideHostHouse();
        pohManager.replenishAtPool();
        pohManager.useOrnateJewelleryBox(POHManager.Jewellery.FISHING_GUILD);

        traversing.walkPath(path,5);
    }

    @Override
    public boolean activate() {
        return !ctx.inventory.select().id(Items.PEBBLE).isEmpty() && // if have pebble
                !WaterfallQuest.GLARIALS_TOMB.containsOrIntersects(ctx.players.local()) && // not already in tomb
                ctx.inventory.select().id(Items.GLARIALS_AMULET, Items.GLARIALS_URN).size() < 2; // if do not have both glarial's items in inventory
    }
}
