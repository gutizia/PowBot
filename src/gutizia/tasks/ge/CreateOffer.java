package gutizia.tasks.ge;

import gutizia.tasks.Activatable;
import gutizia.tasks.Task;
import gutizia.util.constants.Widgets;
import gutizia.util.ge.GrandExchange;
import gutizia.util.ge.Offer;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.Component;

public class CreateOffer extends Task {

    private Activatable activatable;
    private Offer offer;
    private int offerComponent;

    private final static int C_BUY = 0;
    private final static int C_SELL = 1;
    private final static int C_CREATE_OFFER_CONFIRM = 54;

    public CreateOffer(Activatable activatable, Offer offer) {
        this.activatable = activatable;
        this.offer = offer;
    }

    @Override
    public void execute() {
        startOffer();
        GrandExchange.handleAmount(offer.getItem().getAmount());
        GrandExchange.handlePrice(offer.getPrice());

        ctx.widgets.component(GrandExchange.W_MAIN, GrandExchange.C_CREATE_OFFER, C_CREATE_OFFER_CONFIRM).click();
        Condition.wait(() -> ctx.widgets.component(GrandExchange.W_MAIN, offerComponent, GrandExchange.C_OFFER_WINDOW_ITEM).itemId() ==
                offer.getItem().getSharedId(), 600, 4);

        if (ctx.widgets.component(GrandExchange.W_MAIN, offerComponent, GrandExchange.C_OFFER_WINDOW_ITEM).itemId() ==
                offer.getItem().getSharedId()) {
            Offer[] offers = GrandExchange.getCurrentOffers();
            offers[offerComponent - GrandExchange.C_FIRST_OFFER] = offer;
            GrandExchange.setCurrentOffers(offers);
        }
    }

    private void startOffer() {
        Component component = null;
        if (offer.isBuy()) {
            ctx.widgets.component(GrandExchange.W_MAIN, offerComponent, C_BUY).click();
            Condition.wait(() -> ctx.widgets.component(Widgets.CHAT_BOX, GrandExchange.C_BUY_OFFER_CHAT).text().contains("would you like to buy"), 300, 8);

            ctx.input.send(offer.getItem().getName());
            Condition.wait(() -> getBuyItemComponent() != null, 600, 10);

            component = getBuyItemComponent();

        } else {
            ctx.widgets.component(GrandExchange.W_MAIN, offerComponent, C_SELL).click();
            Condition.wait(() -> ctx.widgets.component(GrandExchange.W_MAIN, GrandExchange.C_CREATE_OFFER).visible(), 300, 8);

            for (Component c : ctx.widgets.component(GrandExchange.W_INV, 0).components()) {
                if (c.itemId() == offer.getItem().getSharedId()) {
                    component = c;
                    break;
                }
            }
        }

        if (component != null) {
            component.click();
        }

        Condition.wait(() -> ctx.widgets.component(GrandExchange.W_MAIN, GrandExchange.C_CREATE_OFFER,
                GrandExchange.C_CREATE_OFFER_ITEM).itemId() == offer.getItem().getSharedId(), 600, 10);
    }

    private Component getBuyItemComponent() {
        for (Component component : ctx.widgets.component(Widgets.CHAT_BOX, GrandExchange.CF_BUY_OFFER_CHAT_RESULT).components()) {
            if (component.itemId() == offer.getItem().getId() && component.visible()) {
                return component;
            }
        }
        return null;
    }

    private int getOfferComponent() {
        for (int i = GrandExchange.C_FIRST_OFFER; i <= (ctx.client().isMembers() ? GrandExchange.C_LAST_OFFER_MEMBERS : GrandExchange.C_LAST_OFFER_FREE); i++) {
            if (ctx.widgets.component(GrandExchange.W_MAIN, i, 0).visible()) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean activate() {
        if (!ctx.widgets.widget(GrandExchange.W_MAIN).valid()) {
            return false;
        }

        // if no free offers and this offer is not higher prio than any of the offers
        if ((offerComponent = getOfferComponent()) == -1) {
            int lowestPrio = Integer.MAX_VALUE;
            for (Offer offer : GrandExchange.getCurrentOffers()) {
                if (offer.getPriority() < lowestPrio) {
                    lowestPrio = offer.getPriority();
                }
            }
            if (offer.getPriority() <= lowestPrio) {
                return false;
            }
        }

        return activatable.activate();
    }
}
