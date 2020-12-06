package gutizia.util.skills;

import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

public abstract class Skills extends ClientAccessor {

    public Skills(ClientContext ctx) {
        super(ctx);
    }

    public boolean levelUpChatVisible() {
        return !ctx.components.select().textContains("you just advanced a ").isEmpty();
    }
}