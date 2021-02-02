package gutizia.util.listeners;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.GroundItem;

import java.util.EventObject;

public class GroundItemEvent extends EventObject {

    private final Tile tile;
    private final GroundItem newItem;
    private final GroundItem oldItem;

    public GroundItemEvent(Tile tile, GroundItem newItem, GroundItem oldItem) {
        super(tile);
        this.tile = tile;
        this.newItem = newItem;
        this.oldItem = oldItem;
    }

    public Tile getTile() {
        return tile;
    }

    public GroundItem getNewItem() {
        return newItem;
    }

    public GroundItem getOldItem() {
        return oldItem;
    }
}
