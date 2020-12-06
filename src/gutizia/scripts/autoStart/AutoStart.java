package gutizia.scripts.autoStart;

import gutizia.tasks.combat.chicken.Chicken;
import gutizia.util.listeners.ExperienceEvent;
import gutizia.util.listeners.ExperienceListener;
import gutizia.util.listeners.InventoryEvent;
import gutizia.util.listeners.InventoryListener;
import org.powerbot.script.PaintListener;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.Item;

import java.awt.*;
import java.util.ArrayList;

@Script.Manifest(
        name="AutoTrainer",
        description = "trains combat with gear and npc progression",
        properties="author=Gutizia; topic=1234; client=4;")

public class AutoStart extends gutizia.scripts.Script implements PaintListener, ExperienceListener, InventoryListener {

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
    }

    @Override
    protected void initStandardTasks() {
        newTasks = new ArrayList<>();
        // bank
        // clue
        newTasks.add(new Chicken(ctx));

        /*
        * set vital settings
        * bank
        *   get to a bank
        *   deposit items that should be deposited
        *   withdraw items that should be withdrawn
        *
        * chickens ()
        *   get to chickens
        *   kill chickens
        *       combat tasks (attack, wait, antiban, heal)
        *       optional tasks (bury, loot)
        * goblins
        *   get to goblins
        *       combat tasks (attack, wait, antiban, heal)
        *       optional tasks (bury, loot)
        *   kill goblins
        * cows
        *   get to cows
        *   kill cows
        *       combat tasks (attack, wait, antiban, heal)
        *       optional tasks (bury, loot)
        * upgrade gear
        *   get to ge
        *   get to bank with collect option
        *   collect items
        *   sell items
        *   buy items
        * do beginner clue
        *
        *
        * */
        changingTasks = true;
    }

    @Override
    public void onExperienceChanged(ExperienceEvent experienceEvent) {
    }

    @Override
    public void onInventoryChange(InventoryEvent inventoryEvent) {
        Item newItem = inventoryEvent.getNewItem();
        Item oldItem = inventoryEvent.getOldItem();

    }

    @Override
    public void repaint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

    }
}
