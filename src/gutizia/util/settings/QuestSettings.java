package gutizia.util.settings;

import gutizia.scripts.Script;
import gutizia.tasks.Task;
import gutizia.tasks.quests.cooks.assistant.CooksAssistant;
import gutizia.tasks.quests.waterfall.quest.WaterfallQuest;
import gutizia.util.Quest;

import java.util.ArrayList;

public class QuestSettings {

    private static Quest quest = Quest.COOKS_ASSISTANT;

    public static Quest getCurrentQuest() {
        if (quest.equals(Quest.NONE)) {
            Script.stopScript("no quest to do...");
        }
        return quest;
    }

    public static ArrayList<Task> getQuestTasks() {
        ItemsSettings.setStopScriptIfMissingItems(true);
        ArrayList<Task> tasks = new ArrayList<>();
        // determine quest
        switch (quest) {
            case COOKS_ASSISTANT:
                tasks.add(new CooksAssistant());
                return tasks;

            case WATERFALL_QUEST:
                tasks.add(new WaterfallQuest());
                return tasks;
        }
        return null;
    }
}
