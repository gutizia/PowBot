package gutizia.util.managers;

import gutizia.util.Interact;
import gutizia.util.constants.Items;
import gutizia.util.constants.LevelRequirements;
import gutizia.util.constants.Runes;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import static gutizia.util.managers.POHManager.pohManager;

public class TeleportManager extends ClientAccessor {
    private final static Logger LOGGER = Logger.getLogger("TeleportManager");

    public final static TeleportManager teleportManager = new TeleportManager(org.powerbot.script.ClientContext.ctx());

    private TeleportManager(ClientContext ctx) {
        super(ctx);
    }

    public boolean teleportToVarrock(boolean replenishRun) {
        if (isInVarrockTeleportArea()) {
            return true;
        }

        if (haveRunesFor(Magic.Spell.VARROCK_TELEPORT) && !replenishRun) {
            LOGGER.info("have runes for varrock teleport spell and don't need to replenish energy, teleporting directly to varrock");
            ctx.game.tab(Game.Tab.MAGIC);
            ctx.magic.cast(Magic.Spell.VARROCK_TELEPORT);
            ctx.game.tab(Game.Tab.INVENTORY);

            Condition.wait(this::isInVarrockTeleportArea,1000,5);

            if (isInVarrockTeleportArea()) {
                return true;
            }
        }

        if (!getInsideHouse()) {
            return false;
        }

        LOGGER.info("successfully entered house");
        if (replenishRun) {
            pohManager.replenishAtPool();
        }

        pohManager.useNexusPortal(POHManager.Portal.VARROCK);

        return isInVarrockTeleportArea();
    }

    public boolean teleportToFalador(boolean replenishRun) {
        if (isInFaladorTeleportArea()) {
            return true;
        }

        if (haveRunesFor(Magic.Spell.FALADOR_TELEPORT) && !replenishRun) {
            LOGGER.info("have runes for falador teleport spell and don't need to replenish energy, teleporting directly to falador");
            ctx.game.tab(Game.Tab.MAGIC);
            ctx.magic.cast(Magic.Spell.FALADOR_TELEPORT);
            ctx.game.tab(Game.Tab.INVENTORY);

            Condition.wait(this::isInFaladorTeleportArea,1000,5);

            if (isInFaladorTeleportArea()) {
                return true;
            }
        }

        if (!getInsideHouse()) {
            return false;
        }

        LOGGER.info("successfully entered house");
        if (replenishRun) {
            pohManager.replenishAtPool();
        }

        pohManager.useNexusPortal(POHManager.Portal.FALADOR);

        return isInFaladorTeleportArea();
    }

    public boolean teleportToLumbridge(boolean replenishRun) {
        if (isInLumbridgeCourtyard()) {
            return true;
        }

        if (haveRunesFor(Magic.Spell.LUMBRIDGE_TELEPORT) && !replenishRun) {
            LOGGER.info("have runes for lumbridge teleport spell and don't need to replenish energy, teleporting directly to lumbridge");
            ctx.game.tab(Game.Tab.MAGIC);
            ctx.magic.cast(Magic.Spell.LUMBRIDGE_TELEPORT);
            ctx.game.tab(Game.Tab.INVENTORY);

            Condition.wait(this::isInLumbridgeCourtyard,1000,5);

            if (isInLumbridgeCourtyard()) {
                return true;
            }
        }

        if (!getInsideHouse()) {
            return false;
        }

        LOGGER.info("successfully entered house");
        if (replenishRun) {
            pohManager.replenishAtPool();
        }

        pohManager.useNexusPortal(POHManager.Portal.LUMBRIDGE);


        if (!isInLumbridgeCourtyard()) {
            useHomeTeleport();
        }

        return isInLumbridgeCourtyard();
    }

    public boolean teleportToCastleWars(boolean replenishRun) {
        if (isInCastleWars()) {
            return true;
        }

        if (!getInsideHouse()) {
            return false;
        }

        LOGGER.info("successfully entered house");
        if (replenishRun) {
            pohManager.replenishAtPool();
        }

        pohManager.useOrnateJewelleryBox(POHManager.Jewellery.CASTLE_WARS);

        return isInCastleWars();
    }

