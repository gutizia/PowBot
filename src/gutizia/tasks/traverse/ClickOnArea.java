package gutizia.tasks.traverse;

import gutizia.tasks.Activatable;
import gutizia.tasks.Task;
import gutizia.util.Interact;
import org.powerbot.script.Area;
import org.powerbot.script.rt4.ClientContext;

public class ClickOnArea extends Task {
    private Activatable activatable;
    private Area area;

    public ClickOnArea(ClientContext ctx, Activatable activatable, Area area) {
        super(ctx);
        this.activatable = activatable;
        this.area = area;
    }

    @Override
    public void execute() {
        Interact.clickOnTile(ctx, area.getRandomTile());
    }

    @Override
    public boolean activate() {
        return activatable.activate();
    }
}
