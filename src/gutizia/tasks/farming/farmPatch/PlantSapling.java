package gutizia.tasks.farming.farmPatch;

import gutizia.util.Interact;
import gutizia.util.constants.Bounds;
import gutizia.util.constants.Items;
import gutizia.util.constants.Objects;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;

class PlantSapling extends FarmPatch {

    PlantSapling(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        final GameObject patchObject;
        int[] bounds;
        final Tile tile;
        final String name;
        final Item sapling = farming.getSeed(patch);
        final int saplingsSize;

        switch (patch) {
            case FRUIT_TREE:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_FRUIT_TREE_PATCH).poll();
                tile = patchObject.tile();
                bounds = Bounds.HERB;
                saplingsSize = ctx.inventory.select().id(Items.FRUIT_TREE_SAPLINGS).size();
                break;

            default:
                LOGGER.info("ERROR! did not recognize patch: " + patch);
                return;
        }

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

        sapling.interact("Use");

        if (patchObject.interact(false,"Use",patchObject.name())) {
            Condition.wait(() -> ctx.inventory.select().name(sapling.name()).size() < saplingsSize,600,6);
        }

        if (!ctx.objects.select(5).at(tile).action("Rake").isEmpty()) {
            LOGGER.info("weeds grew while trying to plant sapling. Raking them now...");
            new RakePatch(ctx).execute();
            execute();
        }

        Condition.wait(() -> ctx.objects.select(5).at(tile).name(name).isEmpty(),600,6);

    }

    @Override
    public boolean activate() {
        switch (patch) {
            case FRUIT_TREE:
                return !ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_FRUIT_TREE_PATCH).isEmpty() &&
                        ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_FRUIT_TREE_PATCH).action("Rake").isEmpty();

            default:
                LOGGER.info("plantSapling did not recognize patch: " + patch);
                return false;
        }
    }
}
