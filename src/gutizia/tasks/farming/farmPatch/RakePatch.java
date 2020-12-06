package gutizia.tasks.farming.farmPatch;

import gutizia.util.Interact;
import gutizia.util.constants.Bounds;
import gutizia.util.constants.Objects;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

class RakePatch extends FarmPatch {

    RakePatch(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        final GameObject patchObject;
        int[] bounds;

        switch (patch) {
            case HERB:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).action("Rake").nearest().poll();
                bounds = Bounds.HERB;
                break;

            case FLOWER:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).action("Rake").nearest().poll();
                bounds = Bounds.FLOWER;
                break;

            case ALLOTMENT1:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).action("Rake").nearest().poll();
                bounds = Bounds.ALLOTMENT;
                break;

            case ALLOTMENT2:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).action("Rake").nearest().poll();
                bounds = Bounds.ALLOTMENT;
                break;

            case HOPS:
                patchObject = ctx.objects.select(15).action("Rake").nearest().poll();
                bounds = Bounds.ALLOTMENT;
                break;

            case FRUIT_TREE: case TREE:
                patchObject = ctx.objects.select(15).action("Rake").nearest().poll();
                bounds = Bounds.HERB;
                break;

            default:
                LOGGER.info("ERROR! did not recognize patch: " + patch);
                return;
        }

        if (ctx.inventory.selectedItem().id() != -1) {
            Interact.removeSelectedItem(ctx);
        }

        if (patchObject.equals(ctx.objects.nil())) {
            LOGGER.info("no patch to rake found");
            return;
        }

        patchObject.bounds(bounds);

        if (patchObject.tile().distanceTo(ctx.players.local()) > 4) {
            ctx.movement.step(patchObject);
        }

        if (!patchObject.inViewport()) {
            ctx.movement.step(patchObject);
            ctx.camera.turnTo(patchObject);
        }

        if (patchObject.interact(false,"Rake",patchObject.name())) {
            Condition.wait(() -> ctx.objects.select(5).within(farming.getPatchArea(patch)).action("Rake").isEmpty() || farming.levelUpChatVisible(),1200,40);
        }

    }

    @Override
    public boolean activate() {
        switch (patch) {
            case HERB:
                return !ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_HERB_PATCH).action("Rake").isEmpty();

            case FLOWER:
                return !ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_FLOWER_PATCH).action("Rake").isEmpty();

            case ALLOTMENT1: case ALLOTMENT2:
                return !ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_ALLOTMENT_PATCH).action("Rake").isEmpty();

            case HOPS:
                return !ctx.objects.select(15).name(Objects.CLEAR_HOPS_PATCH).action("Rake").isEmpty();

            case FRUIT_TREE:
                return !ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_FRUIT_TREE_PATCH).action("Rake").isEmpty();

            default:
                LOGGER.info("ERROR! did not recognize patch: " + patch);
                return false;
        }
    }
}
