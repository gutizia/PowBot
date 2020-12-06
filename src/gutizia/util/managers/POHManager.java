package gutizia.util.managers;

import gutizia.util.Interact;
import gutizia.util.constants.Bounds;
import gutizia.util.constants.LevelRequirements;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;
import gutizia.util.constants.Items;
import gutizia.util.constants.Widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class POHManager extends ClientAccessor {
    private final static Logger LOGGER = Logger.getLogger("POHManager");

    public enum Jewellery {CASTLE_WARS,FARMING_GUILD,DRAYNOR_VILLAGE,FISHING_GUILD,BURTHORPE,FALADOR_PARK,GRAND_EXCHANGE,CHAMPION_GUILD}
    public enum Portal {
        CAMELOT("camelot"),LUMBRIDGE("lumbridge"),FALADOR("falador"),VARROCK("varrock"),CANIFIS("kharyrll"),
        WATCHTOWER("Watchtower");

        private String value;

        Portal(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    public enum Xeric {HOSIDIUS}

    private final Widget HOUSE_ADVERTISEMENT = ctx.widgets.widget(52);

    private static ArrayList<String> blackListedHosts = new ArrayList<>();

    private String lastHost = "";

    public POHManager(ClientContext ctx) {
        super(ctx);
    }

    /**
     * gets you to house. either by using spell or breaking tablet
     */
    // TODO maybe delete because replaced with task
    public void teleportToHouse() {
        if (isOutsideHouse() || ctx.controller.isStopping()) {
            return;
        }

        if (!ctx.inventory.select().id(Items.TELEPORT_TO_HOUSE_TABLET).isEmpty()) {
            LOGGER.info("breaking POH tablet");
            ctx.inventory.select().id(Items.TELEPORT_TO_HOUSE_TABLET).poll().interact("Break");

        } else if (ctx.skills.realLevel(Constants.SKILLS_MAGIC) >= LevelRequirements.TELEPORT_TO_HOUSE) {
            LOGGER.info("casting magic spell: teleport to house");
            ctx.game.tab(Game.Tab.MAGIC);
            ctx.widgets.component(218,28).click();
            ctx.game.tab(Game.Tab.INVENTORY);
        }

        Condition.wait(this::isOutsideHouse,600,10);
    }

    public boolean isInsideHouse() {
        return !ctx.objects.select().name("Portal").action("Remove board advert").isEmpty();
    }

    public void leaveHouse() {
        LOGGER.info("leaving house");
        final int[] portalBounds = new int[] {-4, 16, -148, -36, -72, 68};
        final GameObject portal = ctx.objects.select(25).name("Portal").action("Lock").nearest().poll();
        portal.bounds(portalBounds);

        if (portal.tile().distanceTo(ctx.players.local()) > 6) {
            ctx.movement.step(portal);
        }

        if (!portal.inViewport()) {
            ctx.camera.turnTo(portal);
        }

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return portal.inViewport();
            }
        },600,7);

        portal.interact(false,"Enter",portal.name());

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return isOutsideHouse();
            }
        },900,7);

    }

    public void useXericsTalisman() {
        if (ctx.controller.isStopping() || !isInsideHouse()) {
            return;
        }
        LOGGER.info("trying to use Xeric's talisman...");

        if (!ctx.widgets.component(Widgets.XERICS_TALISMAN,0).visible()) {
            final GameObject talisman = ctx.objects.select(20).name("Xeric's Talisman").poll();

            if (talisman.equals(ctx.objects.nil())) {
                LOGGER.info("no xeric's talisman found at this house, exiting house");
                blackListHost();
                leaveHouse();
                return;
            }

            if (!talisman.inViewport()) {
                ctx.movement.step(talisman);
                ctx.camera.turnTo(talisman);
            }
            talisman.bounds(Bounds.XERICS_TALISMAN);

            if (!talisman.inViewport()) {
                ctx.camera.turnTo(talisman);

                if (talisman.tile().distanceTo(ctx.players.local()) > 5) {
                    ctx.movement.step(talisman);
                    Condition.wait(() -> talisman.tile().distanceTo(ctx.players.local()) > 5,500,10);
                }
            }

            if (talisman.interact(false,"Teleport menu",talisman.name())) {
                Condition.wait(() -> ctx.widgets.widget(Widgets.XERICS_TALISMAN).valid(),600, 8);
            }
        }

        if (ctx.widgets.component(Widgets.XERICS_TALISMAN,0).visible()) {

            ctx.input.send("2");

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return !isInsideHouse();
                }
            }, 600, 6);

        }
    }

    public void useOrnateJewelleryBox(Jewellery jewellery) {
        if (ctx.controller.isStopping() || !isInsideHouse()) {
            return;
        }

        if (!ctx.widgets.component(Widgets.ORNATE_JEWELLERY_BOX,0).visible()) {
            final GameObject box = ctx.objects.select(20).name("Ornate Jewellery Box").poll();


            if (box.equals(ctx.objects.nil())) {
                LOGGER.info("no ornate jewellery box found at this house, exiting house");
                blackListHost();
                leaveHouse();
                return;
            }

            LOGGER.info("ornate box tile = " + box.tile());

            if (box.tile().distanceTo(ctx.players.local()) > 6) {
                ctx.movement.step(box.tile());
            }

            if (!box.inViewport()) {
                ctx.camera.turnTo(box);
            }

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return box.inViewport();
                }
            },600,10);


            if (!box.interact(false,"Teleport",box.name())) {
                ctx.camera.turnTo(box);
                useOrnateJewelleryBox(jewellery);
            }

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return ctx.widgets.widget(Widgets.ORNATE_JEWELLERY_BOX).valid();
                }
            },600,8);
        }

        if (ctx.widgets.component(Widgets.ORNATE_JEWELLERY_BOX,0).visible()) {

            switch (jewellery) {
                case FARMING_GUILD:
                    ctx.input.send("i");
                    break;

                case CASTLE_WARS:
                    ctx.input.send("2");
                    break;

                case DRAYNOR_VILLAGE:
                    ctx.input.send("p");
                    break;

                case FISHING_GUILD:
                    ctx.input.send("d");
                    break;

                case BURTHORPE:
                    ctx.input.send("4");
                    break;

                case FALADOR_PARK:
                    ctx.input.send("l");
                    break;

                case GRAND_EXCHANGE:
                    ctx.input.send("k");
                    break;

                case CHAMPION_GUILD:
                    ctx.input.send("a");
                    break;
            }

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return !isInsideHouse();
                }
            },600,6);

        }
    }

    public void useNexusPortal(final Portal portal) {
        if (ctx.controller.isStopping() || !isInsideHouse()) {
            return;
        }
        LOGGER.info("trying to use nexus portal...");

        if (!ctx.widgets.component((Widgets.PORTAL_NEXUS),0).visible()) {
            final GameObject nexusPortal = ctx.objects.select().name("Portal Nexus").poll();

            if (nexusPortal.equals(ctx.objects.nil())) {
                LOGGER.info("no nexus portal found at this house, exiting house");
                blackListHost();
                leaveHouse();
                return;
            }

            if (nexusPortal.tile().distanceTo(ctx.players.local()) > 7) {
                ctx.movement.step(nexusPortal);
            }

            if (!nexusPortal.inViewport()) {
                ctx.camera.turnTo(nexusPortal);
            }

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return nexusPortal.inViewport();
                }
            },600,10);

            nexusPortal.interact(false,"Teleport Menu",nexusPortal.name());
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return ctx.widgets.widget(Widgets.PORTAL_NEXUS).valid();
                }
            },600,8);
        }

        if (ctx.widgets.component((Widgets.PORTAL_NEXUS),0).visible()) {
            if (ctx.components.select(Widgets.PORTAL_NEXUS,12).textContains(portal.getValue()).isEmpty()) {
                LOGGER.info("portal nexus did not have teleport " + portal);
                blackListHost();
                leaveHouse();
                return;
            }

            int index = ctx.components.select(Widgets.PORTAL_NEXUS,12).textContains(portal.getValue()).poll().index();

            if (index >= 0 && index <= 8) {
                ctx.input.send(Integer.toString(index + 1));

            } else {
                switch (index) {
                    case 9:
                        ctx.input.send("a");
                        break;

                    case 10:
                        ctx.input.send("b");
                        break;

                    case 11:
                        ctx.input.send("c");
                        break;

                    case 12:
                        ctx.input.send("d");
                        break;

                    case 13:
                        ctx.input.send("e");
                        break;

                    case 14:
                        ctx.input.send("f");
                        break;

                    case 15:
                        ctx.input.send("g");
                        break;

                    case 16:
                        ctx.input.send("h");
                        break;

                    case 17:
                        ctx.input.send("i");
                        break;
                }
            }

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    return !isInsideHouse();
                }
            },600,6);

        }
    }

    public void replenishAtPool() {
        if (ctx.movement.energyLevel() >= 85 || ctx.controller.isStopping()) {
            return;
        }
        LOGGER.info("replenishing energy at pool");

        GameObject pool = ctx.objects.select(10).name("Ornate rejuvenation pool").poll();

        if (!pool.inViewport()) {
            ctx.camera.turnTo(pool);
        }

        pool.interact("Drink");

        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return ctx.movement.energyLevel() == 100;
            }
        }, 1200, 10);
    }

    public void viewHouseAdvertisement() {
        LOGGER.info("viewing house advertisement");
        if (ctx.controller.isStopping()) {
            return;
        }

        if (!isOutsideHouse()) {
            LOGGER.info("not outside house, stopping viewing house");
            return;
        }

        if (ctx.widgets.component(HOUSE_ADVERTISEMENT.id(),1).visible()) {
            LOGGER.info("already viewing house advertisement");
            return;
        }

        if (WorldManager.worldManager.getCurrentWorld() != 330) {
            WorldManager.worldManager.hopToWorld(330);
        }

        int[] bounds = new int[] {-24, 32, -212, -156, -16, 32};
        final GameObject object = ctx.objects.select(10).name("House Advertisement").nearest().poll();

        Interact.interact(ctx, object, false, "View", false);
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return ctx.widgets.component(HOUSE_ADVERTISEMENT.id(),1).visible();
            }
        },600,5);

    }

    public void getInsideHostHouse() {
        LOGGER.info("checking hosts");
        if (ctx.controller.isStopping()) {
            return;
        }

        if (!ctx.widgets.component(HOUSE_ADVERTISEMENT.id(),1).visible()) {
            viewHouseAdvertisement();
        }

        String[] hosts;
        Component[] validHosts = getListedHouses();

        validHosts = filterBlackListedHosts(validHosts);
        validHosts = filterNotInRimmington(validHosts);
        validHosts = filterGildedAltar(validHosts);
        validHosts = filterJewelleryBox(validHosts);
        validHosts = filterPool(validHosts);

        if (validHosts.length == 0) {
            LOGGER.info("no more valid hosts!");
            LOGGER.info("resetting black listed list and trying again in 10 seconds...");
            blackListedHosts = new ArrayList<>();
            Condition.sleep(10000);
            getInsideHostHouse();
            return;
        }

        hosts = new String[validHosts.length];

        // sort hosts from top to bottom
        validHosts = sortByY(validHosts);

        for (int i = 0;i < validHosts.length;i++) {
            hosts[i] = validHosts[i].text();
        }

        for (int i = 0; i < hosts.length;i++) { // loops through each valid host
            String host = hosts[i];
            LOGGER.info("trying to enter house of host: " + host);

            Component component = getEnterHouseComponent(validHosts[i].screenPoint().y);
            if (component == null) {
                LOGGER.info("did not find enter house button for this host, trying next...");
                continue;
            }

            lastHost = host;
            component.click();

            Condition.wait(() -> !component.visible(),600,5);

            if (!isInsideHouse() && !ctx.widgets.widget(71).valid()) { // if neither inside POH or loading house widget is valid
                if (!ctx.widgets.widget(HOUSE_ADVERTISEMENT.id()).valid()) { // if thrown out of house advertisement widget when trying to enter house
                    viewHouseAdvertisement();
                }
            }

            if (isInsideHouse() || ctx.widgets.widget(71).valid()) {
                break;
            }
        }

        Condition.wait(this::isInsideHouse,1000,5);
    }

    /**
     *
     * @param y the y coordinate of the corresponding host component
     * @return returns the enter house button that corresponds with {@param y}
     */
    private Component getEnterHouseComponent(int y) {
        for (final Component c : ctx.widgets.component(HOUSE_ADVERTISEMENT.id(), 19).components()) {
            if (c.screenPoint().y == y + 2) {
                return c;
            }
        }
        return null;
    }

    /**'
     * sorts an array of components by their screenPoint.y, ascending order (in game: from top to bottom)
     * @param oldArray the unsorted array
     * @return a sorted {@param oldArray}
     */
    private Component[] sortByY(Component[] oldArray) {
        ArrayList<Component> components = new ArrayList<>(Arrays.asList(oldArray));
        Component[] newArray = new Component[oldArray.length];

        for (int i = 0; i < oldArray.length;i++) {
            int y = 1000;
            Component component = null;

            // gets y coordinate of component with lowest y value
            for (Component c : components) {
                if (c.screenPoint().y < y) {
                    y = c.screenPoint().y;
                }
            }

            // gets the component with that y coordinate
            for (Component c : components) {
                if (c.screenPoint().y == y) {
                    component = c;
                }
            }
            if (component == null) {
                LOGGER.info("broken sortByY algorithm!, did not find component with y coordinate: " + y);
            }

            newArray[i] = component;
            components.remove(component);
        }

        return newArray;
    }

    /**
     * designed to get you inside house, regardless of where you are and what stage you are at
     * @return returns true if inside house at end of method
     */
    public boolean getInsideHouse() {
        if (ctx.controller.isStopping()) {
            return false;
        }

        if (!isOutsideHouse() && !isInsideHouse()) {
            LOGGER.info("was neither inside nor outside of house, trying to teleport to house now");
            teleportToHouse();
        }

        if (isOutsideHouse()) {
            LOGGER.info("is outside house, getting inside now");

            if (WorldManager.worldManager.getCurrentWorld() != 330) {
                WorldManager.worldManager.hopToWorld(330);
            }

            if (!ctx.widgets.component(HOUSE_ADVERTISEMENT.id(),1).visible()) {
                viewHouseAdvertisement();
            }

            if (ctx.widgets.component(HOUSE_ADVERTISEMENT.id(),1).visible()) {
                getInsideHostHouse();
            }
        }

        return isInsideHouse();
    }

    /**
     * checks the 20 first host names components and returns all valid and not blacklisted hosts
     * @return an array of components which contains all valid host names, minus all blacklisted hosts
     */
    private Component[] getListedHouses() {
        LOGGER.info("getting listed houses");
        ArrayList<Component> list = new ArrayList<Component>();

        int foo = 0;
        for (Component component : ctx.components.select(HOUSE_ADVERTISEMENT.id(),9)) {
            if (component.text() != null && component.text().length() >= 3) {
                boolean blacklisted = false;

                for (String blackList : blackListedHosts) {
                    if (component.text().equals(blackList)){
                        LOGGER.info(blackList + " is blacklisted, excluding them from list of valid hosts");
                        blacklisted = true;
                    }
                }
                if (!blacklisted) {
                    list.add(component);
                }
            }
            if (foo++ >= 20) {
                break;
            }
        }

        Component[] validHouses = new Component[list.size()];

        for (int i = 0;i < validHouses.length;i++) {
            validHouses[i] = list.get(i);
        }

        return validHouses;
    }

    private Component[] filterBlackListedHosts(Component[] houses) {
        ArrayList<Component> list = new ArrayList<Component>();

        if (blackListedHosts.isEmpty()) {
            return houses;
        }

        int foo = 0;
        for (Component component : ctx.components.select(HOUSE_ADVERTISEMENT.id(),9)) {
            boolean matchFound = false;
            for (String blackListedHost : blackListedHosts) {
                if (blackListedHost.equals(component.text())) {
                    matchFound = true;
                }
            }

            if (component.text() != null && !matchFound) {
                for (Component comp : houses) {
                    if (comp.screenPoint().y == component.screenPoint().y) {
                        list.add(comp);
                    }
                }
            }
            if (foo++ >= 20) {
                break;
            }
        }

        Component[] validHouses = new Component[list.size()];

        for (int i = 0;i < validHouses.length;i++) {
            validHouses[i] = list.get(i);
        }
        return validHouses;
    }

    private Component[] filterNotInRimmington(Component[] houses) {
        ArrayList<Component> list = new ArrayList<Component>();

        int foo = 0;
        for (Component component : ctx.components.select(HOUSE_ADVERTISEMENT.id(),10)) {
            if (component.text() != null && component.text().contains("RIM")) {

                for (Component comp : houses) {
                    if (comp.screenPoint().y == component.screenPoint().y) {
                        list.add(comp);
                    }
                }
            }
            if (foo++ >= 20) {
                break;
            }
        }

        Component[] validHouses = new Component[list.size()];

        for (int i = 0;i < validHouses.length;i++) {
            validHouses[i] = list.get(i);
        }
        return validHouses;
    }

    private Component[] filterJewelleryBox(Component[] houses) {
        ArrayList<Component> list = new ArrayList<Component>();

        int foo = 0;
        for (Component component : ctx.components.select(HOUSE_ADVERTISEMENT.id(),15)) {
            if (component.text() != null && component.text().contains("3")) {

                for (Component comp : houses) {
                    if (comp.screenPoint().y == component.screenPoint().y) {
                        list.add(comp);
                    }
                }
            }
            if (foo++ >= 20) {
                break;
            }
        }

        Component[] validHouses = new Component[list.size()];

        for (int i = 0;i < validHouses.length;i++) {
            validHouses[i] = list.get(i);
        }
        return validHouses;
    }

    private Component[] filterGildedAltar(Component[] houses) {
        ArrayList<Component> list = new ArrayList<Component>();

        int foo = 0;
        for (Component component : ctx.components.select(HOUSE_ADVERTISEMENT.id(),13)) {
            if (component.text() != null && component.text().contains("Y")) {

                for (Component comp : houses) {
                    if (comp.screenPoint().y == component.screenPoint().y) {
                        list.add(comp);
                    }
                }
            }
            if (foo++ >= 20) {
                break;
            }
        }

        Component[] validHouses = new Component[list.size()];

        for (int i = 0;i < validHouses.length;i++) {
            validHouses[i] = list.get(i);
        }
        return validHouses;
    }

    private Component[] filterPool(Component[] houses) {
        ArrayList<Component> list = new ArrayList<Component>();

        int foo = 0;
        for (Component component : ctx.components.select(HOUSE_ADVERTISEMENT.id(),16)) {
            if (component.text() != null && component.text().contains("5")) {

                for (Component comp : houses) {
                    if (comp.screenPoint().y == component.screenPoint().y) {
                        list.add(comp);
                    }
                }
            }
            if (foo++ >= 20) {
                break;
            }
        }

        Component[] validHouses = new Component[list.size()];

        for (int i = 0;i < validHouses.length;i++) {
            validHouses[i] = list.get(i);
        }
        return validHouses;
    }

    public boolean isOutsideHouse() {
        final Area area = new Area(new Tile(2946,3216,0), new Tile(2963,3230,0));
        return area.containsOrIntersects(ctx.players.local());
    }

    private void blackListHost() {
        LOGGER.info("blacklisting host: " + lastHost);
        blackListedHosts.add(lastHost);
        lastHost = "";
    }
}