package gutizia.tasks.combat.chicken;

import gutizia.tasks.Interact;
import gutizia.tasks.SuperTask;
import gutizia.tasks.Task;
import gutizia.tasks.TaskInfo;
import gutizia.util.InteractOptions;
import gutizia.util.constants.Areas;
import gutizia.util.constants.Interactives;
import gutizia.util.constants.Tiles;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;

public class GetToChickens extends SuperTask {

    public GetToChickens(ClientContext ctx) {
        super(ctx);
        setTaskInfo(new TaskInfo(
                () -> !Areas.LUMBRIDGE_CHICKEN_PEN.contains(ctx.players.local()),
                () -> Areas.LUMBRIDGE_CHICKEN_PEN.contains(ctx.players.local()),
                "GetToChickens",
                true
        ));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Interact(ctx, () -> !Areas.LUMBRIDGE_CHICKEN_PEN.contains(ctx.players.local()) &&
                Tiles.LUMBRIDGE_CHICKEN_PEN_GATE.distanceTo(ctx.players.local()) > Interactives.LUMBRIDGE_CHICKEN_PEN_GATE.getInteractRange(), Interactives.LUMBRIDGE_CHICKEN_PEN_GATE,
                new InteractOptions("Open", true, false, false, false, false)));

        //tasks.add(); TODO add task to click on minimap towards chickens' most populated area
        return tasks;
    }
}
