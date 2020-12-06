package gutizia.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class Logout extends Task {
    private final static Logger LOGGER = Logger.getLogger("Logout");

    private Activatable activatable;

    public Logout(ClientContext ctx, Activatable activatable) {
        super(ctx);
        this.activatable = activatable;
    }

    @Override
    public void execute() {
        LOGGER.info("logging out");
        ctx.game.tab(Game.Tab.LOGOUT);
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return ctx.widgets.component(182,12).visible();
            }
        },230,10);

        if (ctx.widgets.component(182,12).visible()) {
            ctx.widgets.component(182, 12).click();

        } else if (ctx.widgets.component(69,23).visible()) {
            ctx.widgets.component(69,23).click();
        }

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return !ctx.game.loggedIn();
            }
        },1000,5);
    }

    @Override
    public boolean activate() {
        return activatable.activate();
    }
}