    public boolean teleportToCanifis(boolean replenishRun) {
        if (isInCanifisTeleportArea()) {
            return true;
        }

        if (!getInsideHouse()) {
            return false;
        }

        LOGGER.info("successfully entered house");
        if (replenishRun) {
            pohManager.replenishAtPool();
        }

        pohManager.useNexusPortal(POHManager.Portal.CANIFIS);

        return isInCanifisTeleportArea();
    }

    public boolean teleportToFaladorPark(boolean replenishRun) {
        if (isInFaladorParkTeleportArea()) {
            return true;
        }

        if (!getInsideHouse()) {
            return false;
        }

        LOGGER.info("successfully entered house");
        if (replenishRun) {
            pohManager.replenishAtPool();
        }

        pohManager.useOrnateJewelleryBox(POHManager.Jewellery.FALADOR_PARK);

        return isInFaladorParkTeleportArea();
    }

    public boolean teleportToDraynor(boolean replenishRun) {
        if (isInDraynorTeleportArea()) {
            return true;
        }

        if (!getInsideHouse()) {
            return false;
        }

        LOGGER.info("successfully entered house");
        if (replenishRun) {
            pohManager.replenishAtPool();
        }

        pohManager.useOrnateJewelleryBox(POHManager.Jewellery.DRAYNOR_VILLAGE);

        return isInDraynorTeleportArea();
    }

    public boolean teleportToHosidius(boolean replenishRun) {
        if (isInHosidiusTeleportArea()) {
            return true;
        }

        if (!getInsideHouse()) {
            return false;
        }

        LOGGER.info("successfully entered house");
        if (replenishRun) {
            pohManager.replenishAtPool();
        }

        pohManager.useXericsTalisman();

        return isInHosidiusTeleportArea();

    }

    public boolean teleportToWatchtower(boolean replenishRun) {
        if (isInWatchtowerTeleportArea()) {
            return true;
        }

        if (!getInsideHouse()) {
            return false;
        }

        LOGGER.info("successfully entered house");
        if (replenishRun) {
            pohManager.replenishAtPool();
        }

        pohManager.useNexusPortal(POHManager.Portal.WATCHTOWER);

        return isInWatchtowerTeleportArea();
    }

    public boolean teleportToChampionGuild(boolean replenishRun) {
        if (isInChampionGuildTeleportArea()) {
            return true;
        }

        if (!getInsideHouse()) {
            return false;
        }

        LOGGER.info("successfully entered house");
        if (replenishRun) {
            pohManager.replenishAtPool();
        }

        pohManager.useOrnateJewelleryBox(POHManager.Jewellery.CHAMPION_GUILD);

        return isInChampionGuildTeleportArea();
    }

    public boolean teleportToFarmingGuild(boolean replenishRun) {
        if (isInFarmingGuildTeleportArea()) {
            return true;
        }

        if (!getInsideHouse()) {
            return false;
        }

        LOGGER.info("successfully entered house");
        if (replenishRun) {
            pohManager.replenishAtPool();
        }

        pohManager.useOrnateJewelleryBox(POHManager.Jewellery.FARMING_GUILD);

        return isInFarmingGuildTeleportArea();
    }

    public boolean teleportToFishingGuild(boolean replenishRun) {
        if (isInFishingGuildTeleportArea()) {
            return true;
        }

        if (!getInsideHouse()) {
            return false;
        }

        LOGGER.info("successfully entered house");
        if (replenishRun) {
            pohManager.replenishAtPool();
        }

        pohManager.useOrnateJewelleryBox(POHManager.Jewellery.FISHING_GUILD);


        return isInFishingGuildTeleportArea();
    }

    public boolean teleportToGE(boolean replenishRun) {
        if (isInGETeleportArea()) {
            return true;
        }

        if (!getInsideHouse()) {
            return false;
        }

        LOGGER.info("successfully entered house");
        if (replenishRun) {
            pohManager.replenishAtPool();
        }

        pohManager.useOrnateJewelleryBox(POHManager.Jewellery.GRAND_EXCHANGE);

        return isInGETeleportArea();
    }

