package gutizia.util.resources;

import gutizia.util.constants.Paths;
import gutizia.util.managers.CameraManager;
import gutizia.util.managers.TeleportManager;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Traversing extends ClientAccessor {
    private final static Logger LOGGER = Logger.getLogger(Traversing.class.getName());
    private CameraManager cameraManager = new CameraManager(ctx);

    public Traversing(ClientContext ctx) {
        super(ctx);
    }

    public boolean isInLumbridgeCourtyard() {
        final Area area = new Area(new Tile(3217,3212,0),new Tile(3230,3226,0));
        return area.containsOrIntersects(ctx.players.local());
    }

    public void takeBoatToEntrana() {
        final int[] gangplankBounds = new int[] {-32, 32, -16, 0, -32, 32};
        final Npc monk = ctx.npcs.select().name("Monk of Entrana").nearest().poll();

        while (ctx.players.local().tile().floor() == 0) {
            monk.interact("Take-boat");
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return ctx.players.local().tile().distanceTo(monk) > 30;
                }
            },600,5);

            if (ctx.players.local().tile().distanceTo(monk) > 30) {
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() {
                        return ctx.players.local().tile().floor() == 1;
                    }
                },600,20);
            }
        }



        while (ctx.players.local().tile().floor() == 1) {
            GameObject gangplank = ctx.objects.select(10).name("Gangplank").nearest().peek();
            gangplank.bounds(gangplankBounds);
            gangplank.interact("Cross");
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return ctx.players.local().tile().floor() == 0;
                }
            },600,5);
        }
    }

    public boolean isInRange(Tile tile, int limit) {
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

    public boolean isInRange(Tile[] tiles, int limit) {
        System.out.println("checking if tile path is in range...");
        Tile playerTile = ctx.players.local().tile();
        int xDelta;
        int yDelta;
        int tilesInRange = 0;
        for (Tile tile : tiles) {
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
            if ((xDelta < limit && yDelta < limit)) {
                tilesInRange++;
            }
        }
        return (tilesInRange > 0);
    }

    /**
     *
     * @param path the tile path to find the closest tile from
     * @param limit the range limit you want
     * @return returns the index of the first tile that is within the range limit parameter
     */

    private int getClosestTile(Tile[] path,int limit) {
        Tile playerTile = ctx.players.local().tile();
        int nearest = -1;
        int xDelta;
        int yDelta;

        for (int i = 0;i < path.length;i++) {
            // getting deltas
            if (playerTile.x() > path[i].x()) {
                xDelta = playerTile.x() - path[i].x();
            } else  {
                xDelta = path[i].x() - playerTile.x();
            }
            if (playerTile.y() > path[i].y()) {
                yDelta = playerTile.y() - path[i].y();
            } else  {
                yDelta = path[i].y() - playerTile.y();
            }

            if ((xDelta < limit && yDelta < limit)) {
                nearest = i;
            }
        }
        return nearest;
    }

    public void enableRun() {
        while (!ctx.movement.running()) {
            ctx.movement.running(true);
            Condition.sleep(Random.nextInt(200,500));
        }
    }

    public boolean isOnTiles(Tile[] tiles) {
        Tile playerTile = ctx.players.local().tile();
        for (Tile tile : tiles) {
            if (tile.equals(playerTile)) {
                return true;
            }
        }
        return false;
    }

    public boolean useHomeTeleport() {
        int HOME_TELEPORT_ANIMATION = 4847;
        ctx.magic.cast(Magic.Spell.HOME_TELEPORT);

        Condition.sleep(Random.nextInt(500,1000));

        ctx.game.tab(Game.Tab.INVENTORY);

        if (ctx.players.local().animation() == HOME_TELEPORT_ANIMATION) {
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return isInLumbridgeCourtyard();
                }
            }, 600, 24);
            return true;
        }
        return false;
    }

    public void walkPastDoor(final int doorID,final Tile doorTile,final Tile goalTile) {
        while (!ctx.controller.isStopping() && !isInRange(goalTile,2)) {
            if (!ctx.objects.select().id(doorID).at(doorTile).isEmpty()) {
                while (!ctx.controller.isStopping() && !ctx.objects.select().id(doorID).at(doorTile).isEmpty()) {
                    GameObject door = ctx.objects.poll();
                    door.interact(false, "Open");

                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() {
                            return ctx.objects.select().id(doorID).at(doorTile).isEmpty();
                        }
                    },600,5);
                }
            }
            ctx.movement.step(goalTile);

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return isInRange(goalTile,2);
                }
            },600,6);
        }
    }

    public void walkPastDoor(final int doorID,final Tile doorTile,final Tile goalTile, int[] bounds) {
        while (!ctx.controller.isStopping() && !isInRange(goalTile,2)) {
            if (!ctx.objects.select().id(doorID).at(doorTile).isEmpty()) {
                while (!ctx.controller.isStopping() && !ctx.objects.select().id(doorID).at(doorTile).isEmpty()) {
                    GameObject door = ctx.objects.poll();
                    door.bounds(bounds);
                    door.interact(false, "Open");

                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() {
                            return ctx.objects.select().id(doorID).at(doorTile).isEmpty();
                        }
                    },600,5);
                }
            }
            ctx.movement.step(goalTile);

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return isInRange(goalTile,2);
                }
            },600,6);
        }
    }

    public void openTollGate(boolean fromLumbridge) {
        cameraManager.zoomToMidHigh();

        if (fromLumbridge) {
            cameraManager.turnCamera(235, 290, 50, 80);

        } else {
            cameraManager.turnCamera(55,110,50,80);
        }

        int gateID = 2883;

        while (ctx.players.local().tile().x() < 3267) {
            ctx.objects.select().id(gateID).poll().interact(false, "Pay-toll(10gp)");
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return ctx.players.local().tile().x() > 3267;
                }
            }, 600, 10);
        }
    }

    public void walkPath(Tile[] myPath, int rangeLimit) {
        LOGGER.setLevel(Level.INFO);
        boolean[] haveWalkedTile = new boolean[myPath.length];
        int runEnergyLimit = Random.nextInt(40,100);

        int j = getClosestTile(myPath,8);

        if (j != -1) {
            for (int i = 0; i < haveWalkedTile.length;i++) {
                haveWalkedTile[i] = i < j; // if index in path is less than found nearest tile, you have already cleared that ground
            }

        } else {
            for (int i = 0; i < haveWalkedTile.length;i++) {
                haveWalkedTile[i] = false;
            }
        }

        do {
            if (ctx.movement.energyLevel() > runEnergyLimit) {
                enableRun();
            }

            if (!ctx.players.local().inMotion() || ctx.movement.destination().equals(Tile.NIL) || ctx.movement.destination().distanceTo(ctx.players.local()) < 3) {
                for (int i = 0; i < myPath.length;i++) {
                    if (!haveWalkedTile[i]) {
                        if (isInRange(myPath[i],3)) {
                            haveWalkedTile[i] = true;
                            continue;
                        }
                        ctx.movement.step(myPath[i]);
                        break;
                    }
                }
                Condition.sleep(1200);
            }
        } while (!(isInRange(myPath[myPath.length -1],rangeLimit)) && !ctx.controller.isStopping());
    }

    public void getToZanaris() {
        TeleportManager teleportManager = new TeleportManager(ctx);

        final int[] doorBounds = new int[] {4, 16, -208, -16, 8, 112};
        teleportManager.teleportToLumbridge(false);

        walkPath(Paths.LUMBRIDGE_SWAMP_SHED,5);

        final GameObject door = ctx.objects.select(10).name("Door").nearest().poll();
        door.bounds(doorBounds);

        cameraManager.turnCamera(CameraManager.Direction.EAST);

        while (ctx.players.local().tile().distanceTo(door.tile()) < 20) {
            door.interact("Open");
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return ctx.players.local().tile().distanceTo(door.tile()) < 20;
                }
            },600,10);
        }
    }

    public void walkPathReverse(Tile[] myPath, int rangeLimit) {
        LOGGER.setLevel(Level.INFO);
        boolean[] haveWalkedTile = new boolean[myPath.length];
        int runEnergyLimit = Random.nextInt(40,100);

        int j = getClosestTile(myPath,8);

        if (j != -1) {
            for (int i = myPath.length - 1; i >= 0;i--) {
                haveWalkedTile[i] = i > j; // if index in path is more than found nearest tile, you have already cleared that ground
            }

        } else {
            for (int i = 0; i < haveWalkedTile.length;i++) {
                haveWalkedTile[i] = false;
            }
        }

        do {
            if (ctx.movement.energyLevel() > runEnergyLimit) {
                enableRun();
            }

            if (!ctx.players.local().inMotion() || ctx.movement.destination().equals(Tile.NIL) || ctx.movement.destination().distanceTo(ctx.players.local()) < 3) {
                for (int i = myPath.length - 1; i >= 0;i--) {
                    if (!haveWalkedTile[i]) {
                        if (isInRange(myPath[i],3)) {
                            haveWalkedTile[i] = true;
                            continue;
                        }
                        ctx.movement.step(myPath[i]);
                        break;
                    }
                }
                Condition.sleep(1200);
            }
        } while (!(isInRange(myPath[0],rangeLimit)) && !ctx.controller.isStopping());
    }
}
