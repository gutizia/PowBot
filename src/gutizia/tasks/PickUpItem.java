package gutizia.tasks;

import gutizia.util.Item;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.GroundItem;

import static gutizia.util.overlay.TaskOverlay.taskOverlay;

public class PickUpItem extends Task {

    private Item item;
    private int[] bounds;

    public PickUpItem(Item item, int[] bounds) {
        this.item = item;
        this.bounds = bounds;
    }

    public PickUpItem(Item item) {
        this(item, null);
    }

    @Override
    public void execute() {
        taskOverlay.setActiveTask("PickUpItem");
        // TODO make better
        GroundItem groundItem = ctx.groundItems.poll();
        if (bounds != null) {
            groundItem.bounds(bounds);
        }
        groundItem.interact("Take");
        Condition.wait(() -> !groundItem.valid(), 500, 20);
    }

    @Override
    public boolean activate() {
        if (item.getId() != -1) {
            return !ctx.groundItems.select().id(item.getId()).isEmpty();
        }
        return !ctx.groundItems.select().name(item.getName()).isEmpty();

    }
}
