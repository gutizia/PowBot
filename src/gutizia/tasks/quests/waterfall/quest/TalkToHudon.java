package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;

import static gutizia.util.overlay.TaskOverlay.taskOverlay;
import static gutizia.util.managers.ChatManager.chatManager;

public class TalkToHudon extends Task {

    @Override
    public void execute() {
        taskOverlay.setActiveTask("talking to Hudon");
        chatManager.startConversation("Hudon");
        chatManager.finishDialog();
    }

    @Override
    public boolean activate() {
        return ctx.varpbits.varpbit(WaterfallQuest.VARBIT) == 1 && // correct stage of quest
                WaterfallQuest.UPPER_RIVER_ISLAND.containsOrIntersects(ctx.players.local()); // within river island
    }
}
