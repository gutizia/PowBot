package gutizia.tasks;

import gutizia.util.InteractOptions;
import gutizia.util.Interactive;
import org.powerbot.script.Actionable;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Npc;

import static gutizia.util.combat.PlayerCombat.playerCombat;

public class Interact extends Task {
    private Interactive interactive;
    private Activatable activatable;
    private Activatable wait;
    private InteractOptions interactOptions;

    public Interact(ClientContext ctx, Activatable activatable, Interactive interactive, InteractOptions interactOptions) {
        super(ctx);
        this.activatable = activatable;
        this.interactive = interactive;
        this.interactOptions = interactOptions;
        wait = null;
    }

    public Interact(ClientContext ctx, Activatable activatable, Interactive interactive, InteractOptions interactOptions, Activatable wait) {
        this(ctx, activatable, interactive, interactOptions);
        this.wait = wait;
    }

    @Override
    public void execute() {
        gutizia.util.Interact.removeSelectedItem(ctx);
        if ((!interactOptions.isIgnoreDistance() || interactive.getTile().distanceTo(ctx.players.local()) > gutizia.util.Interact.MAX_INTERACT_DISTANCE) &&
                interactive.getTile().distanceTo(ctx.players.local()) > interactive.getInteractRange() && // not within range
                interactive.getTile().distanceTo(ctx.movement.destination()) > interactive.getInteractRange()) { // and not moving within range
            if (interactOptions.isHopMouse()) {
                ctx.input.hop(interactive.getTile().matrix(ctx).mapPoint());
                Condition.sleep(Random.nextGaussian(250, 400, 340));
                ctx.input.click(true);
            } else {
                ctx.movement.step(interactive.getTile());
            }
        }

        if (!interactive.getInteractive().inViewport()) {
            ctx.camera.turnTo(interactive.getTile());
        }

        if (interactOptions.isHopMouse()) {
            ctx.input.hop(interactive.getTile().matrix(ctx).mapPoint());
            Condition.wait(() -> ctx.menu.containsAction(interactOptions.getAction()), 50, 6);
        }

        if (interactive.getInteractive().interact(interactOptions.getAction())) {
            if (interactOptions.isCombatRelated() && !interactOptions.getAction().equalsIgnoreCase("attack")) {
                playerCombat.setAttacking(false);
            }
            System.out.println("waiting for player to interact with interactable");
            Condition.wait(() -> ctx.players.local().interacting().equals(interactive.getInteractive()), 100, 20);
            System.out.println("interacting with object = " +
                    ctx.players.local().interacting().equals(interactive.getInteractive()));
            if (wait != null) {
                Condition.wait(() -> wait.activate(), 200, 15);
            }
        }
    }

    @Override
    public boolean activate() {

        if (ctx.players.local().interacting().equals(interactive.getInteractive())) {
            System.out.println("already interacting with this interactive...");
            return false;
        }
        if (!interactive.isValid()) {
            System.out.println("querying new interactive object...");
            interactive.queryInteractive(ctx, interactOptions.isCombatRelated());
        }

        if (!gutizia.util.Interact.actionContains(((Actionable)interactive.getInteractive()).actions(), interactOptions.getAction())) {
            System.out.println("interactive object did not contain interactOptions's action: '" + interactOptions.getAction() + "'");
            return false;
        }

        if (interactOptions.isRequiresInRange() && interactive.getTile().distanceTo(ctx.players.local()) > interactive.getInteractRange()) {
            System.out.println("interactive object is out of range (current distance: " + interactive.getTile().distanceTo(ctx.players.local()) + ")");
            return false;
        }
        if (interactOptions.isRequiresInViewport() && !interactive.getInteractive().inViewport()) {
            System.out.println("interactive object is not in viewport");
            return false;
        }
        return activatable.activate();
    }
}
