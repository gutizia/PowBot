package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;
import gutizia.util.Interact;
import gutizia.util.constants.Runes;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;

import static gutizia.util.overlay.TaskOverlay.taskOverlay;

public class UseRunesOnPillars extends Task {

    @Override
    public void execute() {
        taskOverlay.setActiveTask("apply runes");

        for (GameObject pillar : ctx.objects.select(20).name("Pillar")) {
            final Item airRune = ctx.inventory.select().id(Runes.AIR).poll();
            final Item waterRune = ctx.inventory.select().id(Runes.WATER).poll();
            final Item earthRune = ctx.inventory.select().id(Runes.EARTH).poll();
            final int airRuneAmount = airRune.stackSize();
            final int waterRuneAmount = waterRune.stackSize();
            final int earthRuneAmount = earthRune.stackSize();

            ctx.movement.step(pillar.tile());

            if (!pillar.inViewport()) {
                ctx.camera.turnTo(pillar);
            }

            while (airRuneAmount == ctx.inventory.select().id(Runes.AIR).poll().stackSize()) {
                Interact.use(ctx, airRune);
                pillar.interact("Use");
                Condition.wait(() -> airRuneAmount != airRune.stackSize(), 300, 5);
            }

            while (waterRuneAmount == ctx.inventory.select().id(Runes.WATER).poll().stackSize()) {
                Interact.use(ctx, waterRune);
                pillar.interact("Use");

                Condition.wait(() -> waterRuneAmount != waterRune.stackSize(), 300, 5);
            }

            while (earthRuneAmount == ctx.inventory.select().id(Runes.EARTH).poll().stackSize()) {
                Interact.use(ctx, earthRune);
                pillar.interact("Use");
                Condition.wait(() -> earthRuneAmount != earthRune.stackSize(), 300, 5);
            }
            Condition.sleep(Random.nextInt(600,1000));
        }
    }

    @Override
    public boolean activate() {
        return ctx.varpbits.varpbit(WaterfallQuest.VARBIT) == 6 && // correct stage of quest
                WaterfallQuest.FIRST_CHALICE_AREA.containsOrIntersects(ctx.players.local()) && // within lowerRiverIsland
                !ctx.inventory.select().id(Runes.WATER).isEmpty(); // still have water rune
    }
}
