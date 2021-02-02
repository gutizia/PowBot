package gutizia.tasks.quests.cooks.assistant;

import gutizia.tasks.*;
import gutizia.util.InteractOptions;
import gutizia.util.constants.Interactives;
import gutizia.util.constants.Items;
import gutizia.util.managers.ChatManager;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;

import static gutizia.util.Inventory.inventory;

public class MakeFlour extends SuperTask {

    private Tile millTile = new Tile(3165, 3307, 0);
    private Tile topOfMillTile = new Tile(3165, 3307, 2);

    public MakeFlour(ClientContext ctx) {
        super(ctx);
        setTaskInfo(new TaskInfo(
                () -> inventory.getItems().contains(Items.WHEAT) && inventory.getItems().contains(Items.POT) && !inventory.getItems().contains(Items.POT_OF_FLOUR),
                () -> inventory.getItems().contains(Items.POT_OF_FLOUR),
                "MakeFlour",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new WebWalk(ctx, () -> !new Tile(millTile.x(), millTile.y(), ctx.players.local().tile().floor()).matrix(ctx).reachable(), millTile));
        tasks.add(new Interact(ctx, () -> !topOfMillTile.matrix(ctx).reachable() &&
                ctx.varpbits.varpbit(203) == 0 && !inventory.getItems().contains(Items.POT_OF_FLOUR), Interactives.LADDER,
                new InteractOptions("Climb-up", false, false, false,false, false)));

        tasks.add(new Interact(ctx, () -> inventory.getItems().contains(Items.WHEAT) &&
                        !ChatManager.GAME_MESSAGES.component(0).text().contains("There is already grain"), Interactives.HOPPER,
                new InteractOptions("Fill", false, false, false, false, false)));

        tasks.add(new Interact(ctx, () -> ctx.varpbits.varpbit(203) == 0 &&
                !ChatManager.GAME_MESSAGES.component(0).text().contains("You operate the empty hopper"), Interactives.HOPPER_CONTROLS,
                new InteractOptions("Operate", false, false, false, false, false)));

        tasks.add(new Interact(ctx, () -> !millTile.matrix(ctx).reachable() &&
                ctx.varpbits.varpbit(203) > 0, Interactives.LADDER,
                new InteractOptions("Climb-down", false, false, false,false, false)));

        tasks.add(new Interact(ctx, () -> millTile.matrix(ctx).reachable() && ctx.varpbits.varpbit(203) > 0, Interactives.FLOUR_BIN,
                new InteractOptions("Empty", false, false, false,false, false)));
        return tasks;
    }
}
