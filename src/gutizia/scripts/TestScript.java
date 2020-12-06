package gutizia.scripts;

import gutizia.tasks.Interact;
import gutizia.tasks.traverse.lumbridge.bank.GetToLumbridgeBank;
import gutizia.util.InteractOptions;
import gutizia.util.constants.Areas;
import gutizia.util.constants.Interactives;
import gutizia.util.overlay.Drawer;
import org.powerbot.script.Area;
import org.powerbot.script.PaintListener;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.Component;
import org.powerbot.script.rt4.GameObject;

import java.awt.*;
import java.util.ArrayList;

import static gutizia.util.overlay.DebugOverlay.debugOverlay;

@org.powerbot.script.Script.Manifest(
        name="Test Script",
        description = "for testing purposes",
        properties="author=Gutizia; topic=1234; client=4;")

public class TestScript extends Script implements PaintListener {
    private Area area;
    private Tile tile;
    private GameObject object;
    private Component component = null;

    @Override
    public void start() {
        super.start();
        //area = new Area(new Tile(3257, 3248, 0), new Tile(3254, 3248, 0), new Tile(3254, 3252, 0));
        component = ctx.widgets.component(116, 55);
    }

    @Override
    public void poll() {
        super.poll();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    protected void initStandardTasks() {
        newTasks = new ArrayList<>();
        newTasks.add(new GetToLumbridgeBank(ctx));
        changingTasks = true;
    }

    @Override
    public void repaint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        if (debugOverlay != null) {
            debugOverlay.draw(g);
        }
        if (component != null) {
            Drawer.drawString(g, new Point(200, 200), "x position: " + component.screenPoint().x);

        }
    }
}
