package gutizia.util.listeners;


import java.util.EventListener;

public interface LevelListener extends EventListener {

    void onLevelChanged(LevelEvent levelEvent);
}
