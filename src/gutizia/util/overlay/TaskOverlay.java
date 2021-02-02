package gutizia.util.overlay;

import gutizia.tasks.TaskInfo;
import gutizia.util.constants.Widgets;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;

public class TaskOverlay extends ClientAccessor {
    public final static TaskOverlay taskOverlay = new TaskOverlay(org.powerbot.script.ClientContext.ctx());

    private Rectangle box;
    private Font fontMacroTask;
    private Font fontMicroTask;
    private Font fontDefault = new Font(Font.DIALOG, Font.PLAIN, 12);
    private int x;
    private String activeTask = "";
    private long activeTaskStartTime = 0;

    private TaskOverlay(ClientContext ctx) {
        super(ctx);
        int boxWidth = 160;
        int boxHeight = 60;
        if (ctx.game.resizable()) {
            org.powerbot.script.rt4.Component component = ctx.widgets.component(Widgets.CHAT_BOX, 0);
            box = new Rectangle(component.screenPoint().x + component.width(),
                    component.screenPoint().y,
                    boxWidth, boxHeight
            );

        } else {
            box = new Rectangle();
        }
        x = box.x + 10;
        fontMacroTask = new Font(null, Font.BOLD, 18);
        fontMicroTask = new Font(null, Font.BOLD, 14);
    }

    public void draw(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 100));
        g.fill(box);

        drawMacroTaskName(g);
        drawMicroTaskName(g);
    }

    private void drawMacroTaskName(Graphics2D g) {
        g.setFont(fontMacroTask);
        g.setColor(Color.WHITE);
        g.drawString(TaskInfo.getActiveTaskName(), x, box.y + 20);
        g.setFont(fontDefault);
        Drawer.drawTimer(g, new Point(x, box.y + 36), System.currentTimeMillis() - TaskInfo.getStartTime());
    }

    private void drawMicroTaskName(Graphics2D g) {
        g.setFont(fontMicroTask);
        g.setColor(Color.WHITE);
        g.drawString(activeTask, x, box.y + 52);
        g.setFont(fontDefault);
        Drawer.drawTimer(g, new Point(x, box.y + 70), System.currentTimeMillis() - activeTaskStartTime);
    }

    public void setActiveTask(String activeTask) {
        this.activeTask = activeTask;
        this.activeTaskStartTime = System.currentTimeMillis();
    }

    public String getActiveTask() {
        return activeTask;
    }
}
