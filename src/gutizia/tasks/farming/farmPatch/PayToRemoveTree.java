package gutizia.tasks.farming.farmPatch;

import gutizia.util.Interact;
import gutizia.util.skills.farming.Farming;
import gutizia.util.skills.farming.PatchAreas;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;

import static gutizia.util.trackers.FruitTreeTracker.fruitTreeTracker;

public class PayToRemoveTree extends FarmPatch {

    public PayToRemoveTree(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        Npc npc = ctx.npcs.select().action("Pay").nearest().poll();

        if (Interact.interact(ctx,npc,true,"Pay",true)) Condition.wait(() ->
                ctx.widgets.component(219,1,0).text().contains("have your tree chopped down?"),1000,6);

        if (ctx.widgets.component(219,1,0).text().contains("have your tree chopped down?")) {
            ctx.input.send("1");
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
            return !ctx.objects.select(15).within(patchArea).action("Chop-down").isEmpty();
        }
        return false;
    }
}
