package gutizia.tasks.quests.cooks.assistant;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.tasks.WebWalk;
import org.powerbot.script.Tile;

import java.util.ArrayList;

class StartQuest extends SuperTask {

    StartQuest() {
        setTaskInfo(new TaskInfo(
                () -> ctx.varpbits.varpbit(CooksAssistant.VARBIT) == 0,
                () -> ctx.varpbits.varpbit(CooksAssistant.VARBIT) != 0,
                "Start Quest",
                true
        ));
    }


    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new WebWalk(ctx, () -> ctx.npcs.select().id(CooksAssistant.COOK_ID).isEmpty(),
                new Tile(3208, 3213, 0)));
        tasks.add(new TalkToCook(ctx, () -> !ctx.npcs.select().id(CooksAssistant.COOK_ID).isEmpty()));
        return tasks;
    }
}

