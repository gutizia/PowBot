package gutizia.tasks.farming.getToFarmingSpot;

import gutizia.tasks.Activatable;
import gutizia.tasks.Task;
import gutizia.tasks.farming.getToFarmingSpot.fruitTree.*;
import gutizia.tasks.farming.getToFarmingSpot.herbs.*;
import gutizia.tasks.farming.getToFarmingSpot.tree.*;
import gutizia.tasks.farming.getToFarmingSpot.herbs.GetToCatherby;
import gutizia.tasks.farming.getToFarmingSpot.hops.GetToCamelot;
import gutizia.tasks.farming.getToFarmingSpot.hops.GetToChampGuild;
import gutizia.tasks.farming.getToFarmingSpot.hops.GetToEntrana;
import gutizia.tasks.farming.getToFarmingSpot.hops.GetToYanille;
import gutizia.tasks.poh.EnterPOH;
import gutizia.tasks.poh.GetToHousePortal;
import gutizia.tasks.poh.ReplenishRunEnergy;
import gutizia.tasks.poh.TeleportToDestination;
import gutizia.util.managers.POHManager;
import gutizia.util.managers.TeleportManager;
import gutizia.util.Interact;
import gutizia.util.resources.Traversing;
import gutizia.util.skills.farming.Farming;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;
import java.util.logging.Logger;

import static gutizia.tasks.TaskChanger.taskChanger;
import static gutizia.util.trackers.FruitTreeTracker.fruitTreeTracker;
import static gutizia.util.trackers.HerbRunTracker.herbRunTracker;
import static gutizia.util.trackers.HopsRunTracker.hopsRunTracker;
import static gutizia.util.trackers.TreeTracker.treeTracker;

public class GetToFarmSpot extends Task {
    private Logger LOGGER = Logger.getLogger("GetToFarmSpot");
    protected Interact interact = new Interact();
    protected Farming farming = new Farming(ctx);

    private Activatable activatable;

    protected GetToFarmSpot(ClientContext ctx) {
        super(ctx);
    }

    public GetToFarmSpot(ClientContext ctx, Activatable activatable) {
        super(ctx);
        this.activatable = activatable;
    }

