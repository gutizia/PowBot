package gutizia.util.listeners;

import java.util.EventListener;

public interface GroundItemListener extends EventListener {

    void onGroundItemChange(GroundItemEvent groundItemEvent);
}
