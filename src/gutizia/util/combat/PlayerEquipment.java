package gutizia.util.combat;

import gutizia.util.constants.Supplies;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Item;

import java.util.HashMap;
import java.util.Map;

public class PlayerEquipment extends ClientAccessor {
    public final static PlayerEquipment playerEquipment = new PlayerEquipment(org.powerbot.script.ClientContext.ctx());

    private static Map<Integer, Integer> foodHealingMap;
    private static Map<Integer, Integer> prayerRestoreMap;
    private static Map<Integer, Integer> runRestoreMap;
    private static Map<Integer, Integer> statBoostMap;
    private static Map<Integer, Integer> poisonResistMap; // seconds of immunity

    private int healthInInventory;
    private int prayerPointsInInventory;
    private int runEnergyInInventory;
    private int poisonResistInInventory;

    private PlayerEquipment(ClientContext ctx) {
        super(ctx);
        initFoodHealingMap();
        initPrayerRestoreMap();
        initRunRestoreMap();
        initStatBoostMap();
        initPoisonResistMap();
        calculateHealthInInventory();
        calculatePrayerPointsInInventory();
        calculatePoisonResistInInventory();
        calculateRunEnergyInInventory();
    }

    public void equipmentChange(Item item, boolean old) {
        if (foodHealingMap.containsKey(item.id())) {
            healthItemChange(item, old);

        } else if (prayerRestoreMap.containsKey(item.id())) {
            prayerItemChange(item, old);

        } else if (poisonResistMap.containsKey(item.id())) {
            poisonResistItemChange(item, old);

        } else if (runRestoreMap.containsKey(item.id())) {
            runEnergyItemChange(item, old);
        }
    }

    private void poisonResistItemChange(Item item, boolean old) {
        if (old) {
            poisonResistInInventory -= poisonResistMap.get(item.id());
        } else {
            poisonResistInInventory += poisonResistMap.get(item.id());
        }
    }

    private void runEnergyItemChange(Item item, boolean old) {
        if (old) {
            runEnergyInInventory -= runRestoreMap.get(item.id());
        } else {
            runEnergyInInventory += runRestoreMap.get(item.id());
        }
    }

    private void healthItemChange(Item item, boolean old) {
        if (old) {
            healthInInventory -= foodHealingMap.get(item.id());
        } else {
            healthInInventory += foodHealingMap.get(item.id());
        }
    }

    private void prayerItemChange(Item item, boolean old) {
        if (old) {
            prayerPointsInInventory -= prayerRestoreMap.get(item.id());
        } else {
            prayerPointsInInventory += prayerRestoreMap.get(item.id());
        }
    }

