package gutizia.util.settings;

import gutizia.util.PropertyUtil;
import org.powerbot.script.Random;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LootSettings {

    public final static String ITEMS_TO_LOOT = "items.to.loot.list";
    public final static String MINIMUM_ITEMS_BEFORE_LOOTING = "min.loot";
    public final static String MAXIMUM_ITEMS_BEFORE_LOOTING = "max.loot";
    public final static String AVERAGE_ITEMS_BEFORE_LOOTING = "avg.loot";
    public final static String CUSTOM_LOOT_TABLE = "custom.loot.table";

    private static PropertyUtil propertyUtil = new PropertyUtil(System.getProperty("java.io.tmpdir") + "PowBot\\loot",
            Stream.of(new String[][] {
                    {ITEMS_TO_LOOT, ""},
                    {MINIMUM_ITEMS_BEFORE_LOOTING, "1"},
                    {MAXIMUM_ITEMS_BEFORE_LOOTING, "5"},
                    {AVERAGE_ITEMS_BEFORE_LOOTING, "2"},
                    {CUSTOM_LOOT_TABLE, "false"},
            }).collect(Collectors.toMap(data -> data[0], data -> data[1])));

    public static int getNextRandomLootThreshold() {
        return Random.nextGaussian(
                Integer.parseInt(propertyUtil.getProperty(MINIMUM_ITEMS_BEFORE_LOOTING)),
                Integer.parseInt(propertyUtil.getProperty(MAXIMUM_ITEMS_BEFORE_LOOTING)),
                Integer.parseInt(propertyUtil.getProperty(AVERAGE_ITEMS_BEFORE_LOOTING))
                );
    }

    public static PropertyUtil getPropertyUtil() {
        return propertyUtil;
    }

    public static boolean isStandardLootTable() {
        return propertyUtil.getProperty(CUSTOM_LOOT_TABLE).equalsIgnoreCase("false");
    }
}
