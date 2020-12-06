package gutizia.util.listeners;

import org.powerbot.script.rt4.ClientContext;

import java.util.HashMap;
import java.util.Map;

public class LevelEventSource implements Runnable {

    private final EventDispatcher dispatcher;
    private final ClientContext ctx;
    private final int skillCount;
    private final Map<Integer, Integer> levelCache;

    public LevelEventSource(EventDispatcher dispatcher, ClientContext ctx) {
        this.dispatcher = dispatcher;
        this.ctx = ctx;
        this.skillCount = 23;
        this.levelCache = new HashMap<>();

        for (int i = 0; i < skillCount; i++) {
            levelCache.put(i, getLevelForSkillIndex(i));
        }
    }

    @Override
    public void run() {
        while (dispatcher.isRunning()) {
            for (int i = 0; i < skillCount; i++) {
                int oldLevel = levelCache.get(i);
                int newLevel = getLevelForSkillIndex(i);

                if (oldLevel != newLevel) {
                    if (!dispatcher.isPaused()) {
                        dispatcher.fireEvent(new LevelEvent(i, oldLevel, newLevel)); // ignores updating levels if paused
                    }
                    levelCache.put(i, newLevel);
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private int getLevelForSkillIndex(int skillIndex) {
        return ctx.skills.level(skillIndex);
    }

}