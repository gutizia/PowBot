package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;

import java.util.ArrayList;

class BookOnBaxtorian extends SuperTask {

    BookOnBaxtorian() {
        setTaskInfo(new TaskInfo(
                () -> ctx.varpbits.varpbit(WaterfallQuest.VARBIT) == 2,
                () -> ctx.varpbits.varpbit(WaterfallQuest.VARBIT) > 2,
                "BookOnBaxtorian",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new SwimDownRiver());
        tasks.add(new GetBook());
        tasks.add(new ReadBook());
        return tasks;
    }
}
