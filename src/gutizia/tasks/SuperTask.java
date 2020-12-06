package gutizia.tasks;

import gutizia.scripts.Script;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;

import static gutizia.tasks.TaskChanger.taskChanger;

public abstract class SuperTask extends Task implements MacroTask {

    private TaskInfo taskInfo;
    private boolean finishAtEnd = false;

    private Task finish = new Task(ctx) {
        @Override
        public void execute() {
            LOGGER.info("finishing macro task: " + taskInfo.name);
            taskChanger.setTasksPrevious(ctx);
            taskInfo.finish();
        }

        @Override
        public boolean activate() {
            return taskInfo.finish.activate();
        }
    };

    public SuperTask(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        taskInfo.start();
        LOGGER.info("starting macro task: " + taskInfo.name);
        ArrayList<Task> tasks = getTasks();
        if (finishAtEnd) {
            tasks.add(finish);
        } else {
            tasks.add(0, finish);
        }
        taskChanger.setTasks(tasks);
        Script.setBreakOnActivate(taskInfo.breakOnActivate);
    }

    @Override
    public boolean activate() {
        return taskInfo.start.activate();
    }

    protected void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    protected void setFinishAtEnd() {
        finishAtEnd = true;
    }
}
