package gutizia.util.managers;

import gutizia.tasks.Interact;
import gutizia.tasks.Task;
import gutizia.util.InteractOptions;
import gutizia.util.constants.Components;
import gutizia.util.constants.Widgets;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Component;

import java.util.concurrent.Callable;

public class ChatManager extends ClientAccessor {

    public final static Component GAME_MESSAGES = ClientContext.ctx().widgets.component(Widgets.CHAT_BOX,Components.FOLDER_CHAT_BOX_GAME_MESSAGES);
    public final static ChatManager chatManager = new ChatManager(ClientContext.ctx());

    private ChatManager(ClientContext ctx) {
        super(ctx);
    }

    public void finishDialog() {
        while (chatActive()) {
            ctx.input.send(" ");

            Condition.sleep(Random.nextInt(250,500));
        }
    }

    public void startConversation(String name) {
        Task task =  new Interact(ctx, () -> true, new gutizia.util.Npc(8, new int[] {}, name),
                new InteractOptions("Talk-to", false, false, false, false, false),
                this::chatActive);

        if (task.activate()) {
            task.execute();
        }
    }

    public String getLastGameMessage() {
        return GAME_MESSAGES.component(0).text();
    }

    public void chooseChatOption(int option) {
        if (!ctx.widgets.widget(Widgets.CHAT_BOX_CHAT_OPTIONS).valid()) {
            System.err.println("you are not inside the correct widget to choose chat option");
            return;
        }
        Condition.sleep(Random.nextInt(500,900));
        ctx.input.send(Integer.toString(option));
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return ctx.widgets.component(Widgets.CHAT_BOX_CONVERSATION_PLAYER,Components.CHAT_BOX_CONVERSATION_CLICK_TO_CONTINUE).visible();
            }
        },100,30);
    }

    public boolean chatActive() {
        return ctx.widgets.widget(Widgets.CHAT_BOX_CONVERSATION_NPC).valid() ||
                ctx.widgets.widget(Widgets.CHAT_BOX_CONVERSATION_PLAYER).valid() ||
                ctx.widgets.widget(Widgets.CHAT_BOX_ANNOUNCEMENT).valid() ||
                ctx.widgets.widget(Widgets.CHAT_BOX_ITEMS).valid() ||
                ctx.widgets.widget(Widgets.CHAT_BOX_QUEST_ANNOUNCEMENT).valid() ||
                ctx.widgets.widget(Widgets.CHAT_BOX_HANDING_ITEMS).valid();
    }
}