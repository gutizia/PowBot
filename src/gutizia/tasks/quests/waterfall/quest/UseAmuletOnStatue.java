package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;
import gutizia.util.Interact;
import gutizia.util.constants.Items;
import gutizia.util.constants.Runes;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;

import java.util.concurrent.Callable;

import static gutizia.util.overlay.TaskOverlay.taskOverlay;

public class UseAmuletOnStatue extends Task {

    @Override
    public void execute() {
        taskOverlay.setActiveTask("place amulet");
        ctx.movement.step(new Tile(2603,9913,0));

        if (ctx.equipment.itemAt(Equipment.Slot.NECK).id() == Items.GLARIALS_AMULET) {
            ctx.game.tab(Game.Tab.EQUIPMENT);
            ctx.equipment.itemAt(Equipment.Slot.NECK).click();
            Condition.wait(() -> ctx.inventory.select().name("Glarial's amulet").isEmpty(), 600, 4);
            ctx.game.tab(Game.Tab.INVENTORY);
        }

        if (!ctx.inventory.select().name("Glarial's amulet").isEmpty()) {
            Item amulet = ctx.inventory.select().name("Glarial's amulet").poll();
            GameObject statue = ctx.objects.select(10).name("Statue of Glarial").poll();

            Interact.use(ctx, amulet);
            statue.interact("Use",statue.name());

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return ctx.inventory.select().name("Glarial's amulet").isEmpty();
                }
            },600,10);
        }

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return new Tile(2604,9911,0).distanceTo(ctx.players.local()) < 20;
            }
        },600,10);
    }

    @Override
    public boolean activate() {
        return ctx.varpbits.varpbit(WaterfallQuest.VARBIT) == 6 && // correct stage of quest
                WaterfallQuest.FIRST_CHALICE_AREA.containsOrIntersects(ctx.players.local()) && // within lowerRiverIsland
                ctx.inventory.select().id(Runes.WATER).isEmpty(); // used runes on pillars
    }
}
