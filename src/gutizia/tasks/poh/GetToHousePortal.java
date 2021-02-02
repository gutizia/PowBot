package gutizia.tasks.poh;

import gutizia.tasks.Activatable;
import gutizia.tasks.Task;
import gutizia.util.constants.Items;
import gutizia.util.constants.LevelRequirements;
import gutizia.util.managers.POHManager;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Game;

import static gutizia.util.managers.POHManager.pohManager;


public class GetToHousePortal extends Task {
    private Activatable activatable = null;

    public GetToHousePortal(ClientContext ctx) {
        super(ctx);
    }

    public GetToHousePortal(ClientContext ctx, Activatable activatable) {
        super(ctx);
        this.activatable = activatable;
    }

    @Override
    public void execute() {
        if (ctx.skills.realLevel(Constants.SKILLS_MAGIC) >= LevelRequirements.TELEPORT_TO_HOUSE) {
            ctx.game.tab(Game.Tab.MAGIC);
            ctx.widgets.component(218,28).click();
            ctx.game.tab(Game.Tab.INVENTORY);

        } else if (!ctx.inventory.select().id(Items.TELEPORT_TO_HOUSE_TABLET).isEmpty()) {
            ctx.inventory.select().id(Items.TELEPORT_TO_HOUSE_TABLET).poll().interact("Break");
        }

        Condition.wait(() -> pohManager.isOutsideHouse(),600,10);
    }

    @Override
    public boolean activate() {
        if (activatable != null) {
            return activatable.activate();
        }

        return !pohManager.isOutsideHouse() && !pohManager.isInsideHouse();
    }
}
