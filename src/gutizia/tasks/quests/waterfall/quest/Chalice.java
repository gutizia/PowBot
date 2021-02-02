package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;

import java.util.ArrayList;

class Chalice extends SuperTask {

    Chalice() {
        setTaskInfo(new TaskInfo(
                () -> WaterfallQuest.FIRST_CHALICE_AREA.containsOrIntersects(ctx.players.local()) ||
                        WaterfallQuest.SECOND_CHALICE_AREA.containsOrIntersects(ctx.players.local()),
                () -> ctx.varpbits.varpbit(WaterfallQuest.VARBIT) >= 10,
                "Chalice",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new UseRunesOnPillars());
        tasks.add(new UseAmuletOnStatue());
        tasks.add(new UseUrnOnChalice());
        return tasks;
    }
}
