package gutizia.util.overlay;

import org.powerbot.script.Area;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.TileMatrix;

import java.awt.*;
import java.util.ArrayList;

public class Drawer {

    public enum TileColor {
        FLICK(new Color(255,255,0), new Color(255,255,0,200)),
        SAFE_SPOT(new Color(0,255,0), new Color(0, 200, 0, 150)),
        CORRIDOR(new Color(0,0,255), new Color(0,0,200,150)),
        MELEE(new Color(255,0,0), new Color(255,0,0,150)),
        RANGE(new Color(0,205,0), new Color(0,155,0,150)),
        PROJECTILE(new Color(0,0,0), new Color(0,0,0,50)),
        WHITE(new Color(255,255,255), new Color(255,255,255,100)),
        MAGENTA(Color.MAGENTA, Color.MAGENTA.darker()),
        YELLOW(Color.YELLOW, Color.YELLOW.darker()),
        CYAN(Color.CYAN, Color.CYAN.darker());

        private Color border;
        private Color fill;

        TileColor(Color border, Color fill) {
            this.border = border;
            this.fill = fill;
        }

        public Color getBorder() {
            return border;
        }

        public Color getFill() {
            return fill;
        }
    }

    public static void drawArea(ClientContext ctx, Graphics2D g, Area area, TileColor tileColor) {
        if (area == null) {
            return;
        }
        for (Tile t : area.tiles()) {
            drawTile(g, t.matrix(ctx), tileColor);
        }
    }

    public static void drawTile(Graphics2D g, TileMatrix matrix, TileColor tileColor) {
        g.setColor(tileColor.getBorder());
        g.drawPolygon(matrix.bounds());
        g.setColor(tileColor.getFill());
        g.fillPolygon(matrix.bounds());
    }

    public static void drawTileBorder(Graphics2D g, TileMatrix matrix, TileColor tileColor) {
        g.setColor(tileColor.getBorder());
        g.drawPolygon(matrix.bounds());
    }

    public static void drawTimer(Graphics2D g, Point point, long milliseconds) {
        long seconds = (milliseconds / 1000) % 60;
        long minutes = (milliseconds / 60000) % 60;
        long hours = (milliseconds / (3600000)) % 24;
        long days = (milliseconds / (86400000) % 7);

        g.setColor(new Color(255, 255, 255));
        if (days > 0) {
            g.drawString(String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds), point.x, point.y);
        } else {
            g.drawString(String.format("%02d:%02d:%02d", hours, minutes, seconds), point.x, point.y);
        }
    }

    public static void drawString(Graphics2D g, Point point, String text) {
        g.drawString(text, point.x, point.y);
    }

    public static void drawStringList(Graphics2D g, Point startPoint, ArrayList<String> list) {
        int i = 0;
        for (String s : list) {
            drawString(g, new Point(startPoint.x, startPoint.y + (i++ * 20)), s);
        }
    }
}
