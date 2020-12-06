package gutizia.tasks.farming.farmPatch;

import gutizia.util.Interact;
import gutizia.util.constants.Objects;
import gutizia.util.skills.farming.Farming;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.GameObject;

import static gutizia.util.trackers.FruitTreeTracker.fruitTreeTracker;

class PickProduceFromPatch extends FarmPatch {

    PickProduceFromPatch(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        patch = Farming.Patch.FRUIT_TREE;
        GameObject patchObject;
        String action;

        switch (patch) {
            case FRUIT_TREE:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).action("Guide").poll();
                action = farming.getFruitTreePickAction(patchObject);
                break;

            default:
                LOGGER.info("ERROR! CheckHealth did not recognize patch: " + patch);
                return;
        }

        if (patchObject.equals(ctx.objects.nil())) {
            LOGGER.info("no patchObject found");
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

        if (patchObject.interact(false,action,patchObject.name())) Condition.wait(() ->
                initialExp < ctx.skills.experience(Constants.SKILLS_FARMING),1000,5);

        if (initialExp < ctx.skills.experience(Constants.SKILLS_FARMING)) {
            Condition.wait(() ->
                    !ctx.objects.select(15).within(farming.getPatchArea(patch)).action("Chop-down").isEmpty() ||
                    ctx.inventory.isFull(), 1000, 5);
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
            GameObject gameObject = ctx.objects.select(15).within(patchArea).action("Guide").poll();
            if (gameObject.name().equals(Objects.CLEAR_FRUIT_TREE_PATCH)) return false;

            return Interact.actionContains(gameObject.actions(),farming.getFruitTreePickAction(gameObject));
        }
        return false;
    }
}
