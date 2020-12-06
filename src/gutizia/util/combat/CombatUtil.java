package gutizia.util.combat;

import gutizia.util.constants.Widgets;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.*;

import java.util.logging.Logger;

public class CombatUtil extends ClientAccessor {
    private final static Logger LOGGER = Logger.getLogger("combatUtil");
    public static CombatUtil combatUtil;

    private int specWeapon = -1;

    // TODO initialize these variables at raids ui
    private String[] food = new String[] {"Pineapple pizza", "1/2 pineapple pizza"};
    private String[] prayRestore = new String[] {"Super restore(4)", "Super restore(3)", "Super restore(2)", "Super restore(1)"};

    private boolean haveOverload = false;
    private boolean haveXericsAid = false;
    private boolean haveRevitalisation = false;
    private boolean haveFood = false;

    private long lastHeal = 0;
    private long lastPrayRestore = 0;

    private boolean overloaded = false;

    public CombatUtil(ClientContext ctx) {
        super(ctx);
    }

    public void activatePrayers(Prayer.Effect[] effects) {
        ctx.input.speed(10);

        if (!ctx.widgets.component(Widgets.PRAYER_BOOK,0).visible()) {
            ctx.game.tab(Game.Tab.PRAYER,true);

            Condition.wait(() -> ctx.widgets.component(Widgets.PRAYER_BOOK,0).visible(),100,7);
        }

        if (ctx.widgets.component(Widgets.PRAYER_BOOK,0).visible()) {
            for (Prayer.Effect effect : effects) {
                LOGGER.info("activating prayer: " + effect.name());
                ctx.prayer.prayer(effect,true);
                Condition.sleep(Random.nextGaussian(100,350,150));
            }
        }
        Condition.sleep(Random.nextGaussian(300,550,450));
        ctx.game.tab(Game.Tab.INVENTORY,true);
        ctx.input.speed(100);
    }

    public boolean haveOverload() {
        return haveOverload;
    }

    public boolean haveXericsAid() {
        return haveXericsAid;
    }

    public boolean haveRevitalisation() {
        return haveRevitalisation;
    }

    public void setHaveOverload(boolean haveOverload) {
        this.haveOverload = haveOverload;
    }

    public void setHaveRevitalisation(boolean haveRevitalisation) {
        this.haveRevitalisation = haveRevitalisation;
    }

    public void setHaveXericsAid(boolean haveXericsAid) {
        this.haveXericsAid = haveXericsAid;
    }

    public void setFood(String[] food) {
        this.food = food;
    }

    public String[] getFood() {
        return food;
    }

    public boolean isOverloaded() {
        return overloaded;
    }

    public void setOverloaded(boolean overloaded) {
        this.overloaded = overloaded;
    }

    public void setHaveFood(boolean haveFood) {
        this.haveFood = haveFood;
    }

    public boolean haveFood() {
        return haveFood;
    }

    public int getSpecWeapon() {
        return specWeapon;
    }

    public void setSpecWeapon(int specWeapon) {
        this.specWeapon = specWeapon;
    }

    public String[] getPrayRestore() {
        return prayRestore;
    }

    public long getLastHeal() {
        return lastHeal;
    }

    public void setLastHeal(long lastHeal) {
        this.lastHeal = lastHeal;
    }

    public long getLastPrayRestore() {
        return lastPrayRestore;
    }

    public void setLastPrayRestore(long lastPrayRestore) {
        this.lastPrayRestore = lastPrayRestore;
    }
}
