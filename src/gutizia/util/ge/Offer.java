package gutizia.util.ge;

import gutizia.util.Item;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Offer {
    private Item item;
    private Price price;
    private boolean buy;
    private int priority;
    private int buyLimit;
    private BufferedImage bufferedImage;
    private boolean members;
    private int dailyVolume;
    private boolean sellAll = false;

    public final static Offer EMPTY = new Offer(new Item(-1), new Price(-1), true, -1);

    public Offer() {

    }

    public Offer(Item item, Price price, boolean buy, int priority) {
        this.item = item;
        this.price = price;
        this.buy = buy;
        this.priority = priority;
    }

    public Item getItem() {
        return item;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isBuy() {
        return buy;
    }

    public Price getPrice() {
        return price;
    }

    public ArrayList<String> getPrint() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add((buy ? "buy" : "sell") + " offer");
        strings.add(item.getName() + "(" + item.getAmount() + ")");
        strings.add("price: " + price.getPrice());
        strings.add("priority: " + priority);
        return strings;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public void setBuyLimit(int buyLimit) {
        this.buyLimit = buyLimit;
    }

    public void setDailyVolume(int dailyVolume) {
        this.dailyVolume = dailyVolume;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isMembers() {
        return members;
    }

    public void setMembers(boolean members) {
        this.members = members;
    }

    public int getDailyVolume() {
        return dailyVolume;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public int getBuyLimit() {
        return buyLimit;
    }

    public void setSellAll(boolean sellAll) {
        this.sellAll = sellAll;
    }

    public boolean isSellAll() {
        return sellAll;
    }
}
