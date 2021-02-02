package gutizia.util.managers;

import gutizia.util.Items;
import gutizia.util.resources.Traversing;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import static gutizia.util.managers.CameraManager.cameraManager;
import static gutizia.util.managers.TeleportManager.teleportManager;
import static gutizia.util.resources.Traversing.traversing;

public class BankManager extends ClientAccessor {
    private final static Logger LOGGER = Logger.getLogger(BankManager.class.getName());

    private final Tile LUMBRIDGE_CASTLE_NEXT_TO_STAIRCASE_GROUND_FLOOR = new Tile(3205,3209,0);
    private final Tile LUMBRIDGE_CASTLE_COURTYARD = new Tile(3223, 3219,0);
    private final Tile LUMBRIDGE_CASTLE_SOUTH_WING = new Tile(3213, 3210,0);
    private final Tile LUMBRIDGE_BANK_MIN = new Tile(3207,3216,2);
    private final Tile LUMBRIDGE_BANK_MAX = new Tile(3210,3222,2);
    private final int[] LUMBRIDGE_CASTLE_STAIRCASE_GROUND_FLOOR = new int[]{-40, 12, -68, -24, 12, 72};
    private final int[] LUMBRIDGE_CASTLE_STAIRCASE_FIRST_FLOOR = new int[]{-20, 32, -84, -36, 52, -8};
    private final int[] LUMBRIDGE_CASTLE_STAIRCASE_SECOND_FLOOR = new int[]{-32, 32, -24, 0, -44, 16};
    private final Tile[] PATH_LUMBRIDGE_COURTYARD_TO_STAIRCASE = {LUMBRIDGE_CASTLE_COURTYARD,LUMBRIDGE_CASTLE_SOUTH_WING,LUMBRIDGE_CASTLE_NEXT_TO_STAIRCASE_GROUND_FLOOR};

    public BankManager(ClientContext ctx) {
        super(ctx);
    }

    private void climbUpLumbridgeStairs() {

        if (ctx.camera.yaw() > 350 || ctx.camera.yaw() < 10) {
            cameraManager.turnCamera(30,60,30,60);
        }

        final int STAIRCASE_LUMBRIDGE_CASTLE_SOUTH_WING_GROUND_FLOOR = 16671; // Climb-up {-40, 12, -68, -24, 12, 72}
        final int LUMBRIDGE_CASTLE_STAIRCASE_GROUND_FlOOR = 16672; // Climb-up or Climb-down {-20, 32, -84, -36, 52, -8}

        GameObject staircase = ctx.objects.select().id(STAIRCASE_LUMBRIDGE_CASTLE_SOUTH_WING_GROUND_FLOOR).nearest().poll();

        // getting to top floor from bottom
        while (ctx.players.local().tile().floor() != 2) {
            if (ctx.players.local().tile().floor() == 0) {
                if (staircase.id() != STAIRCASE_LUMBRIDGE_CASTLE_SOUTH_WING_GROUND_FLOOR) {
                    staircase = ctx.objects.select().id(STAIRCASE_LUMBRIDGE_CASTLE_SOUTH_WING_GROUND_FLOOR).nearest().poll();
                }

                staircase.bounds(LUMBRIDGE_CASTLE_STAIRCASE_GROUND_FLOOR);

                while (ctx.players.local().tile().floor() == 0) {
                    staircase.interact(false, "Climb-up");
                    Condition.sleep(700);
                }
            }

            if (ctx.players.local().tile().floor() == 1) {
                if (staircase.id() != LUMBRIDGE_CASTLE_STAIRCASE_GROUND_FlOOR) {
                    staircase = ctx.objects.select().id(LUMBRIDGE_CASTLE_STAIRCASE_GROUND_FlOOR).nearest().poll();
                }

                staircase.bounds(LUMBRIDGE_CASTLE_STAIRCASE_FIRST_FLOOR);

                while (ctx.players.local().tile().floor() == 1) {
                    staircase.interact(false, "Climb-up");
                    Condition.sleep(700);
                }
            }
        }
    }

    public void withdrawAllNoted(int itemID) {
        ctx.bank.withdrawModeNoted(true);
        ctx.bank.select();
        ctx.bank.withdraw(itemID,Bank.Amount.ALL);
    }

    public void withdrawItem(int itemID,int amount) {
        final int invSize = ctx.inventory.select().size();
        ctx.bank.select();
        ctx.bank.withdraw(itemID,amount);

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return ctx.inventory.select().size() > invSize;
            }
        },300,20);
    }

    public void withdrawItems(ArrayList<ArrayList<Integer>> items) {
        for (ArrayList<Integer> item : items) {
            if (ctx.controller.isStopping()) {
                break;
            }

            LOGGER.info("withdrawing item: " + item.get(0) + "," + item.get(1));
            withdrawItem(item.get(0),item.get(1));

        }
    }

    public void getToLumbridgeBank() {
        LOGGER.info("getting to Lumbridge bank");
        traversing.walkPath(PATH_LUMBRIDGE_COURTYARD_TO_STAIRCASE, 8);
        climbUpLumbridgeStairs();

        // walking to bank
        while (!isInLumbridgeBank()) {
            ctx.movement.step(new Tile(3208, 3218, 2)); // roughly middle of bank

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return isInLumbridgeBank();
                }
            },600,9);
        }
    }

    public void withdrawItems(Items items) {

    }

    public boolean isInLumbridgeBank() {
        final Area area = new Area(LUMBRIDGE_BANK_MIN,LUMBRIDGE_BANK_MAX);
        return area.containsOrIntersects(ctx.players.local());
    }

    public boolean openABank() {
        LOGGER.info("opening a bank...");
        if (traversing.isInLumbridgeCourtyard()) {
            LOGGER.info("is in lumbridge courtyard, opening lumbridge bank now");
            getToLumbridgeBank();
            return openLumbridgeBank();
        }

        if (isInLumbridgeBank()) {
            LOGGER.info("is in lumbridge bank, opening it now");
            return openLumbridgeBank();
        }

        if (ctx.bank.nearest().tile().distanceTo(ctx.players.local()) < 10) {
           return openBank();
        }

        if (teleportManager.teleportToCastleWars(false)) {
            LOGGER.info("banking at castle wars");
            ctx.movement.step(new Tile(2443,3083,0));
            openBank();
            return ctx.bank.opened();
        }

        LOGGER.info("no bank nearby, teleporting to Lumbridge to open that bank");
        teleportManager.teleportToLumbridge(false);

        if (!traversing.isInLumbridgeCourtyard()) {
            if (!traversing.useHomeTeleport()) {
                LOGGER.info("could not teleport to Lumbridge");
                return false;
            }

        }
        getToLumbridgeBank();
        return openLumbridgeBank();
    }

    private boolean openLumbridgeBank() {
        final int BANK_BOOTH = 18491;
        GameObject bankBooth;
        int timer = 0;
        while (!ctx.bank.opened()) {
            bankBooth = ctx.objects.select().id(BANK_BOOTH).peek();

            if (!bankBooth.inViewport()) {
                ctx.camera.turnTo(bankBooth);
            }
            bankBooth.interact(false, "Bank");
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return ctx.bank.opened();
                }
            }, 600, 4);
            if (++timer > 2) {
                LOGGER.info("tried opening Lumbridge bank 3 times, but failed");
                return false;
            }
        }
        return true;
    }

    private boolean openBank() {
        if (!ctx.bank.inViewport()) {
            ctx.camera.turnTo(ctx.bank.nearest());
        }
        ctx.bank.open();

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return ctx.bank.opened();
            }
        }, 600, 6);

        return ctx.bank.opened();
    }
}
