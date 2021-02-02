package gutizia.util.listeners;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GroundItem;

import java.util.ArrayList;

public class GroundItemEventSource implements Runnable {

    private final EventDispatcher dispatcher;
    private final ClientContext ctx;
    private final ArrayList<GroundItem> cache;

    public GroundItemEventSource(EventDispatcher dispatcher, ClientContext ctx) {
        this.dispatcher = dispatcher;
        this.ctx = ctx;
        this.cache = new ArrayList<>();
    }

    @Override
    public void run() {
        while (dispatcher.isRunning()) {
            // check for new items
            for (GroundItem groundItem : ctx.groundItems.select(32)) {
                if (!cache.contains(groundItem)) {
                    dispatcher.fireEvent(new GroundItemEvent(groundItem.tile(), groundItem, null));
                    cache.add(groundItem);
                }
            }

            // check for removed items
            ArrayList<GroundItem> toRemove = new ArrayList<>();
            for (GroundItem groundItem : cache) {
                if (!groundItem.valid()) {
                    dispatcher.fireEvent(new GroundItemEvent(groundItem.tile(), null, groundItem));
                    toRemove.add(groundItem);
                }
            }
            cache.removeAll(toRemove);
            try {
                Thread.sleep(1800); // fires every 3 ticks
            } catch (InterruptedException ignored) {
            }
        }
    }
}
