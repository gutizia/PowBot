package gutizia.util.ge;

import gutizia.scripts.Script;
import gutizia.util.Item;
import gutizia.util.constants.Widgets;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Component;

import java.util.logging.Logger;

public class GrandExchange {
    private static ClientContext ctx = org.powerbot.script.ClientContext.ctx();
    private final static Logger LOGGER = Logger.getLogger("GrandExchange");
    public final static Area AREA = new Area(new Tile(3155, 3480, 0), new Tile(3175, 3500, 0));
    public final static Tile TILE = new Tile(3164, 3487, 0);

    public final static int W_MAIN = 465;
    public final static int W_INV = 467;
    public final static int W_COLLECTION = 402;

    public final static int C_MAIN_CLOSE = 1;
    public final static int C_OFFER_IN_PROGRESS = 15;
    public final static int C_CREATE_OFFER = 24;
    public final static int C_BUY_OFFER_CHAT = 45;
    public final static int CF_BUY_OFFER_CHAT_RESULT = 53;
    public final static int C_FIRST_OFFER = 7;
    public final static int C_LAST_OFFER_FREE = 9;
    public final static int C_LAST_OFFER_MEMBERS = 14;
    public final static int C_OFFER_WINDOW_ITEM = 18;
    public final static int C_OFFER_WINDOW_ITEM_NAME = 19;
    public final static int C_OFFER_WINDOW_OFFER_PRICE = 25;
    public final static int C_OFFER_WINDOW_OFFER_TYPE = 16;

    public final static int C_COLL_FIRST_OFFER = 5;
    public final static int C_COLL_LAST_OFFER_MEMBERS = 7;
    public final static int C_COLL_LAST_OFFER_FREE = 12;


    private static boolean offerToCollect = false;

    public final static int C_CREATE_OFFER_ITEM = 21;

    private static boolean offersSet = false;


    private static Offer[] currentOffers = new Offer[8];


    public static void handlePrice(Price price) {
        if (!ctx.widgets.component(W_MAIN, C_CREATE_OFFER).visible()) {
            LOGGER.info("something unexpected happened when setting price for offer");
            return;
        }
        int currPrice = getOfferPrice();
        Component component = ctx.widgets.component(W_MAIN, C_CREATE_OFFER, 12);
        component.click();

        Condition.wait(() -> ctx.widgets.component(Widgets.CHAT_BOX, 44).text().contains("Set a price for each item"), 500, 10);
        Condition.sleep(Random.nextInt(900, 1400));
        ctx.input.sendln(Integer.toString(price.getPrice()));

        Condition.wait(() -> currPrice != getOfferPrice(), 600, 4);

        if (currPrice == getOfferPrice()) {
            cancelOffer();
        }
    }

    public static void initOffers() {
        System.out.println("setting offers");
        for (int i = C_FIRST_OFFER; i <= (ctx.client().isMembers() ? C_LAST_OFFER_MEMBERS : C_LAST_OFFER_FREE); i++) {
            if (ctx.widgets.component(W_MAIN, i, 0).visible()) {
                System.out.println("no offer for index " + (i - C_FIRST_OFFER) + " found, initing empty offer");
                currentOffers[i - C_FIRST_OFFER] = Offer.EMPTY;
                continue;
            }

            Item item = new Item(
                    ctx.widgets.component(W_MAIN, i, C_OFFER_WINDOW_ITEM).itemId(),
                    ctx.widgets.component(W_MAIN, i, C_OFFER_WINDOW_ITEM_NAME).text(),
                    ctx.widgets.component(W_MAIN, i, C_OFFER_WINDOW_ITEM).itemStackSize()
            );
            Price price = new Price(item.getId()).setCustomPrice(filterOfferPrice(ctx.widgets.component(W_MAIN, i, C_OFFER_WINDOW_OFFER_PRICE).text()) / item.getAmount());
            boolean buy = ctx.widgets.component(W_MAIN, i, C_OFFER_WINDOW_OFFER_TYPE).text().equalsIgnoreCase("buy");
            currentOffers[i - C_FIRST_OFFER] = new Offer(item, price, buy, 10);
            System.out.println("offer for index " + (i - C_FIRST_OFFER) + " found: " + item.getName() + ", price: " + price.getPrice() + " amount: " + item.getAmount() + " buy = " + buy);
        }
        offersSet = true;
    }

    public static void cancelOffer() {

    }

    public static int filterOfferPrice(String text) {
        text = text.split(" ")[0];
        return Integer.parseInt(text.replaceAll(",", ""));
    }

    public static int getOfferPrice() {
        if (ctx.widgets.component(W_MAIN, C_CREATE_OFFER).visible()) {
            return filterOfferPrice(ctx.widgets.component(W_MAIN, C_CREATE_OFFER, 39).text());
        }

        return -1;
    }

    public static void handleAmount(int amount) {
        int currAmount = Integer.parseInt(ctx.widgets.component(GrandExchange.W_MAIN, GrandExchange.C_CREATE_OFFER, 32).text());

        if (currAmount != amount) {
            ctx.widgets.component(GrandExchange.W_MAIN, GrandExchange.C_CREATE_OFFER, 7).click();
            Condition.wait(() -> ctx.widgets.component(Widgets.CHAT_BOX, 44).text().contains("How many do you wish to"), 800, 5);

            ctx.input.sendln(Integer.toString(amount));
            Condition.wait(() -> Integer.parseInt(ctx.widgets.component(GrandExchange.W_MAIN, GrandExchange.C_CREATE_OFFER, 32).text())
                    == amount, 600, 4);
        }
    }

    public static Offer[] getCurrentOffers() {
        return currentOffers;
    }

    public static void setCurrentOffers(Offer[] currentOffers) {
        GrandExchange.currentOffers = currentOffers;
    }

    public static boolean isOffersSet() {
        return offersSet;
    }

    public static void setOfferToCollect(boolean offerToCollect) {
        GrandExchange.offerToCollect = offerToCollect;
    }

    public static boolean isOfferToCollect() {
        return offerToCollect;
    }
}
