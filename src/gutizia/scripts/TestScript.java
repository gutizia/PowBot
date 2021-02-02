package gutizia.scripts;

import gutizia.tasks.ge.CloseGe;
import gutizia.tasks.ge.CreateOffer;
import gutizia.tasks.ge.OpenGe;
import gutizia.util.Target;
import gutizia.util.constants.Areas;
import gutizia.util.ge.GrandExchange;
import gutizia.util.ge.Offer;
import gutizia.util.ge.Price;
import gutizia.util.listeners.GroundItemEvent;
import gutizia.util.listeners.GroundItemListener;
import gutizia.util.overlay.Drawer;
import gutizia.util.settings.LootSettings;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.PaintListener;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.Component;

import java.awt.*;
import java.util.ArrayList;

import static gutizia.util.Inventory.inventory;
import static gutizia.util.managers.LootManager.lootManager;
import static gutizia.util.overlay.DebugOverlay.debugOverlay;

@org.powerbot.script.Script.Manifest(
        name = "Test Script",
        description = "for testing purposes",
        properties = "",
        version = "0.0.0")

public class TestScript extends Script implements PaintListener, GroundItemListener {
    private Area area;
    private Tile tile;
    private GameObject object;
    private Component component = null;

    private Target target;

    @Override
    public void start() {
        super.start();
        //area = new Area(new Tile(3257, 3248, 0), new Tile(3254, 3248, 0), new Tile(3254, 3252, 0));
        LootSettings.getPropertyUtil().updateProperty(LootSettings.ITEMS_TO_LOOT, "");

        //ctx.controller.stop();
    }

    @Override
    public void poll() {
        super.poll();
        //Condition.sleep(500);
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    protected void initStandardTasks() {
        newTasks = new ArrayList<>();
        newTasks.add(new OpenGe(() -> true));
        //newTasks.add(new CreateOffer(() -> true, new Offer(new gutizia.util.Item(20997, "Twisted bow", 1), new Price(20997).setCustomPrice(1), true, 10)));
        newTasks.add(new CreateOffer(() -> true, new Offer(new gutizia.util.Item(955, "Rope", 1), new Price(955).minusFivePercent(), false, 10)));
        newTasks.add(new CloseGe(() -> true));
        changingTasks = true;
    }

    @Override
    public void onGroundItemChange(GroundItemEvent groundItemEvent) {
        GroundItem oldItem = groundItemEvent.getOldItem();
        GroundItem newItem = groundItemEvent.getNewItem();

        if (newItem != null) {
            if (lootManager.getItemsToLoot().contains(newItem.id())) {
                lootManager.addItemToLoot(newItem);
            }
        }

        if (oldItem != null) {
            if (lootManager.getLootList().contains(oldItem)) {
                System.out.println("found item that was in your loot list that has disappeared");
                lootManager.removeItemToLoot(oldItem);
            }
        }
    }

    @Override
    public void repaint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        debugOverlay.draw(g);
        Drawer.drawArea(ctx, g, Areas.LUMBRIDGE_CHICKEN_PEN, Drawer.TileColor.PROJECTILE);
        Drawer.drawStringList(g, new Point(30, 100), inventory.getItems().getDisplayInfo());

        if (component != null) {
            Drawer.drawString(g, new Point(200, 200), "x position: " + component.screenPoint().x);

        }

        if (GrandExchange.isOffersSet()) {
            int x = 100;
            int i = 0;
            for (Offer offer : GrandExchange.getCurrentOffers()) {
                Drawer.drawStringList(g, new Point(x + (110 * i), 50), offer.getPrint());
                i++;
            }
        }
    }
}
