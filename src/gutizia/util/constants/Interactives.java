package gutizia.util.constants;

import gutizia.util.GameObject;
import gutizia.util.Npc;
import org.powerbot.script.Tile;

public final class Interactives {
    public final static gutizia.util.Interactive GATE = new GameObject(8, new int[] {}, "Gate");
    public final static gutizia.util.Interactive STAIRCASE = new GameObject(12, new int[] {}, "Staircase");
    public final static gutizia.util.Interactive WHEAT = new GameObject(7, new int[] {15507}, "Wheat");
    public final static gutizia.util.Interactive LADDER = new GameObject(7, new int[] {}, "Ladder");
    public final static gutizia.util.Interactive TRAP_DOOR = new GameObject(7, new int[] {14880}, "Trapdoor");
    public final static gutizia.util.Interactive HOPPER = new GameObject(7, new int[] {}, "Hopper");
    public final static gutizia.util.Interactive HOPPER_CONTROLS = new GameObject(7, new int[] {}, "Hopper controls");
    public final static gutizia.util.Interactive FLOUR_BIN = new GameObject(7, new int[] {}, "Flour bin");

    public final static gutizia.util.Interactive DAIRY_COW = new Npc(8, new int[] {1172}, "Dairy cow");
}
