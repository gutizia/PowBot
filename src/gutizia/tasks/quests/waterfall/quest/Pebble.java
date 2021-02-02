package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.util.constants.Items;

import java.util.ArrayList;

import static gutizia.util.Inventory.inventory;

class Pebble extends SuperTask {

    Pebble() {
        setTaskInfo(new TaskInfo(
                () -> ctx.varpbits.varpbit(WaterfallQuest.VARBIT) == 3,
                () -> ctx.varpbits.varpbit(WaterfallQuest.VARBIT) > 3 ||
                        inventory.getItems().contains(Items.PEBBLE),
                "Pebble",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new GetToMazeDungeon());
        tasks.add(new GetPebble());
        return tasks;
    }
}
