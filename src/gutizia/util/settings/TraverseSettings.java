package gutizia.util.settings;

import gutizia.tasks.Task;
import gutizia.tasks.traverse.ClickNextTile;
import gutizia.tasks.traverse.SetRun;
import gutizia.util.PropertyUtil;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TraverseSettings {
    public final static String RUN_ENERGY_THRESHOLD_MAX = "run.threshold.max";
    public final static String RUN_ENERGY_THRESHOLD_MIN = "run.threshold.min";
    public final static String NEXT_TILE_THRESHOLD_MAX = "next.tile.threshold.max";
    public final static String NEXT_TILE_THRESHOLD_MIN = "next.tile.threshold.min";

    private static PropertyUtil propertyUtil = new PropertyUtil(System.getProperty("java.io.tmpdir") + "\\PowBot\\traverse",
            Stream.of(new String[][] {
                    {RUN_ENERGY_THRESHOLD_MAX, "80"},
                    {RUN_ENERGY_THRESHOLD_MIN, "40"},
                    {NEXT_TILE_THRESHOLD_MAX, "12"},
                    {NEXT_TILE_THRESHOLD_MIN, "4"},
            }).collect(Collectors.toMap(data -> data[0], data -> data[1])));

    public static ArrayList<Task> getTraversingTasks(ClientContext ctx) {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new SetRun(ctx, true));
        tasks.add(new SetRun(ctx, () -> ctx.movement.energyLevel() <= Integer.parseInt(propertyUtil.getProperty(RUN_ENERGY_THRESHOLD_MIN)), false));
        /*
        * check for each task and add them if turned on
        * */
        tasks.add(new ClickNextTile(ctx));
        return tasks;
    }

    public static PropertyUtil getPropertyUtil() {
        return propertyUtil;
    }
}
