package gutizia.tasks.quests.oldRestlessGhost;

import gutizia.util.constants.Items;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Equipment;

import static gutizia.util.managers.ChatManager.chatManager;

public class TalkToGhost extends OldRestlessGhost {

    TalkToGhost(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        //overlay.changeStatus("talking to ghost");
        chatManager.startConversation("Restless ghost");
        chatManager.finishDialog();
        chatManager.chooseChatOption(1);
        chatManager.finishDialog();
    }

    @Override
    public boolean activate() {
        return coffinRoom.containsOrIntersects(ctx.players.local()) && // in coffin room
                !ctx.npcs.select().name("Restless ghost").isEmpty() && // restless ghost is there
                ctx.equipment.itemAt(Equipment.Slot.NECK).id() == Items.GHOSTSPEAK_AMULET && // you have ghostspeak amulet on
                ctx.varpbits.varpbit(107) == 2; // correct state of quest
    }
}
