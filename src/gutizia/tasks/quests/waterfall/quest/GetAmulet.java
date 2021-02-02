package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;
import gutizia.util.Interact;
import gutizia.util.combat.Combat;
import gutizia.util.constants.Items;
import gutizia.util.managers.CameraManager;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.GameObject;

import java.util.concurrent.Callable;

import static gutizia.util.managers.CameraManager.cameraManager;
import static gutizia.util.overlay.TaskOverlay.taskOverlay;
import static gutizia.util.resources.Traversing.traversing;

public class GetAmulet extends Task {

    private final Tile[] pathToChest = new Tile[] {new Tile(2544,9843,0),new Tile(2532,9844,0)};

    private Combat combat = new Combat(ctx, null);

    @Override
    public void execute() {
        taskOverlay.setActiveTask("get amulet");

        combat.protectFromMelee(true);

        cameraManager.turnCamera(CameraManager.Direction.WEST);

        traversing.walkPath(pathToChest,5);

        if (ctx.combat.healthPercent() < 60) {
            LOGGER.info("drinking whine");
            combat.drinkWhine();
        }

        GameObject chest = ctx.objects.select(10).name("Chest").nearest().poll();
        if (!chest.inViewport()) {
            ctx.camera.turnTo(chest);
        }

        while (ctx.inventory.select().name("Glarial's amulet").isEmpty()) {
            if (Interact.actionContains(chest.actions(),"Open")) {
                chest.interact("Open");

            } else {
                chest.interact("Search");
            }

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return !ctx.inventory.select().name("Glarial's amulet").isEmpty();
                }
            },900,5);
        }

        if (ctx.combat.healthPercent() < 70) {
            LOGGER.info("drinking whine");
            combat.drinkWhine();
        }
    }

    @Override
    public boolean activate() {
        return ctx.inventory.select().id(Items.GLARIALS_AMULET).isEmpty() && // if don't have amulet;
                WaterfallQuest.GLARIALS_TOMB.containsOrIntersects(ctx.players.local()); // inside glarials tomb
    }
}
