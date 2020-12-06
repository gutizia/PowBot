package gutizia.util.overlay;

import gutizia.util.constants.Widgets;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;

public class DebugOverlay extends ClientAccessor {
    public static DebugOverlay debugOverlay;

    private Rectangle box;
    private int x;

    public DebugOverlay(ClientContext ctx) {
        super(ctx);
        int boxWidth = 160;
        int boxHeight = 120;
        if (ctx.game.resizable()) {
            org.powerbot.script.rt4.Component component = ctx.widgets.component(Widgets.CHAT_BOX, 0);
            box = new Rectangle(10, component.screenPoint().y - (boxHeight + 10),
                    boxWidth, boxHeight
            );

        } else {
            box = new Rectangle();
        }
        x = box.x + 10;
    }

    public void draw(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 100));
        g.fill(box);
        g.setColor(Color.WHITE);
        g.drawString("mouse speed: " + ctx.input.speed(), x, box.y + 16);
        g.drawString("in motion: " + ctx.players.local().inMotion(), x, box.y + 32);
        g.drawString("health %: " + ctx.combat.healthPercent(), x, box.y + 48);
        g.drawString("orientation: " + ctx.players.local().orientation(), x, box.y + 64);
        g.drawString("interacting with: " + ctx.players.local().interacting().name(), x, box.y + 80);
        g.drawString("", x, box.y + 96);
        g.drawString("", x, box.y + 112);
    }
}
