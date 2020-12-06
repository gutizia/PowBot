package gutizia.tasks.farming.getToFarmingSpot.fruitTree;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.Interact;
import gutizia.util.skills.farming.Farming;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Npc;

import static gutizia.util.trackers.FruitTreeTracker.fruitTreeTracker;

public class GetToBrimhaven extends GetToFarmSpot {
    private Tile[] pathToBoat = new Tile[] {new Tile(2763, 3468, 0), new Tile(2768, 3460, 0), new Tile(2774, 3452, 0),
            new Tile(2781, 3444, 0), new Tile(2789, 3436, 0), new Tile(2799, 3432, 0), new Tile(2804, 3425, 0),
            new Tile(2803, 3416, 0), new Tile(2796, 3414, 0)};
    private Tile[] pathToPatch = new Tile[] {new Tile(2760,3229,0),new Tile(2771,3226,0),new Tile(2770,3219,0),new Tile(2767,3214,0)};
    private Area catherbyDocks = new Area(new Tile(2791,3411,0),new Tile(2804,3418,0));
    private Area brimhavenBoat = new Area(new Tile(2763,3234,1),new Tile(2765,3243,1));

    public GetToBrimhaven(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        if (teleportManager.isInCamelotTeleportArea()) {
            traversing.walkPath(pathToBoat,5);
        }

        // take boat to brimhaven
        if (catherbyDocks.containsOrIntersects(ctx.players.local())) {
            Npc charter = ctx.npcs.select().action("Charter").nearest().poll();
            Interact.interact(ctx,charter,true,"Charter",false);
            Condition.wait(() -> ctx.widgets.component(72,17).visible(),600,10);
            ctx.widgets.component(72,17).click();
            Condition.wait(() -> ctx.widgets.component(219,1,0).text().contains("Brimhaven"),1000,5);
            if (ctx.widgets.component(219,1,0).text().contains("Brimhaven")) ctx.input.send("1");
            Condition.wait(() -> ctx.players.local().tile().equals(new Tile(2763,3238,1)),1000,5);
        }

        // get off boat
        if (brimhavenBoat.containsOrIntersects(ctx.players.local())) {
            int[] gangplankBounds = new int[] {-28, 32, -12, 8, -28, 20};
            GameObject gangplank = ctx.objects.select(10).name("Gangplank").nearest().poll();
            gangplank.bounds(gangplankBounds);
            gangplank.interact("Cross");
            Condition.wait(() -> ctx.players.local().tile().equals(new Tile(2760,3238,0)),1000,5);
        }

        if (!brimhavenBoat.containsOrIntersects(ctx.players.local()) && traversing.isInRange(pathToPatch,8)) {
            traversing.walkPath(pathToPatch,3);
        }
    }

    @Override
    public boolean activate() {
        return teleportManager.isInCamelotTeleportArea() || traversing.isInRange(pathToPatch,8);
    }

}
