package gutizia.tasks.quests.cooks.assistant;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.tasks.WebWalk;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.Inventory.inventory;

import java.util.ArrayList;

class FinishQuest extends SuperTask {

    private Tile tile = new Tile(3208, 3213, 0);

    FinishQuest(ClientContext ctx) {
        super(ctx);
        setTaskInfo(new TaskInfo(
                () -> inventory.getItems().containsAll(gutizia.util.constants.Items.POT_OF_FLOUR,
                        gutizia.util.constants.Items.EGG, gutizia.util.constants.Items.BUCKET_OF_MILK),
                () -> ctx.varpbits.varpbit(CooksAssistant.VARBIT) == 2,
                "FinishQuest",
                false
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new WebWalk(ctx, () -> !tile.matrix(ctx).reachable() || tile.distanceTo(ctx.players.local()) > 8 , tile));
        tasks.add(new HandInItems(ctx));
        return tasks;
    }
}
