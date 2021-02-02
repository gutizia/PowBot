package gutizia.util;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;

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

    public static Tile getClosestTile(ClientContext ctx, ArrayList<Tile> tileList) {
        double min = Integer.MAX_VALUE;
        Tile t = Tile.NIL;
        for (Tile tile : tileList) {
            if (tile.distanceTo(ctx.players.local()) < min) {
                min = tile.distanceTo(ctx.players.local());
                t = tile;
            }
        }
        return t;
    }
}
