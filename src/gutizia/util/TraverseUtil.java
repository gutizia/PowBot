package gutizia.util;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import java.util.Arrays;

public class TraverseUtil {

    private static Tile[] path;
    private static boolean[] haveWalkedTile;
    private static int nextTileLimit = 3;
    private static int destinationLimit = 8;

    public static void createNewPath(ClientContext ctx, Tile[] path) {
        TraverseUtil.path = path;
        haveWalkedTile = new boolean[path.length];
        int index = TileUtil.getClosestTileIndex(ctx, path);

        if (index != -1) {
            for (int i = 0; i < haveWalkedTile.length;i++) {
                haveWalkedTile[i] = i < index;
            }
        } else {
            Arrays.fill(haveWalkedTile, false);
        }
    }

    public static Tile getNextTile(ClientContext ctx) {
        for (int i = 0; i < path.length;i++) {
            if (!haveWalkedTile[i]) {
                if (TileUtil.isInRange(ctx, path[i], nextTileLimit)) {
                    haveWalkedTile[i] = true;
                    continue;
                }
                return path[i];
            }
        }
        return Tile.NIL;
    }

    public static int getNextTileLimit() {
        return nextTileLimit;
    }

    public static int getDestinationLimit() {
        return destinationLimit;
    }

    public static void setDestinationLimit(int destinationLimit) {
        TraverseUtil.destinationLimit = destinationLimit;
    }

    public static void setNextTileLimit(int nextTileLimit) {
        TraverseUtil.nextTileLimit = nextTileLimit;
    }

    public static int getClosestTileDistance(Tile[] tiles, Tile tile) {
        int closest = Integer.MAX_VALUE;
        for (Tile t : tiles) {
            if (t.distanceTo(tile) < closest) {
                closest = (int)t.distanceTo(tile);
            }
        }
        return closest;
    }
}
