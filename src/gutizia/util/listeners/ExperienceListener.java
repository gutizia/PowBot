package gutizia.util.listeners;

import java.util.EventListener;

public interface ExperienceListener extends EventListener {

    void onExperienceChanged(ExperienceEvent experienceEvent);

}