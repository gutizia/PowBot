package gutizia.tasks.farming.getToFarmingSpot.hops;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.constants.Paths;
import gutizia.util.managers.POHManager;
import gutizia.util.resources.Traversing;
import gutizia.util.skills.farming.Farming;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Npc;

import static gutizia.util.managers.POHManager.pohManager;
import static gutizia.util.managers.WorldManager.worldManager;
import static gutizia.util.resources.Traversing.traversing;
import static gutizia.util.trackers.HopsRunTracker.hopsRunTracker;

public class GetToEntrana extends GetToFarmSpot {
    private Farming farming = new Farming(ctx);

    private final Area entranaBoatArea = new Area(new Tile(2823,3328,1),new Tile(2835,3332,1));
    private final Area portSarimMonkArea = new Area(new Tile(3038,3234,0), new Tile(3050,3240,0));
    private final Tile[] pathToPatch = new Tile[] {new Tile(2826,3343,0),new Tile(2819,3343,0),new Tile(2812,3333,0)};

    public GetToEntrana(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        if (!entranaBoatArea.containsOrIntersects(ctx.players.local()) && !portSarimMonkArea.containsOrIntersects(ctx.players.local())) {
            if (!pohManager.isOutsideHouse()) {
                pohManager.teleportToHouse();
            }
        }

        if (pohManager.isOutsideHouse()) {
            traversing.walkPath(Paths.RIMMINGTON_POH_ENTRANA_BOAT,4);
        }

        if (portSarimMonkArea.containsOrIntersects(ctx.players.local())) {
            final Npc monk = ctx.npcs.select().name("Monk of Entrana").nearest().poll();
            monk.interact("Take-boat");

            Condition.wait(() -> ctx.players.local().tile().distanceTo(monk) > 30,600,5);

            if (ctx.players.local().tile().distanceTo(monk) > 30)
                Condition.wait(() -> ctx.players.local().tile().floor() == 1,600,20);
        }

        if (entranaBoatArea.containsOrIntersects(ctx.players.local())) {
            final int[] gangplankBounds = new int[] {-32, 32, -16, 0, -32, 32};
            GameObject gangplank = ctx.objects.select(10).name("Gangplank").nearest().peek();
            gangplank.bounds(gangplankBounds);
            gangplank.interact("Cross");
            Condition.wait(() -> ctx.players.local().tile().floor() == 0,600,5);
        }

        if (!farming.getFarmSpotYouAreAt().equals(Farming.FarmingSpot.ENTRANA_HOPS) && traversing.isInRange(pathToPatch,10)) {
            traversing.walkPath(pathToPatch,5);
        }
    }

    @Override
    public boolean activate() {
        return !PatchAreas.entranaHopsArea.containsOrIntersects(ctx.players.local());
    }
}
