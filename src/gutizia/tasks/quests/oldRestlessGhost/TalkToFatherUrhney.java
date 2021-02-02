package gutizia.tasks.quests.oldRestlessGhost;

import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.managers.ChatManager.chatManager;


public class TalkToFatherUrhney extends OldRestlessGhost {

    TalkToFatherUrhney(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        //overlay.changeStatus("talking to father Urhney");
        chatManager.startConversation("Father Urhney");
        chatManager.finishDialog();
        chatManager.chooseChatOption(2);
        chatManager.finishDialog();
        chatManager.chooseChatOption(1);
        chatManager.finishDialog();
    }

    @Override
    public boolean activate() {
        return ctx.varpbits.varpbit(107) == 1 && // correct state of quest
                shack.containsOrIntersects(ctx.players.local()); // inside shack
    }
}
