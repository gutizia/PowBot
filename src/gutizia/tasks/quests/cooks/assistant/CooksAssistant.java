package gutizia.tasks.quests.cooks.assistant;

import gutizia.tasks.*;
import gutizia.util.Quest;
import gutizia.util.settings.QuestSettings;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;

public class CooksAssistant extends SuperTask {

    static int VARBIT = 29;
    static int COOK_ID = 4626;

    public CooksAssistant() {
        setTaskInfo(new TaskInfo(
                () -> QuestSettings.getCurrentQuest().equals(Quest.COOKS_ASSISTANT),
                () -> !QuestSettings.getCurrentQuest().equals(Quest.COOKS_ASSISTANT),
                "CooksAssistant",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new StartQuest());
        tasks.add(new GetContainers());
        tasks.add(new GetEgg(ctx));
        tasks.add(new GetMilk(ctx));
        tasks.add(new GetWheat(ctx));
        tasks.add(new MakeFlour(ctx));
        tasks.add(new FinishQuest(ctx));
        return tasks;
    }
}
