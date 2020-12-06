package gutizia.util.settings;

import gutizia.tasks.Task;
import gutizia.tasks.traverse.ClickNextTile;
import gutizia.tasks.traverse.SetRun;
import gutizia.util.PropertyUtil;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;

public class TraverseSettings {
    private static PropertyUtil propertyUtil = new PropertyUtil(System.getProperty("java.io.tmpdir") + "\\PowBot\\settings");
    public final static String RUN_ENERGY_THRESHOLD_MAX = "run.threshold.max";
    public final static String RUN_ENERGY_THRESHOLD_MIN = "run.threshold.min";
    public final static String NEXT_TILE_THRESHOLD_MAX = "next.tile.threshold.max";
    public final static String NEXT_TILE_THRESHOLD_MIN = "next.tile.threshold.min";

    public static ArrayList<Task> getTraversingTasks(ClientContext ctx) {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new SetRun(ctx, () -> ctx.movement.energyLevel() >= Integer.parseInt(propertyUtil.getProperty(RUN_ENERGY_THRESHOLD_MIN)), true));
        tasks.add(new SetRun(ctx, () -> ctx.movement.energyLevel() >= Integer.parseInt(propertyUtil.getProperty(RUN_ENERGY_THRESHOLD_MAX)), false));
        /*
        * check for each task and add them if turned on
        * */
        tasks.add(new ClickNextTile(ctx));
        return tasks;
    }
}
