package gutizia.scripts.farmer.util.trackers;

import gutizia.util.skills.farming.Farming;
import org.powerbot.script.Random;

import java.util.ArrayList;

public class HopsRun extends Run {
    public final static HopsRun hopsRun = new HopsRun();

    public Patch hops = Patch.HOPS;

    private HopsRun() {
        super();
    }

    @Override
    public void reset() {
        resetRunValues();
        hops.resetRunValues();
    }

    @Override
    void setPatchOrder() {
        patchOrder = new ArrayList<>();
        ArrayList<Integer> storedIntegers = new ArrayList<>();
        int random;

        for (int i = 0; i < 4;i++) {
            do {
                random = Random.nextInt(1,5);
            } while (storedIntegers.contains(random));
            storedIntegers.add(random);

            switch (random) {
                case 1:
                    patchOrder.add(Farming.FarmingSpot.CHAMP_GUILD_HOPS);
                    break;
                case 2:
                    patchOrder.add(Farming.FarmingSpot.ENTRANA_HOPS);
                    break;
                case 3:
                    patchOrder.add(Farming.FarmingSpot.CAMELOT_HOPS);
                    break;
                case 4:
                    patchOrder.add(Farming.FarmingSpot.YANILLE_HOPS);
                    break;
            }
        }
    }
}
