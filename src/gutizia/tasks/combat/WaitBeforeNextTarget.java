package gutizia.tasks.combat;

import gutizia.tasks.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;

import static gutizia.util.combat.PlayerCombat.playerCombat;

public class WaitBeforeNextTarget extends Task {

    @Override
    public void execute() {
        Condition.sleep(Random.nextGaussian(357, 12000, 700));
    }

    @Override
    public boolean activate() {
        return playerCombat.getTarget().isDead();
    }
}