    private void initFoodHealingMap() {
        foodHealingMap = new HashMap<>();
        foodHealingMap.put(Supplies.SHRIMP, 3);
        foodHealingMap.put(Supplies.COOKED_CHICKEN, 3);
        foodHealingMap.put(Supplies.COOKED_MEAT, 3);
        foodHealingMap.put(Supplies.SARDINE, 4);
        foodHealingMap.put(Supplies.BREAD, 5);
        foodHealingMap.put(Supplies.HERRING, 5);
        foodHealingMap.put(Supplies.MACKEREL, 6);
        foodHealingMap.put(Supplies.CHOC_ICE, 6);
        foodHealingMap.put(Supplies.TROUT, 7);
        foodHealingMap.put(Supplies.COD, 7);
        foodHealingMap.put(Supplies.PIKE, 8);
        foodHealingMap.put(Supplies.ROAST_BEAST_MEAT, 8);
        foodHealingMap.put(Supplies.PINEAPPLE_PUNCH, 9);
        foodHealingMap.put(Supplies.SALMON, 9);
        foodHealingMap.put(Supplies.TUNA, 10);
        foodHealingMap.put(Supplies.JUG_OF_WINE, 11);
        foodHealingMap.put(Supplies.RAINBOW_FISH, 11);
        foodHealingMap.put(Supplies.STEW, 11);
        foodHealingMap.put(Supplies.BANANA_STEW, 11);
        foodHealingMap.put(Supplies.CAKE, 4);
        foodHealingMap.put(Supplies.TWO_THIRDS_CAKE, 4);
        foodHealingMap.put(Supplies.SLICE_CAKE, 4);
        foodHealingMap.put(Supplies.MEAT_PIE, 6);
        foodHealingMap.put(Supplies.HALF_MEAT_PIE, 6);
        foodHealingMap.put(Supplies.LOBSTER, 12);
        foodHealingMap.put(Supplies.BASS, 13);
        foodHealingMap.put(Supplies.PLAIN_PIZZA, 14);
        foodHealingMap.put(Supplies.HALF_PLAIN_PIZZA, 7);
        foodHealingMap.put(Supplies.SWORDFISH, 14);
        foodHealingMap.put(Supplies.POTATO_WITH_BUTTER, 14);
        foodHealingMap.put(Supplies.APPLE_PIE, 7);
        foodHealingMap.put(Supplies.HALF_APPLE_PIE, 7);
        foodHealingMap.put(Supplies.CHOCOLATE_CAKE, 5);
        foodHealingMap.put(Supplies.TWO_THIRDS_CHOCOLATE_CAKE, 5);
        foodHealingMap.put(Supplies.SLICE_CHOCOLATE_CAKE, 5);
        foodHealingMap.put(Supplies.TANGLED_TOADS_LEGS, 15);
        foodHealingMap.put(Supplies.CHOCOLATE_BOMB, 15);
        foodHealingMap.put(Supplies.POTATO_WITH_CHEESE, 16);
        foodHealingMap.put(Supplies.MEAT_PIZZA, 16);
        foodHealingMap.put(Supplies.HALF_MEAT_PIZZA, 8);
        foodHealingMap.put(Supplies.MONKFISH, 16);
        foodHealingMap.put(Supplies.ANCHOVY_PIZZA, 18);
        foodHealingMap.put(Supplies.HALF_ANCHOVY_PIZZA, 9);
        foodHealingMap.put(Supplies.COOKED_KARAMBWAN, 18); // can be tick eaten
        foodHealingMap.put(Supplies.CURRY, 19);
        foodHealingMap.put(Supplies.UGTHANKI_KEBAB, 19);
        foodHealingMap.put(Supplies.DRAGONFRUIT_PIE, 10);
        foodHealingMap.put(Supplies.HALF_DRAGONFRUIT_PIE, 10);
        foodHealingMap.put(Supplies.MUSHROOM_POTATO, 20);
        foodHealingMap.put(Supplies.SHARK, 20);
        foodHealingMap.put(Supplies.SEA_TURTLE, 21);
        foodHealingMap.put(Supplies.PINEAPPLE_PIZZA, 22);
        foodHealingMap.put(Supplies.HALF_PINEAPPLE_PIZZA, 11);
        foodHealingMap.put(Supplies.SUMMER_PIE, 11);
        foodHealingMap.put(Supplies.HALF_SUMMER_PIE, 11);
        foodHealingMap.put(Supplies.WILD_PIE, 11);
        foodHealingMap.put(Supplies.HALF_WILD_PIE, 11);
        foodHealingMap.put(Supplies.MANTA_RAY, 22);
        foodHealingMap.put(Supplies.TUNA_POTATO, 22);
        foodHealingMap.put(Supplies.DARK_CRAB, 22);
        foodHealingMap.put(Supplies.ANGLERFISH, getAnglerFishHealth());
        foodHealingMap.put(Supplies.SARADOMIN_BREW1, (int) (((double)ctx.skills.realLevel(Constants.SKILLS_HITPOINTS) * 0.15) + 2));
        foodHealingMap.put(Supplies.SARADOMIN_BREW2, (int) (((double)ctx.skills.realLevel(Constants.SKILLS_HITPOINTS) * 0.15) + 2) * 2);
        foodHealingMap.put(Supplies.SARADOMIN_BREW3, (int) (((double)ctx.skills.realLevel(Constants.SKILLS_HITPOINTS) * 0.15) + 2) * 3);
        foodHealingMap.put(Supplies.SARADOMIN_BREW4, (int) (((double)ctx.skills.realLevel(Constants.SKILLS_HITPOINTS) * 0.15) + 2) * 4);
        foodHealingMap.put(Supplies.XERICS_AID_MINUS1, (int) (((double)ctx.skills.realLevel(Constants.SKILLS_HITPOINTS) * 0.15) + 2));
        foodHealingMap.put(Supplies.XERICS_AID_MINUS2, (int) (((double)ctx.skills.realLevel(Constants.SKILLS_HITPOINTS) * 0.15) + 2) * 2);
        foodHealingMap.put(Supplies.XERICS_AID_MINUS3, (int) (((double)ctx.skills.realLevel(Constants.SKILLS_HITPOINTS) * 0.15) + 2) * 3);
        foodHealingMap.put(Supplies.XERICS_AID_MINUS4, (int) (((double)ctx.skills.realLevel(Constants.SKILLS_HITPOINTS) * 0.15) + 2) * 4);
        foodHealingMap.put(Supplies.XERICS_AID1, (int) (((double)ctx.skills.realLevel(Constants.SKILLS_HITPOINTS) * 0.15) + 2));
        foodHealingMap.put(Supplies.XERICS_AID2, (int) (((double)ctx.skills.realLevel(Constants.SKILLS_HITPOINTS) * 0.15) + 2) * 2);
        foodHealingMap.put(Supplies.XERICS_AID3, (int) (((double)ctx.skills.realLevel(Constants.SKILLS_HITPOINTS) * 0.15) + 2) * 3);
        foodHealingMap.put(Supplies.XERICS_AID4, (int) (((double)ctx.skills.realLevel(Constants.SKILLS_HITPOINTS) * 0.15) + 2) * 4);
        foodHealingMap.put(Supplies.XERICS_AID_PLUS1, (int) (((double)ctx.skills.realLevel(Constants.SKILLS_HITPOINTS) * 0.15) + 5));
        foodHealingMap.put(Supplies.XERICS_AID_PLUS2, (int) (((double)ctx.skills.realLevel(Constants.SKILLS_HITPOINTS) * 0.15) + 5) * 2);
        foodHealingMap.put(Supplies.XERICS_AID_PLUS3, (int) (((double)ctx.skills.realLevel(Constants.SKILLS_HITPOINTS) * 0.15) + 5) * 3);
        foodHealingMap.put(Supplies.XERICS_AID_PLUS4, (int) (((double)ctx.skills.realLevel(Constants.SKILLS_HITPOINTS) * 0.15) + 5) * 4);
    }

