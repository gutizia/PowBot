package gutizia.util.listeners;

import org.powerbot.script.rt4.ClientContext;

import java.util.HashMap;
import java.util.Map;

public class ExperienceEventSource implements Runnable {

    private final EventDispatcher dispatcher;
    private final ClientContext ctx;
    private final int skillCount;
    private final Map<Integer, Integer> experienceCache;

    public ExperienceEventSource(EventDispatcher dispatcher, ClientContext ctx) {
        this.dispatcher = dispatcher;
        this.ctx = ctx;
        this.skillCount = 23;
        this.experienceCache = new HashMap<>();

        for (int i = 0; i < skillCount; i++) {
            experienceCache.put(i, getExperienceForSkillIndex(i));
        }
    }

    @Override
    public void run() {
        while (dispatcher.isRunning()) {
            for (int i = 0; i < skillCount; i++) {
                int oldExperience = experienceCache.get(i);
                int newExperience = getExperienceForSkillIndex(i);

                if (oldExperience != newExperience) {
                    if (!dispatcher.isPaused())  {
                        dispatcher.fireEvent(new ExperienceEvent(i, oldExperience, newExperience));
                    }
                    experienceCache.put(i, newExperience);
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private int getExperienceForSkillIndex(int skillIndex) {
        return ctx.skills.experience(skillIndex);
    }

}