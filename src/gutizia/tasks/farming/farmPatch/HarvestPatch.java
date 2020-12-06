package gutizia.tasks.farming.farmPatch;

import gutizia.tasks.farming.FreeInventorySpace;
import gutizia.util.Interact;
import gutizia.util.constants.Bounds;
import gutizia.util.managers.ChatManager;
import gutizia.util.skills.farming.Farming;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.GameObject;


class HarvestPatch extends FarmPatch {

    HarvestPatch(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        final GameObject patchObject;
        int[] bounds;
        final String action;

        if (farming.levelUpChatVisible()) {
            new ChatManager(ctx).finishDialog();
        }

        farming.equipMagicSecateurs();

        switch (patch) {
            case HERB:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).action("Pick").poll();
                bounds = Bounds.HERB;
                action = "Pick";
                break;

            case FLOWER:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).action("Pick").poll();
                bounds = Bounds.FLOWER;
                action = "Pick";
                break;

            case ALLOTMENT1:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).action("Harvest").nearest().poll();
                bounds = Bounds.ALLOTMENT;
                action = "Harvest";
                break;

            case ALLOTMENT2:
                patchObject = ctx.objects.select(15).within(farming.getPatchArea(patch)).action("Harvest").nearest().poll();
                bounds = Bounds.ALLOTMENT;
                action = "Harvest";
                break;

            case HOPS:
                patchObject = ctx.objects.select(15).action("Harvest").nearest().poll();
                bounds = Bounds.ALLOTMENT;
                action = "Harvest";
                break;

            default:
                LOGGER.info("ERROR! harvest Patch did not recognize patch: " + patch);
                return;
        }

        if (patchObject.equals(ctx.objects.nil())) {
            LOGGER.info("no patch to harvest/pick found");
            return;
        }

        if (patch.equals(Farming.Patch.FLOWER) && ctx.inventory.select().size() <= 18) {
            LOGGER.info("freeing some inventory space before harvesting flower patch");
            new FreeInventorySpace(ctx).execute();
        }

        patchObject.bounds(bounds);

        if (ctx.inventory.selectedItem().id() != -1) {
            Interact.removeSelectedItem(ctx);
        }

        if (ctx.inventory.select().size() == 28) {
            return;
        }

        if (patchObject.tile().distanceTo(ctx.players.local()) > 4) {
            ctx.movement.step(patchObject);
        }

        if (!patchObject.inViewport()) {
            ctx.movement.step(patchObject);
            ctx.camera.turnTo(patchObject);
        }

        final int initialExp = ctx.skills.experience(Constants.SKILLS_FARMING);

        if (!patchObject.interact(false,action,patchObject.name())) {
            ctx.camera.turnTo(patchObject);
            execute(); // retries
        }

        Condition.wait(() -> initialExp < ctx.skills.experience(Constants.SKILLS_FARMING),600,4);

        if (initialExp < ctx.skills.experience(Constants.SKILLS_FARMING)) {
            Condition.wait(() -> ctx.inventory.select().size() == 28 ||
                    ctx.objects.select(10).within(farming.getPatchArea(patch)).action(action).isEmpty() ||
                    farming.levelUpChatVisible(), 600, 40);
        }

        if (farming.levelUpChatVisible() || ctx.inventory.size() == 28) {
            execute(); // retries
        }
    }

    @Override
    public boolean activate() {
        // if flower patch, only activate if have 18 or less inventory size, this for the purpose of not losing limpwurts on the ground
        switch (farming.getFarmSpotYouAreAt()) {
            case CATHERBY_HAF:
                switch (patch) {
                    case HERB:
                        return !ctx.objects.select(15).within(PatchAreas.catherbyHerb).action("Pick","Harvest").isEmpty();

                    case ALLOTMENT1:
                        return !ctx.objects.select(15).within(PatchAreas.catherbyAllotment1).action("Pick","Harvest").isEmpty();

                    case ALLOTMENT2:
                        return !ctx.objects.select(15).within(PatchAreas.catherbyAllotment2).action("Pick","Harvest").isEmpty();

                    case FLOWER:
                        return !ctx.objects.select(15).within(PatchAreas.catherbyFlower).action("Pick","Harvest").isEmpty();
                }

            case HOSIDIUS_HAF:
                switch (patch) {
                    case HERB:
                        return !ctx.objects.select(15).within(PatchAreas.hosidiusHerb).action("Pick","Harvest").isEmpty();

                    case ALLOTMENT1:
                        return !ctx.objects.select(15).within(PatchAreas.hosidiusAllotment1).action("Pick","Harvest").isEmpty();

                    case ALLOTMENT2:
                        return !ctx.objects.select(15).within(PatchAreas.hosidiusAllotment2).action("Pick","Harvest").isEmpty();

                    case FLOWER:
                        return !ctx.objects.select(15).within(PatchAreas.hosidiusFlower).action("Pick","Harvest").isEmpty();
                }

            case CANIFIS_HAF:
                switch (patch) {
                    case HERB:
                        return !ctx.objects.select(15).within(PatchAreas.canifisHerb).action("Pick","Harvest").isEmpty();

                    case ALLOTMENT1:
                        return !ctx.objects.select(15).within(PatchAreas.canifisAllotment1).action("Pick","Harvest").isEmpty();

                    case ALLOTMENT2:
                        return !ctx.objects.select(15).within(PatchAreas.canifisAllotment2).action("Pick","Harvest").isEmpty();

                    case FLOWER:
                        return !ctx.objects.select(15).within(PatchAreas.canifisFlower).action("Pick","Harvest").isEmpty();
                }

            case ARDOUGNE_HAF:
                switch (patch) {
                    case HERB:
                        return !ctx.objects.select(15).within(PatchAreas.ardoungeHerb).action("Pick","Harvest").isEmpty();

                    case ALLOTMENT1:
                        return !ctx.objects.select(15).within(PatchAreas.ardoungeAllotment1).action("Pick","Harvest").isEmpty();

                    case ALLOTMENT2:
                        return !ctx.objects.select(15).within(PatchAreas.ardoungeAllotment2).action("Pick","Harvest").isEmpty();

                    case FLOWER:
                        return !ctx.objects.select(15).within(PatchAreas.ardoungeFlower).action("Pick","Harvest").isEmpty();
                }

            case FALADOR_HAF:
                switch (patch) {
                    case HERB:
                        return !ctx.objects.select(15).within(PatchAreas.faladorHerb).action("Pick","Harvest").isEmpty();

                    case ALLOTMENT1:
                        return !ctx.objects.select(15).within(PatchAreas.faladorAllotment1).action("Pick","Harvest").isEmpty();

                    case ALLOTMENT2:
                        return !ctx.objects.select(15).within(PatchAreas.faladorAllotment2).action("Pick","Harvest").isEmpty();

                    case FLOWER:
                        return !ctx.objects.select(15).within(PatchAreas.faladorFlower).action("Pick","Harvest").isEmpty();
                }

            case CAMELOT_HOPS:
                return !ctx.objects.select(15).within(PatchAreas.camelotHops).action("Pick","Harvest").isEmpty();
            case CHAMP_GUILD_HOPS:
                return !ctx.objects.select(15).within(PatchAreas.championGuildHops).action("Pick","Harvest").isEmpty();
            case YANILLE_HOPS:
                return !ctx.objects.select(15).within(PatchAreas.yanilleHops).action("Pick","Harvest").isEmpty();
            case ENTRANA_HOPS:
                return !ctx.objects.select(15).within(PatchAreas.entranaHops).action("Pick","Harvest").isEmpty();
        }

        LOGGER.info("did not recognize any patch of panels " + patch + " at spot " + farming.getFarmSpotYouAreAt());
        return false;
    }
}