    private void initPrayerRestoreMap() {
        prayerRestoreMap = new HashMap<>();
        prayerRestoreMap.put(Supplies.PRAYER_POTION1, (int)(Math.floor((double) ctx.skills.realLevel(Constants.SKILLS_PRAYER) * .25) + 7));
        prayerRestoreMap.put(Supplies.PRAYER_POTION2, (int)(Math.floor((double) ctx.skills.realLevel(Constants.SKILLS_PRAYER) * .25) + 7) * 2);
        prayerRestoreMap.put(Supplies.PRAYER_POTION3, (int)(Math.floor((double) ctx.skills.realLevel(Constants.SKILLS_PRAYER) * .25) + 7)* 3);
        prayerRestoreMap.put(Supplies.PRAYER_POTION4, (int)(Math.floor((double) ctx.skills.realLevel(Constants.SKILLS_PRAYER) * .25) + 7) * 4);
        prayerRestoreMap.put(Supplies.SUPER_RESTORE1, (int)(Math.floor((double) ctx.skills.realLevel(Constants.SKILLS_PRAYER) * .25) + 8));
        prayerRestoreMap.put(Supplies.SUPER_RESTORE2, (int)(Math.floor((double) ctx.skills.realLevel(Constants.SKILLS_PRAYER) * .25) + 8) * 2);
        prayerRestoreMap.put(Supplies.SUPER_RESTORE3, (int)(Math.floor((double) ctx.skills.realLevel(Constants.SKILLS_PRAYER) * .25) + 8) * 3);
        prayerRestoreMap.put(Supplies.SUPER_RESTORE4, (int)(Math.floor((double) ctx.skills.realLevel(Constants.SKILLS_PRAYER) * .25) + 8) * 4);
        prayerRestoreMap.put(Supplies.REVITALISATION_PLUS1, (int)(Math.floor((double) ctx.skills.realLevel(Constants.SKILLS_PRAYER) * .30) + 11));
        prayerRestoreMap.put(Supplies.REVITALISATION_PLUS2, (int)(Math.floor((double) ctx.skills.realLevel(Constants.SKILLS_PRAYER) * .30) + 11) * 2);
        prayerRestoreMap.put(Supplies.REVITALISATION_PLUS3, (int)(Math.floor((double) ctx.skills.realLevel(Constants.SKILLS_PRAYER) * .30) + 11) * 3);
        prayerRestoreMap.put(Supplies.REVITALISATION_PLUS4, (int)(Math.floor((double) ctx.skills.realLevel(Constants.SKILLS_PRAYER) * .30) + 11) * 4);
    }

