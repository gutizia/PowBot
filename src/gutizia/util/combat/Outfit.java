package gutizia.util.combat;

import gutizia.scripts.Script;
import gutizia.util.Items;
import gutizia.util.PropertyUtil;
import gutizia.util.Item;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.*;

import java.util.ArrayList;

public class Outfit extends CombatStats {
    private static PropertyUtil propertyUtil = new PropertyUtil(Script.getStorageLocation().getAbsolutePath() + "\\outfits.properties");

    private String outfitName;
    private ArrayList<Gear> gear;
    private AttackStyle attackStyle;
    private CombatStyle combatStyle;

    public Outfit(CombatStyle combatStyle, AttackStyle attackStyle, ArrayList<Gear> gear) {
        this.attackStyle = attackStyle;
        this.combatStyle = combatStyle;
        this.gear = gear;
        setStats();
    }

    private void setStats() {
        int[] totalStats = new int[stats.length];
        for (int i = 0; i < stats.length; i++) {
            totalStats[i] = 0;
        }
        for (Gear gear : gear) {
            for (int i = 0; i < stats.length; i++) {
                totalStats[i] += gear.stats[i];
            }
        }
        stats = totalStats;
    }

    public ArrayList<Integer> getIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Gear g : gear) {
            ids.add(g.getId());
        }
        return ids;
    }

    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Gear g : gear) {
            names.add(g.getName());
        }
        return names;
    }

    public static Outfit getSavedOutfit(String key) {
        String k = propertyUtil.getProperty(key);
        if (k == null) {
            System.out.println(propertyUtil.getAbsolutePath() + " returned null for key: " + key);
            return null;
        }
        String[] strings = k.split(";");
        ArrayList<Gear> gear = new ArrayList<>();
        int i = 0;
        for (String string : strings) {
            String[] s = string.split(",");
            if (s.length >= 2) {
                gear.add(new Gear(new Item(Integer.parseInt(s[0]), s[1]), Equipment.Slot.values()[i]));
            } else {
                gear.add(new Gear(new Item(Integer.parseInt(s[0]), "nil"), Equipment.Slot.values()[i]));
            }
            i++;
        }
        return new Outfit(getCombatStyle(key), getAttackStyle(key), gear);
    }

    /**
     * saves the entire outfit to outfits.properties file in script folder
     * format: @param outfitName:ITEM_ID,ITEM_NAME;ITEM_ID,ITEM_NAME;...
     * @param outfitName the name of the outfit, used as key value when saving to outfits.properties
     * @param items the items in the outfit
     * @param combatStyle the combat style of the outfit
     * @param attackStyle the attack style of the outfit
     */
    public static void save(String outfitName, Item[] items, CombatStyle combatStyle, AttackStyle attackStyle) {
        StringBuilder sb = new StringBuilder();
        for (Item item : items) {
            sb.append(item.getSharedId());
            sb.append(",");
            sb.append(item.getName());
            sb.append(";");
        }
        System.out.println("saving " + outfitName + " to " + propertyUtil.getAbsolutePath());
        propertyUtil.updateProperty(outfitName, sb.toString());
        propertyUtil.updateProperty(outfitName + ".combat.style", getStringCombatStyle(combatStyle));
        propertyUtil.updateProperty(outfitName + ".attack.style", getStringAttackStyle(attackStyle));
    }

    public void saveOutfit() {
        StringBuilder sb = new StringBuilder();
        for (Gear gear : gear) {
            sb.append(gear.getId());
            sb.append(",");
            sb.append(gear.getName());
            sb.append(";");
        }
        System.out.println("saving " + outfitName + " to " + propertyUtil.getAbsolutePath());
        propertyUtil.updateProperty(outfitName, sb.toString());
        propertyUtil.updateProperty(outfitName + ".combat.style", getStringCombatStyle(combatStyle));
        propertyUtil.updateProperty(outfitName + ".attack.style", getStringAttackStyle(attackStyle));
    }

    private static String getStringCombatStyle(CombatStyle combatStyle) {
        switch (combatStyle) {
            case MELEE:
                return "melee";

            case MAGIC:
                return "magic";

            case RANGE:
                return "range";

            default:
                Script.stopScript("could not get valid combat style for combatStyle value: " + combatStyle);
                return null;
        }
    }

    private static String getStringAttackStyle(AttackStyle attackStyle) {
        switch (attackStyle) {
            case STAB:
                return "stab";

            case RANGE:
                return "range";

            case MAGIC:
                return "magic";

            case CRUSH:
                return "crush";

            case SLASH:
                return "slash";

            default:
                Script.stopScript("could not get valid attack style for attackStyle value: " + attackStyle);
                return null;
        }
    }

    private static CombatStyle getCombatStyle(String key) {
        String s = propertyUtil.getProperty(key + ".combat.style");
        switch (s) {
            case "melee":
                return CombatStyle.MELEE;

            case "range":
                return CombatStyle.RANGE;

            case "magic":
                return CombatStyle.MAGIC;

            default:
                Script.stopScript("could not get valid combat style for key value: " + key + ". value stored at key: \"" + s + "\"");
                return null;
        }
    }

    private static AttackStyle getAttackStyle(String key) {
        String s = propertyUtil.getProperty(key + ".attack.style");
        switch (s) {
            case "crush":
                return AttackStyle.CRUSH;

            case "stab":
                return AttackStyle.STAB;

            case "slash":
                return AttackStyle.SLASH;

            case "range":
                return AttackStyle.RANGE;

            case "magic":
                return AttackStyle.MAGIC;

            default:
                Script.stopScript("could not get valid attack style for key value: " + key + ". value stored at key: \"" + s + "\"");
                return null;
        }
    }

    public static org.powerbot.script.rt4.Item itemAt(ClientContext ctx, final org.powerbot.script.rt4.Equipment.Slot slot) {
        final int index = slot.getIndex();
        if (index < 0) {
            return ctx.inventory.nil();
        }
        final org.powerbot.script.rt4.Component c = ctx.widgets.widget(Constants.EQUIPMENT_WIDGET).component(slot.getComponentIndex()).component(1);
        return new org.powerbot.script.rt4.Item(ctx, c, c.itemId(), c.itemStackSize());
    }

    public int[] getGearIds() {
        int[] itemIds = new int[gear.size()];
        int i = 0;
        for (Integer integer : getIds()) {
            itemIds[i++] = integer;
        }
        return itemIds;
    }

    public ArrayList<String> getDisplayInfo() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("off stab = " + stats[OFF_STAB]);
        strings.add("off slash = " + stats[OFF_SLASH]);
        strings.add("off crush = " + stats[OFF_CRUSH]);
        strings.add("off magic = " + stats[OFF_MAGIC]);
        strings.add("off range = " + stats[OFF_RANGE]);
        strings.add("def stab = " + stats[DEF_STAB]);
        strings.add("def slash = " + stats[DEF_SLASH]);
        strings.add("def crush = " + stats[DEF_CRUSH]);
        strings.add("def magic = " + stats[DEF_MAGIC]);
        strings.add("def range = " + stats[DEF_RANGE]);
        strings.add("str melee = " + stats[STR_MELEE]);
        strings.add("str range = " + stats[STR_RANGE]);
        strings.add("str magic = " + stats[STR_MAGIC]);
        strings.add("prayer = " + stats[PRAYER]);
        return strings;
    }

    public ArrayList<Gear> getGear() {
        return gear;
    }

    public AttackStyle getAttackStyle() {
        return attackStyle;
    }

    public CombatStyle getCombatStyle() {
        return combatStyle;
    }

    public Items getItems() {
        Items items = new Items();
        for (Gear gear : gear) {
            items.addItem(gear.getItem());
        }
        return items;
    }

    public boolean equippedAll(ClientContext ctx) {
        ctx.game.tab(Game.Tab.EQUIPMENT, true);
        Condition.wait(() -> ctx.game.tab().equals(Game.Tab.EQUIPMENT), 50, 10);
        for (Gear gear : getGear()) {
            if (ctx.equipment.itemAt(gear.getSlot()).id() != gear.getId()) {
                System.out.println("slot " + gear.getSlot() + " did not share id with outfit " + this.getAttackStyle() + " gear " + gear.getName());
                ctx.game.tab(Game.Tab.INVENTORY);
                return false;
            }
        }
        System.out.println("found no mismatch in ids across all slots");
        ctx.game.tab(Game.Tab.INVENTORY, true);
        return true;
    }

    public static PropertyUtil getPropertyUtil() {
        return propertyUtil;
    }

    public String getOutfitName() {
        return outfitName;
    }

    public void setOutfitName(String outfitName) {
        this.outfitName = outfitName;
    }

    public boolean isVoid() {
        return getItems().contains(8842);
    }
}
