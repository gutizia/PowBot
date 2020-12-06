package gutizia.tasks;

import gutizia.scripts.Script;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.logging.Logger;

public class TaskChanger {
    public final static TaskChanger taskChanger = new TaskChanger();
    private final static Logger LOGGER = Logger.getLogger("taskChanger");

    private Deque<ArrayList<Task>> previousTasks = new ArrayDeque<>();

    public void setTasks(ArrayList<Task> tasks) {
        setTasks(tasks, true);
    }

    private void setTasks(ArrayList<Task> tasks, boolean saveTasks) {
        if (saveTasks) {
            savePreviousTasks();
        }
        Script.setTasks(tasks);
    }

    private void savePreviousTasks() {
        previousTasks.addLast(Script.getTasks());
    }

    public void setTasksStandard(ClientContext ctx) {
        Script.setStandardTasks();
    }

    public void setTasksPrevious(ClientContext ctx) {
        if (previousTasks != null) {
            LOGGER.info("setting tasks to previous");
            previousTasks.removeLast();
            if (previousTasks.isEmpty()) {
                setTasksStandard(ctx);
            } else {
                setTasks(previousTasks.getLast(), false);
            }
        } else {
            LOGGER.info("no previous tasks found, setting standard tasks");
            setTasksStandard(ctx);
        }
    }
}
