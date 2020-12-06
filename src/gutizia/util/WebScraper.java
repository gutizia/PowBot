package gutizia.util;

import org.powerbot.script.rt4.Equipment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class WebScraper {

    public static String[] getGearStats(String itemName, Equipment.Slot slot) {
        return getGearStats(itemName, slot, false);
    }

    public static String[] getGearStats(String itemName, Equipment.Slot slot, boolean twohander) {
        try {
            System.out.println("getting stats for " + itemName);
            long startTime = System.currentTimeMillis();
            URLConnection connection = new URL("https://oldschool.runescape.wiki/w/" + getSlotUrl(slot, twohander)).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();

            BufferedReader r  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
            String string;
            do {
                string = r.readLine();
                System.out.println(string);
            } while (string != null && !string.contains(itemName));

            if (string != null) {
                int startIndex = string.indexOf(itemName);
                string = string.substring(startIndex, string.indexOf("</tr>", startIndex));
                startIndex = string.indexOf("<td>");
                string = string.substring(string.indexOf("<td", startIndex + "<td>".length()));

                String[] strings = string.split("</td>");

                for (int i = 0; i < strings.length; i++) {
                    if (strings[i].contains("+")) {
                        strings[i] = strings[i].substring(strings[i].lastIndexOf("+"));
                    } else {
                        strings[i] = strings[i].substring(strings[i].lastIndexOf("-"));
                    }
                }
                for (String s : strings) {
                    System.out.println(s);
                }
                System.out.println("time in ms to complete: " + (System.currentTimeMillis() - startTime));
                for (String s : strings) {
                    System.out.println(s);
                }
                return strings;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getGearStats(String itemName) {
        try {
            itemName = itemName.replaceAll(" ", "_");
            itemName = itemName.replaceAll("_100", "");
            itemName = itemName.replaceAll("_75", "");
            itemName = itemName.replaceAll("_50", "");
            itemName = itemName.replaceAll("_25", "");
            System.out.println("getting stats for " + itemName);
            long startTime = System.currentTimeMillis();
            URLConnection connection = new URL("https://oldschool.runescape.wiki/w/" + itemName).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();

            BufferedReader r  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();

            String string;
            String[] stats = new String[14];
            while ((string = r.readLine()) != null) {
                sb.append(string);
            }

            int i = 0;
            for (String s : sb.toString().split("infobox-nested\">")) {
                if (s.charAt(0) == '+' || s.charAt(0) == '-') {
                    stats[i] = s.substring(0, s.indexOf("<"));
                    if (stats[i].contains("%")) {
                        stats[i] = stats[i].substring(0, stats[i].indexOf("%"));
                    }
                    i++;
                }
                // some items like fire cape has different tags like #broken but same stats, so to only get the info once we return once the stats array is filled
                if (i == 14) {
                    break;
                }
            }

            System.out.println("time in ms to complete: " + (System.currentTimeMillis() - startTime));
            for (String s : stats) {
                System.out.println(s);
            }
            return stats;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getEnemyStats(String name, int level) {
        try {
            name = name.replaceAll(" ", "_");
            if (level >= 1) {
                name += "";
            }

            System.out.println("getting stats for " + name);
            long startTime = System.currentTimeMillis();
            URLConnection connection = new URL("https://oldschool.runescape.wiki/w/" + name).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();

            BufferedReader r  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();

            String string;
            String[] stats = new String[14];
            while ((string = r.readLine()) != null) {
                sb.append(string);
            }

            int i = 0;
            for (String s : sb.toString().split("infobox-nested\">")) {
                if (s.charAt(0) == '+' || s.charAt(0) == '-') {
                    stats[i] = s.substring(0, s.indexOf("<"));
                    if (stats[i].contains("%")) {
                        stats[i] = stats[i].substring(0, stats[i].indexOf("%"));
                    }
                    i++;
                }
                // some items like fire cape has different tags like #broken but same stats, so to only get the info once we return once the stats array is filled
                if (i == 14) {
                    break;
                }
            }

            System.out.println("time in ms to complete: " + (System.currentTimeMillis() - startTime));
            for (String s : stats) {
                System.out.println(s);
            }
            return stats;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getSlotUrl(Equipment.Slot slot, boolean twohander) {
        switch (slot) {
            case HEAD:
                return "head_slot_table";

            case NECK:
                return "Neck_slot_table";

            case QUIVER:
                return "Ammunition_slot_table";

            case TORSO:
                return "Body_slot_table";

            case CAPE:
                return "Cape_slot_table";

            case FEET:
                return "Feet_slot_table";

            case HANDS:
                return "Hand_slot_table";

            case LEGS:
                return "Legs_slot_table";

            case RING:
                return "Ring_slot_table";

            case MAIN_HAND:
                return twohander ? "Weapon_slot_table" : "Two-handed_slot_table";

            case OFF_HAND:
                return "Shield_slot_table";
        }
        return null;
    }
}
