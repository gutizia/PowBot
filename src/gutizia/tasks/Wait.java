package gutizia.tasks;

import org.powerbot.script.Condition;

public class Wait extends Task {

    private Activatable activatable;
    private Activatable waitFor;
    private int freq = 300;
    private int tries = 14;

    public Wait(Activatable activatable) {
        this.activatable = activatable;
        this.waitFor = () -> false;
    }

    public Wait(Activatable activatable, Activatable waitFor) {
        this.activatable = activatable;
        this.waitFor = waitFor;
    }

    public Wait(Activatable activatable, Activatable waitFor, int freq, int tries) {
        this.activatable = activatable;
        this.waitFor = waitFor;
        this.freq = freq;
        this.tries = tries;
    }

    @Override
    public void execute() {
        Condition.wait(() -> waitFor.activate(), freq, tries);
    }

    @Override
    public boolean activate() {
        return activatable.activate();
    }
}
