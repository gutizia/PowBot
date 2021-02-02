package gutizia.tasks.quests.waterfall.quest;

import gutizia.tasks.Task;
import gutizia.util.Interact;
import gutizia.util.constants.Items;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;

import static gutizia.util.overlay.TaskOverlay.taskOverlay;

public class ClimbDownDeadTree extends Task {

    ClimbDownDeadTree(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        taskOverlay.setActiveTask("climb down tree");

        Item rope = ctx.inventory.select().id(Items.ROPE).poll();
        GameObject tree = ctx.objects.select(10).name("Dead Tree").nearest().poll();


        Interact.use(ctx, rope);
        tree.interact("Use",tree.name());

        Condition.wait(() -> ctx.players.local().tile().equals(new Tile(2511,3463,0)), 600, 5);
    }

    @Override
    public boolean activate() {
        return WaterfallQuest.LOWER_RIVER_ISLAND.containsOrIntersects(ctx.players.local()); // on small island with dead tree
    }
}
