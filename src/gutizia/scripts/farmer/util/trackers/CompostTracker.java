package gutizia.scripts.farmer.util.trackers;

import gutizia.util.constants.Items;

import java.util.logging.Logger;

import static gutizia.util.resources.PropertyUtil.*;

public class CompostTracker {
    public final static CompostTracker compostTracker = new CompostTracker();
    public final static Logger LOGGER = Logger.getLogger("CompostTracker");

    private boolean haveNormalCompost = true;
    private boolean haveSuperCompost = true;
    private boolean haveUltraCompost = true;
    private boolean usingCompost = true;
    private boolean usingBottomlessBucket = false;
    private String blCompostType;

    private int lepCompostAmount = 0;
    private int lepSuperAmount = 0;
    private int lepUltraAmount = 0;

    private CompostTracker() {
    }

    public boolean haveCompost(int compost) {
        switch (compost) {
            case Items.COMPOST:
                return haveNormalCompost;

            case Items.SUPERCOMPOST:
                return haveSuperCompost;

            case Items.ULTRACOMPOST:
                return haveUltraCompost;

            default:
                return false;
        }
    }

    public int getCompostForSeed(int seedID) {
        try {
            String compostName = "";
            for (int i = 0; i < Items.HERB_SEEDS.length; i++) {
                if (Items.HERB_SEEDS[i] == seedID) {
                    compostName =  herbs.getProperty(COMPOST).split(",")[i];
                }
            }
            for (int i = 0; i < Items.HOPS_SEEDS.length; i++) {
                if (Items.HOPS_SEEDS[i] == seedID) {
                    compostName =  hops.getProperty(COMPOST).split(",")[i];
                }
            }
            for (int i = 0; i < Items.ALLOTMENT_SEEDS.length; i++) {
                if (Items.ALLOTMENT_SEEDS[i] == seedID) {
                    compostName =  allotments.getProperty(COMPOST).split(",")[i];
                }
            }
            for (int i = 0; i < Items.FLOWER_SEEDS.length; i++) {
                if (Items.FLOWER_SEEDS[i] == seedID) {
                    compostName =  flowers.getProperty(COMPOST).split(",")[i];
                }
            }

            switch (compostName) {
                case "normal":
                    return Items.COMPOST;

                case "super":
                    return Items.SUPERCOMPOST;

                case "ultra":
                    return Items.ULTRACOMPOST;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setHaveNormalCompost(boolean haveNormalCompost) {
        this.haveNormalCompost = haveNormalCompost;
    }

    public void setHaveSuperCompost(boolean haveSuperCompost) {
        this.haveSuperCompost = haveSuperCompost;
    }

    public void setHaveUltraCompost(boolean haveUltraCompost) {
        this.haveUltraCompost = haveUltraCompost;
    }

    public void setUsingBottomlessBucket(boolean usingBottomlessBucket) {
        this.usingBottomlessBucket = usingBottomlessBucket;
    }

    public boolean isUsingBottomlessBucket() {
        return usingBottomlessBucket;
    }

    public boolean isUsingCompost() {
        return usingCompost;
    }

    public void setUsingCompost(boolean usingCompost) {
        this.usingCompost = usingCompost;
    }

    public void setBlCompostType(String blCompostType) {
        this.blCompostType = blCompostType;
    }

    public String getBlCompostType() {
        return blCompostType;
    }

    public int getLepCompostAmount() {
        return lepCompostAmount;
    }

    public int getLepSuperAmount() {
        return lepSuperAmount;
    }

    public int getLepUltraAmount() {
        return lepUltraAmount;
    }

    public void setLepCompostAmount(int lepCompostAmount) {
        this.lepCompostAmount = lepCompostAmount;
        System.out.println("new lep normal compost amount: " + lepCompostAmount);
    }

    public void setLepSuperAmount(int lepSuperAmount) {
        this.lepSuperAmount = lepSuperAmount;
        System.out.println("new lep super compost amount: " + lepSuperAmount);
    }

    public void setLepUltraAmount(int lepUltraAmount) {
        this.lepUltraAmount = lepUltraAmount;
        System.out.println("new lep ultra compost amount: " + lepUltraAmount);
    }
}
