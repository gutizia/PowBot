package gutizia.util;

import gutizia.scripts.Script;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import static gutizia.util.combat.PlayerCombat.playerCombat;

public class Npc extends Interactive {
    private org.powerbot.script.rt4.Npc npc = ClientContext.ctx().npcs.nil();

    public Npc(int interactRange, int[] ids, String name) {
        super(interactRange);
        this.ids = ids;
        this.name = name;
    }

    public Npc(Tile tile, int interactRange, int[] ids, String name) {
        super(tile, interactRange);
        this.ids = ids;
        this.name = name;
    }

    @Override
    public org.powerbot.script.rt4.Interactive getInteractive() {
        return npc;
    }

    @Override
    public void queryInteractive(ClientContext ctx, boolean combatRelated) {
        if (getTile().equals(Tile.NIL)) {
            if (ids.length < 1 && !name.isEmpty()) {
                ctx.npcs.select().nearest().name(name).nearest();
                if (combatRelated) {
                    for (org.powerbot.script.rt4.Npc n : ctx.npcs) {
                        if (!n.interacting().valid() && !n.healthBarVisible()) {
                            npc = n;
                        }
                    }
                } else {
                    npc = ctx.npcs.poll();
                }

            } else if (ids.length > 0) {
                ctx.npcs.select().nearest().id(ids).nearest();
                if (combatRelated) {
                    for (org.powerbot.script.rt4.Npc n : ctx.npcs) {
                        if (!n.interacting().valid() && !n.healthBarVisible()) {
                            npc = n;
                        }
                    }
                } else {
                    npc = ctx.npcs.poll();
                }
            } else {
                Script.stopScript("failed to query... neither ids or name was set");
            }
        } else {
            if (ids.length < 1 && !name.isEmpty()) {
                ctx.npcs.select().nearest(getTile()).name(name).nearest();
                if (combatRelated) {
                    for (org.powerbot.script.rt4.Npc n : ctx.npcs) {
                        if (!n.interacting().valid() && !n.healthBarVisible()) {
                            npc = n;
                        }
                    }
                } else {
                    npc = ctx.npcs.poll();
                }
            } else if (ids.length > 0) {
                ctx.npcs.select().nearest(getTile()).id(ids).nearest();
                if (combatRelated) {
                    for (org.powerbot.script.rt4.Npc n : ctx.npcs) {
                        if (!n.interacting().valid() && !n.healthBarVisible()) {
                            npc = n;
                        }
                    }
                } else {
                    npc = ctx.npcs.poll();
                }
            } else {
                Script.stopScript("failed to query... neither ids or name was set");
            }
        }
    }


    @Override
    public boolean isValid() {
        return npc.valid();
    }

    @Override
    public Tile getTile() {
        if (super.getTile().equals(Tile.NIL)) {
            return npc.tile();
        }
        return super.getTile();
    }
}
