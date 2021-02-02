package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;
import gutizia.util.combat.Combat;
import gutizia.util.constants.Items;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.GameObject;

import static gutizia.util.managers.POHManager.pohManager;
import static gutizia.util.overlay.TaskOverlay.taskOverlay;
import static gutizia.util.resources.Traversing.traversing;

public class GetUrn extends Task {

    private final Tile[] pathToTomb = new Tile[] {new Tile(2532,9844,0),new Tile(2545,9830,0),new Tile(2542,9818,0),new Tile(2542,9809,0)};

    private Combat combat = new Combat(ctx, null);

    @Override
    public void execute() {
        taskOverlay.setActiveTask("get urn");
        combat.protectFromMelee(true);

        traversing.walkPath(pathToTomb,5);

        if (ctx.combat.healthPercent() < 60) {
            LOGGER.info("drinking whine");
            combat.drinkWhine();
        }

        GameObject tomb = ctx.objects.select(10).name("Glarial's Tomb").poll();
        if (!tomb.inViewport()) {
            ctx.camera.turnTo(tomb);
        }

        while (ctx.inventory.select().name("Glarial's urn").isEmpty() &&
                WaterfallQuest.GLARIALS_TOMB.containsOrIntersects(ctx.players.local())) {
            tomb.interact("Search");
            Condition.sleep(Random.nextInt(600,1000));
        }

        if (ctx.combat.healthPercent() < 60) {
            LOGGER.info("drinking whine");
            combat.drinkWhine();
        }

        pohManager.teleportToHouse();
        LOGGER.info("teleporting out");

        combat.protectFromMelee(false);
    }

    @Override
    public boolean activate() {
        return ctx.inventory.select().id(Items.GLARIALS_URN).isEmpty() && // if don't have urn
                WaterfallQuest.GLARIALS_TOMB.containsOrIntersects(ctx.players.local());
    }
}
