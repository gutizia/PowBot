package gutizia.scripts.farmer.util.trackers;

import gutizia.util.skills.farming.Farming;
import org.powerbot.script.Random;

import java.util.ArrayList;

public class HerbRun extends Run {
    public final static HerbRun herbRun = new HerbRun();

    public Patch herb = Patch.HERB;
    public Patch flower = Patch.FLOWER;
    public Patch allotment = Patch.ALLOTMENT;

    private HerbRun() {
        super();
    }

    @Override
    public void reset() {
        resetRunValues();
        this.herb.resetRunValues();
        this.flower.resetRunValues();
        this.allotment.resetRunValues();
    }

    @Override
    void setPatchOrder() {
        patchOrder = new ArrayList<>();
        ArrayList<Integer> storedIntegers = new ArrayList<>();
        int random;

        for (int i = 0; i < 5;i++) {
            do {
                random = Random.nextInt(0,5);
            } while (storedIntegers.contains(random));
            storedIntegers.add(random);

            switch (random) {
                case 0:
                    patchOrder.add(Farming.FarmingSpot.CATHERBY_HAF);
                    break;
                case 1:
                    patchOrder.add(Farming.FarmingSpot.FALADOR_HAF);
                    break;
                case 2:
                    patchOrder.add(Farming.FarmingSpot.ARDOUGNE_HAF);
                    break;
                case 3:
                    //patchOrder.add(Farming.FarmingSpot.CANIFIS_HAF); // TODO ADD SUPPORT FOR IT LATER
                    break;
                case 4:
                    patchOrder.add(Farming.FarmingSpot.HOSIDIUS_HAF);
                    break;
            }
        }
    }
}
