package gutizia.tasks.quests.oldRestlessGhost;

import gutizia.util.Interact;
import gutizia.util.constants.Widgets;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;

import java.util.concurrent.Callable;

public class UseSkullOnCoffin extends OldRestlessGhost {

    UseSkullOnCoffin(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        //overlay.changeStatus("using skull on coffin");

        Item skull = ctx.inventory.select().name("Ghost's skull").poll();

        Interact.use(ctx, skull);
        ctx.objects.select(10).name("Coffin").poll().interact("Use");

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return ctx.inventory.selectedItem().id() == -1;
            }
        },600,5);

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return ctx.widgets.component(Widgets.QUEST_COMPLETE,15).visible();
            }
        },900,20);
    }

    @Override
    public boolean activate() {

        return coffinRoom.containsOrIntersects(ctx.players.local()) && // in coffin room
                ctx.varpbits.varpbit(107) == 4 && // correct state of quest
                ctx.objects.select(10).name("Coffin").action("Open").isEmpty(); // coffin is open
    }
}
