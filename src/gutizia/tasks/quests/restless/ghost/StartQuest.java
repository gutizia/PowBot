package gutizia.tasks.quests.restless.ghost;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import org.powerbot.script.rt4.Npc;

import java.util.ArrayList;

class StartQuest extends SuperTask {

    StartQuest() {
        setTaskInfo(new TaskInfo(
                () -> ctx.varpbits.varpbit(RestlessGhost.VARBIT) == 0,
                () -> ctx.varpbits.varpbit(RestlessGhost.VARBIT) > 0,
                "StartQuest",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        //tasks.add();

        // get to feather aereck
        // talk to him
        // leave house
        return tasks;
    }
}
