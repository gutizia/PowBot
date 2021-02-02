package gutizia.scripts.autoStart;

import gutizia.tasks.bank.lumbridge.DepositItemsInLumbridge;
import gutizia.tasks.combat.chicken.Chicken;
import gutizia.tasks.combat.cow.Cow;
import gutizia.tasks.combat.goblin.Goblin;
import gutizia.util.listeners.*;
import gutizia.util.managers.AttackOptionManager;
import gutizia.util.overlay.Drawer;
import org.powerbot.script.PaintListener;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.GroundItem;

import java.awt.*;
import java.util.ArrayList;

import static gutizia.util.combat.TargetSelector.targetSelector;
import static gutizia.util.managers.LootManager.lootManager;
import static gutizia.util.overlay.CombatOverlay.combatOverlay;
import static gutizia.util.Inventory.inventory;

@Script.Manifest(
        name = "Advanced Combat Script",
        description = "trains combat with gear and npc progression",
        properties = "",
        version = "0.0.0")

public class AutoStart extends gutizia.scripts.Script implements PaintListener, ExperienceListener, InventoryListener, LevelListener, GroundItemListener {

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void poll() {
        super.poll();
    }

    @Override
    public void stop() {
        super.stop();
        eventDispatcher.setRunning(false);
    }

    @Override
    protected void initStandardTasks() {
        newTasks = new ArrayList<>();
        newTasks.add(new Chicken());
        newTasks.add(new Goblin());
        newTasks.add(new Cow());
        newTasks.add(new DepositItemsInLumbridge());

        /*
        * set vital settings
        * upgrade gear
        *   get to ge
        *   get to bank with collect option
        *   collect items
        *   sell items
        *   buy items
        * do beginner clue if selected
        *
        *
        * */
        changingTasks = true;
    }

    @Override
    public void onGroundItemChange(GroundItemEvent groundItemEvent) {
        GroundItem oldItem = groundItemEvent.getOldItem();
        GroundItem newItem = groundItemEvent.getNewItem();

        if (oldItem != null) {
            if (lootManager.getLootList().contains(oldItem)) {
                System.out.println("found item that was in your loot list that has disappeared");
                lootManager.removeItemToLoot(oldItem);
            }
        }
        if (newItem != null) {
            if (lootManager.getItemsToLoot().contains(newItem.id())) {
                if (lootManager.getLootTiles().contains(newItem.tile())) {
                    lootManager.addItemToLoot(newItem);
                }
            }
        }
    }

    @Override
    public void onExperienceChanged(ExperienceEvent experienceEvent) {
    }

    @Override
    public void onLevelChanged(LevelEvent levelEvent) {
        targetSelector.getTargetToKill();
        AttackOptionManager.updateAttackOption();
    }

    @Override
    public void onInventoryChange(InventoryEvent inventoryEvent) {
        super.onInventoryChange(inventoryEvent);
    }

    @Override
    public void repaint(Graphics graphics) {
        super.repaint(graphics);
        Graphics2D g = (Graphics2D) graphics;
        combatOverlay.draw(g);
        Drawer.drawStringList(g, new Point(30, 100), inventory.getItems().getDisplayInfo());
    }
}
