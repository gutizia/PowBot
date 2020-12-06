package gutizia.util.managers;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class WorldManager extends ClientAccessor {
    private final static Logger LOGGER = Logger.getLogger("WorldManager");

    public static WorldManager worldManager;

    private final Widget LOGOUT = ctx.widgets.widget(182);
    private final Widget WORLD_HOPPER = ctx.widgets.widget(69);

    private int currentWorld;

    public WorldManager(ClientContext ctx) {
        super(ctx);
    }

    public void hopToWorld(int world) {
        LOGGER.info("trying to join world: " + world);
        openWorldsList();
        ctx.worlds.select().id(world).poll().hop();

        Condition.wait(() -> ctx.widgets.component(WORLD_HOPPER.id(),2).text().split("- ")[1].contains(Integer.toString(world)),1000,5);
        setCurrentWorld();
        ctx.game.tab(Game.Tab.INVENTORY);
    }

    public void setCurrentWorld() {
        openWorldsList();
        String[] string = ctx.widgets.component(WORLD_HOPPER.id(),2).text().split("- ");

        currentWorld = Integer.parseInt(string[string.length - 1]);

        LOGGER.info("current world = " + currentWorld);
        ctx.game.tab(Game.Tab.INVENTORY);

        Condition.wait(() -> !ctx.widgets.component(WORLD_HOPPER.id(),0).visible(),1000,3);
    }

    public void hopToRandomMembersWorld() {
        openWorldsList();
        World world = ctx.worlds.select().types(World.Type.MEMBERS).joinable().shuffle().poll();
        LOGGER.info("hopping to random world: " + world.id());
        world.hop();
        Condition.wait(() -> ctx.widgets.component(WORLD_HOPPER.id(),2).text().split("- ")[1].contains(Integer.toString(world.id())),1000,5);
        setCurrentWorld();
        ctx.game.tab(Game.Tab.INVENTORY);
    }

    private void openWorldsList() {
        if (!ctx.components.select(WORLD_HOPPER).textContains("current world").isEmpty()) {
            return;
        }

        if (ctx.components.select(LOGOUT).textContains("world switcher").isEmpty()) {
            ctx.game.tab(Game.Tab.LOGOUT);
        }

        if (!ctx.components.select(LOGOUT).textContains("world switcher").isEmpty()) {
            ctx.worlds.open();
            Condition.wait(() -> !ctx.worlds.select().isEmpty(),1000,5);
        }
    }

    public int getCurrentWorld() {
        if (currentWorld == 0) {
            setCurrentWorld();
        }
        return currentWorld;
    }
}
