package gutizia.util.overlay;

import gutizia.util.combat.CombatStats;
import gutizia.util.combat.Gear;
import gutizia.util.combat.Outfit;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment;

import java.awt.*;
import java.util.ArrayList;

import static gutizia.util.combat.PlayerCombat.playerCombat;

public class CombatOverlay extends Overlay {
    public final static CombatOverlay combatOverlay = new CombatOverlay(
            new Point(
                    ClientContext.ctx().widgets.component(122, 0).screenPoint().x +
                            ClientContext.ctx().widgets.component(122, 0).width() - 200,
                    ClientContext.ctx().widgets.component(122, 0).screenPoint().y + 15
            )
    );

    private ArrayList<String> outfitGear = new ArrayList<>();
    private ArrayList<String> stats = new ArrayList<>();

    private CombatOverlay(Point point) {
        super(new Rectangle(point.x, point.y, 300, 400));
        setStringArray(playerCombat.getOutfit());
    }

    public void setStringArray(Outfit outfit) {
        for (Gear gear : outfit.getGear()) {
            if (gear.getItem().getId() == -1) {
                outfitGear.add("None");
            } else {
                if (gear.getSlot().equals(Equipment.Slot.QUIVER)) {
                    outfitGear.add(gear.getItem().getSharedName() + "(" + gear.getItem().getAmount() + ")");
                } else {
                    outfitGear.add(gear.getItem().getSharedName());
                }
            }
        }
        stats.add("off. stab: " + outfit.getStats()[CombatStats.OFF_STAB]);
        stats.add("off. slash: " + outfit.getStats()[CombatStats.OFF_SLASH]);
        stats.add("off. crush: " + outfit.getStats()[CombatStats.OFF_CRUSH]);
        stats.add("off. magic: " + outfit.getStats()[CombatStats.OFF_MAGIC]);
        stats.add("off. range: " + outfit.getStats()[CombatStats.OFF_RANGE]);
        stats.add("def. stab: " + outfit.getStats()[CombatStats.DEF_STAB]);
        stats.add("def. slash: " + outfit.getStats()[CombatStats.DEF_SLASH]);
        stats.add("def. crush: " + outfit.getStats()[CombatStats.DEF_CRUSH]);
        stats.add("def. magic: " + outfit.getStats()[CombatStats.DEF_MAGIC]);
        stats.add("def. range: " + outfit.getStats()[CombatStats.DEF_RANGE]);
        stats.add("melee str.: " + outfit.getStats()[CombatStats.STR_MELEE]);
        stats.add("range str.: " + outfit.getStats()[CombatStats.STR_RANGE]);
        stats.add("magic str.: " + outfit.getStats()[CombatStats.STR_MAGIC]);
        stats.add("pray bonus: " + outfit.getStats()[CombatStats.PRAYER]);
        stats.add("max hit: " + playerCombat.getMaxHit());
        stats.add("accuracy: " + playerCombat.getAccuracy());
        stats.add("attack style: " + playerCombat.getCurrentAttackOption().getAttackStyle());
        stats.add("attack type: " + playerCombat.getCurrentAttackOption().getAttackType());
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        Drawer.drawStringList(g, new Point(getBox().x + 10, getBox().y + 10), outfitGear);
        Drawer.drawStringList(g, new Point(getBox().x + 160, getBox().y + 10), stats);
    }
}
