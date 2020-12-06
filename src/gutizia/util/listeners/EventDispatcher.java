package gutizia.util.listeners;

import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

public class EventDispatcher {

    private final List<EventListener> listeners;
    private final Object syncLock = new Object();
    private volatile boolean running;
    private boolean paused;

    public EventDispatcher(final ClientContext ctx) {
        this.listeners = new ArrayList<>();
        this.running = true;
        this.paused = false;

        new Thread(new InventoryEventSource(this, ctx)).start();
        new Thread(new ExperienceEventSource(this, ctx)).start();
        new Thread(new LevelEventSource(this, ctx)).start();
    }

    public void addListener(EventListener listener) {
        synchronized (syncLock) {
            listeners.add(listener);
        }
    }

    public void removeListener(EventListener listener) {
        synchronized (syncLock) {
            listeners.remove(listener);
        }
    }

    public void clearListeners() {
        synchronized (syncLock) {
            listeners.clear();
        }
    }

    protected void fireEvent(EventObject event) {
        synchronized (syncLock) {
            for (EventListener listener : listeners) {
                if (listener instanceof ExperienceListener && event instanceof ExperienceEvent) {
                    ((ExperienceListener) listener).onExperienceChanged((ExperienceEvent) event);
                } else if (listener instanceof InventoryListener && event instanceof InventoryEvent) {
                    ((InventoryListener) listener).onInventoryChange((InventoryEvent) event);
                }
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}
