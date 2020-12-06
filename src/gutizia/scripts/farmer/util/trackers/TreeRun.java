package gutizia.scripts.farmer.util.trackers;

import gutizia.util.skills.farming.Farming;
import org.powerbot.script.Random;

import java.util.ArrayList;

public class TreeRun extends Run {
    public final static TreeRun treeRun = new TreeRun();

    public Patch tree = Patch.TREE;

    private TreeRun() {
        super();
    }

    @Override
    public void reset() {
        resetRunValues();
        tree.resetRunValues();
    }

    @Override
    void setPatchOrder() {
        patchOrder = new ArrayList<>();
        ArrayList<Integer> storedIntegers = new ArrayList<>();
        int random;

        for (int i = 0; i < tree.getAmountOfPatches();i++) {
            do {
                random = Random.nextInt(1, tree.getAmountOfPatches() + 1);
            } while (storedIntegers.contains(random));
            storedIntegers.add(random);

            switch (random) {
                case 1:
                    patchOrder.add(Farming.FarmingSpot.GNOME_STRONGHOLD_TREE);
                    break;
                case 2:
                    patchOrder.add(Farming.FarmingSpot.TAVERLEY_TREE);
                    break;
                case 3:
                    patchOrder.add(Farming.FarmingSpot.LUMBRIDGE_TREE);
                    break;
                case 4:
                    patchOrder.add(Farming.FarmingSpot.FALADOR_TREE);
                    break;
                case 5:
                    patchOrder.add(Farming.FarmingSpot.VARROCK_TREE);
                    break;

                case 6:
                    //patchOrder.add(Farming.FarmingSpot.FARMING_GUILD_TREE); todo uncomment when implemented
                    break;
            }
        }
    }
}
