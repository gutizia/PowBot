package gutizia.tasks.farming;

import gutizia.tasks.Task;
import gutizia.util.Interact;
import gutizia.util.constants.Bounds;
import gutizia.util.constants.Items;
import gutizia.util.constants.Widgets;
import gutizia.util.skills.farming.Farming;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;
import org.powerbot.script.rt4.Npc;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static gutizia.util.trackers.HerbRunTracker.herbRunTracker;

public class FreeInventorySpace extends Task {
    private final static Logger LOGGER = Logger.getLogger("FreeInventorySpace");
    private Farming farming = new Farming(ctx);

    public FreeInventorySpace(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        // falador patch leprechaun wont note cabbages
        if (herbRunTracker.isDoingRun() && farming.getFarmSpotYouAreAt().equals(Farming.FarmingSpot.FALADOR_HAF) &&
                !ctx.inventory.select().id(Items.CABBAGE).isEmpty()) {
            LOGGER.info("dropping cabbages at falador patch because leprechaun won't note them");
            Interact.dropItems(ctx, Items.CABBAGE);
        }

        if (!ctx.inventory.select().id(Items.ALLOTMENT_PRODUCE, Items.GRIMY_HERB_PRODUCE, Items.FLOWER_PRODUCE, Items.HOPS_PRODUCE , new int[]{Items.WEEDS}).isEmpty()) {
            Npc leprechaun = ctx.npcs.select().name("Tool Leprechaun").poll();
            leprechaun.bounds(Bounds.LEPRECHAUN);

            if (leprechaun.tile().distanceTo(ctx.players.local().tile()) > 7) {
                ctx.movement.step(leprechaun.tile());
            }

            if (!leprechaun.inViewport()) {
                ctx.movement.step(leprechaun);
                ctx.camera.turnTo(leprechaun);
            }

            List<Integer> itemsNoted = new ArrayList<>();

            for (Item item : ctx.inventory) {
                if (item.id() == -1 || itemsNoted.contains(item.id()) || item.stackable()) {
                    continue;
                }

                Interact.use(ctx, item);
                if (leprechaun.interact(false, "Use", leprechaun.name())) {
                    itemsNoted.add(item.id());
                    Condition.wait(() -> ctx.widgets.component(Widgets.CHAT_BOX_ITEMS, 2).text().toLowerCase().contains("leprechaun exchanges your items"), 600, 6);
                }
            }

            ctx.input.send(" ");
        }

        if (!ctx.inventory.select().id(Items.WEEDS).isEmpty()) {
            Interact.dropItems(ctx, Items.WEEDS);
        }
    }

    @Override
    public boolean activate() {
        if (ctx.npcs.select().name("Tool Leprechaun").isEmpty()) {
            return false;
        }

        return (ctx.inventory.select().id(Items.GRIMY_HERB_PRODUCE, Items.FLOWER_PRODUCE, Items.ALLOTMENT_PRODUCE, Items.HOPS_PRODUCE).size() >= 8 &&
                !farming.getFarmSpotYouAreAt().equals(Farming.FarmingSpot.NONE));
    }
}