package gutizia.scripts;

import gutizia.tasks.Task;
import gutizia.util.combat.PlayerCombat;
import gutizia.util.listeners.EventDispatcher;
import gutizia.util.overlay.DebugOverlay;
import gutizia.util.overlay.TaskOverlay;
import org.powerbot.script.PollingScript;
import org.powerbot.script.rt4.ClientContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import static gutizia.util.overlay.TaskOverlay.taskOverlay;
import static gutizia.util.overlay.DebugOverlay.debugOverlay;
import static gutizia.util.combat.PlayerCombat.playerCombat;

public abstract class Script extends PollingScript<ClientContext> {
    protected static ArrayList<Task> tasks = new ArrayList<>();
    protected static ArrayList<Task> newTasks = new ArrayList<>();
    protected static ArrayList<Task> standardTasks = new ArrayList<>();

    protected EventDispatcher eventDispatcher;

    protected static boolean changingTasks = false;
    private static boolean stopping = false;
    private static File storageLocation;

    private static boolean breakOnActivate = true;

    public static long startTime;

    protected abstract void initStandardTasks();

    @Override
    public void start() {
        startTime = System.currentTimeMillis();
        storageLocation = getStorageDirectory();
        taskOverlay = new TaskOverlay(ctx);
        debugOverlay = new DebugOverlay(ctx);
        eventDispatcher = new EventDispatcher(ctx);
        playerCombat = new PlayerCombat(ctx);
        initStandardTasks();
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

    public static File getStorageLocation() {
        return storageLocation;
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
