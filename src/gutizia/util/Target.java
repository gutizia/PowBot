package gutizia.util;

import gutizia.util.combat.CombatStats;
import org.powerbot.script.Area;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;

import java.awt.*;
import java.util.ArrayList;

import static gutizia.util.combat.PlayerCombat.playerCombat;
import static gutizia.util.managers.LootManager.lootManager;

public abstract class Target extends CombatStats {

    protected ClientContext ctx;
    private int healthPercent;
    private Npc npc;
    private int size;
    private String name;

    private boolean isDead = false;

    public abstract void display(Graphics2D g, int x, int y);

    public Target(ClientContext ctx) {
        this(ctx, "target");
    }

    public Target(ClientContext ctx, String name) {
        this.ctx = ctx;
        this.name = name;
        init();
    }

    private void init() {
        healthPercent = 100;
        npc = ctx.npcs.nil();
    }

    public boolean isInAttackRange() {
        return getArea().getClosestTo(ctx.players.local().tile()).distanceTo(ctx.players.local().tile()) <= playerCombat.getAttackRange();
    }

    public Area getArea() {
        if (npc == null || npc.tile().equals(Tile.NIL)) {
            return null;
        }
        if (size % 2 == 0) {
            int i = size / 2;
            return new Area(new Tile(npc.tile().x() - i, npc.tile().y() - i, npc.tile().floor()),
                    new Tile(npc.tile().x() + i, npc.tile().y() + i, npc.tile().floor()));
        } else {
            int i = (size - 1) / 2;
            return new Area(new Tile(npc.tile().x() - i, npc.tile().y() - i, npc.tile().floor()),
                    new Tile(npc.tile().x() + i + 1, npc.tile().y() + i + 1, npc.tile().floor()));
        }
    }

    public boolean isAggroed() {
        return getNpc().interacting().equals(ctx.players.local());
    }

    public Npc getNpc() {
        if (npc == null) {
            return ctx.npcs.nil();
        }
        return npc;
    }

    public int getHealthPercent() {
        return healthPercent;
    }

    public void updateHealthPercent() {
        if (npc != null && npc.healthBarVisible()) {
            healthPercent = npc.healthPercent();
        }
    }

    public boolean isDead() {
        if (isDead) {
            return true;
        }
        if (npc.valid() && npc.healthPercent() <= 0) {
            isDead = true;
            lootManager.addTileToLootTile(getArea().tiles()[0]); // south western most tile. this is where loot appears for monsters occupying multiple tiles
            return true;
        }
        return false;
    }

    public int attackDistance() {
        return (int)getArea().getClosestTo(ctx.players.local()).distanceTo(ctx.players.local());
    }

    public void setHealthPercent(int healthPercent) {
        this.healthPercent = healthPercent;
    }

    public void setNpc(Npc npc) {
        this.npc = npc;
        isDead = false;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

}