    private void initRunRestoreMap() {
        runRestoreMap = new HashMap<>();
        runRestoreMap.put(Supplies.STAMINA_POTION1, 20);
        runRestoreMap.put(Supplies.STAMINA_POTION2, 20 * 2);
        runRestoreMap.put(Supplies.STAMINA_POTION3, 20 * 3);
        runRestoreMap.put(Supplies.STAMINA_POTION4, 20 * 4);
    }

    private void initStatBoostMap() {
        statBoostMap = new HashMap<>();
        statBoostMap.put(Supplies.RANGING_POTION1, ((int)Math.floor((double)ctx.skills.realLevel(Constants.SKILLS_RANGE) * 1.10)) + 4);
        statBoostMap.put(Supplies.RANGING_POTION2, ((int)Math.floor((double)ctx.skills.realLevel(Constants.SKILLS_RANGE) * 1.10)) + 4);
        statBoostMap.put(Supplies.RANGING_POTION3, ((int)Math.floor((double)ctx.skills.realLevel(Constants.SKILLS_RANGE) * 1.10)) + 4);
        statBoostMap.put(Supplies.RANGING_POTION4, ((int)Math.floor((double)ctx.skills.realLevel(Constants.SKILLS_RANGE) * 1.10)) + 4);
        statBoostMap.put(Supplies.DIVINE_RANGING_POTION1, ((int)Math.floor((double)ctx.skills.realLevel(Constants.SKILLS_RANGE) * 1.10)) + 4);
        statBoostMap.put(Supplies.DIVINE_RANGING_POTION2, ((int)Math.floor((double)ctx.skills.realLevel(Constants.SKILLS_RANGE) * 1.10)) + 4);
        statBoostMap.put(Supplies.DIVINE_RANGING_POTION3, ((int)Math.floor((double)ctx.skills.realLevel(Constants.SKILLS_RANGE) * 1.10)) + 4);
        statBoostMap.put(Supplies.DIVINE_RANGING_POTION4, ((int)Math.floor((double)ctx.skills.realLevel(Constants.SKILLS_RANGE) * 1.10)) + 4);
        statBoostMap.put(Supplies.MAGIC_POTION1, 4);
        statBoostMap.put(Supplies.MAGIC_POTION2, 4);
        statBoostMap.put(Supplies.MAGIC_POTION3, 4);
        statBoostMap.put(Supplies.MAGIC_POTION4, 4);
        statBoostMap.put(Supplies.DIVINE_MAGIC_POTION1, 4);
        statBoostMap.put(Supplies.DIVINE_MAGIC_POTION2, 4);
        statBoostMap.put(Supplies.DIVINE_MAGIC_POTION3, 4);
        statBoostMap.put(Supplies.DIVINE_MAGIC_POTION4, 4);
        statBoostMap.put(Supplies.SUPER_COMBAT_POTION1, ((int)Math.floor((double)ctx.skills.realLevel(Constants.SKILLS_ATTACK) * 1.15)) + 5); // TODO include all stats
        statBoostMap.put(Supplies.SUPER_COMBAT_POTION2, ((int)Math.floor((double)ctx.skills.realLevel(Constants.SKILLS_ATTACK) * 1.15)) + 5); // TODO include all stats
        statBoostMap.put(Supplies.SUPER_COMBAT_POTION3, ((int)Math.floor((double)ctx.skills.realLevel(Constants.SKILLS_ATTACK) * 1.15)) + 5); // TODO include all stats
        statBoostMap.put(Supplies.SUPER_COMBAT_POTION4, ((int)Math.floor((double)ctx.skills.realLevel(Constants.SKILLS_ATTACK) * 1.15)) + 5); // TODO include all stats
        statBoostMap.put(Supplies.DIVINE_SUPER_COMBAT_POTION1, ((int)Math.floor((double)ctx.skills.realLevel(Constants.SKILLS_ATTACK) * 1.15)) + 5); // TODO include all stats
        statBoostMap.put(Supplies.DIVINE_SUPER_COMBAT_POTION2, ((int)Math.floor((double)ctx.skills.realLevel(Constants.SKILLS_ATTACK) * 1.15)) + 5); // TODO include all stats
        statBoostMap.put(Supplies.DIVINE_SUPER_COMBAT_POTION3, ((int)Math.floor((double)ctx.skills.realLevel(Constants.SKILLS_ATTACK) * 1.15)) + 5); // TODO include all stats
        statBoostMap.put(Supplies.DIVINE_SUPER_COMBAT_POTION4, ((int)Math.floor((double)ctx.skills.realLevel(Constants.SKILLS_ATTACK) * 1.15)) + 5); // TODO include all stats
    }

