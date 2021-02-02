package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;

import static gutizia.util.overlay.TaskOverlay.taskOverlay;
import static gutizia.util.managers.ChatManager.chatManager;

class TalkToAlmera extends Task {

    @Override
    public void execute() {
        taskOverlay.setActiveTask("talking to Almera");
        chatManager.startConversation("Almera");
        chatManager.finishDialog();
        chatManager.chooseChatOption(1);
        chatManager.finishDialog();
    }

    @Override
    public boolean activate() {
        return ctx.varpbits.varpbit(WaterfallQuest.VARBIT) == 0 &&
                WaterfallQuest.ALMERA_HOUSE.containsOrIntersects(ctx.players.local());
    }
}
