package gutizia.scripts.farmer.util.trackers;

import gutizia.util.skills.farming.Farming;
import org.powerbot.script.Random;

import java.util.ArrayList;

public class FruitTreeRun extends Run {
    public final static FruitTreeRun fruitTreeRun = new FruitTreeRun();

    public Patch fruitTree = Patch.FRUIT_TREE;

    private FruitTreeRun() {
        super();
    }

    @Override
    public void reset() {
        resetRunValues();
        fruitTree.resetRunValues();
    }

    @Override
    void setPatchOrder() {
        patchOrder = new ArrayList<>();
        ArrayList<Integer> storedIntegers = new ArrayList<>();
        int random;

        for (int i = 0; i < fruitTree.getAmountOfPatches(); i++) {
            do {
                random = Random.nextInt(1, fruitTree.getAmountOfPatches() + 1);
            } while (storedIntegers.contains(random));
            storedIntegers.add(random);

            switch (random) {
                case 1:
                    patchOrder.add(Farming.FarmingSpot.GNOME_STRONGHOLD_FT);
                    break;
                case 2:
                    patchOrder.add(Farming.FarmingSpot.CATHERBY_FT);
                    break;
                case 3:
                    patchOrder.add(Farming.FarmingSpot.GNOME_VILLAGE_FT);
                    break;
                case 4:
                    patchOrder.add(Farming.FarmingSpot.BRIMHAVEN_FT);
                    break;

                case 5:
                    //patchOrder.add(Farming.FarmingSpot.FARMING_GUILD_FT);
                    break;

                case 6:
                    //patchOrder.add(Farming.FarmingSpot.LLETYA_FT);
                    break;
            }
        }
    }
}
