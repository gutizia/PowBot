package gutizia.util.combat;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Loot extends ClientAccessor {
    private final static Logger LOGGER = Logger.getLogger("Loot");

    private int lootItemsTimes = 0;

    private Integer[] itemsToLoot;
    private ArrayList<Tile> lootTiles = new ArrayList<>();

    public Loot(ClientContext ctx, Integer[] itemsToLoot) {
        super(ctx);
        this.itemsToLoot = itemsToLoot;
    }

    public void saveTileForLoot(Tile tile, int size) {
        switch (size) {
            case 1:
                lootTiles.add(tile);
                break;

            case 2:
                lootTiles.add(new Tile(tile.x() - 1,tile.y() - 1, tile.floor()));
                break;
        }
    }

    public static int[] setBounds(int itemId) {
        switch (itemId) {
            default: // based on potions, very small and should suffice for most items
                return new int[] {0, 12, -4, 0, -8, 4};
        }
    }

    public ArrayList<Tile> getLootTiles() {
        return lootTiles;
    }

    public Integer[] getItemsToLoot() {
        return itemsToLoot;
    }

    public int getLootItemsTimes() {
        return lootItemsTimes;
    }

    public void setLootItemsTimes(int lootItemsTimes) {
        this.lootItemsTimes = lootItemsTimes;
    }

    public void setLootTiles(ArrayList<Tile> lootTiles) {
        this.lootTiles = lootTiles;
    }
}
