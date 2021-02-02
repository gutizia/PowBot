package gutizia.tasks.quests.oldRestlessGhost;

import gutizia.tasks.Task;
import gutizia.tasks.quests.restless.ghost.GetToFatherAereck;
import gutizia.util.constants.Runes;
import gutizia.util.managers.BankManager;
import gutizia.util.managers.TeleportManager;
import gutizia.util.resources.Requirements;
import org.powerbot.script.Area;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;
import java.util.logging.Logger;

public class OldRestlessGhost extends Requirements {
    final static Logger LOGGER = Logger.getLogger(OldRestlessGhost.class.getName());
    BankManager bankManager = new BankManager(ctx);

    final Tile[] LUMBRIDGE_CHURCH = new Tile[] {new Tile(3227,3219,0),new Tile(3234,3216,0),new Tile(3236,3209,0)};
    final Area altarRoom = new Area(new Tile(3111,9564,0),new Tile(3121,9569,0));
    final Area coffinRoom = new Area(new Tile(3247,3190,0),new Tile(3252,3195,0));
    final Area church = new Area(new Tile(3240,3204,0),new Tile(3247,3215,0));
    final Area shack = new Area(new Tile(3144,3173,0),new Tile(3151,3177,0));

    public OldRestlessGhost(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        LOGGER.info("starting Restless Ghost");
    }

    protected ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();

        tasks.add(new EquipAmulet(ctx));
        tasks.add(new GetSkull(ctx));
        tasks.add(new Finish(ctx));
        tasks.add(new GetStartingItems(ctx));
        tasks.add(new GetToAltar(ctx));
        tasks.add(new GetToFatherAereck());
        tasks.add(new GetToFatherUrhney(ctx));
        tasks.add(new GetToGhost(ctx));
        tasks.add(new OpenCoffin(ctx));
        tasks.add(new TalkToFatherUrhney(ctx));
        tasks.add(new TalkToGhost(ctx));
        tasks.add(new UseSkullOnCoffin(ctx));

        return tasks;
    }

        @Override
    public boolean activate() {
        return false;
    }

    @Override
    protected void setRequiredStats() {

    }

    @Override
    protected void setRequiredItems() {
        ArrayList<Integer> item = new ArrayList<Integer>();

        item.add(Runes.AIR);
        item.add(10);
        requiredItems.add(item);
        item = new ArrayList<Integer>();

        item.add(Runes.EARTH);
        item.add(10);
        requiredItems.add(item);
        item = new ArrayList<Integer>();

        item.add(Runes.LAW);
        item.add(10);
        requiredItems.add(item);
    }
}

/*


    private void finishQuest() {
        LOGGER.info("waiting for cut scene to finish and quest to be done");
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return !ctx.components.select().textContains("you have completed the restless ghost").isEmpty();
            }
        },3000,20);
    }
*/
