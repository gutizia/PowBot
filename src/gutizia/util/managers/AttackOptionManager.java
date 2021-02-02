package gutizia.util.managers;

import gutizia.scripts.Script;
import gutizia.util.combat.AttackOption;
import gutizia.util.combat.AttackStyle;
import gutizia.util.combat.WeaponCategory;
import gutizia.util.constants.Widgets;
import gutizia.util.settings.CombatSettings;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Game;

import java.util.ArrayList;
import java.util.logging.Logger;

import static gutizia.util.combat.TargetSelector.targetSelector;

public class AttackOptionManager {
    private final static Logger LOGGER = Logger.getLogger("AttackOptionManager");
    public final static int ATTACK_STANCE_VARBIT = 43;

    private static ClientContext ctx = org.powerbot.script.ClientContext.ctx();

    public static void updateAttackOption() {
        LOGGER.info("checking if need to update attack option");
        if (targetSelector.getNextTarget().getRequirement().haveSkillReq(getSkillBeingTrained())) {
            LOGGER.info("reached requirement for current skill being trained");
            for (int skill : CombatSettings.getLevelingPrioOrder()) {
                if (!targetSelector.getNextTarget().getRequirement().haveSkillReq(skill)) {
                    LOGGER.info("found next skill to train: " + skill);
                    trainSkill(skill);
                    return;
                }
            }
        }
    }

    public static int getSkillBeingTrained() {
        switch (getCurrentWeaponCategory().getAttackOptions()[ctx.varpbits.varpbit(ATTACK_STANCE_VARBIT)].getAttackStyle()) {
            case AGGRESSIVE:
                return Constants.SKILLS_STRENGTH;

            case ACCURATE:
                return Constants.SKILLS_ATTACK;

            case DEFENSIVE:
                return Constants.SKILLS_DEFENSE;

            default:
                Script.stopScript("getSkillBeingTrained did not recognize attack style: " + getCurrentWeaponCategory().getAttackOptions()[ctx.varpbits.varpbit(ATTACK_STANCE_VARBIT)].getAttackStyle());
                return -1;
        }
    }

    public static void trainSkill(int skillToTrain) {
        if (getSkillBeingTrained() == skillToTrain) {
            LOGGER.info("already training that skill");
            return;
        }

        ArrayList<AttackStyle> allowedStyles = getAllowedAttackStyles(skillToTrain);
        System.out.println("allowed styles for skill " + skillToTrain + ": ");
        for (AttackStyle attackStyle : allowedStyles) {
            System.out.println(attackStyle);
        }
        int index = 0;
        for (AttackOption attackOption : getCurrentWeaponCategory().getAttackOptions()) {
            if (allowedStyles.contains(attackOption.getAttackStyle())) {
                changeAttackStyle(index);
                return;
            }
            index++;
        }
    }

    public static void changeAttackStyle(int index) {
        System.out.println("changing attack style to index: " + index);
        ctx.game.tab(Game.Tab.ATTACK, true);
        Condition.wait(() -> ctx.game.tab().equals(Game.Tab.ATTACK), 50, 10);
        ctx.widgets.component(Widgets.COMBAT_SETTINGS, 5 + (index * 4)).click();
        Condition.sleep(Random.nextInt(190, 340));
        ctx.game.tab(Game.Tab.INVENTORY, true);
        Condition.wait(() -> ctx.game.tab().equals(Game.Tab.INVENTORY), 50, 10);
    }

    private static ArrayList<AttackStyle> getAllowedAttackStyles(int skillToTrain) {
        ArrayList<AttackStyle> attackStyles = new ArrayList<>();
        switch (skillToTrain) {
            case Constants.SKILLS_ATTACK:
                attackStyles.add(AttackStyle.ACCURATE);
                break;

            case Constants.SKILLS_STRENGTH:
                attackStyles.add(AttackStyle.AGGRESSIVE);
                break;

            case Constants.SKILLS_DEFENSE:
                attackStyles.add(AttackStyle.DEFENSIVE_AUTOCAST);
                attackStyles.add(AttackStyle.DEFENSIVE);
                break;
        }
        return attackStyles;
    }

    public static WeaponCategory getCurrentWeaponCategory() {
        String s = ctx.widgets.component(Widgets.COMBAT_SETTINGS, 2).text().split(": ")[1];
        System.out.println("category: " + s);
        for (WeaponCategory weaponCategory : WeaponCategory.values()) {
            if (weaponCategory.getCategoryName().equalsIgnoreCase(s)) {
                return weaponCategory;
            }
        }
        Script.stopScript("getCurrentWeaponCategory returning null (category string = '" + s + "')");
        return WeaponCategory.UNARMED;
    }
}
