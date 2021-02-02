package gutizia.tasks.quests.cooks.assistant;

import gutizia.tasks.Activatable;
import gutizia.tasks.Task;
import gutizia.util.managers.ChatManager;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.managers.ChatManager.chatManager;
import static gutizia.util.overlay.TaskOverlay.taskOverlay;

public class TalkToCook extends Task {

    private Activatable activatable;


    public TalkToCook(ClientContext ctx, Activatable activatable) {
        super(ctx);
        this.activatable = activatable;
    }

    @Override
    public void execute() {
        taskOverlay.setActiveTask("StartQuest");
        chatManager.startConversation("Cook");
        chatManager.finishDialog();
        chatManager.chooseChatOption(1);
        chatManager.finishDialog();
        chatManager.chooseChatOption(1);
        chatManager.finishDialog();
    }

    @Override
    public boolean activate() {
        return activatable.activate();
    }
}
