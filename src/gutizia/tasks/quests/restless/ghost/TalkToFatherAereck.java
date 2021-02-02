package gutizia.tasks.quests.restless.ghost;

import gutizia.tasks.Task;

import static gutizia.util.overlay.TaskOverlay.taskOverlay;
import static gutizia.util.managers.ChatManager.chatManager;

class TalkToFatherAereck extends Task {

    @Override
    public void execute() {
        taskOverlay.setActiveTask("Talk to Aereck");
        chatManager.startConversation("Father Aereck");
        chatManager.finishDialog();
        chatManager.chooseChatOption(3);
        chatManager.finishDialog();
        chatManager.chooseChatOption(1);
        chatManager.finishDialog();
    }

    @Override
    public boolean activate() {
        return RestlessGhost.CHURCH.containsOrIntersects(ctx.players.local()) && // is inside church
                ctx.varpbits.varpbit(107) == 0; // have not started quest
    }
}
