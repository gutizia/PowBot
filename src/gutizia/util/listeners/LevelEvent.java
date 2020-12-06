package gutizia.util.listeners;

import java.util.EventObject;

public class LevelEvent extends EventObject {

    private final int skillIndex;
    private final int oldLevel;
    private final int newLevel;

    public LevelEvent(int skillIndex, int oldLevel, int newLevel) {
        super(skillIndex);
        this.skillIndex = skillIndex;
        this.oldLevel = oldLevel;
        this.newLevel = newLevel;
    }

    public int getSkillIndex() {
        return skillIndex;
    }

    public int getOldLevel() {
        return oldLevel;
    }

    public int getNewLevel() {
        return newLevel;
    }

    public int getExperienceChange() {
        return newLevel - oldLevel;
    }

}