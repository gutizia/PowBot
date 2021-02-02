package gutizia.tasks.quests.restless.ghost;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;

import java.util.ArrayList;

class Amulet extends SuperTask {

    Amulet() {
        setTaskInfo(new TaskInfo(
                () -> false,
                () -> false,
                "Amulet",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        return tasks;
    }
}

