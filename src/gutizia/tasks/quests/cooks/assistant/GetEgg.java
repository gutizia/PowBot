package gutizia.tasks.quests.cooks.assistant;

import gutizia.tasks.*;
import gutizia.tasks.combat.chicken.GetToChickens;
import gutizia.util.Item;
import gutizia.util.constants.Items;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;

class GetEgg extends SuperTask {

    private Tile tile = new Tile(3230, 3298, 0);

    GetEgg(ClientContext ctx) {
        super(ctx);
        setTaskInfo(new TaskInfo(
                () -> ctx.inventory.select().id(Items.EGG).isEmpty() && !ctx.inventory.isFull(),
                () -> !ctx.inventory.select().id(Items.EGG).isEmpty(),
                "GetEgg",
                false
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new GetToChickens());
        tasks.add(new PickUpItem(new Item(Items.EGG)));
        return tasks;
    }
}
