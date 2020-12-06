package gutizia.util.skills.farming;

import gutizia.util.Interact;
import gutizia.util.constants.*;
import gutizia.util.constants.Components;
import gutizia.util.constants.Items;
import gutizia.util.constants.Objects;
import gutizia.util.constants.Widgets;
import gutizia.util.constants.Tools;
import gutizia.util.skills.Skills;
import org.powerbot.script.*;
import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;
import java.util.logging.Logger;

import static gutizia.util.trackers.CompostTracker.compostTracker;

public class Farming extends Skills {
    private final static Logger LOGGER = Logger.getLogger("Farming");

    public enum FarmingSpot {
        FALADOR_HAF, CATHERBY_HAF, ARDOUGNE_HAF, CANIFIS_HAF, HOSIDIUS_HAF,
        CHAMP_GUILD_HOPS,CAMELOT_HOPS,YANILLE_HOPS,ENTRANA_HOPS,
        GNOME_STRONGHOLD_FT, CATHERBY_FT, GNOME_VILLAGE_FT, BRIMHAVEN_FT, LLETYA_FT, FARMING_GUILD_FT,
        LUMBRIDGE_TREE,VARROCK_TREE,FALADOR_TREE,TAVERLEY_TREE,GNOME_STRONGHOLD_TREE,FARMING_GUILD_TREE,
        NONE}

    public enum Patch {HERB,FLOWER,ALLOTMENT1,ALLOTMENT2,HOPS,CACTUS,FRUIT_TREE,TREE}
    public enum FarmRun {HERB_RUN, HOPS_RUN, FRUIT_TREE_RUN, TREE_RUN}

    public Farming(ClientContext ctx) {
        super(ctx);
    }

    public Item getSeed(Patch patch) {
        switch (patch) {
            case ALLOTMENT1: case ALLOTMENT2:
                return ctx.inventory.select().id(Items.ALLOTMENT_SEEDS).poll();

            case FLOWER:
                return ctx.inventory.select().id(Items.FLOWER_SEEDS).poll();

            case HERB:
                return ctx.inventory.select().id(Items.HERB_SEEDS).poll();

            case HOPS:
                return ctx.inventory.select().id(Items.HOPS_SEEDS).poll();

            case FRUIT_TREE:
                return ctx.inventory.select().id(Items.FRUIT_TREE_SAPLINGS).poll();

            case TREE:
                return ctx.inventory.select().id(Items.TREE_SAPLINGS).poll();

            default:
                LOGGER.info("ERROR! getSeed() did not recognize patch: " + patch);
                return ctx.inventory.nil();
        }
    }

