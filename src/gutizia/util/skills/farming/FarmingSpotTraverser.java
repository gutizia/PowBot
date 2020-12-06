package gutizia.util.skills.farming;

import gutizia.util.constants.Paths;
import gutizia.util.managers.CameraManager;
import gutizia.util.managers.POHManager;
import gutizia.util.managers.TeleportManager;
import gutizia.util.Interact;
import gutizia.util.resources.Traversing;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;

import java.util.logging.Logger;

public class FarmingSpotTraverser extends ClientAccessor {
    private final static Logger LOGGER = Logger.getLogger("FarmingSpotTraverser");
    private Interact interact = new Interact();
    private Farming farming = new Farming(ctx);

    public FarmingSpotTraverser(ClientContext ctx) {
        super(ctx);
    }

    private TeleportManager teleportManager = new TeleportManager(ctx);
    private Traversing traversing = new Traversing(ctx);
    private POHManager pohManager = new POHManager(ctx);

    public void getToFarmingSpot(Farming.FarmingSpot farmingSpot) {
        Tile[] pathToPatch;
        Tile[] pathToShortcut;
        Tile[] pathAroundShortcut;
        Tile[] pathToBoat;
        switch (farmingSpot) {
            case FALADOR_HAF:
                if (teleportManager.teleportToDraynor(true)) {
                    traversing.walkPath(Paths.FALADOR_HERB_PATCH, 5);
                }
                break;

            case ARDOUGNE_HAF:
                if (teleportManager.teleportToFishingGuild(true)) {
                    traversing.walkPath(Paths.ARDOUNGE_HERB_PATCH, 5);
                }
                break;

            case CATHERBY_HAF:
                if (teleportManager.teleportToCamelot(true)) {
                    traversing.walkPath(Paths.CATHERBY_HERB_PATCH, 5);
                }
                break;

            case HOSIDIUS_HAF:
                if (teleportManager.teleportToHosidius(true)) {
                    traversing.walkPath(Paths.HOSIDIUS_HERB_PATCH, 5);
                }
                break;

            case ENTRANA_HOPS:
                pathToPatch = new Tile[] {new Tile(2826,3343,0),new Tile(2819,3343,0),new Tile(2812,3333,0),}; // from boat to leprechaun on hops patch

                pohManager.teleportToHouse();

                if (pohManager.isOutsideHouse()) {
                    traversing.walkPath(Paths.RIMMINGTON_POH_ENTRANA_BOAT,5);
                    traversing.takeBoatToEntrana();
                    traversing.walkPath(pathToPatch,4);
                }
                break;

            case YANILLE_HOPS:
                pathToShortcut = new Tile[] {new Tile(2554,3116,0),new Tile(2565,3114,0),new Tile(2574,3113,0)};
                pathAroundShortcut = new Tile[] {new Tile(2586,3115,0),new Tile(2595,3118,0),new Tile(2604,3121,0),
                        new Tile(2609,3114,0),new Tile(2607,3100,0),new Tile(2593,3097,0),new Tile(2581,3099,0),new Tile(2572,3103,0),};

                if (teleportManager.teleportToWatchtower(true)) {
                    climbDownWatchtower();
                    if (ctx.players.local().tile().floor() == 0) {
                        traversing.walkPath(pathToShortcut, 4);

                        if (ctx.skills.level(Constants.SKILLS_AGILITY) >= 16) {
                            useYanilleShortcut();
                        } else {
                            traversing.walkPath(pathAroundShortcut, 4);
                        }
                    }
                }
                break;

            case CHAMP_GUILD_HOPS:
                pathToShortcut = new Tile[] {new Tile(3201,3362,0),new Tile(3212,3359,0),new Tile(3223,3355,0),new Tile(3226,3342,0),
                        new Tile(3231,3336,0),new Tile(3240,3337,0)};
                pathAroundShortcut = new Tile[] {new Tile(3249,3336,0),new Tile(3260,3333,0),new Tile(3255,3323,0),new Tile(3245,3318,0)};
                pathToPatch = new Tile[] {new Tile(3240,3315,0),new Tile(3232,3316,0)};

                if (teleportManager.teleportToChampionGuild(true)) {
                    traversing.walkPath(pathToShortcut,5);

                    if (ctx.skills.level(Constants.SKILLS_AGILITY) >= 13) {
                        useSouthVarrockShortcut();
                    } else {
                        traversing.walkPath(pathAroundShortcut,5);
                    }

                    traversing.walkPath(pathToPatch,5);
                }
                break;

            case CAMELOT_HOPS:
                pathToPatch = new Tile[] {new Tile(2749,3478,0),new Tile(2739,3482,0),new Tile(2737,3491,0),new Tile(2729,3500,0),
                        new Tile(2720,3504,0),new Tile(2710,3506,0),new Tile(2697,3510,0),new Tile(2688,3515,0),new Tile(2678,3521,0),
                        new Tile(2672,3523,0)};

                if (teleportManager.teleportToCamelot(true)) {
                    traversing.walkPath(pathToPatch,5);
                }
                break;

            case GNOME_STRONGHOLD_FT:
                Tile[] pathToGate = new Tile[] {new Tile(2601,3388,0),new Tile(2589,3387,0),new Tile(2576,3387,0),new Tile(2565,3391,0),
                        new Tile(2554,3393,0),new Tile(2542,3398,0),new Tile(2529,3399,0),new Tile(2518,3393,0),new Tile(2506,3388,0),
                        new Tile(2494,3388,0),new Tile(2481,3387,0),new Tile(2469,3385,0),new Tile(2461,3382,0)};
                pathToPatch = new Tile[] {new Tile(2461,3395,0),new Tile(2461,3408,0),new Tile(2461,3420,0),new Tile(2462,3432,0),
                        new Tile(2466,3441,0),new Tile(2474,3447,0)};

                if (teleportManager.teleportToFishingGuild(true)) {
                    traversing.walkPath(pathToGate,4);
                    openGnomeStrongholdGate();
                    traversing.walkPath(pathToPatch,4);
                }
                break;

            case CATHERBY_FT:
                pathToPatch = new Tile[] {new Tile(2766,3474,0),new Tile(2779,3473,0),
                        new Tile(2793,3472,0),new Tile(2803,3471,0),new Tile(2812,3470,0),new Tile(2816,3466,0),new Tile(2825,3462,0),
                        new Tile(2829,3452,0),new Tile(2828,3441,0),new Tile(2839,3437,0),new Tile(2850,3432,0),new Tile(2857,3433,0)};

                if (teleportManager.teleportToCamelot(true)) {
                    traversing.walkPath(pathToPatch,4);
                }
                break;

            case BRIMHAVEN_FT:
                if (farming.getFarmSpotYouAreAt().equals(Farming.FarmingSpot.CATHERBY_FT)) {
                    pathToBoat = new Tile[] {new Tile(2847,3433,0),new Tile(2839,3434,0),new Tile(2827,3437,0),new Tile(2816,3435,0),
                            new Tile(2807,3433,0),new Tile(2804,3422,0),new Tile(2802,3415,0),new Tile(2796,3414,0)};
                    pathToPatch = new Tile[] {new Tile(2760,3229,0),new Tile(2771,3226,0),new Tile(2770,3219,0),new Tile(2767,3214,0)};

                    traversing.walkPath(pathToBoat,5);

                    takeBoatToBrimhaven();

                    traversing.walkPath(pathToPatch,3);
                }
                break;

            case GNOME_VILLAGE_FT:
                pathToPatch = new Tile[] {new Tile(2453,3089,0),new Tile(2455,3076,0),new Tile(2469,3074,0),new Tile(2484,3080,0),new Tile(2497,3087,0),
                        new Tile(2501,3099,0),new Tile(2496,3111,0),new Tile(2496,3126,0),new Tile(2493,3139,0),new Tile(2497,3153,0),new Tile(2494,3166,0),
                        new Tile(2488,3178,0)};

                if (teleportManager.teleportToCastleWars(true)) {
                    traversing.walkPath(pathToPatch,4);
                }
                break;
        }
    }

