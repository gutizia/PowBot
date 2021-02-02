package gutizia.tasks.quests.cooks.assistant;

import gutizia.tasks.*;
import gutizia.util.InteractOptions;
import gutizia.util.Item;
import gutizia.util.constants.Interactives;
import gutizia.util.constants.Items;
import org.powerbot.script.Tile;

import static gutizia.util.Inventory.inventory;

import java.util.ArrayList;

class GetContainers extends SuperTask {

    GetContainers() {
        setTaskInfo(new TaskInfo(
                () -> !inventory.getItems().containsAll(Items.POT, Items.EMPTY_BUCKET) &&
                ctx.varpbits.varpbit(CooksAssistant.VARBIT) > 0,
                () -> inventory.getItems().containsAll(Items.POT, Items.EMPTY_BUCKET) &&
                        !ctx.npcs.select().id(CooksAssistant.COOK_ID).isEmpty(),
                "GetContainers",
                false
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new WebWalk(ctx, () -> ctx.npcs.select().id(CooksAssistant.COOK_ID).isEmpty(),
                new Tile(3208, 3213, 0)));
        tasks.add(new PickUpItem(new Item(Items.POT, "Pot"), new int[] {-16, 16, -136, -108, -16, 16}));
        tasks.add(new Interact(ctx, () -> inventory.getItems().contains(Items.POT) &&
                !ctx.npcs.select().id(CooksAssistant.COOK_ID).isEmpty(), Interactives.TRAP_DOOR,
                new InteractOptions("Climb-down", false, false, false, false, false),
                () -> new Tile(3208, 3213, 0).distanceTo(ctx.players.local()) > 1000));
        tasks.add(new PickUpItem(new Item(Items.EMPTY_BUCKET, "Bucket"), new int[] {-16, 16, -120, -96, -16, 16}));
        tasks.add(new Interact(ctx, () -> inventory.getItems().containsAll(Items.POT, Items.EMPTY_BUCKET) &&
                ctx.npcs.select().id(CooksAssistant.COOK_ID).isEmpty(), Interactives.LADDER,
                new InteractOptions("Climb-Up", false, false, false, false, false),
                () -> !ctx.npcs.select().id(CooksAssistant.COOK_ID).isEmpty()));
        return tasks;
    }
}
