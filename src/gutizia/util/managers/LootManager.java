package gutizia.util.managers;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GroundItem;

import java.util.ArrayList;
import java.util.logging.Logger;

public class LootManager {
    private final static Logger LOGGER = Logger.getLogger("LootManager");
    public final static LootManager lootManager = new LootManager();
    private ClientContext ctx = org.powerbot.script.ClientContext.ctx();

    private ArrayList<Integer> itemsToLoot = new ArrayList<>();
    private ArrayList<GroundItem> lootList = new ArrayList<>();
    private ArrayList<Tile> lootTiles = new ArrayList<>();

    private LootManager() {

    }

    public ArrayList<GroundItem> getGroundItemsOnTile(Tile tile) {
        ArrayList<GroundItem> arrayList = new ArrayList<>();
        for (GroundItem groundItem : lootList) {
            if (groundItem.tile().equals(tile)) {
                arrayList.add(groundItem);
            }
        }
        return arrayList;
    }

    public void addItemToLoot(GroundItem groundItem) {
        lootList.add(groundItem);
        if (!lootTiles.contains(groundItem.tile())) {
            lootTiles.add(groundItem.tile());
        }
    }

    public void removeItemToLoot(GroundItem groundItem) {
        lootList.remove(groundItem);
    }

    public void removeLootTile(Tile tile) {
        lootTiles.remove(tile);
    }

    public ArrayList<Tile> getLootTiles() {
        return lootTiles;
    }

    public void addTileToLootTile(Tile tile) {
        lootTiles.add(tile);
    }

    public ArrayList<GroundItem> getLootList() {
        return lootList;
    }

    public ArrayList<Integer> getItemsToLoot() {
        return itemsToLoot;
    }

    public void setItemsToLoot(int[] list) {
        itemsToLoot = new ArrayList<>();
        for (int i : list) {
            itemsToLoot.add(i);
        }
    }
}
