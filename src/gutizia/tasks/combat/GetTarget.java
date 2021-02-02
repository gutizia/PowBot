package gutizia.tasks.combat;

import gutizia.tasks.Task;
import gutizia.util.constants.Areas;
import gutizia.util.settings.CombatSettings;
import org.powerbot.script.rt4.Npc;

import static gutizia.util.combat.PlayerCombat.playerCombat;
import static gutizia.util.combat.TargetSelector.targetSelector;

public class GetTarget extends Task {

    @Override
    public void execute() {
        ctx.npcs.select();
        ctx.npcs.id(targetSelector.getTargetToKill().getIds());
        ctx.npcs.within(CombatSettings.getIncludedAreas());
        for (Npc npc : ctx.npcs.select().id(targetSelector.getTargetToKill().getIds()).within(CombatSettings.getIncludedAreas()).nearest()) {
            if (!npc.interacting().valid() && !npc.healthBarVisible() &&
                !CombatSettings.getExcludedAreas().contains(npc) && npc.tile().matrix(ctx).reachable()) {
                playerCombat.getTarget().setNpc(npc);
                return;
            }
        }
    }

    @Override
    public boolean activate() {
        if (playerCombat.getTarget().isDead()) {
            return true;
        }

        return !playerCombat.getTarget().getNpc().valid() ||
                (!playerCombat.getTarget().getNpc().interacting().equals(ctx.players.local()) && playerCombat.getTarget().getNpc().interacting().valid());
    }
}
