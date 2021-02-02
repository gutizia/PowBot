package gutizia.tasks.quests.oldRestlessGhost;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

public class OpenCoffin extends OldRestlessGhost {

    OpenCoffin(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {

        //overlay.changeStatus("opening coffin");

        GameObject coffin = ctx.objects.poll();
        coffin.interact("Open");
        Condition.sleep(Random.nextInt(900, 1600));
    }

    @Override
    public boolean activate() {
        return coffinRoom.containsOrIntersects(ctx.players.local()) && // if in coffin room
                !ctx.objects.select(10).name("Coffin").action("Open").isEmpty(); // is not already open
    }
}