    @Override
    public void execute() {
        LOGGER.info("getting to farming spot");
        taskChanger.setTasks(getTasks());
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();

        if (herbRunTracker.isDoingRun()) {
            switch (herbRunTracker.getPatchOrder().get(0)) {
                case HOSIDIUS_HAF:
                    tasks.add(new GetToHousePortal(ctx));
                    tasks.add(new EnterPOH(ctx));
                    tasks.add(new ReplenishRunEnergy(ctx));
                    tasks.add(new TeleportToDestination(ctx, POHManager.Xeric.HOSIDIUS));
                    tasks.add(new GetToHosidius(ctx));
                    break;

                case CATHERBY_HAF:
                    tasks.add(new GetToHousePortal(ctx));
                    tasks.add(new EnterPOH(ctx));
                    tasks.add(new ReplenishRunEnergy(ctx));
                    tasks.add(new TeleportToDestination(ctx, POHManager.Portal.CAMELOT));
                    tasks.add(new GetToCatherby(ctx));
                    break;

                case ARDOUGNE_HAF:
                    tasks.add(new GetToHousePortal(ctx));
                    tasks.add(new EnterPOH(ctx));
                    tasks.add(new ReplenishRunEnergy(ctx));
                    tasks.add(new TeleportToDestination(ctx, POHManager.Jewellery.FISHING_GUILD));
                    tasks.add(new GetToArdougne(ctx));
                    break;

                case FALADOR_HAF:
                    tasks.add(new GetToHousePortal(ctx));
                    tasks.add(new EnterPOH(ctx));
                    tasks.add(new ReplenishRunEnergy(ctx));
                    tasks.add(new TeleportToDestination(ctx, POHManager.Jewellery.DRAYNOR_VILLAGE));
                    tasks.add(new GetToFalador(ctx));
                    break;

                case CANIFIS_HAF:
                    // TODO add task: use echtophail
                    tasks.add(new GetToCanifis(ctx));
                    break;
            }
        }
        if (hopsRunTracker.isDoingRun()) {
            switch (hopsRunTracker.getPatchOrder().get(0)) {
                case ENTRANA_HOPS:
                    tasks.add(new GetToHousePortal(ctx));
                    tasks.add(new GetToEntrana(ctx));
                    break;

                case YANILLE_HOPS:
                    tasks.add(new GetToHousePortal(ctx));
                    tasks.add(new EnterPOH(ctx));
                    tasks.add(new ReplenishRunEnergy(ctx));
                    tasks.add(new TeleportToDestination(ctx, POHManager.Portal.WATCHTOWER));
                    tasks.add(new GetToYanille(ctx));
                    break;

                case CHAMP_GUILD_HOPS:
                    tasks.add(new GetToHousePortal(ctx));
                    tasks.add(new EnterPOH(ctx));
                    tasks.add(new ReplenishRunEnergy(ctx));
                    tasks.add(new TeleportToDestination(ctx, POHManager.Jewellery.CHAMPION_GUILD));
                    tasks.add(new GetToChampGuild(ctx));
                    break;

                case CAMELOT_HOPS:
                    tasks.add(new GetToHousePortal(ctx));
                    tasks.add(new EnterPOH(ctx));
                    tasks.add(new ReplenishRunEnergy(ctx));
                    tasks.add(new TeleportToDestination(ctx, POHManager.Portal.CAMELOT));
                    tasks.add(new GetToCamelot(ctx));
                    break;
            }
        }
        if (fruitTreeTracker.isDoingRun()) {
            switch (fruitTreeTracker.getPatchOrder().get(0)) {
                case LLETYA_FT:
                    // TODO add task: teleport with crystal seed
                    //tasks.add(new GetTo(ctx)); todo add getting to lletya
                    break;

                case CATHERBY_FT:
                    tasks.add(new GetToHousePortal(ctx));
                    tasks.add(new EnterPOH(ctx));
                    tasks.add(new ReplenishRunEnergy(ctx));
                    tasks.add(new TeleportToDestination(ctx, POHManager.Portal.CAMELOT));
                    tasks.add(new gutizia.tasks.farming.getToFarmingSpot.fruitTree.GetToCatherby(ctx));
                    break;

                case GNOME_STRONGHOLD_FT:
                    tasks.add(new GetToHousePortal(ctx));
                    tasks.add(new EnterPOH(ctx));
                    tasks.add(new ReplenishRunEnergy(ctx));

                    if (false) { // TODO replace with done gnome village
                        // TODO add teleport to gnome stronghold via tree
                    } else {
                        tasks.add(new TeleportToDestination(ctx, POHManager.Jewellery.FISHING_GUILD));
                        tasks.add(new WalkToGnomeStrongholdGate(ctx));
                        tasks.add(new OpenGnomeGate(ctx));
                    }
                    tasks.add(new GetToGnomeStronghold(ctx));
                    break;

                case FARMING_GUILD_FT:
                    tasks.add(new GetToHousePortal(ctx));
                    tasks.add(new EnterPOH(ctx));
                    tasks.add(new ReplenishRunEnergy(ctx));
                    tasks.add(new TeleportToDestination(ctx, POHManager.Jewellery.FARMING_GUILD));
                    tasks.add(new GetToFarmingGuild(ctx));
                    break;

                case GNOME_VILLAGE_FT:
                    tasks.add(new GetToHousePortal(ctx));
                    tasks.add(new EnterPOH(ctx));
                    tasks.add(new ReplenishRunEnergy(ctx));
                    tasks.add(new TeleportToDestination(ctx, POHManager.Jewellery.CASTLE_WARS));
                    tasks.add(new GetToGnomeVillage(ctx));
                    break;

                case BRIMHAVEN_FT:
                    tasks.add(new GetToHousePortal(ctx));
                    tasks.add(new EnterPOH(ctx));
                    tasks.add(new ReplenishRunEnergy(ctx));
                    tasks.add(new TeleportToDestination(ctx, POHManager.Portal.CAMELOT));
                    tasks.add(new GetToBrimhaven(ctx));
                    break;
            }
        }
        if (treeTracker.isDoingRun()) {
            switch (treeTracker.getPatchOrder().get(0)) {
                case GNOME_STRONGHOLD_TREE:
                    tasks.add(new GetToHousePortal(ctx));
                    tasks.add(new EnterPOH(ctx));
                    tasks.add(new ReplenishRunEnergy(ctx));

                    if (false) { // TODO replace with done gnome village
                        // TODO add teleport to gnome stronghold via tree
                    } else {
                        tasks.add(new TeleportToDestination(ctx, POHManager.Jewellery.FISHING_GUILD));
                        tasks.add(new WalkToGnomeStrongholdGate(ctx));
                        tasks.add(new OpenGnomeGate(ctx));
                    }
                    tasks.add(new GetToGnomeStrongholdTree(ctx));
                    break;

                case FARMING_GUILD_TREE:
                    tasks.add(new GetToHousePortal(ctx));
                    tasks.add(new EnterPOH(ctx));
                    tasks.add(new ReplenishRunEnergy(ctx));
                    tasks.add(new TeleportToDestination(ctx, POHManager.Jewellery.FARMING_GUILD));
                    tasks.add(new GetToFarmingGuildTree(ctx));
                    break;

                case LUMBRIDGE_TREE:
                    // TODO add task: teleport to lumbridge
                    tasks.add(new GetToLumbridgeTree(ctx));
                    break;

                case TAVERLEY_TREE:
                    tasks.add(new GetToHousePortal(ctx));
                    tasks.add(new EnterPOH(ctx));
                    tasks.add(new ReplenishRunEnergy(ctx));
                    tasks.add(new TeleportToDestination(ctx, POHManager.Jewellery.BURTHORPE));
                    tasks.add(new GetToTaverlyTree(ctx));
                    break;

                case VARROCK_TREE:
                    tasks.add(new GetToHousePortal(ctx));
                    tasks.add(new EnterPOH(ctx));
                    tasks.add(new ReplenishRunEnergy(ctx));
                    tasks.add(new TeleportToDestination(ctx, POHManager.Portal.VARROCK));
                    tasks.add(new GetToVarrockTree(ctx));
                    break;

                case FALADOR_TREE:
                    tasks.add(new GetToHousePortal(ctx));
                    tasks.add(new EnterPOH(ctx));
                    tasks.add(new ReplenishRunEnergy(ctx));
                    tasks.add(new TeleportToDestination(ctx, POHManager.Portal.FALADOR));
                    tasks.add(new GetToFaladorTree(ctx));
                    break;
            }
        }
        tasks.add(new Finish(ctx));
        return tasks;
    }

    @Override
    public boolean activate() {
        return activatable.activate();
    }
}