    public boolean teleportToCamelot(boolean replenishRun) {
        if (isInCamelotTeleportArea()) {
            return true;
        }

        if (haveRunesFor(Magic.Spell.CAMELOT_TELEPORT) && !replenishRun) {
            if (ctx.skills.realLevel(Constants.SKILLS_MAGIC) >= LevelRequirements.TELEPORT_CAMOLET) {
                LOGGER.info("had runes for telepor to camelot spell and don't need to replenish run energy, teleporting directly to camelot");
                ctx.game.tab(Game.Tab.MAGIC);
                ctx.magic.cast(Magic.Spell.VARROCK_TELEPORT);
                ctx.game.tab(Game.Tab.INVENTORY);

                Condition.wait(this::isInCamelotTeleportArea,1000,5);
            }
        }

        if (!getInsideHouse()) {
            return false;
        }

        LOGGER.info("successfully entered house");
        if (replenishRun) {
            pohManager.replenishAtPool();
        }

        pohManager.useNexusPortal(POHManager.Portal.CAMELOT);

        return isInCamelotTeleportArea();
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
        }

        return isInLumbridgeCourtyard();
    }

    private boolean haveRunesFor(Magic.Spell spell) {
        ArrayList<ArrayList<Integer>> runes = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> rune = new ArrayList<Integer>();

        switch (spell) {
            case VARROCK_TELEPORT:
                rune.add(Runes.AIR);
                rune.add(3);
                runes.add(rune);
                rune = new ArrayList<Integer>();

                rune.add(Runes.FIRE);
                rune.add(1);
                runes.add(rune);
                rune = new ArrayList<Integer>();

                rune.add(Runes.LAW);
                rune.add(1);
                runes.add(rune);
                break;

            case LUMBRIDGE_TELEPORT:
                rune.add(Runes.AIR);
                rune.add(3);
                runes.add(rune);
                rune = new ArrayList<Integer>();

                rune.add(Runes.EARTH);
                rune.add(1);
                runes.add(rune);
                rune = new ArrayList<Integer>();

                rune.add(Runes.LAW);
                rune.add(1);
                runes.add(rune);
                break;

            case TELEPORT_TO_HOUSE:
                rune.add(Runes.AIR);
                rune.add(1);
                runes.add(rune);
                rune = new ArrayList<Integer>();

                rune.add(Runes.EARTH);
                rune.add(1);
                runes.add(rune);
                rune = new ArrayList<Integer>();

                rune.add(Runes.LAW);
                rune.add(1);
                runes.add(rune);
                break;

            case FALADOR_TELEPORT:
                rune.add(Runes.AIR);
                rune.add(3);
                runes.add(rune);
                rune = new ArrayList<Integer>();

                rune.add(Runes.WATER);
                rune.add(1);
                runes.add(rune);
                rune = new ArrayList<Integer>();

                rune.add(Runes.LAW);
                rune.add(1);
                runes.add(rune);
                break;
        }

        return Interact.haveItemsInInventory(ctx, runes);
    }

    private boolean canTeleportToHouse() {
        if (ctx.varpbits.varpbit(738) == 0) {
            LOGGER.info("player do not own a house, can't teleport to house");
            return false;
        }

        if (!ctx.inventory.select().id(Items.TELEPORT_TO_HOUSE_TABLET).isEmpty()) {
            return true;
        }

        if (ctx.skills.realLevel(Constants.SKILLS_MAGIC) >= 40) {
            final int[] runes = new int[] {Runes.LAW,Runes.AIR,Runes.EARTH};
            if (ctx.inventory.select().id(runes).size() < 3) {
                LOGGER.info("did not have all necessary runes to teleport to house");
                return false;
            }
            return true;
        }

        LOGGER.info("did not have teleport to house tablets in inventory and did not have levels for the teleport to house spell");
        return false;
    }

    private boolean getInsideHouse() {
        if (!canTeleportToHouse()) {
            return false;
        }

        if (!pohManager.isInsideHouse() && !pohManager.isOutsideHouse()) {
            if (!getToHousePortal()) {
                LOGGER.info("was unsuccessful in attempt to teleport to house");
                return false;
            }
        }

        if (!pohManager.isInsideHouse()) {
            if (!pohManager.getInsideHouse()) {
                LOGGER.info("was unsuccessful in attempt to get inside a house");
                return false;
            }
        }
        return pohManager.isInsideHouse();
    }

    private boolean getToHousePortal() {
        LOGGER.info("neither inside nor outside of a house, teleporting to house now..");
        pohManager.teleportToHouse();
        return pohManager.isOutsideHouse();
    }

    public boolean isInVarrockTeleportArea() {
        Tile minTile = new Tile(3207,3421,0);
        Tile maxTile = new Tile(3220,3434,0);
        Area area = new Area(minTile,maxTile);
        return area.containsOrIntersects(ctx.players.local());
    }

    public boolean isInCanifisTeleportArea() {
        final Area teleportArea = new Area(new Tile(3488,3470,0),new Tile(3504,3477,0));
        return teleportArea.containsOrIntersects(ctx.players.local());
    }

    private boolean isInLumbridgeCourtyard() {
        Tile minTile = new Tile(3217,3212,0);
        Tile maxTile = new Tile(3230,3226,0);
        Area area = new Area(minTile,maxTile);
        return area.containsOrIntersects(ctx.players.local());
    }

    private boolean isInFaladorTeleportArea() {
        Tile minTile = new Tile(2959,3376,0);
        Tile maxTile = new Tile(2971,3385,0);
        Area area = new Area(minTile,maxTile);
        return area.containsOrIntersects(ctx.players.local());
    }

    public boolean isInDraynorTeleportArea() {
        final Area draynor = new Area(new Tile(3098,3244,0),new Tile(3111,3256,0));
        return draynor.containsOrIntersects(ctx.players.local());
    }

    public boolean isInFaladorParkTeleportArea() {
        final Area faladorPark = new Area(new Tile(2990,3370,0),new Tile(3001,3379,0));
        return faladorPark.containsOrIntersects(ctx.players.local());
    }

    public boolean isInFishingGuildTeleportArea() {
        final Area teleportArea = new Area(new Tile(2605,3385,0),new Tile(2621,3393,0));
        return teleportArea.containsOrIntersects(ctx.players.local());
    }

    public boolean isInCamelotTeleportArea() {
        final Area teleportArea = new Area(new Tile(2752,3473,0),new Tile(2765,3481,0));
        return teleportArea.containsOrIntersects(ctx.players.local());
    }

    private boolean isInGETeleportArea() {
        final Area teleportArea = new Area(new Tile(3158,3472,0),new Tile(3171,3484,0));
        return teleportArea.containsOrIntersects(ctx.players.local());
    }

    public boolean isInCastleWars() {
        final Area teleportArea = new Area(new Tile(2436,3081,0),new Tile(2446,3098,0));
        return teleportArea.containsOrIntersects(ctx.players.local());
    }

    public boolean isInHosidiusTeleportArea() {
        final Area teleportArea = new Area(new Tile(1747,3563,0),new Tile(1758,3569,0));
        return teleportArea.containsOrIntersects(ctx.players.local());
    }

    private boolean isInFarmingGuildTeleportArea() {
        final Area teleportArea = new Area(new Tile(1244,3715,0),new Tile(1255,3723,0));
        return teleportArea.containsOrIntersects(ctx.players.local());
    }

    public boolean isInWatchtowerTeleportArea() {
        final Area teleportArea = new Area(new Tile(2543,3111,2),new Tile(2550,3118,2));
        return teleportArea.containsOrIntersects(ctx.players.local());
    }

    public boolean isInChampionGuildTeleportArea() {
        final Area teleportArea = new Area(new Tile(3186,3362,0),new Tile(3198,3372,0));
        return teleportArea.containsOrIntersects(ctx.players.local());
    }

    public boolean isInFarmingTeleportAreaOutside() {
        final Area teleportArea = new Area(new Tile(1246, 3723,0),new Tile(1251, 3729, 0));
        return teleportArea.containsOrIntersects(ctx.players.local());
    }

    public boolean isInFarmingTeleportAreaInside() {
        final Area teleportArea = new Area(new Tile(1242, 3714, 0),new Tile(1257, 3722, 0));
        return teleportArea.containsOrIntersects(ctx.players.local());
    }
}
