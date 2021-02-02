package gutizia.tasks.quests.cooks.assistant;

import gutizia.tasks.Task;
import gutizia.util.managers.ChatManager;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.managers.ChatManager.chatManager;

class HandInItems extends Task {

    private Tile tile = new Tile(3208, 3213, 0);

    HandInItems(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        chatManager.startConversation("Cook");
        chatManager.finishDialog();
    }

    @Override
    public boolean activate() {
        return tile.matrix(ctx).reachable() && tile.distanceTo(ctx.players.local()) <= 8;
    }
}
