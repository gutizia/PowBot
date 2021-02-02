package gutizia.tasks.farming.getToFarmingSpot.fruitTree;

import gutizia.tasks.farming.getToFarmingSpot.GetToFarmSpot;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.resources.Traversing.traversing;


public class GetToGnomeStronghold extends GetToFarmSpot {
    private final Tile[] pathToPatch = new Tile[] {new Tile(2461,3395,0),new Tile(2461,3408,0),new Tile(2461,3420,0),new Tile(2462,3432,0),
            new Tile(2466,3441,0),new Tile(2474,3447,0)};

    private final Tile treeTeleportSpot = new Tile(2461, 3444, 0);


    public GetToGnomeStronghold(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        if (ctx.players.local().tile().equals(treeTeleportSpot)) {
            ctx.movement.step(new Tile(2475, 3447, 0));
            return;
        }

        traversing.walkPath(pathToPatch,4);
    }

    @Override
    public boolean activate() {
        return ctx.players.local().tile().equals(treeTeleportSpot) ||
                (traversing.isInRange(pathToPatch,8) && ctx.players.local().tile().y() >= 3385);
    }

}