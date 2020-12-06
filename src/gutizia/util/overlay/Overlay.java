package gutizia.util.overlay;

import java.awt.*;

public abstract class Overlay {
    private Rectangle box;
    private int startX;

    public Overlay() {}

    public Overlay(Rectangle box) {
        this.box = box;
        this.startX = box.x + 10;
    }

    public void draw(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 100));
        g.fill(box);
        g.setColor(Color.WHITE);
    }

    protected int getStartX() {
        return startX;
    }

    protected Rectangle getBox() {
        return box;
    }
}
