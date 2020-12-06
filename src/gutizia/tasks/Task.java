package gutizia.tasks;

import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import java.util.logging.Logger;

public abstract class Task extends ClientAccessor implements Activatable {
    protected final static Logger LOGGER = Logger.getLogger("Task");

    public Task(ClientContext ctx) {
        super(ctx);
    }

    abstract public void execute();
    abstract public boolean activate();
}
