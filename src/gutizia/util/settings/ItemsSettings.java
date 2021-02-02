package gutizia.util.settings;

public class ItemsSettings {

    private static boolean STOP_SCRIPT_IF_MISSING_ITEMS = false;

    public static void setStopScriptIfMissingItems(boolean stopScriptIfMissingItems) {
        STOP_SCRIPT_IF_MISSING_ITEMS = stopScriptIfMissingItems;
    }

    public static boolean isStopScriptIfMissingItems() {
        return STOP_SCRIPT_IF_MISSING_ITEMS;
    }
}
