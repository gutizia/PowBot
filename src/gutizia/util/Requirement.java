package gutizia.util;

import org.powerbot.script.rt4.ClientContext;

import java.util.Map;

public class Requirement {

    private Map<Integer, Integer> skills = null;
    private Map<Integer, Integer> varbits = null;

    public boolean haveSkillReq(int skillIndex) {
        if (skills.containsKey(skillIndex)) {
            return ClientContext.ctx().skills.realLevel(skillIndex) >= skills.get(skillIndex);
        }
        return true;
    }

    public void setSkills(Map<Integer, Integer> skills) {
        this.skills = skills;
    }

    public void setVarbits(Map<Integer, Integer> varbits) {
        this.varbits = varbits;
    }

    // map of skill index and what level it has to be?
    // additional requirements like: items, maxhit, varbits etc
}
