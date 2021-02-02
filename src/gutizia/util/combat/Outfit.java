package gutizia.util.combat;

import gutizia.scripts.Script;
import gutizia.util.Items;
import gutizia.util.PropertyUtil;
import gutizia.util.Item;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.*;

import java.util.ArrayList;

public class Outfit extends CombatStats {
    private static PropertyUtil propertyUtil = new PropertyUtil(System.getProperty("java.io.tmpdir") + "PowBot\\outfits.properties");

    private ClientContext ctx = org.powerbot.script.ClientContext.ctx();

    private String outfitName;
    private ArrayList<Gear> gear = new ArrayList<>();

    public Outfit(ArrayList<Gear> gear) {
        this.gear = gear;
        setStats();
    }

    public Outfit() {}

    public Outfit getPlayerEquipment() {
        for (Equipment.Slot slot : Equipment.Slot.values()) {
            gear.add(new Gear(ctx.equipment.itemAt(slot), slot));
        }

        Outfit outfit = new Outfit(gear);
        setStats();
        return outfit;
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
        return new Outfit(gear);
    }

    /**
     * saves the entire outfit to outfits.properties file in script folder
     * format: @param outfitName:ITEM_ID,ITEM_NAME;ITEM_ID,ITEM_NAME;...
     * @param outfitName the name of the outfit, used as key value when saving to outfits.properties
     * @param items the items in the outfit
     * @param attackType the attack style of the outfit
     */
    public static void save(String outfitName, Item[] items, AttackType attackType) {
        StringBuilder sb = new StringBuilder();
        for (Item item : items) {
            sb.append(item.getSharedId());
            sb.append(",");
            sb.append(item.getName());
            sb.append(";");
        }
        System.out.println("saving " + outfitName + " to " + propertyUtil.getAbsolutePath());
        propertyUtil.updateProperty(outfitName, sb.toString());
        propertyUtil.updateProperty(outfitName + ".attack.type", getStringAttackStyle(attackType));
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
    }

    private static String getStringCombatStyle(AttackType attackType) {
        switch (attackType) {
            case MELEE:
                return "melee";

            case MAGIC:
                return "magic";

            case RANGE:
                return "range";

            default:
                Script.stopScript("could not get valid attack style for attackType value: " + attackType);
                return null;
        }
    }

    private static String getStringAttackStyle(AttackType attackType) {
        switch (attackType) {
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
                Script.stopScript("could not get valid attack style for attackStyle value: " + attackType);
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
                System.out.println("slot " + gear.getSlot() + " did not share id with outfit " + this.getOutfitName() + " gear " + gear.getName());
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
