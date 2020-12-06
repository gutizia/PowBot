package gutizia.tasks.poh;

import gutizia.tasks.Task;
import gutizia.util.managers.POHManager;
import gutizia.util.managers.WorldManager;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Widget;

public class EnterPOH extends Task {
    private POHManager pohManager = new POHManager(ctx);

    private final Widget HOUSE_ADVERTISEMENT = ctx.widgets.widget(52);

    public EnterPOH(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        if (WorldManager.worldManager.getCurrentWorld() != 330) {
            WorldManager.worldManager.hopToWorld(330);
        }

        if (!ctx.widgets.component(HOUSE_ADVERTISEMENT.id(),1).visible()) {
            pohManager.viewHouseAdvertisement();
        }

        if (ctx.widgets.component(HOUSE_ADVERTISEMENT.id(),1).visible()) {
            pohManager.getInsideHostHouse();
        }
    }

    @Override
    public boolean activate() {
        return pohManager.isOutsideHouse();
    }
}
