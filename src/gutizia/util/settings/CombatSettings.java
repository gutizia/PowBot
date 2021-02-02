package gutizia.util.settings;

import gutizia.tasks.Task;
import gutizia.tasks.combat.AttackTarget;
import gutizia.tasks.combat.GetTarget;
import gutizia.tasks.combat.WaitBeforeNextTarget;
import gutizia.tasks.loot.LootItemsToLoot;
import gutizia.util.InteractOptions;
import gutizia.util.PropertyUtil;
import org.powerbot.script.Area;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.Constants;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CombatSettings {
    public final static String IGNORE_DISTANCE = "ignore.distance";
    public final static String HOP_MOUSE = "hop.mouse";

    public final static String LOOT_BONES = "loot.bones";
    public final static String BURY_BONES = "bury.bones"; // if false and loot.bones is true the bones will be banked

    // leveling
    public final static String LEVEL_DEFENCE = "level.defence";
    public final static String LEVELING_FIRST_PRIORITY = "leveling.first.prio";
    public final static String LEVELING_SECOND_PRIORITY = "leveling.second.prio";
    public final static String LEVELING_THIRD_PRIORITY = "leveling.third.prio";

    private static Area excludedAreas = new Area(Tile.NIL);
    private static Area includedAreas = new Area(Tile.NIL);

    private static PropertyUtil propertyUtil = new PropertyUtil(System.getProperty("java.io.tmpdir") + "PowBot\\combat",
            Stream.of(new String[][] {
                    {IGNORE_DISTANCE, "false"},
                    {HOP_MOUSE, "false"},
                    {LOOT_BONES, "false"},
                    {BURY_BONES, "false"},
                    {LEVEL_DEFENCE, "true"},
                    {LEVELING_FIRST_PRIORITY, Integer.toString(Constants.SKILLS_STRENGTH)},
                    {LEVELING_SECOND_PRIORITY, Integer.toString(Constants.SKILLS_ATTACK)},
                    {LEVELING_THIRD_PRIORITY, Integer.toString(Constants.SKILLS_DEFENSE)},
            }).collect(Collectors.toMap(data -> data[0], data -> data[1])));
    // public final static String _ = "";
    // what to loot
    //

    public static InteractOptions getNormalAttackOptions() {
        return new InteractOptions("Attack", true, true,
                propertyUtil.getProperty(IGNORE_DISTANCE).equalsIgnoreCase("true"),
                propertyUtil.getProperty(HOP_MOUSE).equalsIgnoreCase("true"),true);
    }

    public static ArrayList<Task> getCombatTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new LootItemsToLoot());
        tasks.add(new GetTarget());
        tasks.add(new WaitBeforeNextTarget());
        tasks.add(new AttackTarget());
        if (propertyUtil.getProperty(LOOT_BONES).equalsIgnoreCase("true")) {
            // add all bones to loot table
            if (propertyUtil.getProperty(BURY_BONES).equalsIgnoreCase("true")) {
                // tasks.add(new BuryBones(ctx));
                // add bury bones to task list
            }
        }
        // tasks.add(new Heal(ctx));
        // tasks.add(new Wait(ctx)); // to simulate being afk or not paying attention
        // combat tasks (attack, wait, antiban, heal, bury, loot)
        return tasks;
    }

    public static int[] getLevelingPrioOrder() {
        int[] skillOrder = new int[3];
        skillOrder[0] = getLevelingFirstPriority();
        skillOrder[1] = getLevelingSecondPriority();
        skillOrder[2] = getLevelingThirdPriority();
        return skillOrder;
    }

    public static int getLevelingFirstPriority() {
        return Integer.parseInt(propertyUtil.getProperty(LEVELING_FIRST_PRIORITY));
    }

    public static int getLevelingSecondPriority() {
        return Integer.parseInt(propertyUtil.getProperty(LEVELING_SECOND_PRIORITY));
    }

    public static int getLevelingThirdPriority() {
        return Integer.parseInt(propertyUtil.getProperty(LEVELING_THIRD_PRIORITY));
    }

    public static void setIncludedAreas(Area includedAreas) {
        CombatSettings.includedAreas = includedAreas;
    }

    public static void setExcludedAreas(Area excludedAreas) {
        CombatSettings.excludedAreas = excludedAreas;
    }

    public static Area getExcludedAreas() {
        return excludedAreas;
    }

    public static Area getIncludedAreas() {
        return includedAreas;
    }

    public static boolean levelDefence() {
        return propertyUtil.getProperty(LEVEL_DEFENCE).equalsIgnoreCase("true");
    }
}
