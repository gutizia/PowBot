package gutizia.util;

import gutizia.scripts.Script;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class GameObject extends Interactive {
    private org.powerbot.script.rt4.GameObject gameObject = ClientContext.ctx().objects.nil();

    public GameObject(int interactRange, int[] ids, String name) {
        super(interactRange);
        this.ids = ids;
        this.name = name;
    }

    public GameObject(Tile tile, int interactRange, int[] ids, String name) {
        super(tile, interactRange);
        this.ids = ids;
        this.name = name;
    }

    @Override
    public org.powerbot.script.rt4.Interactive getInteractive() {
        return gameObject;
    }

    @Override
    public void queryInteractive(ClientContext ctx, boolean combatRelated) {
        if (getTile().equals(Tile.NIL)) {
            if (ids.length < 1 && !name.isEmpty()) {
                gameObject = ctx.objects.select(QUERY_RANGE).nearest().name(name).nearest().poll();
            } else if (ids.length > 0) {
                gameObject = ctx.objects.select(QUERY_RANGE).nearest().id(ids).nearest().poll();
            } else {
                Script.stopScript("failed to query... neither ids or name was set");
            }
        } else {
            if (ids.length < 1 && !name.isEmpty()) {
                gameObject = ctx.objects.select(QUERY_RANGE).nearest(getTile()).name(name).nearest().poll();
            } else if (ids.length > 0) {
                gameObject = ctx.objects.select(QUERY_RANGE).nearest(getTile()).id(ids).nearest().poll();
            } else {
                Script.stopScript("failed to query... neither ids or name was set");
            }
        }
    }


    @Override
    public boolean isValid() {
        return gameObject.valid();
    }

    @Override
    public Tile getTile() {
        if (super.getTile().equals(Tile.NIL)) {
            return gameObject.tile();
        }
        return super.getTile();
    }
}
