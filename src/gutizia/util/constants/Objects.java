package gutizia.util.constants;

public final class Objects {
    public final static String CLEAR_HERB_PATCH = "Herb patch";
    public final static String CLEAR_FLOWER_PATCH = "Flower Patch";
    public final static String CLEAR_ALLOTMENT_PATCH = "Allotment";
    public final static String CLEAR_HOPS_PATCH = "Hops Patch";
    public final static String CLEAR_FRUIT_TREE_PATCH = "Fruit Tree Patch";
    public final static String CLEAR_TREE_PATCH = "Tree patch";

    // patch name after planting seed, sometimes differs from fully grown patch (for fertilizing and determining patch being planted)
    public final static String HERBS = "Herbs";
    public final static String[] FLOWERS = new String[] {"Marigold","Rosemary","Nasturtium","Woad plant","Limpwurt plant","White lily"};
    public final static String[] ALLOTMENT = new String[] {"Potato seed","Onion seeds","Cabbages","Tomato plant","Sweetcorn seed","Strawberry seed","Watermelon seed","Snape grass seed"};
    public final static String[] HOPS = new String[] {"Yanillian Hops","Hammerstone Hops","Barley","Jute","Krandorian Hops","Wildblood Hops"};

    public final static int RANGE = 7183;
    public final static int ANVIL = 2097;

    public final static int GATE_FENCE_CLOSED_FIRST = 1558; // {4, 24, -108, 0, -8, 260}
    public final static int GATE_FENCE_CLOSED_SECOND = 1560; // {0, 32, -112, 0, -124, 124}
    public final static int[] GATE_FENCE_CLOSED = new int[] {GATE_FENCE_CLOSED_FIRST,GATE_FENCE_CLOSED_SECOND};

    public final static int[] IRON_ROCKS = new int[] {11364,11365};
}
