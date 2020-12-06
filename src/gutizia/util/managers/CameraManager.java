package gutizia.util.managers;

import org.powerbot.script.Condition;
import org.powerbot.script.Locatable;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

public class CameraManager extends ClientAccessor {
    private final static Logger LOGGER = Logger.getLogger("CameraManager");

    public enum Zoom {
        //max X = 697
        //min X = 601
        NONE(new int[] {0,0}),
        MID_HIGH(new int[] {617,632}),
        MID(new int[] {636,654}),
        MID_LOW(new int[] {660,675});

        private int[] range;

        Zoom(int[] range) {
            this.range = range;
        }

        public int[] getRange() {
            return range;
        }

        public int getMidPoint() {
            return (range[0] + range[1]) / 2;
        }

    } // none is only to be used at start

    private Zoom currentZoom = Zoom.NONE;
    private final int WIDGET_OPTIONS = 261;

    private final int COMPONENT_CAMERA_ZOOM_POINT = 15;
    private final Point MID_HIGH_ZOOM_MIN = new Point(621,265);

    private final Point MID_HIGH_ZOOM_MAX = new Point(629,280);
    private final Point MID_ZOOM_MARK_MIN = new Point(642,265);

    private final Point MID_ZOOM_MARK_MAX = new Point(652,280);
    private final Point MID_LOW_ZOOM_MIN = new Point(669,265);

    private final Point MID_LOW_ZOOM_MAX = new Point(678,280);

    private static int DIV = 10;
    private static int BIG_BOX_START = 2;
    private static int BIG_BOX_END = 9;
    private static int SMALL_BOX_START = 4;
    private static int SMALL_BOX_END = 7;

    private final static Point SCREEN = new Point(516,337);

    public final static Point BIG_BOX_MIN = new Point(SCREEN.x / DIV * BIG_BOX_START,SCREEN.y / DIV * BIG_BOX_START);
    public final static Point BIG_BOX_MAX = new Point(SCREEN.x / DIV * BIG_BOX_END,SCREEN.y / DIV * BIG_BOX_END);
    public final static Point SMALL_BOX_MIN = new Point(SCREEN.x / DIV * SMALL_BOX_START,SCREEN.y / DIV * SMALL_BOX_START);
    public final static Point SMALL_BOX_MAX = new Point(SCREEN.x / DIV * SMALL_BOX_END,SCREEN.y / DIV * SMALL_BOX_END);

    public enum Direction {NORTH,EAST,SOUTH,WEST}

    public CameraManager(ClientContext ctx) {
        super(ctx);
    }

    // TODO replace enum with just coordinates of camera pointer widget
    public void turnCamera(int yawMin, int yawMax, int pitchMin, int pitchMax) {

        if (yawMin < yawMax) {
            ctx.camera.angle(Random.nextInt(yawMin,yawMax));

        } else {

            int yawMinDelta = 360 - yawMin;
            int rngMax = 360 - yawMin + yawMax;
            int randomNumber = Random.nextInt(0,rngMax);

            if (randomNumber > yawMinDelta) {
                ctx.camera.angle(randomNumber - yawMinDelta);

            } else {
                ctx.camera.angle(360 - randomNumber - yawMax);
            }
        }

        if (pitchMin < pitchMax) {
            ctx.camera.pitch(Random.nextInt(pitchMin,pitchMax));

        } else {
            int pitchMinDelta = 360 - pitchMin;
            int rngMax = 360 - pitchMin + pitchMax;
            int randomNumber = Random.nextInt(0,rngMax);

            if (randomNumber > pitchMinDelta) {
                ctx.camera.angle(randomNumber - pitchMinDelta);

            } else {
                ctx.camera.angle(360 - randomNumber - pitchMax);
            }
        }
    }

    public void turnCamera(int yaw,int threshold) {
        int angle = ctx.camera.angleTo(yaw);

        while (!ctx.controller.isStopping() && (angle >= 0 && angle > threshold) || (angle < 0 && angle < -threshold)) {
            LOGGER.info("angle = " + angle);
            turnYaw(angle);
            angle = ctx.camera.angleTo(yaw);
        }
    }

    private void adjustPitch(int pitch) {
        int diff = pitch > ctx.camera.pitch() ? pitch - ctx.camera.pitch() : ctx.camera.pitch() - pitch;


    }

    public void zoomToMidHigh() {
        int myCurrentZoom = ctx.widgets.component(WIDGET_OPTIONS,COMPONENT_CAMERA_ZOOM_POINT).screenPoint().x;

        if (!(myCurrentZoom > 620 && myCurrentZoom < 630)) {
            ctx.game.tab(Game.Tab.OPTIONS);
            Point newZoom = new Point(Random.nextInt(MID_HIGH_ZOOM_MIN.x, MID_HIGH_ZOOM_MAX.x),Random.nextInt(MID_HIGH_ZOOM_MAX.y, MID_HIGH_ZOOM_MAX.y));
            ctx.input.click(newZoom,true);
            ctx.game.tab(Game.Tab.INVENTORY);
        }
        currentZoom = Zoom.MID_HIGH;
    }

