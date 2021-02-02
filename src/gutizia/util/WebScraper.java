package gutizia.util;

import gutizia.scripts.Script;
import gutizia.util.combat.CombatStats;
import gutizia.util.ge.Offer;
import gutizia.util.ge.Price;
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

            // there is at least two layouts for html pages... 1 if there is multiple stages/forms of the item, example: poisoned versions or barrows items.
            // the second is if it's only one type
            int index = sb.toString().indexOf("plainlinks rsw-infobox infobox infobox-switch infobox-bonuses infobox");
            if (index != -1) {
                string = sb.toString().substring(index, sb.toString().indexOf("</table>", index));

                stats[CombatStats.OFF_STAB] = string.substring(index = string.indexOf("astab\">") + 7, string.indexOf("<", index));
                stats[CombatStats.OFF_SLASH] = string.substring(index = string.indexOf("aslash\">") + 8, string.indexOf("<", index));
                stats[CombatStats.OFF_CRUSH] = string.substring(index = string.indexOf("acrush\">") + 8, string.indexOf("<", index));
                stats[CombatStats.OFF_MAGIC] = string.substring(index = string.indexOf("amagic\">") + 8, string.indexOf("<", index));
                stats[CombatStats.OFF_RANGE] = string.substring(index = string.indexOf("arange\">") + 8, string.indexOf("<", index));

                stats[CombatStats.DEF_STAB] = string.substring(index = string.indexOf("dstab\">") + 7, string.indexOf("<", index));
                stats[CombatStats.DEF_SLASH] = string.substring(index = string.indexOf("dslash\">") + 8, string.indexOf("<", index));
                stats[CombatStats.DEF_CRUSH] = string.substring(index = string.indexOf("dcrush\">") + 8, string.indexOf("<", index));
                stats[CombatStats.DEF_MAGIC] = string.substring(index = string.indexOf("dmagic\">") + 8, string.indexOf("<", index));
                stats[CombatStats.DEF_RANGE] = string.substring(index = string.indexOf("drange\">") + 8, string.indexOf("<", index));

                stats[CombatStats.STR_MELEE] = string.substring(index = string.indexOf("str\">") + 5, string.indexOf("<", index));
                stats[CombatStats.STR_RANGE] = string.substring(index = string.indexOf("rstr\">") + 6, string.indexOf("<", index));
                stats[CombatStats.STR_MAGIC] = string.substring(index = string.indexOf("mdmg\">") + 6, string.indexOf("%<", index));
                stats[CombatStats.PRAYER] = string.substring(index = string.indexOf("prayer\">") + 8, string.indexOf("<", index));

            } else {
                index = sb.toString().indexOf("plainlinks rsw-infobox infobox infobox-bonuses infobox");
                if (index == -1) {
                    System.out.println("failed to find table for combat stats");
                    return new String[] {};
                }
                string = sb.toString().substring(index, sb.toString().indexOf("</table>", index));
                int i = 0;
                for (String s : string.split("infobox-nested\">")) {
                    if (s.charAt(0) == '+' || s.charAt(0) == '-') {
                        stats[i] = s.substring(0, s.indexOf("<"));
                        if (stats[i].contains("%")) {
                            stats[i] = stats[i].substring(0, stats[i].indexOf("%"));
                        }
                        i++;
                    }
                    if (i == 14) {
                        break;
                    }
                }
            }
            System.out.println("time in ms to complete: " + (System.currentTimeMillis() - startTime));
            return stats;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[] {};
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

    public static Offer initOffer(String itemName, boolean buy, Script main) {
        Offer offer = new Offer();
        offer.setBuy(buy);
        try {
            itemName = itemName.replaceAll(" ", "_");
            System.out.println("looking up item: '" + itemName + "'");
            long startTime = System.currentTimeMillis();
            URLConnection connection = new URL("https://oldschool.runescape.wiki/w/Exchange:" + itemName).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();

            BufferedReader r  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();

            String string;
            while ((string = r.readLine()) != null) {
                sb.append(string);
            }

            // you want:
            // 1. init price
            // 2. status (f2p/p2p)
            // 3. buy limit
            // 4. itemId
            // 5. daily volume
            // TODO shift index by size of charSequence
            // first section: image + price
            String test;
            String s = sb.toString().substring(sb.toString().indexOf("gemw-section-left"), sb.toString().indexOf("gemw-section-right"));
            int index = s.indexOf("/images");
            test = "https://oldschool.runescape.wiki" + s.substring(index, s.indexOf("\"", index));
            System.out.println("image url: " + test);
            offer.setBufferedImage(main.downloadImage("https://oldschool.runescape.wiki" + s.substring(index, s.indexOf("\"", index))));
            index = s.indexOf("GEPrice\">") + 9;
            test = s.substring(index, s.indexOf("</", index));
            System.out.println("price = " + test);
            offer.setPrice(new Price(Long.parseLong(s.substring(index, s.indexOf("</", index)).replaceAll(",", ""))));

            // second section: status, buy limit, itemId, daily volume
            s = sb.toString().substring(sb.toString().lastIndexOf("gemw-section-left"), sb.toString().lastIndexOf("</dl>"));
            index = s.indexOf("<dd>") + 4;
            test = s.substring(index, s.indexOf("</dd>"));
            System.out.println("members: " + test);
            offer.setMembers(s.substring(index, s.indexOf("</dd>")).equalsIgnoreCase("members"));
            index = s.indexOf("<dd>", index) + 4;
            test = s.substring(index, s.indexOf("</dd>", index));
            System.out.println("buy limit: " + test);
            offer.setBuyLimit(Integer.parseInt(s.substring(index, s.indexOf("</dd>", index)).replaceAll(",", "")));
            index = s.indexOf("<dd>", index) + 4;
            test = s.substring(index, s.indexOf("</dd>", index));
            System.out.println("itemId = " + test);
            int itemId = Integer.parseInt(s.substring(index, s.indexOf("</dd>", index)));
            test = s.substring(s.lastIndexOf("<dd>") + 4, s.lastIndexOf("</dd>"));
            System.out.println("daily volume = " + test);
            offer.setDailyVolume(Integer.parseInt(s.substring(s.lastIndexOf("<dd>") + 4, s.lastIndexOf("</dd>")).replaceAll(",", "")));
            offer.setItem(new Item(itemId, itemName, 1));

            System.out.println("time in ms to complete: " + (System.currentTimeMillis() - startTime));
            return offer;
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
