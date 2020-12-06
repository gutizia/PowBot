package gutizia.util.managers;

import gutizia.util.resources.Traversing;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class BankManager extends ClientAccessor {
    private final static Logger LOGGER = Logger.getLogger(BankManager.class.getName());
    private CameraManager cameraManager = new CameraManager(ctx);

    private Traversing traversing = new Traversing(ctx);
    private TeleportManager teleportManager = new TeleportManager(ctx);

    private final Tile LUMBRIDGE_CASTLE_NEXT_TO_STAIRCASE_GROUND_FLOOR = new Tile(3205,3209,0);
    private final Tile LUMBRIDGE_CASTLE_NEXT_TO_STAIRCASE_SECOND_FLOOR = new Tile(3205,3209,2);
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

    public void getBackToCourtyard() {
        LOGGER.info("getting back to courtyard");

        traversing.walkPath(new Tile[] {new Tile(3208,3219,2),LUMBRIDGE_CASTLE_NEXT_TO_STAIRCASE_SECOND_FLOOR},7);
        climbDownLumbridgeStairs();
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return ctx.players.local().tile().floor() == 0;
            }
        },600,4);

        traversing.walkPathReverse(PATH_LUMBRIDGE_COURTYARD_TO_STAIRCASE,5);
    }

    private void climbDownLumbridgeStairs() {
        if (ctx.camera.yaw() > 350 || ctx.camera.yaw() < 10) {
            cameraManager.turnCamera(30,60,30,60);
        }

        final int STAIRCASE_LUMBRIDGE_CASTLE_SOUTH_WING_FIRST_FLOOR = 16672; // Climb-up or Climb-down {-20, 32, -84, -36, 52, -8}
        final int STAIRCASE_LUMBRIDGE_CASTLE_SOUTH_WING_SECOND_FLOOR = 16673; // Climb-down {-32, 32, -24, 0, -44, 16}
        GameObject staircase = ctx.objects.select().id(STAIRCASE_LUMBRIDGE_CASTLE_SOUTH_WING_SECOND_FLOOR).nearest().poll();

        while (ctx.players.local().tile().floor() != 0) {
            if (ctx.players.local().tile().floor() == 2) {
                if (staircase.id() != STAIRCASE_LUMBRIDGE_CASTLE_SOUTH_WING_SECOND_FLOOR) {
                    staircase = ctx.objects.select().id(STAIRCASE_LUMBRIDGE_CASTLE_SOUTH_WING_SECOND_FLOOR).nearest().poll();
                }

                staircase.bounds(LUMBRIDGE_CASTLE_STAIRCASE_SECOND_FLOOR);

                while (ctx.players.local().tile().floor() == 2) {
                    staircase.interact(false,"Climb-down");
                    Condition.sleep(Random.nextInt(900,1600));
                }
            }

            if (ctx.players.local().tile().floor() == 1) {
                if (staircase.id() != STAIRCASE_LUMBRIDGE_CASTLE_SOUTH_WING_FIRST_FLOOR) {
                    staircase = ctx.objects.select().id(STAIRCASE_LUMBRIDGE_CASTLE_SOUTH_WING_FIRST_FLOOR).nearest().poll();
                }

                staircase.bounds(LUMBRIDGE_CASTLE_STAIRCASE_FIRST_FLOOR);

                while (ctx.players.local().tile().floor() == 1) {
                    staircase.interact(false,"Climb-down");
                    Condition.sleep(Random.nextInt(900,1600));
                }
            }
        }
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

    public void withdrawItem(int itemID,Bank.Amount amount) {
        ctx.bank.withdrawModeNoted(false);
        ctx.bank.select();
        ctx.bank.withdraw(itemID,amount);

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

    public void withdrawItems(gutizia.util.Items items) {
        for (gutizia.util.Item item : items.getItems()) {
            withdrawItem(item.getId(), item.getAmount());
        }
    }

    public void withdrawItemsNoted(ArrayList<ArrayList<Integer>> items) {
        ctx.bank.withdrawModeNoted(true);
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

    public boolean isInLumbridgeBank() {
        final Area area = new Area(LUMBRIDGE_BANK_MIN,LUMBRIDGE_BANK_MAX);
        return area.containsOrIntersects(ctx.players.local());
    }

    /**
     * pipeline method for getting to a bank
     * this method should always get you to a bank
     * exceptions: home teleport is on cooldown and no other banking methods made
     *
     * @return returns true if got to a bank and successfully opened it
     */

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

    public void getToABank() {
        if (traversing.isInLumbridgeCourtyard()) {
            LOGGER.info("is in lumbridge courtyard, opening lumbridge bank now");
            getToLumbridgeBank();
        }

        if (teleportManager.teleportToCastleWars(false)) {
            LOGGER.info("banking at castle wars");
            ctx.movement.step(new Tile(2443,3083,0));
            openBank();
        }

        LOGGER.info("no bank nearby, teleporting to Lumbridge to open that bank");
        teleportManager.teleportToLumbridge(false);

        if (!traversing.isInLumbridgeCourtyard()) {
            if (traversing.useHomeTeleport()) {
                getToLumbridgeBank();
                LOGGER.info("could not teleport to Lumbridge");
            }
        }
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

    /**
     * checks an array of itemIDs in bank to see if you need more of items
     * should be used in groups like, teleport runes
     * @param requiredItems the list of itemIDS of items you want to check
     * @param threshold the minimum acceptable number of an item
     * @return true if found one item that is below threshold
     */
    public boolean needMoreOf(int[] requiredItems,int threshold) {
        for (Item item : ctx.bank.select().id(requiredItems)) {
            for (int i : requiredItems) {
                if (i == item.id()) {
                    if (item.stackSize() < threshold) {
                        return true;
                    }
                }

            }
        }
        return false;
    }
}