    public Area getPatchArea(Patch patch) {
        Area area = null;
        switch (getFarmSpotYouAreAt()) {
            case FALADOR_HAF:
                switch (patch) {
                    case HERB:
                        area = PatchAreas.faladorHerb;
                        break;

                    case ALLOTMENT1:
                        area = PatchAreas.faladorAllotment1;
                        break;

                    case ALLOTMENT2:
                        area = PatchAreas.faladorAllotment2;
                        break;

                    case FLOWER:
                        area = PatchAreas.faladorFlower;
                        break;
                }
                break;

            case ARDOUGNE_HAF:
                switch (patch) {
                    case HERB:
                        area = PatchAreas.ardoungeHerb;
                        break;

                    case ALLOTMENT1:
                        area = PatchAreas.ardoungeAllotment1;
                        break;

                    case ALLOTMENT2:
                        area = PatchAreas.ardoungeAllotment2;
                        break;

                    case FLOWER:
                        area = PatchAreas.ardoungeFlower;
                        break;

                }
                break;

            case CANIFIS_HAF:
                switch (patch) {
                    case HERB:
                        area = PatchAreas.canifisHerb;
                        break;

                    case ALLOTMENT1:
                        area = PatchAreas.canifisAllotment1;
                        break;

                    case ALLOTMENT2:
                        area = PatchAreas.canifisAllotment2;
                        break;

                    case FLOWER:
                        area = PatchAreas.canifisFlower;
                        break;

                }
                break;

            case HOSIDIUS_HAF:
                switch (patch) {
                    case HERB:
                        area = PatchAreas.hosidiusHerb;
                        break;

                    case ALLOTMENT1:
                        area = PatchAreas.hosidiusAllotment1;
                        break;

                    case ALLOTMENT2:
                        area = PatchAreas.hosidiusAllotment2;
                        break;

                    case FLOWER:
                        area = PatchAreas.hosidiusFlower;
                        break;

                }
                break;

            case CATHERBY_HAF:
                switch (patch) {
                    case HERB:
                        area = PatchAreas.catherbyHerb;
                        break;

                    case ALLOTMENT1:
                        area = PatchAreas.catherbyAllotment1;
                        break;

                    case ALLOTMENT2:
                        area = PatchAreas.catherbyAllotment2;
                        break;

                    case FLOWER:
                        area = PatchAreas.catherbyFlower;
                        break;

                }
                break;

            case ENTRANA_HOPS:
                area = PatchAreas.entranaHops;
                break;

            case YANILLE_HOPS:
                area = PatchAreas.yanilleHops;
                break;

            case CHAMP_GUILD_HOPS:
                area = PatchAreas.championGuildHops;
                break;

            case CAMELOT_HOPS:
                area = PatchAreas.camelotHops;
                break;

            case GNOME_VILLAGE_FT:
                area = PatchAreas.gnomeVillageFT;
                break;

            case GNOME_STRONGHOLD_FT:
                area = PatchAreas.gnomeStrongholdFT;
                break;

            case BRIMHAVEN_FT:
                area = PatchAreas.brimhavenFT;
                break;

            case CATHERBY_FT:
                area = PatchAreas.catherbyFT;
                break;

            case LLETYA_FT:
                area = PatchAreas.lletyaFT;
                break;

            case FARMING_GUILD_FT:
                area = PatchAreas.farmingGuildFT;
                break;

            case FALADOR_TREE:
                area = PatchAreas.faladorTree;
                break;

            case TAVERLEY_TREE:
                area = PatchAreas.taverlyTree;
                break;

            case LUMBRIDGE_TREE:
                area = PatchAreas.lumbridgeTree;
                break;

            case VARROCK_TREE:
                area = PatchAreas.varrockTree;
                break;

            case FARMING_GUILD_TREE:
                area = PatchAreas.farmingGuildTree;
                break;

            case GNOME_STRONGHOLD_TREE:
                area = PatchAreas.gnomeStrongholdTree;
                break;
        }
        return area;
    }

    public void equipMagicSecateurs() {
        if (!ctx.inventory.select().name("Magic secateurs").isEmpty()) {
            int[] gear = new int[] {Tools.MAGIC_SECATEURS};
            Interact.equipOutfit(ctx, gear);
        }
    }

    public void openFarmingEquipmentStore() {
        if (!ctx.widgets.widget(Widgets.FARMING_EQUIPMENT_STORE).valid()) {
            LOGGER.info("opening farming equipment store");

            Npc leprechaun = ctx.npcs.select().name("Tool Leprechaun").nearest().poll();

            if (leprechaun.equals(ctx.npcs.nil())) {
                LOGGER.info("no leprechaun found!");
                return;
            }

            leprechaun.bounds(Bounds.LEPRECHAUN);

            if (leprechaun.tile().distanceTo(ctx.players.local()) > 7 || !leprechaun.inViewport()) {
                ctx.movement.step(leprechaun.tile());
                ctx.camera.turnTo(leprechaun);
            }

            if (!leprechaun.interact(false,"Exchange",leprechaun.name())) {
                ctx.movement.step(leprechaun.tile());
                ctx.camera.turnTo(leprechaun);
                openFarmingEquipmentStore();
            }
            Condition.wait(() -> ctx.widgets.widget(Widgets.FARMING_EQUIPMENT_STORE).valid(),1000,6);
        }
    }

    public boolean levelUpChatVisible() {
        return !ctx.components.select().textContains("you just advanced a ").isEmpty();
    }

    public void closeFarmingEquipmentStore() {
        while (ctx.widgets.widget(Widgets.FARMING_EQUIPMENT_STORE).valid()) {
            ctx.widgets.component(Widgets.FARMING_EQUIPMENT_STORE,1,11).click();
            Condition.sleep(Random.nextInt(700,1000));
        }
    }

