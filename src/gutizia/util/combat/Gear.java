package gutizia.util.combat;

import gutizia.scripts.Script;
import gutizia.util.WebScraper;
import gutizia.util.PropertyUtil;
import org.powerbot.script.rt4.Equipment;
import org.powerbot.script.rt4.Item;


/**
 * used to represent an item that is used as equipment with stats
 */
public class Gear extends CombatStats {
    private final static PropertyUtil propertyUtil = new PropertyUtil(System.getProperty("java.io.tmpdir") + "PowBot\\savedGear");
    private gutizia.util.Item item;
    private Equipment.Slot slot;
    // .bounds(-12,20,-12,20,0,0); if you need bounds, set this

    public Gear(gutizia.util.Item item, Equipment.Slot slot) {
        this.slot = slot;
        this.item = new gutizia.util.Item(item.getSharedId(), item.getSharedName());
        setStats();
    }

    public Gear(Item item, Equipment.Slot slot) {
        this(new gutizia.util.Item(item.id(), item.name()), slot);
    }

    private void setStats() {
        if (item.getId() == -1) {
            return;
        }
        if (isSaved()) {
            load();
            return;
        }
        String[] gearStats = WebScraper.getGearStats(item.getName());
        //String[] stats = WebScraper.getGearStats(itemName, slot, twohander);

        if (gearStats == null || gearStats.length < 14) {
            Script.stopScript("error! was unable to get stats for item: " + item.getName() + ", slot: " + slot);
            return;
        }

        for (int i = 0; i < gearStats.length; i++) {
            stats[i] = Integer.parseInt(gearStats[i]);
        }
        save();
    }

    public void load() {
        int i = 0;
        String[] string = propertyUtil.getProperty(item.getName()).split(":");
        for (String s : string[1].split(",")) {
            stats[i++] = Integer.parseInt(s);
        }
    }

    /**
     * saves the stats of a piece of gear into the savedGear.properties file with the following format: ITEM_ID:STAT1,STAT2,STAT3...
     */
    private void save() {
        StringBuilder sb = new StringBuilder();
        sb.append(item.getId());
        sb.append(":");
        for (int i = 0; i < stats.length; i++) {
            sb.append(i);
            if (i != stats.length) {
                sb.append(",");
            }
        }
        propertyUtil.updateProperty(item.getName(), sb.toString());
    }

    private boolean isSaved() {
        return propertyUtil.getProperty(item.getName()) != null && !propertyUtil.getProperty(item.getName()).equals("");
    }

    @Override
    public void printStats() {
        super.printStats();
    }

    public Equipment.Slot getSlot() {
        return slot;
    }

    public int getId() {
        return item.getId();
    }

    public String getName() {
        return item.getName();
    }

    public gutizia.util.Item getItem() {
        return item;
    }
}
