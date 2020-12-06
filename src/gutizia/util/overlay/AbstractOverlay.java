package gutizia.util.overlay;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AbstractOverlay {

    private BufferedImage overlayImage;

    AbstractOverlay(BufferedImage overlayImage) {
        this.overlayImage = overlayImage;
    }

    AbstractOverlay() {
    }

    public void drawImage(Graphics2D g) {
        g.drawImage(overlayImage,0,338,null);
    }
}