    private void takeBoatToBrimhaven() {
        Npc charter = ctx.npcs.select().action("Charter").nearest().poll();
        int[] gangplankBounds = new int[] {-28, 32, -12, 8, -28, 20};

        Interact.interact(ctx, charter,true,"Charter",false);

        Condition.wait(() -> ctx.widgets.component(72,17).visible(),600,10);

        ctx.widgets.component(72,17).click();

        Condition.wait(() -> ctx.widgets.component(219,1,0).text().contains("Brimhaven"),1000,5);

        if (ctx.widgets.component(219,1,0).text().contains("Brimhaven")) ctx.input.send("1");

        Condition.wait(() -> ctx.players.local().tile().equals(new Tile(2763,3238,1)),1000,5);

        GameObject gangplank = ctx.objects.select(10).name("Gangplank").nearest().poll();
        gangplank.bounds(gangplankBounds);
        gangplank.interact("Cross");

        Condition.wait(() -> ctx.players.local().tile().equals(new Tile(2760,3238,0)),1000,5);
    }

    private void openGnomeStrongholdGate() {
        new CameraManager(ctx).turnCamera(CameraManager.Direction.NORTH);
        GameObject gate = ctx.objects.select(15).name("Gate").nearest().poll();
        Interact.interact(ctx, gate,true,"Open", false);

        // TODO handle exception of first entering stronghold where the gnome lady talks to you
        Condition.wait(() -> ctx.players.local().tile().equals(new Tile(2461,3385,0)),600,10);
    }

    private void climbDownWatchtower() {
        final int[] ladderBounds = new int[] {-32, 32, -60, 0, -16, 4};
        final Area firstFloor = new Area(new Tile(2543,3111,1),new Tile(2550,3118,1));
        if (teleportManager.isInWatchtowerTeleportArea()) {
            LOGGER.info("is in watchtower teleport area");
            GameObject ladder = ctx.objects.select(15).name("Ladder").at(new Tile(2549,3111,2)).poll();
            Interact.climbLadder(ctx,ladder,false,ladderBounds,ctx.players.local().tile());
        }

        if (firstFloor.containsOrIntersects(ctx.players.local())) {
            LOGGER.info("inside firstFloor");
            GameObject ladder = ctx.objects.select(15).name("Ladder").at(new Tile(2544,3111,1)).poll();
            Interact.climbLadder(ctx,ladder,false,ladderBounds,ctx.players.local().tile());
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

    private void useYanilleShortcut() {
        final int[] holeBounds = new int[] {-32, 32, 0, 4, -52, 32};

        GameObject hole = ctx.objects.select(10).at(new Tile(2575,3111,0)).name("Hole").poll();

        hole.bounds(holeBounds);

        if (hole.interact("Climb-into")) {
            Condition.wait(() -> ctx.players.local().tile().equals(new Tile(2575,3107,0)),900,10);
        }
    }
}
