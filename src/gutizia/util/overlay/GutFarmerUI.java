package gutizia.util.overlay;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class GutFarmerUI extends AbstractOverlay {
    private final static Logger LOGGER = Logger.getLogger("Overlay");

    // home = login screen
    public final static Point HOME_HERB_RUNS_DONE = new Point();
    public final static Point HOME_FRUIT_TREE_RUNS_DONE = new Point();
    public final static Point HOME_TREE_RUNS_DONE = new Point();
    public final static Point HOME_HOPS_RUNS_DONE = new Point();

    public final static Point TIME_ELAPSED = new Point(397, 370);
    public final static Point TOTAL_EXP_GAINED = new Point(395, 392);
    public final static Point TOTAL_MONEY_MADE = new Point(402, 413);

    public final static Point HERB_HERBS_FARMED = new Point();
    public final static Point HERB_ALLOTMENTS_FARMED = new Point();
    public final static Point HERB_FLOWERS_FARMED = new Point();

    public final static Point HOPS_HOPS_FARMED = new Point();

    public final static Point FT_TREES_PLANTED = new Point();
    public final static Point FT_CHECKED_HEALTH_OF = new Point();
    public final static Point FT_FRUITS_FARMED = new Point();

    public final static Point TREE_TREES_PLANTED = new Point();
    public final static Point TREE_CHECKED_HEALTH_OF = new Point();

    public GutFarmerUI(BufferedImage overlayImage) {
        super(overlayImage);
    }
}