    public void depositFarmingItems() {
        ArrayList<Component> components = getComponentsToDeposit();

        for (final Component component : components) {
            component.interact("Store-All");
            Condition.wait(() -> component.component(Components.SUB_AMOUNT).text().equals("0"), 400, 4);
        }

    }

    public boolean hopsDone() {
        boolean done = true;
        Area patchArea;

        switch (getFarmSpotYouAreAt()) {
            case CAMELOT_HOPS:
                patchArea = PatchAreas.camelotHops;
                break;

            case CHAMP_GUILD_HOPS:
                patchArea = PatchAreas.championGuildHops;
                break;

            case YANILLE_HOPS:
                patchArea = PatchAreas.yanilleHops;
                break;

            case ENTRANA_HOPS:
                patchArea = PatchAreas.entranaHops;
                break;

            default:
                LOGGER.info("farm spot: " + getFarmSpotYouAreAt() + " is not a hops farming spot");
                return false;
        }

        if (!ctx.objects.select(15).within(patchArea).action("Clear","Harvest","Rake").isEmpty()) {
            done = false;
        } else if (!ctx.objects.select(15).within(patchArea).name(Objects.CLEAR_HOPS_PATCH).isEmpty()) {
            done = false;
        }

        return done;
    }

    public boolean herbDone() {
        boolean done = true;
        Area patchArea;

        switch (getFarmSpotYouAreAt()) {
            case FALADOR_HAF:
                patchArea = PatchAreas.faladorHerb;
                break;

            case ARDOUGNE_HAF:
                patchArea = PatchAreas.ardoungeHerb;
                break;

            case CATHERBY_HAF:
                patchArea = PatchAreas.catherbyHerb;
                break;

            case HOSIDIUS_HAF:
                patchArea = PatchAreas.hosidiusHerb;
                break;

            default:
                LOGGER.info("farm spot: " + getFarmSpotYouAreAt() + " is not a herb farming spot");
                return false;
        }

        if (!ctx.objects.select(10).within(patchArea).action("Clear","Pick","Rake").isEmpty()) {
            done = false;
        } else if (!ctx.objects.select(10).within(patchArea).name(Objects.CLEAR_HERB_PATCH).isEmpty()) {
            done = false;
        }

        return done;
    }

    public boolean flowerDone() {
        boolean done = true;
        Area patchArea;

        switch (getFarmSpotYouAreAt()) {
            case FALADOR_HAF:
                patchArea = PatchAreas.faladorFlower;
                break;

            case ARDOUGNE_HAF:
                patchArea = PatchAreas.ardoungeFlower;
               break;

            case CATHERBY_HAF:
                patchArea = PatchAreas.catherbyFlower;
                break;

            case HOSIDIUS_HAF:
                patchArea = PatchAreas.hosidiusFlower;
                break;

            default:
                LOGGER.info("farm spot: " + getFarmSpotYouAreAt() + " is not an flower farming spot");
                return false;
        }

        if (!ctx.objects.select(15).within(patchArea).action("Clear","Pick","Rake").isEmpty()) {
            done = false;
        } else if (!ctx.objects.select(15).within(patchArea).name(Objects.CLEAR_FLOWER_PATCH).isEmpty()) {
            done = false;
        }

        return done;
    }

    public boolean allotment1Done() {
        boolean done = true;
        Area patchArea;

        switch (getFarmSpotYouAreAt()) {
            case FALADOR_HAF:
                patchArea = PatchAreas.faladorAllotment1;
                break;

            case ARDOUGNE_HAF:
                patchArea = PatchAreas.ardoungeAllotment1;
                break;

            case CATHERBY_HAF:
                patchArea = PatchAreas.catherbyAllotment1;
                break;

            case HOSIDIUS_HAF:
                patchArea = PatchAreas.hosidiusAllotment1;
                break;

            default:
                LOGGER.info("farm spot: " + getFarmSpotYouAreAt() + " is not an allotment farming spot");
                return false;
        }

        if (!ctx.objects.select(15).within(patchArea).action("Clear","Harvest","Rake").isEmpty()) {
            done = false;
        } else if (!ctx.objects.select(15).within(patchArea).name(Objects.CLEAR_ALLOTMENT_PATCH).isEmpty()) {
            done = false;
        }

        return done;
    }

