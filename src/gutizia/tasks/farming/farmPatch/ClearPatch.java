package gutizia.tasks.farming.farmPatch;

import gutizia.util.Interact;
import gutizia.util.constants.Bounds;
import gutizia.util.constants.Objects;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;


public class ClearPatch extends FarmPatch {

    ClearPatch(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        final GameObject patchObject;
        final String nameOfClearedPatch;
        int[] bounds;

        switch (patch) {
            case HERB:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).action("Clear").poll();
                nameOfClearedPatch = Objects.CLEAR_HERB_PATCH;
                bounds = Bounds.DEAD_HERB;
                break;

            case FLOWER:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).action("Clear").poll();
                nameOfClearedPatch = Objects.CLEAR_FLOWER_PATCH;
                bounds = Bounds.FLOWER;
                break;

            case ALLOTMENT1:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).action("Clear").nearest().poll();
                nameOfClearedPatch = Objects.CLEAR_ALLOTMENT_PATCH;
                bounds = Bounds.ALLOTMENT;
                break;

            case ALLOTMENT2:
                patchObject = ctx.objects.select(15).within(farming .getPatchArea(patch)).action("Clear").nearest().poll();
                nameOfClearedPatch = Objects.CLEAR_ALLOTMENT_PATCH;
                bounds = Bounds.ALLOTMENT;
                break;

            case HOPS:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).action("Clear").nearest().poll();
                nameOfClearedPatch = Objects.CLEAR_HOPS_PATCH;
                bounds = Bounds.ALLOTMENT; // maybe replace with it's own bounds, but should be same dimensions
                break;

            default:
                LOGGER.info("ERROR! clear patch did not recognize patch: " + patch);
                return;
        }

        if (patchObject.equals(ctx.objects.nil())) {
            LOGGER.info("no dead patch found");
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

        if (ctx.inventory.selectedItem().id() != -1) {
            Interact.removeSelectedItem(ctx);
        }

        if (patchObject.interact(false,"Clear",patchObject.name())) {
            Condition.wait(() -> !ctx.objects.select(5).within(farming.getPatchArea(patch)).name(nameOfClearedPatch).isEmpty(),1200,20);
        }

    }

    @Override
    public boolean activate() {
        switch (farming.getFarmSpotYouAreAt()) {
            case CATHERBY_HAF:
                switch (patch) {
                    case HERB:
                        return !ctx.objects.select(15).within(PatchAreas.catherbyHerb).action("Clear").isEmpty();

                    case ALLOTMENT1:
                        return !ctx.objects.select(15).within(PatchAreas.catherbyAllotment1).action("Clear").isEmpty();

                    case ALLOTMENT2:
                        return !ctx.objects.select(15).within(PatchAreas.catherbyAllotment2).action("Clear").isEmpty();

                    case FLOWER:
                        return !ctx.objects.select(15).within(PatchAreas.catherbyFlower).action("Clear").isEmpty();

                }
                break;

            case FALADOR_HAF:
                switch (patch) {
                    case HERB:
                        return !ctx.objects.select(15).within(PatchAreas.faladorHerb).action("Clear").isEmpty();

                    case ALLOTMENT1:
                        return !ctx.objects.select(15).within(PatchAreas.faladorAllotment1).action("Clear").isEmpty();

                    case ALLOTMENT2:
                        return !ctx.objects.select(15).within(PatchAreas.faladorAllotment2).action("Clear").isEmpty();

                    case FLOWER:
                        return !ctx.objects.select(15).within(PatchAreas.faladorFlower).action("Clear").isEmpty();

                }
                break;

            case ARDOUGNE_HAF:
                switch (patch) {
                    case HERB:
                        return !ctx.objects.select(15).within(PatchAreas.ardoungeHerb).action("Clear").isEmpty();

                    case ALLOTMENT1:
                        return !ctx.objects.select(15).within(PatchAreas.ardoungeAllotment1).action("Clear").isEmpty();

                    case ALLOTMENT2:
                        return !ctx.objects.select(15).within(PatchAreas.ardoungeAllotment2).action("Clear").isEmpty();

                    case FLOWER:
                        return !ctx.objects.select(15).within(PatchAreas.ardoungeFlower).action("Clear").isEmpty();

                }
                break;

            case CANIFIS_HAF:
                switch (patch) {
                    case HERB:
                        return !ctx.objects.select(15).within(PatchAreas.canifisHerb).action("Clear").isEmpty();

                    case ALLOTMENT1:
                        return !ctx.objects.select(15).within(PatchAreas.canifisAllotment1).action("Clear").isEmpty();

                    case ALLOTMENT2:
                        return !ctx.objects.select(15).within(PatchAreas.canifisAllotment2).action("Clear").isEmpty();

                    case FLOWER:
                        return !ctx.objects.select(15).within(PatchAreas.canifisFlower).action("Clear").isEmpty();

                }
                break;

            case HOSIDIUS_HAF:
                switch (patch) {
                    case HERB:
                        return !ctx.objects.select(15).within(PatchAreas.hosidiusHerb).action("Clear").isEmpty();

                    case ALLOTMENT1:
                        return !ctx.objects.select(15).within(PatchAreas.hosidiusAllotment1).action("Clear").isEmpty();

                    case ALLOTMENT2:
                        return !ctx.objects.select(15).within(PatchAreas.hosidiusAllotment2).action("Clear").isEmpty();

                    case FLOWER:
                        return !ctx.objects.select(15).within(PatchAreas.hosidiusFlower).action("Clear").isEmpty();

                }
                break;

            case ENTRANA_HOPS: case YANILLE_HOPS: case CHAMP_GUILD_HOPS: case CAMELOT_HOPS:
                return !ctx.objects.select(15).action("Clear").nearest().isEmpty();

        }

        LOGGER.info("did not identify any dead patches of panels " + patch + "at spot " + farming.getFarmSpotYouAreAt());
        return false;
    }
}
