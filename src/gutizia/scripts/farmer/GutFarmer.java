package gutizia.scripts.farmer;

import gutizia.scripts.farmer.ui.FarmingUI;
import gutizia.tasks.*;
import gutizia.tasks.farming.FruitTreeRun;
import gutizia.tasks.farming.HerbRun;
import gutizia.scripts.farmer.tasks.Wait;
import gutizia.tasks.farming.HopsRun;
import gutizia.tasks.farming.TreeRun;
import gutizia.tasks.poh.SetTeleportToOutside;
import gutizia.util.constants.Items;
import gutizia.util.listeners.*;
import gutizia.util.managers.WorldManager;
import gutizia.util.overlay.Drawer;
import gutizia.util.overlay.GutFarmerUI;
import gutizia.util.resources.PropertyUtil;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GeItem;

import javax.imageio.ImageIO;

import static gutizia.util.managers.SettingsManager.settingsManager;
import static gutizia.scripts.farmer.ui.FarmingUI.farmingUi;
import static gutizia.util.managers.WorldManager.worldManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.logging.Logger;

import static gutizia.util.resources.PropertyUtil.*;
import static gutizia.util.resources.PropertyUtil.trees;
import static gutizia.util.trackers.FarmingTracker.farmingTracker;
import static gutizia.util.trackers.FruitTreeTracker.fruitTreeTracker;
import static gutizia.util.trackers.HerbRunTracker.herbRunTracker;
import static gutizia.util.trackers.HopsRunTracker.hopsRunTracker;
import static gutizia.util.trackers.TreeTracker.treeTracker;

@Script.Manifest(
        name="gutFarmer",
        description = "automates the farming process! supports all: trees, fruit trees, herbs, flowers, allotments, hops!",
        properties="author=Gutizia; topic=1353575; client=4;")

public class GutFarmer extends PollingScript<ClientContext> implements PaintListener, ExperienceListener, InventoryListener {
    private final static Logger LOGGER = Logger.getLogger("GutFarmer");
    public EventDispatcher eventDispatcher;

    private static ArrayList<Task> tasks = new ArrayList<>();
    private static ArrayList<Task> newTasks = new ArrayList<>();

    private static boolean changingTasks = false;

    public static boolean active = false;

    private boolean waiting = false;

    private GutFarmerUI loginScreenUI;
    private GutFarmerUI herbRunUI = new GutFarmerUI(getImage("https://ibb.co/8Pv2cZ8", "herb"));
    private GutFarmerUI hopsRunUI;
    private GutFarmerUI fruitTreeRunUI = new GutFarmerUI(getImage("https://ibb.co/YQtm97v", "fruit tree"));
    private GutFarmerUI treeRunUI = new GutFarmerUI(getImage("https://ibb.co/B2NCmDQ", "tree"));

    private Activatable logoutActivate = () -> {
        if (!ctx.game.loggedIn()) {
            return false;
        }
        boolean b = true;

        if (farmingTracker.isHerbsActivated() && herbs.getProperty(PropertyUtil.LOGIN_DONE).equals("true") &&
                System.currentTimeMillis() > Long.parseLong(herbs.getProperty("next.harvest"))) {
            b = false;

        } else if (farmingTracker.isFlowersActivated() && flowers.getProperty(PropertyUtil.LOGIN_DONE).equals("true") &&
                System.currentTimeMillis() > Long.parseLong(flowers.getProperty("next.harvest"))) {
            b = false;

        } else if (farmingTracker.isAllotmentsActivated() && allotments.getProperty(PropertyUtil.LOGIN_DONE).equals("true") &&
                System.currentTimeMillis() > Long.parseLong(allotments.getProperty("next.harvest"))) {
            b = false;

        } else if (farmingTracker.isHopsActivated() && hops.getProperty(PropertyUtil.LOGIN_DONE).equals("true") &&
                System.currentTimeMillis() > Long.parseLong(hops.getProperty("next.harvest"))) {
            b = false;

        } else if (farmingTracker.isFruitTreesActivated() && fruitTrees.getProperty(PropertyUtil.LOGIN_DONE).equals("true") &&
                System.currentTimeMillis() > Long.parseLong(fruitTrees.getProperty("next.harvest"))) {
            b = false;

        } else if (farmingTracker.isTreesActivated() && trees.getProperty(PropertyUtil.LOGIN_DONE).equals("true") &&
                System.currentTimeMillis() > Long.parseLong(trees.getProperty("next.harvest"))) {
            b = false;
        }

        return b;
    };

    @Override
    public void start() {
        settingsManager.setLogin(ctx.properties.getProperty("user.name"));
        active = true;
        farmingUi = new FarmingUI(ctx);
        worldManager = new WorldManager(ctx);
        eventDispatcher = new EventDispatcher(ctx);

        ctx.properties.setProperty("login.disable","true");
        eventDispatcher.addListener(this);

        setStandardTasks();
        farmingUi.start();
        ctx.properties.setProperty("login.disable","false");
    }

    @Override
    public void stop() {
        eventDispatcher.setRunning(false);
    }

    @Override
    public void poll() {
        if (changingTasks) {
            changeTasks();
            changingTasks = false;
        }

        for (Task task : tasks) {
            if (ctx.controller.isStopping()) {
                break;
            }
            if (task.activate() && !changingTasks) {
                task.execute();
            }
        }
    }