    public boolean allotment2Done() {
        boolean done = true;
        Area patchArea;

        switch (getFarmSpotYouAreAt()) {
            case FALADOR_HAF:
                patchArea = PatchAreas.faladorAllotment2;
                break;

            case ARDOUGNE_HAF:
                patchArea = PatchAreas.ardoungeAllotment2;
                break;

            case CATHERBY_HAF:
                patchArea = PatchAreas.catherbyAllotment2;
                break;

            case HOSIDIUS_HAF:
                patchArea = PatchAreas.hosidiusAllotment2;
                break;

            default:
                LOGGER.info("farm spot: " + getFarmSpotYouAreAt() + " is not an allotment farming spot");
                return false;
        }

        if (!ctx.objects.select(15).within(patchArea).action("Clear","Harvest","Rake").isEmpty()) {
            done = false;
        } else if (!ctx.objects.select(15).within(patchArea).name(Objects.CLEAR_ALLOTMENT_PATCH).isEmpty()) {
            done = false;
        }

        return done;
    }

    public boolean fruitTreeDone() {
        boolean done = true;
        Area patchArea;

        switch (getFarmSpotYouAreAt()) {
            case CATHERBY_FT:
                patchArea = PatchAreas.catherbyFT;
                break;

            case BRIMHAVEN_FT:
                patchArea = PatchAreas.brimhavenFT;
                break;

            case GNOME_STRONGHOLD_FT:
                patchArea = PatchAreas.gnomeStrongholdFT;
                break;

            case GNOME_VILLAGE_FT:
                patchArea = PatchAreas.gnomeVillageFT;
                break;

            case FARMING_GUILD_FT:
                patchArea = PatchAreas.farmingGuildFT;
                break;

            case LLETYA_FT:
                patchArea = PatchAreas.lletyaFT;
                break;

            default:
                LOGGER.info("farm spot: " + getFarmSpotYouAreAt() + " is not a fruit tree farming spot");
                return false;
        }

        GameObject fruitTree = ctx.objects.select(15).within(patchArea).action("Guide").nearest().poll();

        if (!fruitTree.equals(ctx.objects.nil())) {
            if (getFruitTreePickAction(fruitTree).equals("") &&
                    !Interact.actionContains(fruitTree.actions(), "Clear", "Check-health", "Rake", "Chop down")) {
                done = false;
            }
        } else if (!ctx.objects.select(15).within(patchArea).name(Objects.CLEAR_FRUIT_TREE_PATCH).isEmpty()) {
            done = false;
        }

        return done;
    }

    public String getFruitTreePickAction(GameObject object) {
        for (String action : object.actions()) {
            if (action == null) {
                continue;
            }

            if (action.toLowerCase().contains("pick")) {
                return action;
            }
        }
        return "";
    }

    // TODO finish this
    public boolean treeDone() {
        boolean done = true;
        Area patchArea;
        switch (getFarmSpotYouAreAt()) {
            case LUMBRIDGE_TREE:
                patchArea = PatchAreas.lumbridgeTree;
                break;

            case VARROCK_TREE:
                patchArea = PatchAreas.varrockTree;
                break;

            case FALADOR_TREE:
                patchArea = PatchAreas.faladorTree;
                break;

            case TAVERLEY_TREE:
                patchArea = PatchAreas.taverlyTree;
                break;

            case GNOME_STRONGHOLD_TREE:
                patchArea = PatchAreas.gnomeStrongholdTree;
                break;

            case FARMING_GUILD_TREE:
                patchArea = PatchAreas.farmingGuildTree;
                break;

            default:
                LOGGER.info("farm spot: " + getFarmSpotYouAreAt() + " is not a tree farming spot");
                return false;
        }

        if (!ctx.objects.select(15).within(patchArea).action("Clear","Check-health","Rake","Chop down").isEmpty()) {
            done = false;
        } else if (!ctx.objects.select(15).within(patchArea).name(Objects.CLEAR_TREE_PATCH).isEmpty()) {
            done = false;
        }
        return done;
    }

