package gutizia.util.settings;

import gutizia.scripts.Script;
import gutizia.util.PropertyUtil;
import gutizia.util.Requirement;
import gutizia.util.combat.Target;
import org.powerbot.script.rt4.Constants;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static gutizia.util.combat.TargetSelector.targetSelector;

public class TargetSettings {
    public final static String ATTACK_LEVEL_REQUIREMENT = "att.lvl.req";
    public final static String DEFENCE_LEVEL_REQUIREMENT = "def.lvl.req";
    public final static String STRENGTH_LEVEL_REQUIREMENT = "str.lvl.req";
    public final static String TARGET_ORDER = "target.order";

    private static PropertyUtil CHICKEN = new PropertyUtil(System.getProperty("java.io.tmpdir") + "PowBot\\chicken", Stream.of(new String[][] {
            {ATTACK_LEVEL_REQUIREMENT, "1"},
            {DEFENCE_LEVEL_REQUIREMENT, "1"},
            {STRENGTH_LEVEL_REQUIREMENT, "1"},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1])));

    private static PropertyUtil GOBLIN = new PropertyUtil(System.getProperty("java.io.tmpdir") + "PowBot\\goblin", Stream.of(new String[][] {
            {ATTACK_LEVEL_REQUIREMENT, "10"},
            {DEFENCE_LEVEL_REQUIREMENT, "10"},
            {STRENGTH_LEVEL_REQUIREMENT, "10"},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1])));

    private static PropertyUtil COW = new PropertyUtil(System.getProperty("java.io.tmpdir") + "PowBot\\cow", Stream.of(new String[][] {
            {ATTACK_LEVEL_REQUIREMENT, "30"},
            {DEFENCE_LEVEL_REQUIREMENT, "20"},
            {STRENGTH_LEVEL_REQUIREMENT, "20"},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1])));

    private static PropertyUtil TARGETS = new PropertyUtil(System.getProperty("java.io.tmpdir") + "PowBot\\targets", Stream.of(new String[][] {
            {TARGET_ORDER, Target.COWS.toString() + "," + Target.GOBLINS.toString() + "," + Target.CHICKENS.toString()},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1])));

    public static ArrayList<Target> getTargetPriorityList() {
        ArrayList<Target> targets = new ArrayList<>();

        for (String s : TARGETS.getProperty(TARGET_ORDER).split(",")) {
            for (Target t : Target.values()) {
                if (s.equals(t.toString())) {
                    targets.add(t);
                }
            }
        }
        return targets;
    }

    public static int getCurrentTargetAttThreshold() {
        switch (targetSelector.getTargetToKill()) {
            case CHICKENS:
                return Integer.parseInt(CHICKEN.getProperty(ATTACK_LEVEL_REQUIREMENT));

            case GOBLINS:
                return Integer.parseInt(GOBLIN.getProperty(ATTACK_LEVEL_REQUIREMENT));

            case COWS:
                return Integer.parseInt(COW.getProperty(ATTACK_LEVEL_REQUIREMENT));

            default:
                Script.stopScript("TargetSettings class failed to find default target (target = " + targetSelector.getTargetToKill() + ")");
                return -1;
        }
    }

    public static int getCurrentTargetDefReq() {
        switch (targetSelector.getTargetToKill()) {
            case CHICKENS:
                return Integer.parseInt(CHICKEN.getProperty(DEFENCE_LEVEL_REQUIREMENT));

            case GOBLINS:
                return Integer.parseInt(GOBLIN.getProperty(DEFENCE_LEVEL_REQUIREMENT));

            case COWS:
                return Integer.parseInt(COW.getProperty(DEFENCE_LEVEL_REQUIREMENT));

            default:
                Script.stopScript("TargetSettings class failed to find default target (target = " + targetSelector.getTargetToKill() + ")");
                return -1;
        }
    }

    public static int getCurrentTargetStrReq() {
        switch (targetSelector.getTargetToKill()) {
            case CHICKENS:
                return Integer.parseInt(CHICKEN.getProperty(STRENGTH_LEVEL_REQUIREMENT));

            case GOBLINS:
                return Integer.parseInt(GOBLIN.getProperty(STRENGTH_LEVEL_REQUIREMENT));

            case COWS:
                return Integer.parseInt(COW.getProperty(STRENGTH_LEVEL_REQUIREMENT));

            default:
                Script.stopScript("TargetSettings class failed to find default target (target = " + targetSelector.getTargetToKill() + ")");
                return -1;
        }
    }

    public static Requirement getRequirements(Target target) {
        Requirement requirement = new Requirement();

        switch (target) {
            case CHICKENS:
                requirement.setSkills(Stream.of(new Integer[][] {{
                    Constants.SKILLS_ATTACK, Integer.parseInt(CHICKEN.getProperty(ATTACK_LEVEL_REQUIREMENT)),
                    Constants.SKILLS_STRENGTH, Integer.parseInt(CHICKEN.getProperty(STRENGTH_LEVEL_REQUIREMENT)),
                    Constants.SKILLS_DEFENSE, Integer.parseInt(CHICKEN.getProperty(DEFENCE_LEVEL_REQUIREMENT)),
                        },
                }).collect(Collectors.toMap(data -> data[0], data -> data[1])));
                break;

            case GOBLINS:
                requirement.setSkills(Stream.of(new Integer[][] {{
                        Constants.SKILLS_ATTACK, Integer.parseInt(GOBLIN.getProperty(ATTACK_LEVEL_REQUIREMENT)),
                        Constants.SKILLS_STRENGTH, Integer.parseInt(GOBLIN.getProperty(STRENGTH_LEVEL_REQUIREMENT)),
                        Constants.SKILLS_DEFENSE, Integer.parseInt(GOBLIN.getProperty(DEFENCE_LEVEL_REQUIREMENT)),
                },
                }).collect(Collectors.toMap(data -> data[0], data -> data[1])));
                break;

            case COWS:
                requirement.setSkills(Stream.of(new Integer[][] {{
                        Constants.SKILLS_ATTACK, Integer.parseInt(COW.getProperty(ATTACK_LEVEL_REQUIREMENT)),
                        Constants.SKILLS_STRENGTH, Integer.parseInt(COW.getProperty(STRENGTH_LEVEL_REQUIREMENT)),
                        Constants.SKILLS_DEFENSE, Integer.parseInt(COW.getProperty(DEFENCE_LEVEL_REQUIREMENT)),
                },
                }).collect(Collectors.toMap(data -> data[0], data -> data[1])));
                break;
        }
        return requirement;
    }
}
