package gutizia.tasks.farming.farmPatch;

import gutizia.util.Interact;
import gutizia.util.constants.Bounds;
import gutizia.util.constants.Objects;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;

class PlantPatch extends FarmPatch {

    PlantPatch(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        final GameObject patchObject;
        int[] bounds;
        final Tile tile;
        final String name;
        final Item seed = farming.getSeed(patch);
        final int seedStackSize = seed.stackSize();

        switch (patch) {
            case ALLOTMENT1:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_ALLOTMENT_PATCH).nearest().poll();
                bounds = Bounds.ALLOTMENT;
                break;

            case ALLOTMENT2:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_ALLOTMENT_PATCH).nearest().poll();
                bounds = Bounds.ALLOTMENT;
                break;

            case FLOWER:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_FLOWER_PATCH).poll();
                bounds = Bounds.FLOWER;
                break;

            case HERB:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_HERB_PATCH).poll();
                bounds = Bounds.HERB;
                break;

            case HOPS:
                patchObject = ctx.objects.select(15).name(Objects.CLEAR_HOPS_PATCH).nearest().poll();
                bounds = Bounds.ALLOTMENT;
                break;

            case FRUIT_TREE:
                patchObject = ctx.objects.select(15).name(Objects.CLEAR_FRUIT_TREE_PATCH).nearest().poll();
                bounds = Bounds.HERB;
                break;

            case TREE:
                patchObject = ctx.objects.select(15).name(Objects.CLEAR_TREE_PATCH).nearest().poll();
                bounds = Bounds.HERB;
                break;

            default:
                LOGGER.info("ERROR! did not recognize patch: " + patch);
                return;
        }
        tile = patchObject.tile();

        if (patchObject.equals(ctx.objects.nil())) {
            LOGGER.info("no patch found");
            return;
        }

        name = patchObject.name();
        patchObject.bounds(bounds);


        if (ctx.inventory.selectedItem().id() != -1) {
            Interact.removeSelectedItem(ctx);
        }

        if (patchObject.tile().distanceTo(ctx.players.local()) > 4) {
            ctx.movement.step(patchObject);
        }

        if (!patchObject.inViewport()) {
            ctx.movement.step(patchObject);
            ctx.camera.turnTo(patchObject);
        }

        seed.interact("Use");

        if (patchObject.interact(false,"Use",patchObject.name())) {
            Condition.wait(() -> ctx.inventory.select().name(seed.name()).poll().stackSize() < seedStackSize,600,6);
        }

        if (!ctx.objects.select(5).at(tile).action("Rake").isEmpty()) {
            LOGGER.info("weeds grew while trying to plant seeds. Raking them now...");
            new RakePatch(ctx).execute();
            execute();
        }

        Condition.wait(() -> ctx.objects.select(5).at(tile).name(name).isEmpty(),600,6);

    }

    @Override
    public boolean activate() {
        switch (patch) {
            case HERB:
                return !ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_HERB_PATCH).isEmpty() &&
                        ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_HERB_PATCH).action("Rake").isEmpty();

            case FLOWER:
                return !ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_FLOWER_PATCH).isEmpty() &&
                        ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_FLOWER_PATCH).action("Rake").isEmpty();

            case ALLOTMENT1: case ALLOTMENT2:
                return !ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_ALLOTMENT_PATCH).isEmpty() &&
                        ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_ALLOTMENT_PATCH).action("Rake").isEmpty();

            case HOPS:
                return !ctx.objects.select(15).name(Objects.CLEAR_HOPS_PATCH).isEmpty() &&
                        ctx.objects.select(15).name(Objects.CLEAR_HOPS_PATCH).action("Rake").isEmpty();
        }

        return false;
    }
}
