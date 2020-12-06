package gutizia.tasks.farming.getToFarmingSpot;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

class WalkToGnomeStrongholdGate extends GetToFarmSpot {
    private final Tile[] pathToGate = new Tile[] {new Tile(2601,3388,0), new Tile(2589,3387,0), new Tile(2576,3387,0), new Tile(2565,3391,0),
            new Tile(2554,3393,0), new Tile(2542,3398,0), new Tile(2529,3399,0), new Tile(2518,3393,0), new Tile(2506,3388,0),
            new Tile(2494,3388,0), new Tile(2481,3387,0), new Tile(2469,3385,0), new Tile(2461,3382,0)};

    WalkToGnomeStrongholdGate(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        traversing.walkPath(pathToGate,4);
    }

    @Override
    public boolean activate() {
        return teleportManager.isInFishingGuildTeleportArea() || ((traversing.isInRange(pathToGate, 8) && ctx.players.local().tile().y() < 3385));
    }
}
