package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;

import java.util.ArrayList;

class Hudon extends SuperTask {

    Hudon() {
        setTaskInfo(new TaskInfo(
                () -> ctx.varpbits.varpbit(WaterfallQuest.VARBIT) == 1,
                () -> ctx.varpbits.varpbit(WaterfallQuest.VARBIT) > 1,
                "Hudon",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new BoardRaft(() -> !WaterfallQuest.UPPER_RIVER_ISLAND.containsOrIntersects(ctx.players.local())));
        tasks.add(new TalkToHudon());
        return tasks;
    }
}
