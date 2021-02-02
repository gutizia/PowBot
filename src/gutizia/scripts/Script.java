package gutizia.scripts;

import gutizia.tasks.Task;
import gutizia.util.ge.GrandExchange;
import gutizia.util.listeners.EventDispatcher;
import gutizia.util.listeners.InventoryEvent;
import gutizia.util.listeners.InventoryListener;
import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import static gutizia.util.overlay.TaskOverlay.taskOverlay;
import static gutizia.util.overlay.DebugOverlay.debugOverlay;
import static gutizia.util.combat.PlayerCombat.playerCombat;
import static gutizia.util.Inventory.inventory;

public abstract class Script extends PollingScript<ClientContext> implements PaintListener, InventoryListener, MessageListener {
    protected static ArrayList<Task> tasks = new ArrayList<>();
    protected static ArrayList<Task> newTasks = new ArrayList<>();
    protected static ArrayList<Task> standardTasks = new ArrayList<>();

    protected EventDispatcher eventDispatcher;

    protected static boolean changingTasks = false;
    private static boolean stopping = false;

    private static boolean breakOnActivate = true;

    public static long startTime;

    private boolean debugging = false;
    private boolean started = false;

    protected abstract void initStandardTasks();

    @Override
    public void start() {
        startTime = System.currentTimeMillis();
        eventDispatcher = new EventDispatcher(ctx);
        eventDispatcher.addListener(this);
        initStandardTasks();
        debugging = ctx.properties.getProperty("user.name").equalsIgnoreCase("gutizia");
        started = true;
    }

    @Override
    public void poll() {
        if (stopping) {
            ctx.controller.stop();
            return;
        }
        if (changingTasks) {
            changeTasks();
            changingTasks = false;
        }

        for (Task task : tasks) {
            if (ctx.controller.isStopping() || changingTasks || stopping) {
                break;
            }
            if (task.activate()) {
                long time = System.currentTimeMillis();
                task.execute();
                System.out.println(task.getClass().getName() + " took " + (System.currentTimeMillis() - time) + " ms to complete.");
                if (breakOnActivate) {
                    break;
                }
            }
            if (!taskOverlay.getActiveTask().equals("")) {
                taskOverlay.setActiveTask("");
            }
        }
    }

    @Override
    public void stop() {
        eventDispatcher.setRunning(false);
        eventDispatcher.clearListeners();
    }

    @Override
    public void onInventoryChange(InventoryEvent inventoryEvent) {
        Item newItem = inventoryEvent.getNewItem();
        Item oldItem = inventoryEvent.getOldItem();
        int index = inventoryEvent.getInvIndex();

        inventory.removeOldItem(index);

        inventory.addNewItem(new gutizia.util.Item(newItem.id(), newItem.name(), newItem.stackSize()), index);
    }

    @Override
    public void repaint(Graphics graphics) {
        if (started) {
            Graphics2D g = (Graphics2D)graphics;
            if (debugging) {
                taskOverlay.draw(g);
                debugOverlay.draw(g);
            }

            if (playerCombat.getTarget().getNpc().valid()) {
                playerCombat.getTarget().display(g, 350, 10);
            }
        }
    }

    @Override
    public void messaged(MessageEvent messageEvent) {
        if (!GrandExchange.isOfferToCollect() && messageEvent.text().contains("Grand Exchange: Finished")) {
            System.out.println("a offer has completed");
            GrandExchange.setOfferToCollect(true);
        }
    }

    public static void setTasks(ArrayList<Task> tasks) {
        changingTasks = true;
        newTasks = tasks;
    }

    protected void changeTasks() {
        tasks = newTasks;
        newTasks = new ArrayList<>();
    }

    public static void stopScript(String msg) {
        System.err.println(msg);
        stopping = true;
    }

    protected void downloadImage(String url, String name) {
        final File file = new File(getStorageDirectory(), name);
        try {
            if (!file.exists()) {
                log.info("file " + name + " did not exist. Downloading now");
                final BufferedImage img = downloadImage(url);
                if (img != null && img.getWidth() > 1 && img.getHeight() > 1) {
                    ImageIO.write(img, "png", file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getSavedImage(String url, String name) {
        final File file = new File(getStorageDirectory(), name);
        try {
            if (!file.exists()) {
                log.info("file " + name + " did not exist");
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

    public static ArrayList<Task> getTasks() {
        return tasks;
    }

    public static void setBreakOnActivate(boolean breakOnActivate) {
        Script.breakOnActivate = breakOnActivate;
    }

    public static void setNewTasks(ArrayList<Task> newTasks) {
        Script.newTasks = newTasks;
    }

    public static void setStandardTasks() {
        newTasks = standardTasks;
        changingTasks = true;
    }
}
