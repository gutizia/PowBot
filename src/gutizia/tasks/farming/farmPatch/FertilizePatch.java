package gutizia.tasks.farming.farmPatch;

import gutizia.tasks.farming.GetStoredCompost;
import gutizia.util.Interact;
import gutizia.util.constants.Bounds;
import gutizia.util.constants.Items;
import gutizia.util.constants.Objects;
import gutizia.util.managers.ChatManager;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;

import static gutizia.util.trackers.CompostTracker.compostTracker;
import static gutizia.util.trackers.FruitTreeTracker.fruitTreeTracker;
import static gutizia.util.trackers.TreeTracker.treeTracker;

class FertilizePatch extends PlantPatch {

    FertilizePatch(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        GameObject patchObject;

        switch (patch) {
            case HERB:
                patchObject = ctx.objects.select(15).name(Objects.HERBS).poll();
                break;

            case FLOWER:
                patchObject = ctx.objects.select(10).name(Objects.FLOWERS).poll();
                break;

            case ALLOTMENT1:
                patchObject = ctx.objects.select(10).within(farming.getPatchArea(patch)).name(Objects.ALLOTMENT).nearest().poll();
                patchObject.bounds(Bounds.ALLOTMENT);
                break;

            case ALLOTMENT2:
                patchObject = ctx.objects.select(10).within(farming.getPatchArea(patch)).name(Objects.ALLOTMENT).nearest().poll();
                patchObject.bounds(Bounds.ALLOTMENT);
                break;

            case HOPS:
                patchObject = ctx.objects.select(10).name(Objects.HOPS).nearest().poll();
                patchObject.bounds(Bounds.ALLOTMENT);
                break;

            default:
                LOGGER.info("ERROR! did not recognize patch: " + patch);
                return;
        }

        if (patchObject.equals(ctx.objects.nil())) {
            LOGGER.info("no patch to fertilize found");
            usedCompost = true;
            return;
        }

        int compostId = compostTracker.getCompostForSeed(seed);

        if (compostTracker.isUsingBottomlessBucket()) {
            String bottomlessBucketType = compostTracker.getBlCompostType();
            LOGGER.info("bottomlessBucketType = " + bottomlessBucketType);

            switch (compostId) {
                case Items.COMPOST:
                    if (bottomlessBucketType.equals("normal")) {
                        compostId = Items.BOTTOMLESS_BUCKET;
                        LOGGER.info("using bottomless bucket");
                    }
                    break;

                case Items.SUPERCOMPOST:
                    if (bottomlessBucketType.equals("super")) {
                        compostId = Items.BOTTOMLESS_BUCKET;
                        LOGGER.info("using bottomless bucket");
                    }
                    break;

                case Items.ULTRACOMPOST:
                    if (bottomlessBucketType.equals("ultra")) {
                        compostId = Items.BOTTOMLESS_BUCKET;
                        LOGGER.info("using bottomless bucket");
                    }
                    break;
            }
        }

        Item compost = ctx.inventory.select().id(compostId).poll();

        if (compost.id() == -1) {
            LOGGER.info("no valid compost found for patch " + patch);
            usedCompost = true;
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
        Interact.use(ctx, compost);
        ChatManager chatManager = new ChatManager(ctx);

        if (patchObject.interact(false,"Use",patchObject.name())) {
            Condition.wait(() -> initialExp < ctx.skills.experience(Constants.SKILLS_FARMING) || chatManager.getLastGameMessage().contains("already been treated"),600,5);
        }

        if (initialExp < ctx.skills.experience(Constants.SKILLS_FARMING)) {
            LOGGER.info("successfully treated patch with compost");
            usedCompost = true;
        }

        if (chatManager.getLastGameMessage().contains("already been treated")) {
            LOGGER.info("patch was already treated with compost");
            usedCompost = true;
        }

    }

    @Override
    public boolean activate() {
        if (usedCompost) {
            return false;
        }
        if (!fruitTreeTracker.isDoingRun() && !treeTracker.isDoingRun() &&
                FarmPatch.initialSeedAmount == farming.getSeed(patch).stackSize()) {
            return false;
        }

        if (compostTracker.isUsingBottomlessBucket()) {
            if (!ctx.inventory.select().id(Items.BOTTOMLESS_BUCKET_EMPTY).isEmpty()) {
                LOGGER.info("bottomless bucket is empty! not using bottomless bucket anymore");
                compostTracker.setUsingBottomlessBucket(false);
                LOGGER.info("getting new compost from leprechaun");
                new GetStoredCompost(ctx).execute();
            }
        }

        switch (patch) {
            case HERB:
                return ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_HERB_PATCH).isEmpty();

            case FLOWER:
                return ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_FLOWER_PATCH).isEmpty();

            case ALLOTMENT1:
                return ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_ALLOTMENT_PATCH).isEmpty();

            case ALLOTMENT2:
                return ctx.objects.select(15).within(farming.getPatchArea(patch)).name(Objects.CLEAR_ALLOTMENT_PATCH).isEmpty();

            case HOPS:
                return ctx.objects.select(15).name(Objects.CLEAR_HOPS_PATCH).isEmpty();
        }
        return true;
    }
}
