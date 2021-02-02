package gutizia.tasks.quests.oldRestlessGhost;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import java.util.concurrent.Callable;

public class GetSkull extends OldRestlessGhost {

    GetSkull(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        //overlay.changeStatus("getting skull");
        GameObject altar = ctx.objects.select(10).name("Altar").nearest().poll();
        altar.interact("Search");
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return !ctx.inventory.select().name("Ghost's skull").isEmpty();
            }
        },600,6);
    }

    @Override
    public boolean activate() {
        return altarRoom.containsOrIntersects(ctx.players.local()) && // if in altar room
                ctx.inventory.select().name("Ghost's skull").isEmpty(); // and don't have skull in inventory
    }
}
