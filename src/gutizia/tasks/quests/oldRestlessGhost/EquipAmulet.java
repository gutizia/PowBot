package gutizia.tasks.quests.oldRestlessGhost;

import gutizia.util.Interact;
import gutizia.util.constants.Items;
import org.powerbot.script.rt4.ClientContext;

public class EquipAmulet extends OldRestlessGhost {

    EquipAmulet(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        //overlay.changeStatus("equipping ghostspeak amulet");
        int[] amulet = new int[] {552};
        //Interact.equipGear(amulet);
    }

    @Override
    public boolean activate() {
        return !ctx.inventory.select().id(Items.GHOSTSPEAK_AMULET).isEmpty(); // if have ghostspeak amulet in inventory
    }
}
