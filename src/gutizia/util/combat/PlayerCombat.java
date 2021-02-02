package gutizia.util.combat;

import gutizia.scripts.Script;
import gutizia.util.Target;
import gutizia.util.constants.Varbits;
import gutizia.util.constants.Widgets;
import gutizia.util.overlay.Drawer;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;
import java.util.logging.Logger;

/**
 * used for extended references to local player related to combat
 */
public class PlayerCombat extends CombatStats {
    private final static Logger LOGGER = Logger.getLogger("playerCombat");
    public final static PlayerCombat playerCombat = new PlayerCombat();

    private ClientContext ctx = org.powerbot.script.ClientContext.ctx();

    private int[] defence;
    private Outfit outfit;
    private int interactRange = 8; // max range you want to interact with npcs

    private boolean attacking; // for fast paced combat where you can't get data from client if you've attacked or not
    private AttackOption attackOption = null;

    private PlayerCombat() {
        outfit = new Outfit().getPlayerEquipment();
        maxHit = CombatCalculations.calculateMaxHit(ctx, getCurrentAttackOption().getAttackType(), getStrBonus(AttackType.MELEE), false);
    }

    // TODO make it find max hit and accuracy to calculate hit chance on you
    public Target target = new Target(org.powerbot.script.ClientContext.ctx()) {

        @Override
        public void display(Graphics2D g, int x, int y) {
            StringBuilder sb = new StringBuilder();
            sb.append(getName());
            sb.append(" | ");
            if (getNpc().valid()) {
                sb.append(getHealthPercent());
                sb.append("%");
                sb.append(" | ");
                sb.append("in viewport ");
                sb.append(getNpc().inViewport());
                sb.append(" | ");
                sb.append("orientation ");
                sb.append(getNpc().orientation());
                sb.append(" | ");
                sb.append("distance to player ");
                sb.append(getArea().getClosestTo(ctx.players.local()).distanceTo(ctx.players.local()));
                sb.append(" | ");
                sb.append("interacting with ");
                sb.append(getNpc().interacting().name());
            } else {
                sb.append("not initialized yet");
            }
            g.setColor(Color.WHITE);
            g.drawString(sb.toString(), x, y);
            Drawer.drawTile(g, getNpc().tile().matrix(ctx), Drawer.TileColor.PROJECTILE);
            if (getSize() > 1) {
                Drawer.drawArea(ctx, g, getArea(), Drawer.TileColor.FLICK);
            }
        }
    };

    public AttackOption getCurrentAttackOption() {
        if (attackOption != null) {
            return attackOption;
        }
        return attackOption = calculateAttackOption();
    }

    private AttackOption calculateAttackOption() {
        String wCategory = ctx.widgets.component(Widgets.COMBAT_SETTINGS, 2).text().split(": ")[1];
        for (WeaponCategory weaponCategory : WeaponCategory.values()) {
            if (weaponCategory.getCategoryName().equalsIgnoreCase(wCategory)) {
                return weaponCategory.getAttackOptions()[ctx.varpbits.varpbit(Varbits.ATTACK_STYLE)];
            }
        }
        Script.stopScript("failed to get attack option for weapon category: " + wCategory);
        return null;
    }

    public void setOutfit(Outfit outfit) {
        this.outfit = outfit;
        updateCombatStats();
    }

    public void updateCombatStats() {
        ClientContext ctx = org.powerbot.script.ClientContext.ctx();
        maxHit = CombatCalculations.calculateMaxHit(ctx, attackOption.getAttackType(), outfit.getStrBonus(attackOption.getAttackType()), outfit.isVoid());
        accuracy = CombatCalculations.calculateAccuracy(ctx, attackOption.getAttackType(), outfit.getOffBonus(attackOption.getAttackType()), outfit.isVoid());
        defence = CombatCalculations.calculateDefence(ctx, outfit.getDefBonuses());
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public Target getTarget() {
        return target;
    }

    public void setInteractRange(int interactRange) {
        this.interactRange = interactRange;
    }

    public int getInteractRange() {
        return interactRange;
    }

    public Outfit getOutfit() {
        return outfit;
    }

    public int[] getDefence() {
        return defence;
    }
}
