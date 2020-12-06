package gutizia.util.skills;

import gutizia.util.constants.Items;
import gutizia.util.constants.Objects;
import gutizia.util.constants.Widgets;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

public class Smithing extends ClientAccessor {

    public Smithing(ClientContext ctx) {
        super(ctx);
    }

    public void smith(final int itemToSmith) {
        int itemIndex = getCorrectItemComponentIndex(itemToSmith);

        openSmithMenu();
        ctx.widgets.component(Widgets.SMITHING_ITEMS,itemIndex).click();

        Condition.sleep(Random.nextInt(1300,1900));
    }

    private void openSmithMenu() {
        while (!ctx.widgets.component(Widgets.SMITHING_ITEMS,0).visible()) {
            ctx.objects.select().id(Objects.ANVIL).nearest().poll().interact(false,"Smith");
            Condition.sleep(Random.nextInt(1200,2000));
        }
    }

    /**
     *
     * @param item the desired item to smith
     * @return returns the correct component for clicking purposes, if unsupported item passed returns -1
     */

    private int getCorrectItemComponentIndex(int item) {
        switch (item) {
            case Items.IRON_DAGGER:
                return 9;
        }
        return -1;
    }

}
