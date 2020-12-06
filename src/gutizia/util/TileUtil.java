package gutizia.util;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class TileUtil {

    public static boolean isInRange(ClientContext ctx, Tile tile, int limit) {
        Tile playerTile = ctx.players.local().tile();
        int xDelta;
        int yDelta;
        if (playerTile.x() > tile.x()) {
            xDelta = playerTile.x() - tile.x();
        } else  {
            xDelta = tile.x() - playerTile.x();
        }
        if (playerTile.y() > tile.y()) {
            yDelta = playerTile.y() - tile.y();
        } else  {
            yDelta = tile.y() - playerTile.y();
        }
        return (xDelta < limit && yDelta < limit);
    }

    public static int getClosestTileIndex(ClientContext ctx, Tile[] path) {
        int index = -1;
        double nearest = Double.MAX_VALUE;
        for (int i = 0;i < path.length;i++) {
            if (ctx.players.local().tile().distanceTo(path[i]) < nearest) {
                index = i;
                nearest = ctx.players.local().tile().distanceTo(path[i]);
            }
        }
        return index;
    }
}