    private void initPoisonResistMap() {
        poisonResistMap = new HashMap<>();
        poisonResistMap.put(Supplies.ANTIPOISON1, 90);
        poisonResistMap.put(Supplies.ANTIPOISON2, 90 * 2);
        poisonResistMap.put(Supplies.ANTIPOISON3, 90 * 3);
        poisonResistMap.put(Supplies.ANTIPOISON4, 90 * 4);
        poisonResistMap.put(Supplies.SUPER_ANTIPOISON1, 600);
        poisonResistMap.put(Supplies.SUPER_ANTIPOISON2, 600 * 2);
        poisonResistMap.put(Supplies.SUPER_ANTIPOISON3, 600 * 3);
        poisonResistMap.put(Supplies.SUPER_ANTIPOISON4, 600 * 4);
        poisonResistMap.put(Supplies.ANTIDOTE_P1, 540);
        poisonResistMap.put(Supplies.ANTIDOTE_P2, 540 * 2);
        poisonResistMap.put(Supplies.ANTIDOTE_P3, 540 * 3);
        poisonResistMap.put(Supplies.ANTIDOTE_P4, 540 * 4);
        poisonResistMap.put(Supplies.ANTIDOTE_PP1, 720);
        poisonResistMap.put(Supplies.ANTIDOTE_PP2, 720 * 2);
        poisonResistMap.put(Supplies.ANTIDOTE_PP3, 720 * 3);
        poisonResistMap.put(Supplies.ANTIDOTE_PP4, 720 * 4);
    }

    private int getAnglerFishHealth() {
        int hpLvl = ctx.skills.realLevel(Constants.SKILLS_HITPOINTS);

        if (hpLvl >= 1 && hpLvl <= 19) return 3;
        if (hpLvl >= 20 && hpLvl <= 24) return 4;
        if (hpLvl >= 20 && hpLvl <= 29) return 6;
        if (hpLvl >= 25 && hpLvl <= 39) return 7;
        if (hpLvl >= 30 && hpLvl <= 49) return 8;
        if (hpLvl >= 40 && hpLvl <= 59) return 11;
        if (hpLvl >= 50 && hpLvl <= 69) return 12;
        if (hpLvl >= 60 && hpLvl <= 74) return 13;
        if (hpLvl >= 70 && hpLvl <= 79) return 15;
        if (hpLvl >= 80 && hpLvl <= 89) return 16;
        if (hpLvl >= 90 && hpLvl <= 92) return 17;
        if (hpLvl >= 93 && hpLvl <= 99) return 22;
        return 22;
    }

    private void calculateHealthInInventory() {
        healthInInventory = 0;
        for (Item item : ctx.inventory.select()) {
            if (foodHealingMap.containsKey(item.id())) {
                healthInInventory += foodHealingMap.get(item.id());
            }
        }
    }

    private void calculatePrayerPointsInInventory() {
        prayerPointsInInventory = 0;
        for (Item item : ctx.inventory.select()) {

            if (prayerRestoreMap.containsKey(item.id())) {
                prayerPointsInInventory += prayerRestoreMap.get(item.id());
            }
        }
    }

    private void calculatePoisonResistInInventory() {
        poisonResistInInventory = 0;
        for (Item item : ctx.inventory.select()) {
            if (poisonResistMap.containsKey(item.id())) {
                poisonResistInInventory += poisonResistMap.get(item.id());
            }
        }
    }

    private void calculateRunEnergyInInventory() {
        runEnergyInInventory = 0;
        for (Item item : ctx.inventory.select()) {
            if (runRestoreMap.containsKey(item.id())) {
                runEnergyInInventory += runRestoreMap.get(item.id());
            }
        }
    }

    public static Map<Integer, Integer> getFoodHealingMap() {
        return foodHealingMap;
    }

    public static Map<Integer, Integer> getPrayerRestoreMap() {
        return prayerRestoreMap;
    }

    public int getPrayerPointsInInventory() {
        return prayerPointsInInventory;
    }

    public int getHealthInInventory() {
        return healthInInventory;
    }

    public int getRunEnergyInInventory() {
        return runEnergyInInventory;
    }

    public int getPoisonResistInInventory() {
        return poisonResistInInventory;
    }

    public static Map<Integer, Integer> getPoisonResistMap() {
        return poisonResistMap;
    }

    public static Map<Integer, Integer> getRunRestoreMap() {
        return runRestoreMap;
    }

    public static Map<Integer, Integer> getStatBoostMap() {
        return statBoostMap;
    }
}