    public void zoomToMid() {
        int myCurrentZoom = ctx.widgets.component(WIDGET_OPTIONS,COMPONENT_CAMERA_ZOOM_POINT).screenPoint().x;

        if (!(myCurrentZoom >= MID_ZOOM_MARK_MIN.x && myCurrentZoom <= MID_ZOOM_MARK_MAX.x)) {
            ctx.game.tab(Game.Tab.OPTIONS);
            Point newZoom = new Point(Random.nextInt(MID_ZOOM_MARK_MIN.x, MID_ZOOM_MARK_MAX.x),Random.nextInt(MID_ZOOM_MARK_MIN.y, MID_ZOOM_MARK_MAX.y));
            ctx.input.click(newZoom,true);
            ctx.game.tab(Game.Tab.INVENTORY);
        }
        currentZoom = Zoom.MID;
    }

    public void zoomToMidLow() {
        int myCurrentZoom = ctx.widgets.component(WIDGET_OPTIONS,COMPONENT_CAMERA_ZOOM_POINT).screenPoint().x;

        if (!(myCurrentZoom >= MID_LOW_ZOOM_MIN.x && myCurrentZoom <= MID_LOW_ZOOM_MAX.x)) {
            ctx.game.tab(Game.Tab.OPTIONS);
            Point newZoom = new Point(Random.nextInt(MID_LOW_ZOOM_MIN.x, MID_LOW_ZOOM_MAX.x),Random.nextInt(MID_LOW_ZOOM_MIN.y, MID_LOW_ZOOM_MAX.y));
            ctx.input.click(newZoom,true);
            ctx.game.tab(Game.Tab.INVENTORY);
        }
        currentZoom = Zoom.MID_LOW;
    }

    // about 36 degrees
    private void bigToFirstSmall(boolean positive) {
        Point start;
        Point end;

        int offset = Random.nextInt(-60,100);

        int x = (positive ? SMALL_BOX_MAX.x : SMALL_BOX_MIN.x) + offset + Random.nextInt(-10,10);
        int y = (SCREEN.y / 2) + Random.nextGaussian(-15,35,10,3);

        start = new Point(x,y);

        x = (positive ? BIG_BOX_MAX.x : BIG_BOX_MIN.x) + offset + Random.nextInt(-10,10);

        y = (SCREEN.y / 2) + Random.nextGaussian(-15,35,10,3);

        end = new Point(x,y);
        ctx.input.move(start);
        ctx.input.drag(end,MouseEvent.BUTTON2);
    }

    // about 90 degrees
    private void bigToSecondSmall(boolean positive) {
        Point start;
        Point end;

        int offset = Random.nextInt(-60,100);

        int x = (positive ? SMALL_BOX_MIN.x : SMALL_BOX_MAX.x) + offset + Random.nextInt(-10,10);
        int y = (SCREEN.y / 2) + Random.nextGaussian(-15,35,10,3);

        start = new Point(x,y);

        x = (positive ? BIG_BOX_MAX.x : BIG_BOX_MIN.x) + offset + Random.nextInt(-10,10);
        y = (SCREEN.y / 2) + Random.nextGaussian(-15,35,10,3);

        end = new Point(x,y);
        ctx.input.move(start);
        ctx.input.drag(end,MouseEvent.BUTTON2);
    }

    // about 50 degrees
    private void smallToSmall(boolean positive) {
        Point start;
        Point end;

        int offset = Random.nextInt(-60,100);

        int x = (positive ? SMALL_BOX_MIN.x : SMALL_BOX_MAX.x) + offset + Random.nextInt(-10,10);
        int y = (SCREEN.y / 2) + Random.nextGaussian(-15,35,10,3);

        start = new Point(x,y);

        x = (positive ? SMALL_BOX_MAX.x : SMALL_BOX_MIN.x) + offset + Random.nextInt(-10,10);
        y = (SCREEN.y / 2) + Random.nextGaussian(-15,35,10,3);

        end = new Point(x,y);
        ctx.input.move(start);
        ctx.input.drag(end,MouseEvent.BUTTON2);
    }

    // about 128 degrees
    private void bigToBig(boolean positive) {
        Point start;
        Point end;

        int offset = Random.nextInt(-60,100);

        int x = (positive ? BIG_BOX_MIN.x : BIG_BOX_MAX.x) + offset + Random.nextInt(-10,10);
        int y = (SCREEN.y / 2) + Random.nextGaussian(-15,35,10,3);

        start = new Point(x,y);

        x = (positive ? BIG_BOX_MAX.x : BIG_BOX_MIN.x) + offset + Random.nextInt(-10,10);
        y = (SCREEN.y / 2) + Random.nextGaussian(-15,35,10,3);

        end = new Point(x,y);
        ctx.input.move(start);
        ctx.input.drag(end,MouseEvent.BUTTON2);
    }

