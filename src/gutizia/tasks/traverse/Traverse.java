package gutizia.tasks.traverse;

import gutizia.tasks.Activatable;
import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.util.TraverseUtil;
import gutizia.util.settings.TraverseSettings;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;

public class Traverse extends SuperTask {

    public Traverse(Activatable activatable, Tile[] path, boolean reverse) {
        if (reverse) {
            Tile[] reversePath = new Tile[path.length];
            int j = 0;
            for (int i = path.length - 1; i >= 0; i--) {
                reversePath[j++] = path[i];
            }
            path = reversePath;
        }
        TraverseUtil.createNewPath(ctx, path);
        Tile lastTile = path[path.length - 1];
        setTaskInfo(new TaskInfo(
                () -> activatable.activate() &&
                        lastTile.distanceTo(ctx.players.local()) > TraverseUtil.getDestinationLimit(),
                () -> lastTile.distanceTo(ctx.players.local()) < TraverseUtil.getDestinationLimit(),
                "Traverse",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(TraverseSettings.getTraversingTasks(ctx));
    }
}