    private ArrayList<Component> getComponentsToDeposit() {

        ArrayList<Component> components = new ArrayList<>();

        if (!ctx.widgets.component(Widgets.FARMING_EQUIPMENT_STORE_INVENTORY,Components.FOLDER_INV_BUCKETS,Components.SUB_AMOUNT).text().equals("0")) {
            components.add(ctx.widgets.component(Widgets.FARMING_EQUIPMENT_STORE_INVENTORY,Components.FOLDER_INV_BUCKETS));
        }
        if (!ctx.widgets.component(Widgets.FARMING_EQUIPMENT_STORE_INVENTORY,Components.FOLDER_INV_COMPOST,Components.SUB_AMOUNT).text().equals("0")) {
            components.add(ctx.widgets.component(Widgets.FARMING_EQUIPMENT_STORE_INVENTORY,Components.FOLDER_INV_COMPOST));
        }
        if (!ctx.widgets.component(Widgets.FARMING_EQUIPMENT_STORE_INVENTORY,Components.FOLDER_INV_SUPERCOMPOST,Components.SUB_AMOUNT).text().equals("0")) {
            components.add(ctx.widgets.component(Widgets.FARMING_EQUIPMENT_STORE_INVENTORY,Components.FOLDER_INV_SUPERCOMPOST));
        }
        if (!ctx.widgets.component(Widgets.FARMING_EQUIPMENT_STORE_INVENTORY,Components.FOLDER_INV_ULTRACOMPOST,Components.SUB_AMOUNT).text().equals("0")) {
            components.add(ctx.widgets.component(Widgets.FARMING_EQUIPMENT_STORE_INVENTORY,Components.FOLDER_INV_ULTRACOMPOST));
        }
        if (!compostTracker.isUsingBottomlessBucket() &&
                !ctx.widgets.component(Widgets.FARMING_EQUIPMENT_STORE_INVENTORY,Components.FOLDER_INV_BOTTOMLESS_BUCKET,Components.SUB_AMOUNT).text().equals("0")) {
            components.add(ctx.widgets.component(Widgets.FARMING_EQUIPMENT_STORE_INVENTORY,Components.FOLDER_INV_BOTTOMLESS_BUCKET));
        }
        return components;
    }

    public FarmingSpot getFarmSpotYouAreAt() {
        if (PatchAreas.ardounge.containsOrIntersects(ctx.players.local())) return FarmingSpot.ARDOUGNE_HAF;
        if (PatchAreas.falador.containsOrIntersects(ctx.players.local())) return FarmingSpot.FALADOR_HAF;
        if (PatchAreas.canifis.containsOrIntersects(ctx.players.local())) return FarmingSpot.CANIFIS_HAF;
        if (PatchAreas.catherby.containsOrIntersects(ctx.players.local())) return FarmingSpot.CATHERBY_HAF;
        if (PatchAreas.hosidius.containsOrIntersects(ctx.players.local())) return FarmingSpot.HOSIDIUS_HAF;
        if (PatchAreas.camelotHopsArea.containsOrIntersects(ctx.players.local())) return FarmingSpot.CAMELOT_HOPS;
        if (PatchAreas.yanilleHopsArea.containsOrIntersects(ctx.players.local())) return FarmingSpot.YANILLE_HOPS;
        if (PatchAreas.entranaHopsArea.containsOrIntersects(ctx.players.local())) return FarmingSpot.ENTRANA_HOPS;
        if (PatchAreas.championGuildHopsArea.containsOrIntersects(ctx.players.local())) return FarmingSpot.CHAMP_GUILD_HOPS;
        if (PatchAreas.gnomeStrongholdFTArea.containsOrIntersects(ctx.players.local())) return FarmingSpot.GNOME_STRONGHOLD_FT;
        if (PatchAreas.brimhavenFTArea.containsOrIntersects(ctx.players.local())) return FarmingSpot.BRIMHAVEN_FT;
        if (PatchAreas.catherbyFTArea.containsOrIntersects(ctx.players.local())) return FarmingSpot.CATHERBY_FT;
        if (PatchAreas.lletyaFTArea.containsOrIntersects(ctx.players.local())) return FarmingSpot.LLETYA_FT;
        if (PatchAreas.gnomeVillageFTArea.containsOrIntersects(ctx.players.local())) return FarmingSpot.GNOME_VILLAGE_FT;
        if (PatchAreas.farmingGuildFTArea.containsOrIntersects(ctx.players.local())) return FarmingSpot.FARMING_GUILD_FT;
        return FarmingSpot.NONE;
    }
}
