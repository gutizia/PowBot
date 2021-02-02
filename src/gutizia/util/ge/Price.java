package gutizia.util.ge;

import org.powerbot.script.rt4.GeItem;

import java.util.ArrayList;

public class Price {
    private int price;

    enum Action {
        PLUS_5_PERCENT, MINUS_5_PERCENT, PLUS_1, MINUS_1, CUSTOM_PRICE
    }

    public Price(int itemId) {
        price = GeItem.getPrice(itemId);
    }

    public Price(long price) {
        this.price = (int)price;
    }

    public Price setCustomPrice(int customPrice) {
        price = customPrice;
        return this;
    }

    public Price plusFivePercent() {
        float f = (float)price / 100;
        price += Math.floor(f * 5);
        return this;
    }

    public Price minusFivePercent() {
        float f = (float)price / 100;
        price -= Math.floor(f * 5);
        return this;
    }

    public Price plusOne() {
        price++;
        return this;
    }

    public Price minusOne() {
        price--;
        return this;
    }

    public int getPrice() {
        return price;
    }
}
