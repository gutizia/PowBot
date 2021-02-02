package gutizia.tasks.farming.getToFarmingSpot.hops;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.Interact;
import gutizia.util.skills.farming.Farming;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.GameObject;

import java.util.logging.Logger;

import static gutizia.util.managers.TeleportManager.teleportManager;
import static gutizia.util.resources.Traversing.traversing;


public class GetToYanille extends GetToFarmSpot {
    private Logger LOGGER = Logger.getLogger("GetToYanille");

    private final Tile[] pathToShortcut = new Tile[] {new Tile(2554,3116,0),new Tile(2565,3114,0),new Tile(2574,3113,0)};
    private final Tile[] pathAroundShortcut = new Tile[] {new Tile(2586,3115,0),new Tile(2595,3118,0),new Tile(2604,3121,0),
            new Tile(2609,3114,0),new Tile(2607,3100,0),new Tile(2593,3097,0),new Tile(2581,3099,0),new Tile(2572,3103,0)};
    private final Area firstFloor = new Area(new Tile(2543,3111,1),new Tile(2550,3118,1));
    private final int[] ladderBounds = new int[] {-32, 32, -60, 0, -16, 4};

    public GetToYanille(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        if (teleportManager.isInWatchtowerTeleportArea()) {
            GameObject ladder = ctx.objects.select(15).name("Ladder").at(new Tile(2549,3111,2)).poll();
            Interact.climbLadder(ctx, ladder,false, ctx.players.local().tile());
        }

        if (firstFloor.containsOrIntersects(ctx.players.local())) {
            GameObject ladder = ctx.objects.select(15).name("Ladder").at(new Tile(2544,3111,1)).poll();
            Interact.climbLadder(ctx, ladder,false, ctx.players.local().tile());
        }

        if (pathAroundShortcut[pathAroundShortcut.length -1].distanceTo(ctx.players.local()) > 8 &&
                traversing.isInRange(pathToShortcut,10)) {

            traversing.walkPath(pathToShortcut, 4);
        }

        if (pathAroundShortcut[pathAroundShortcut.length -1].distanceTo(ctx.players.local()) <= 8) {
            if (ctx.skills.level(Constants.SKILLS_AGILITY) >= 16) {
                useYanilleShortcut();
            } else {
                traversing.walkPath(pathAroundShortcut, 4);
            }
        }
    }

    private void useYanilleShortcut() {
        final int[] holeBounds = new int[] {-32, 32, 0, 4, -52, 32};
        GameObject hole = ctx.objects.select(10).at(new Tile(2575,3111,0)).name("Hole").poll();
        hole.bounds(holeBounds);

        if (hole.tile().distanceTo(ctx.players.local()) >= 8) {
            ctx.movement.step(hole);
        }

        ctx.camera.turnTo(hole);

        if (hole.interact("Climb-into")) {
            Condition.wait(() -> ctx.players.local().tile().equals(new Tile(2575,3107,0)),900,10);
        }
    }

    @Override
    public boolean activate() {
        return !PatchAreas.yanilleHopsArea.containsOrIntersects(ctx.players.local());
    }
}
