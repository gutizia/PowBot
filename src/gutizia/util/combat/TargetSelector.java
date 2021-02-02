package gutizia.util.combat;

import gutizia.util.settings.CombatSettings;
import gutizia.util.settings.LootSettings;
import gutizia.util.settings.TargetSettings;

import java.util.logging.Logger;

import static gutizia.util.combat.PlayerCombat.playerCombat;
import static gutizia.util.managers.LootManager.lootManager;

public class TargetSelector {
    private final static Logger LOGGER = Logger.getLogger("TargetSelector");
    public final static TargetSelector targetSelector = new TargetSelector();

    private Target targetToKill = Target.NONE;
    private Target nextTarget = Target.NONE;

    private TargetSelector() {}

    public Target getTargetToKill() {
        if (targetToKill.equals(Target.NONE)) {
            calculateTargetToKill();
        }
        return targetToKill;
    }

    public void calculateTargetToKill() {
        System.out.println("calculating targetToKill");
        Target oldTarget = targetToKill;
        Target nextTarget = Target.NONE;

        for (Target target : TargetSettings.getTargetPriorityList()) {
            boolean haveReqs = true;
            for (int skill : CombatSettings.getLevelingPrioOrder()) {
                if (!target.getRequirement().haveSkillReq(skill)) {
                    haveReqs = false;
                    break;
                }
            }
            if (haveReqs) {
                targetToKill = target;
                break;
            }
        }

        if (!oldTarget.equals(targetToKill)) {
            LOGGER.info("new target calculated: " + targetToKill);
            playerCombat.getTarget().setSize(targetToKill.getSize());
            CombatSettings.setIncludedAreas(targetToKill.getIncluded());
            CombatSettings.setExcludedAreas(targetToKill.getExcluded());
            if (LootSettings.isStandardLootTable()) {
                lootManager.setItemsToLoot(targetToKill.getItemsToLoot());
            }
        }
        if (!nextTarget.equals(this.nextTarget)) {
            LOGGER.info("new nextTarget calculated: " + nextTarget);
            this.nextTarget = nextTarget;
        }
    }

    public Target getNextTarget() {
        return nextTarget;
    }
}
