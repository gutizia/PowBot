package gutizia.util.combat;

import gutizia.util.Requirement;
import gutizia.util.constants.*;
import gutizia.util.settings.TargetSettings;
import org.powerbot.script.Area;
import org.powerbot.script.Tile;

public enum Target {
    NONE(new Area(Tile.NIL), new Area(Tile.NIL), 0, new int[] {-1}),
    CHICKENS(Areas.LUMBRIDGE_CHICKEN_PEN, new Area(Tile.NIL), 1, Npcs.CHICKEN_IDS),
    GOBLINS(Areas.GOBLIN, Areas.GOBLIN_HOUSE, 1, Npcs.GOBLIN_IDS,
            new int[] {Items.GOBLIN_MAIL, Items.BONES, Runes.WATER, Runes.EARTH, Runes.BODY, Items.BRONZE_BOLTS, Items.COINS, Tools.HAMMER, Items.CHEFS_HAT}),
    COWS(Areas.LUMBRIDGE_COW_PEN, new Area(Tile.NIL), 2, Npcs.COW_IDS, new int[] {Items.COW_HIDE});

    private int[] ids;
    private int[] itemsToLoot;
    private Area included, excluded;
    private int size;

    Target(Area included, Area excluded, int size, int[] ids, int[] itemsToLoot) {
        this.ids = ids;
        this.included = included;
        this.excluded = excluded;
        this.size = size;
        this.itemsToLoot = itemsToLoot;
    }

    Target(Area included, Area excluded, int size, int[] ids) {
        this(included, excluded, size, ids, new int[] {-1});
    }

    public int[] getItemsToLoot() {
        return itemsToLoot;
    }

    public int[] getIds() {
        return ids;
    }

    public Area getIncluded() {
        return included;
    }

    public Area getExcluded() {
        return excluded;
    }

    public int getSize() {
        return size;
    }

    public Requirement getRequirement() {
        return TargetSettings.getRequirements(this);
    }

}