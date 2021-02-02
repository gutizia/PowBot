package gutizia.util;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.Tile;

public abstract class Interactive {
    private Tile tile;
    private int interactRange;
    protected final static int QUERY_RANGE = 20;
    protected String name;
    protected int[] ids;

    public abstract org.powerbot.script.rt4.Interactive getInteractive();
    public abstract void queryInteractive(ClientContext ctx, boolean combatRelated);
    public abstract boolean isValid();

    public Interactive(Tile tile, int interactRange) {
        this.interactRange = interactRange;
        this.tile = tile;
    }

    public Interactive(int interactRange) {
        this(Tile.NIL, interactRange);
    }

    public int getInteractRange() {
        return interactRange;
    }

    public Tile getTile() {
        return tile;
    }
}
