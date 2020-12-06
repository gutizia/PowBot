package gutizia.util.combat;

import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import java.util.logging.Logger;

/**
 * used for extended references to local player related to combat
 */
public class PlayerCombat extends ClientAccessor {
    private final static Logger LOGGER = Logger.getLogger("playerCombat");
    public static PlayerCombat playerCombat;

    private int maxHit;
    private int accuracy;
    private int[] defence;
    private Outfit outfit;

    private boolean attacking; // for fast paced combat where you can't get data from client if you've attacked or not

    public PlayerCombat(ClientContext ctx) {
        super(ctx);
    }

    public void setOutfit(Outfit outfit) {
        this.outfit = outfit;
        updateCombatStats();
    }

    public void updateCombatStats() {
        maxHit = CombatCalculations.calculateMaxHit(ctx, outfit.getCombatStyle(), outfit.getStrBonus(outfit.getCombatStyle()), outfit.isVoid());
        accuracy = CombatCalculations.calculateAccuracy(ctx, outfit.getCombatStyle(), outfit.getOffBonus(outfit.getAttackStyle()), outfit.isVoid());
        defence = CombatCalculations.calculateDefence(ctx, outfit.getDefBonuses());
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isAttacking() {
        return attacking;
    }
}
