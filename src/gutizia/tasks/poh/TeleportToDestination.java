package gutizia.tasks.poh;

import gutizia.tasks.Task;
import gutizia.util.managers.POHManager;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.managers.POHManager.pohManager;

public class TeleportToDestination extends Task {

    private POHManager.Jewellery jewellery = null;
    private POHManager.Portal portal = null;
    private POHManager.Xeric xeric = null;

    public TeleportToDestination(ClientContext ctx, POHManager.Jewellery jewellery) {
        super(ctx);
        this.jewellery = jewellery;
    }

    public TeleportToDestination(ClientContext ctx, POHManager.Portal portal) {
        super(ctx);
        this.portal = portal;
    }

    public TeleportToDestination(ClientContext ctx, POHManager.Xeric xeric) {
        super(ctx);
        this.xeric = xeric;
    }

    @Override
    public void execute() {
        if (jewellery != null) {
            pohManager.useOrnateJewelleryBox(jewellery);
        }
        if (portal != null) {
            pohManager.useNexusPortal(portal);
        }
        if (xeric != null) {
            pohManager.useXericsTalisman();
        }
    }

    @Override
    public boolean activate() {
        return pohManager.isInsideHouse();
    }
}
