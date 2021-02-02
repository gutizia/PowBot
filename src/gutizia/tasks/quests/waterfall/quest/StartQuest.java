package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.tasks.bank.GetToABank;
import gutizia.tasks.bank.WithdrawItems;
import gutizia.util.Quest;
import gutizia.util.settings.QuestSettings;

import java.util.ArrayList;

import static gutizia.util.Inventory.inventory;

class StartQuest extends SuperTask {

    StartQuest() {
        setTaskInfo(new TaskInfo(
                () -> ctx.varpbits.varpbit(WaterfallQuest.VARBIT) == 0,
                () -> !QuestSettings.getCurrentQuest().equals(Quest.WATERFALL_QUEST) ||
                    ctx.varpbits.varpbit(WaterfallQuest.VARBIT) > 0,
                "StartQuest",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new GetToABank(() -> !inventory.getItems().containsAll(WaterfallQuest.startingItems.getIds())));
        tasks.add(new WithdrawItems(WaterfallQuest.startingItems, true));
        tasks.add(new GetToAlmera(() -> !WaterfallQuest.ALMERA_HOUSE.containsOrIntersects(ctx.players.local())));
        tasks.add(new TalkToAlmera());
        return tasks;
    }
}
