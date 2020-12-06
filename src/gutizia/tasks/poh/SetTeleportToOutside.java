package gutizia.tasks.poh;

import gutizia.tasks.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;

import java.util.concurrent.Callable;

public class SetTeleportToOutside extends Task {

    public SetTeleportToOutside(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        ctx.game.tab(Game.Tab.OPTIONS);
        ctx.widgets.component(261,106).click();

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return ctx.widgets.component(370,9).visible();
            }
        },600,5);

        ctx.widgets.component(370,9).click();

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return ctx.varpbits.varpbit(1047) != 0;
            }
        },500,4);

        while (ctx.widgets.component(370,9).visible()) {
            ctx.widgets.component(370,21).click();

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return !ctx.widgets.component(370,9).visible();
                }
            },600,4);
        }

        ctx.game.tab(Game.Tab.INVENTORY);
    }

    @Override
    public boolean activate() {
        return ctx.varpbits.varpbit(738) != 0 &&
                ctx.varpbits.varpbit(1047) == 0;
    }
}
