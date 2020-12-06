package gutizia.tasks.farming.getToFarmingSpot.hops;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import gutizia.util.managers.TeleportManager;
import gutizia.util.resources.Traversing;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.GameObject;

public class GetToChampGuild extends GetToFarmSpot {
    private TeleportManager teleportManager = new TeleportManager(ctx);
    private Traversing traversing = new Traversing(ctx);

    private final Tile[] pathToShortcut = new Tile[] {new Tile(3201,3362,0),new Tile(3212,3359,0),new Tile(3223,3355,0),new Tile(3226,3342,0),
            new Tile(3231,3336,0),new Tile(3240,3337,0)};
    private final Tile[] pathAroundShortcut = new Tile[] {new Tile(3249,3336,0),new Tile(3260,3333,0),new Tile(3255,3323,0),new Tile(3245,3318,0)};
    private final Tile[] pathToPatch = new Tile[] {new Tile(3240,3315,0),new Tile(3232,3316,0)};

    public GetToChampGuild(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        if (teleportManager.isInChampionGuildTeleportArea()) {
            traversing.walkPath(pathToShortcut,5);
        }

        if (pathToShortcut[pathToShortcut.length -1].distanceTo(ctx.players.local()) < 6) {
            if (ctx.skills.level(Constants.SKILLS_AGILITY) >= 13) {
                useSouthVarrockShortcut();
            } else {
                traversing.walkPath(pathAroundShortcut,5);
            }
        }

        if (traversing.isInRange(pathToPatch,5)) {
            traversing.walkPath(pathToPatch,5);
        }
    }

    private void useSouthVarrockShortcut() {
        final int[] fenceBounds = new int[] {12, 136, -56, -52, -8, 4};
        GameObject fence = ctx.objects.select(10).at(new Tile(3240,3335,0)).name("Fence").poll();
        fence.bounds(fenceBounds);

        if (fence.interact("Jump-over")) {
            Condition.wait(() -> ctx.players.local().tile().equals(new Tile(3240,3334,0)),900,10);
        }

        ctx.movement.step(new Tile(3243,3322,0)); // catch up with path around fence
    }

    @Override
    public boolean activate() {
        return teleportManager.isInChampionGuildTeleportArea() || traversing.isInRange(pathToPatch, 8) || traversing.isInRange(pathToShortcut, 8);
    }
}