    public void setStandardTasks() {
        newTasks = new ArrayList<>();
        newTasks.add(new Logout(ctx, logoutActivate));
        newTasks.add(new SetTeleportToOutside(ctx));
        newTasks.add(new HerbRun(ctx));
        newTasks.add(new HopsRun(ctx));
        newTasks.add(new FruitTreeRun(ctx));
        newTasks.add(new TreeRun(ctx));
        newTasks.add(new Wait(ctx)); // has to be last task to avoid ending poll loop
        changingTasks = true;
    }

    public void setTasks(ArrayList<Task> tasks) {
        changingTasks = true;
        newTasks = tasks;
    }

    private void changeTasks() {
        tasks = newTasks;
        newTasks = new ArrayList<>();
    }

    private BufferedImage getImage(String url, String name) {
        final java.io.File file = new java.io.File(getStorageDirectory(), name);
        try {
            if (!file.exists()) {
                LOGGER.info("file did not exist");
                final BufferedImage img = downloadImage(url);
                if (img != null && img.getWidth() > 1 && img.getHeight() > 1) {
                    ImageIO.write(img, "png", file);
                    return img;
                }
            } else {
                return ImageIO.read(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void repaint(Graphics graphics) {
        Graphics2D g = (Graphics2D)graphics;

        if (herbRunTracker.isDoingRun()) {
            herbRunUI.drawImage(g);
            Drawer.drawString(g, GutFarmerUI.HERB_HERBS_FARMED, Integer.toString(farmingTracker.getHerbsFarmed()));
            Drawer.drawString(g, GutFarmerUI.HERB_FLOWERS_FARMED, Integer.toString(farmingTracker.getFlowersFarmed()));
            Drawer.drawString(g, GutFarmerUI.HERB_ALLOTMENTS_FARMED, Integer.toString(farmingTracker.getAllotmentsFarmed()));

        } else if (hopsRunTracker.isDoingRun()) {
            hopsRunUI.drawImage(g);
            Drawer.drawString(g, GutFarmerUI.HOPS_HOPS_FARMED, Integer.toString(farmingTracker.getHopsFarmed()));

        } else if (fruitTreeTracker.isDoingRun()) {
            fruitTreeRunUI.drawImage(g);
            Drawer.drawString(g, GutFarmerUI.FT_TREES_PLANTED, Integer.toString(farmingTracker.getFruitTreesPlanted()));
            Drawer.drawString(g, GutFarmerUI.FT_CHECKED_HEALTH_OF, Integer.toString(farmingTracker.getFruitTreesCheckedHealthOf()));
            Drawer.drawString(g, GutFarmerUI.FT_FRUITS_FARMED, Integer.toString(farmingTracker.getFruitsFarmed()));

        } else if (treeTracker.isDoingRun()) {
            treeRunUI.drawImage(g);
            Drawer.drawString(g, GutFarmerUI.TREE_TREES_PLANTED, Integer.toString(farmingTracker.getTreesPlanted()));
            Drawer.drawString(g, GutFarmerUI.TREE_CHECKED_HEALTH_OF, Integer.toString(farmingTracker.getTreesCheckedHealthOf()));

        }

        if (waiting) {
            //loginScreenUI.drawImage(g);

        } else {
            Drawer.drawString(g, GutFarmerUI.TIME_ELAPSED, Long.toString(getRuntime()));
            Drawer.drawString(g, GutFarmerUI.TOTAL_EXP_GAINED, Integer.toString(farmingTracker.getExpGained()));
            Drawer.drawString(g, GutFarmerUI.TOTAL_MONEY_MADE, Integer.toString(farmingTracker.getMoneyMade()));
        }
    }

    @Override
    public void onExperienceChanged(ExperienceEvent experienceEvent) {
        farmingTracker.increaseExpGained(experienceEvent.getExperienceChange());
    }

    @Override
    public void onInventoryChange(InventoryEvent inventoryEvent) {
        int newItemId = inventoryEvent.getNewItem().id();
        int oldItemId = inventoryEvent.getOldItem().id();

        if (newItemId != -1) {
            if (Arrays.asList(Items.GRIMY_HERB_PRODUCE).contains(newItemId)) {
                farmingTracker.increaseMoneyMade(GeItem.getPrice(newItemId));
                farmingTracker.increaseHerbsFarmed();

            } else if (Arrays.asList(Items.FLOWER_PRODUCE).contains(newItemId)) {
                farmingTracker.increaseMoneyMade(GeItem.getPrice(newItemId));
                farmingTracker.increaseFlowersFarmed();

            } else if (Arrays.asList(Items.ALLOTMENT_PRODUCE).contains(newItemId)) {
                farmingTracker.increaseMoneyMade(GeItem.getPrice(newItemId));
                farmingTracker.increaseAllotmentsFarmed();

            } else if (Arrays.asList(Items.FRUIT_TREE_PRODUCE).contains(newItemId)) {
                farmingTracker.increaseMoneyMade(GeItem.getPrice(newItemId));
                farmingTracker.increaseFruitsFarmed();
            }
        }
        if (oldItemId != -1) {
            if (Arrays.asList(Items.FRUIT_TREE_SAPLINGS).contains(oldItemId)) {
                farmingTracker.increaseFruitTreesPlanted();

            } else if (Arrays.asList(Items.TREE_SAPLINGS).contains(oldItemId)) {
                farmingTracker.increaseTreesPlanted();
            }
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }
}
