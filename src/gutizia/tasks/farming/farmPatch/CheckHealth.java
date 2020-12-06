package gutizia.tasks.farming.farmPatch;

import gutizia.util.Interact;
import gutizia.util.skills.farming.Farming;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.GameObject;

import static gutizia.util.trackers.FruitTreeTracker.fruitTreeTracker;

public class CheckHealth extends FarmPatch {

    public CheckHealth(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        patch = Farming.Patch.FRUIT_TREE;
        GameObject patchObject;

        switch (patch) {
            case FRUIT_TREE:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).action("Check-health").nearest().poll();
                break;

            default:
                LOGGER.info("ERROR! CheckHealth did not recognize patch: " + patch);
                return;
        }

        if (patchObject.equals(ctx.objects.nil())) {
            LOGGER.info("no patch to check health found");
            return;
        }

        patchObject.bounds(getBounds(patchObject.name()));

        if (!patchObject.inViewport()) {
            ctx.movement.step(patchObject);
            ctx.camera.turnTo(patchObject);
        }

        if (ctx.inventory.selectedItem().id() != -1) {
            Interact.removeSelectedItem(ctx);
        }

        int initialExp = ctx.skills.experience(Constants.SKILLS_FARMING);

        if (patchObject.interact(false,"Check-health",patchObject.name())) {
            Condition.wait(() -> ctx.skills.experience(Constants.SKILLS_FARMING) > initialExp,1000,5);

            if (ctx.skills.experience(Constants.SKILLS_FARMING) > initialExp) {
                if (patch.equals(Farming.Patch.TREE)) {

                }
            }
        }
    }

    @Override
    public boolean activate() {
        if (fruitTreeTracker.isDoingRun()) {
            Farming.FarmingSpot farmingSpot = farming.getFarmSpotYouAreAt();
            Area patchArea;
            switch (farmingSpot) {
                case GNOME_STRONGHOLD_FT:
                    patchArea = PatchAreas.gnomeStrongholdFT;
                    break;

                case CATHERBY_FT:
                    patchArea = PatchAreas.catherbyFT;
                    break;

                case BRIMHAVEN_FT:
                    patchArea = PatchAreas.brimhavenFT;
                    break;

                case GNOME_VILLAGE_FT:
                    patchArea = PatchAreas.gnomeVillageFT;
                    break;

                default:
                    LOGGER.info("ERROR! activate() did not recognize farming spot: " + farmingSpot);
                    return false;
            }
            return !ctx.objects.select(15).within(patchArea).action("Check-health").isEmpty();
        }
        return false;
    }
}
