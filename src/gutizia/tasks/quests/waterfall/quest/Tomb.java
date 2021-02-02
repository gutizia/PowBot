package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.util.constants.Items;

import java.util.ArrayList;

import static gutizia.util.Inventory.inventory;

class Tomb extends SuperTask {

    Tomb() {
        setTaskInfo(new TaskInfo(
                () -> inventory.getItems().contains(Items.PEBBLE) &&
                    !inventory.getItems().containsAll(Items.GLARIALS_AMULET, Items.GLARIALS_URN) &&
                ctx.varpbits.varpbit(WaterfallQuest.VARBIT) < 4,
                () -> inventory.getItems().containsAll(Items.GLARIALS_AMULET, Items.GLARIALS_URN) &&
                !WaterfallQuest.GLARIALS_TOMB.containsOrIntersects(ctx.players.local()),
                "Tomb",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new GetOutsideTomb());
        tasks.add(new OpenTomb());
        tasks.add(new GetAmulet());
        tasks.add(new GetUrn());
        return tasks;
    }
}
