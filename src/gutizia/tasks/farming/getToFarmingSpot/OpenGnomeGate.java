package gutizia.tasks.farming.getToFarmingSpot;

import gutizia.util.Interact;
import gutizia.util.managers.CameraManager;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import static gutizia.util.managers.CameraManager.cameraManager;

class OpenGnomeGate extends GetToFarmSpot {
    private final Area gateArea = new Area(new Tile(2455,3374,0),new Tile(2466,3383,0));

    OpenGnomeGate(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        cameraManager.turnCamera(CameraManager.Direction.NORTH);
        GameObject gate = ctx.objects.select(15).name("Gate").nearest().poll();
        Interact.interact(ctx,gate,true,"Open",true);

        // TODO handle exception of first entering stronghold where the gnome lady talks to you
        Condition.wait(() -> ctx.players.local().tile().equals(new Tile(2461,3385,0)),600,10);
    }

    @Override
    public boolean activate() {
        return gateArea.containsOrIntersects(ctx.players.local());
    }
}