    public void scrollZoom(Zoom zoom) {
        int zoomAmount = (getZoom() < zoom.getMidPoint() ? (int) Math.floor((zoom.getMidPoint() - getZoom()) / 3) : (int) Math.floor((getZoom() - zoom.getMidPoint()) / 3)) + Random.nextInt(-2,3);

        int group = Random.nextInt(5,9);
        for (int i = 0; i < zoomAmount;i++) {
            if (i % group == 0) {
                Condition.sleep(Random.nextInt(120,300));
            }
            ctx.input.scroll(getZoom() > zoom.getMidPoint());
            Condition.sleep(Random.nextInt(40,56));
        }
    }

    /**
     *
     * @param zoomGoal the x coordinate of the zoom component you want to end up around
     * @param min minimum offset in amount of zooms (2-4 per zoom)
     * @param max maximum offset in amount of zooms (2-4 per zoom)
     */

    public void scrollZoom(int zoomGoal,int min,int max) {
        int zoomAmount = (getZoom() < zoomGoal ? (int) Math.floor((zoomGoal - getZoom()) / 3) : (int) Math.floor((getZoom() - zoomGoal) / 3) + Random.nextInt(min,max + 1));

        int group = Random.nextInt(5,9);
        for (int i = 0; i < zoomAmount;i++) {
            if (i % group == 0) {
                Condition.sleep(Random.nextInt(120,300));
            }
            ctx.input.scroll(getZoom() > zoomGoal);
            Condition.sleep(Random.nextInt(40,56));

        }
    }

    public int getZoom() {
        return ctx.widgets.component(WIDGET_OPTIONS,COMPONENT_CAMERA_ZOOM_POINT).screenPoint().x;
    }

    private void turnPitch(int angle) {
        int speed = ctx.input.speed(Random.nextInt(20,40));



        ctx.input.speed(100); // put speed back to normal
    }

    /**
     * turns the yaw of the camera towards the specified {@param angle} with a small difference in pitch
     * @param angle between -180,180. the angle difference between where you want your camera yaw and current camera yaw
     */
    private void turnYaw(int angle) {
        if (angle < -180 || angle > 180) {
            throw new IllegalArgumentException("angle must be between -180 and 180, current angle: " + angle);
        }

        int speed = ctx.input.speed(Random.nextInt(40,65));
        LOGGER.info("turning  degree differential: " + angle + " with speed: " + speed);

            if (Math.abs(angle) <= 40) {
                LOGGER.info("bigToFirstSmall positbe = " + (angle > 0));
                bigToFirstSmall(angle > 0);

            } else if (Math.abs(angle) <= 78) {
                LOGGER.info("smallToSmall positbe = " + (angle > 0));
                smallToSmall(angle > 0);

            } else if (Math.abs(angle) <= 115) {
                LOGGER.info("bigToSecondSmall positbe = " + (angle > 0));
                bigToSecondSmall(angle > 0);

            } else {
                LOGGER.info("bigToBig positve = " + (angle > 0));
                bigToBig(angle > 0);

            }

        ctx.input.speed(100); // put speed back to normal

    }

    public void turnTo(Locatable locatable) {
        int degrees = getAngleToLocatable(locatable);
        int angle = ctx.camera.angleTo(degrees);
        int threshold = 20;

        while (!ctx.controller.isStopping() && (angle >= 0 && angle > threshold) || (angle < 0 && angle < -threshold)) {
            turnYaw(angle);
            angle = ctx.camera.angleTo(getAngleToLocatable(locatable));
            LOGGER.info("angle after moving = " + angle);
            Condition.sleep(Random.nextGaussian(600,1900,1100));
        }
    }

    private int getAngleToLocatable(final Locatable mobile) {
        final Player local = ctx.players.local();
        final Tile t1 = local != null ? local.tile() : null;
        final Tile t2 = mobile.tile();
        return t1 != null && t2 != null ? ((int) Math.toDegrees(Math.atan2(t2.y() - t1.y(), t2.x() - t1.x()))) - 90 : 0;
    }

    public Zoom getCurrentZoom() {
        return currentZoom;
    }

    public void turnCamera(Direction direction) {
        switch (direction) {
            case NORTH:
                turnCamera(340,20,45,70);
                break;

            case EAST:
                turnCamera(250,290,45,70);
                break;

            case SOUTH:
                turnCamera(160,200,45,70);
                break;

            case WEST:
                turnCamera(70,110,45,70);
                break;
        }
    }
}
