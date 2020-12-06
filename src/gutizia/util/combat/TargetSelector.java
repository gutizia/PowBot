package gutizia.util.combat;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;

public class TargetSelector {
    public final static TargetSelector targetSelector = new TargetSelector();

    private boolean customTarget = false;

    private Target targetToKill = Target.NONE;
    private Target defaultTarget = Target.NONE;

    private TargetSelector() {}

    public Target getTargetToKill(ClientContext ctx) {
        if (customTarget) {
            if (targetToKill.equals(Target.NONE)) {
                setTargetToKill(calculateTargetToKill(ctx));
            }
            return targetToKill;
        }
        if (defaultTarget.equals(Target.NONE)) {
            setDefaultTarget(calculateDefaultTarget(ctx));
        }
        return defaultTarget;
    }

    public Target calculateDefaultTarget(ClientContext ctx) {
        /*
        * chickens: maxHit < 3 || attackLevel < 10
        *
        * goblins:
        * */
        int attLvl = ctx.skills.realLevel(Constants.SKILLS_ATTACK);
        int defLvl = ctx.skills.realLevel(Constants.SKILLS_DEFENSE);
        int maxHit = -1; // TODO get max hit


        return Target.NONE;
    }

    private Target calculateTargetToKill(ClientContext ctx) {
        // TODO make this once you've made the ui settings
        System.err.println("calculateTargetToKill() called, but it's not implemented yet. returning calculateDefaultTarget() instead...");
        return calculateDefaultTarget(ctx);
    }

    public void setTargetToKill(Target targetToKill) {
        this.targetToKill = targetToKill;
    }

    private void setDefaultTarget(Target defaultTarget) {
        this.defaultTarget = defaultTarget;
    }

    public void setCustomTarget(boolean customTarget) {
        this.customTarget = customTarget;
    }
}
