package gutizia.util;

public class InteractOptions {
    private String action;
    private boolean requiresInRange;
    private boolean requiresInViewport;
    private boolean ignoreDistance;
    private boolean hopMouse;
    private boolean isCombatRelated;

    public InteractOptions(String action, boolean requiresInRange, boolean requiresInViewport, boolean ignoreDistance, boolean hopMouse, boolean isCombatRelated) {
        this.action = action;
        this.requiresInRange = requiresInRange;
        this.requiresInViewport = requiresInViewport;
        this.ignoreDistance = ignoreDistance;
        this.hopMouse = hopMouse;
        this.isCombatRelated = isCombatRelated;
    }

    public String getAction() {
        return action;
    }

    public boolean isCombatRelated() {
        return isCombatRelated;
    }

    public boolean isHopMouse() {
        return hopMouse;
    }

    public boolean isIgnoreDistance() {
        return ignoreDistance;
    }

    public boolean isRequiresInRange() {
        return requiresInRange;
    }

    public boolean isRequiresInViewport() {
        return requiresInViewport;
    }
}
