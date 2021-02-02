package gutizia.scripts.flipper;

import gutizia.scripts.Script;
import gutizia.scripts.flipper.ui.FlipperUI;
import gutizia.util.listeners.InventoryEvent;

import java.awt.*;
import java.util.ArrayList;

@org.powerbot.script.Script.Manifest(
        name = "Flipper",
        description = "flips items on the ge",
        properties = "",
        version = "0.0.0")

public class Flipper extends Script {

    @Override
    public void start() {
        super.start();
        new FlipperUI(ctx, "Flipper", this).start();
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
        //newTasks.add(new ());
        changingTasks = true;
    }

    @Override
    public void repaint(Graphics graphics) {
        super.repaint(graphics);
    }

    @Override
    public void onInventoryChange(InventoryEvent inventoryEvent) {
        super.onInventoryChange(inventoryEvent);
    }


}
