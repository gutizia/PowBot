package gutizia.tasks.combat;

import gutizia.tasks.Task;
import gutizia.util.Interact;
import gutizia.util.settings.CombatSettings;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;

import static gutizia.util.combat.PlayerCombat.playerCombat;

public class AttackTarget extends Task {

    @Override
    public void execute() {
        Interact.removeSelectedItem(ctx);
        if ((!CombatSettings.getNormalAttackOptions().isIgnoreDistance() || playerCombat.getTarget().getNpc().tile().distanceTo(ctx.players.local()) > Interact.MAX_INTERACT_DISTANCE) &&
                playerCombat.getTarget().getNpc().tile().distanceTo(ctx.players.local()) > playerCombat.getInteractRange() && // not within range
                playerCombat.getTarget().getNpc().tile().distanceTo(ctx.movement.destination()) > playerCombat.getInteractRange()) { // and not moving within range
            if (CombatSettings.getNormalAttackOptions().isHopMouse()) {
                ctx.input.hop(playerCombat.getTarget().getNpc().tile().matrix(ctx).mapPoint());
                Condition.sleep(Random.nextGaussian(250, 400, 340));
                ctx.input.click(true);
            } else {
                ctx.movement.step(playerCombat.getTarget().getNpc().tile());
            }
        }

        if (!playerCombat.getTarget().getNpc().inViewport()) {
            ctx.camera.turnTo(playerCombat.getTarget().getNpc().tile());
        }

        if (CombatSettings.getNormalAttackOptions().isHopMouse()) {
            ctx.input.hop(playerCombat.getTarget().getNpc().tile().matrix(ctx).mapPoint());
            Condition.wait(() -> ctx.menu.containsAction(CombatSettings.getNormalAttackOptions().getAction()), 50, 6);
        }

        if (playerCombat.getTarget().getNpc().interact(CombatSettings.getNormalAttackOptions().getAction())) {
            if (CombatSettings.getNormalAttackOptions().isCombatRelated() && !CombatSettings.getNormalAttackOptions().getAction().equalsIgnoreCase("attack")) {
                playerCombat.setAttacking(false);
            }
            System.out.println("waiting for player to interact with interactable");
            Condition.wait(() -> ctx.players.local().interacting().equals(playerCombat.getTarget().getNpc()), 100, 20);
            System.out.println("interacting with object = " +
                    ctx.players.local().interacting().equals(playerCombat.getTarget().getNpc()));
            Condition.wait(() -> playerCombat.getTarget().getNpc().healthBarVisible(), 300, Random.nextInt(5, 12));
        }
    }

    @Override
    public boolean activate() {
        return !ctx.players.local().interacting().valid() && !playerCombat.getTarget().getNpc().healthBarVisible() && playerCombat.getTarget().getNpc().valid() &&
                !playerCombat.getTarget().getNpc().interacting().valid();
    }
}
