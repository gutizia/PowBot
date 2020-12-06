package gutizia.util.resources;

import gutizia.util.constants.Items;
import gutizia.util.skills.farming.Farming;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static gutizia.util.trackers.FruitTreeTracker.fruitTreeTracker;
import static gutizia.util.trackers.HerbRunTracker.herbRunTracker;
import static gutizia.util.trackers.HopsRunTracker.hopsRunTracker;
import static gutizia.util.trackers.TreeTracker.treeTracker;

public class PropertyUtil {
    public final static PropertyUtil herbs = new PropertyUtil(System.getProperty("java.io.tmpdir") + "gutHerbs.properties");
    public final static PropertyUtil flowers = new PropertyUtil(System.getProperty("java.io.tmpdir") + "gutFlowers.properties");
    public final static PropertyUtil allotments = new PropertyUtil(System.getProperty("java.io.tmpdir") + "gutAllotments.properties");
    public final static PropertyUtil hops = new PropertyUtil(System.getProperty("java.io.tmpdir") + "gutHops.properties");
    public final static PropertyUtil fruitTrees = new PropertyUtil(System.getProperty("java.io.tmpdir") + "gutFruitTrees.properties");
    public final static PropertyUtil trees = new PropertyUtil(System.getProperty("java.io.tmpdir") + "gutNormalTrees.properties");

    public final static String USE_PREF = "use.preference";
    public final static String LOGIN_DONE = "login.when.done";
    public final static String ACTIVATE = "active";
    public final static String PRIO = "priority";
    public final static String COMPOST = "compost";
    public final static String NEXT_HARVEST = "next.harvest";

    private Map<String, String> defaultProperties;
    private final String absolutePath;
    private Properties properties;
    private FileInputStream fis;
    private FileOutputStream fos;

    public PropertyUtil(String absolutePath, Map<String, String> defaultProperties) {
        this.absolutePath = absolutePath;
        this.defaultProperties = defaultProperties;
        this.properties = new Properties();
        init();
    }

    public PropertyUtil(String path) {
        this.absolutePath = path;
        this.properties = new Properties();
        createFile();
    }

    private void createFile() {
        try {
            File file = new File(absolutePath);
            if (!file.exists()) {
                System.out.println("file " + absolutePath + " did not exist");
                if (!file.createNewFile()) {
                    System.err.println("failed to create new file: " + absolutePath);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        try {
            File file = new File(absolutePath);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new Exception("unable to create new file");
                }
            }

            if (isPropertiesFileIncomplete()) {
                setPropertiesToDefault();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isPropertiesFileIncomplete() {
        try {
            fis = new FileInputStream(absolutePath);
            properties.load(fis);

            if (properties.size() < defaultProperties.size()) {
                System.out.println("properties size is less than defaultProperties size");
                return true;
            }
            int i = 0;
            for (Map.Entry<Object, Object> property : properties.entrySet()) {
                for (Map.Entry<String, String> defaultProp : defaultProperties.entrySet()) {
                    if (property.getKey().toString().equals(defaultProp.getKey())) {
                        i++;
                    }
                }
            }
            if (i < properties.size()) {
                System.out.println("properties did not contain all defaultProperties keys (" + i + "/" + defaultProperties.size() + ")");
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return true; // I assume it's incomplete if any errors were thrown
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void setPropertiesToDefault() {
        try {
            fos = new FileOutputStream(absolutePath);
            properties.clear();
            for (Map.Entry<String, String> entry : defaultProperties.entrySet()) {
                properties.setProperty(entry.getKey(), entry.getValue());
            }
            properties.store(fos, "default properties");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getProperty(String key) {
        try {

            fis = new FileInputStream(absolutePath);
            properties.load(fis);
            return properties.getProperty(key);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public void updateProperty(String key, boolean value) {
        if (value) {
            updateProperty(key, "true");
        } else {
            updateProperty(key, "false");
        }
    }

    public void updateProperty(String key, String value) {
        try {
            fis = new FileInputStream(absolutePath);
            properties.load(fis);
            properties.remove(key);
            properties.setProperty(key, value);
            fis.close();

            fos = new FileOutputStream(absolutePath);
            properties.store(fos, "custom properties");
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveFarmingTime() {
        Farming.Patch patch;
        if (absolutePath.contains("Herbs")) {
            patch = Farming.Patch.HERB;

        } else if (absolutePath.contains("Flowers")) {
            patch = Farming.Patch.FLOWER;

        } else if (absolutePath.contains("Allotments")) {
            patch = Farming.Patch.ALLOTMENT1;

        } else if (absolutePath.contains("Hops")) {
            patch = Farming.Patch.HOPS;

        } else if (absolutePath.contains("FruitTrees")) {
            patch = Farming.Patch.FRUIT_TREE;

        } else if (absolutePath.contains("NormalTree")) {
            patch = Farming.Patch.TREE;

        } else {
            return;
        }

        int seed = getSeed(patch);
        long time = getGrowthTime(patch) * getStages(patch, seed);

        updateProperty(NEXT_HARVEST, Long.toString(time));
    }

    private int getSeed(Farming.Patch patch) {
        int[] seeds = new int[0];
        switch (patch) {
            case HERB:
                seeds = herbRunTracker.getHerbSeedUsed();
                break;

            case FLOWER:
                seeds = herbRunTracker.getFlowerSeedUsed();
                break;

            case ALLOTMENT1: case ALLOTMENT2:
                seeds = herbRunTracker.getAllotmentSeedUsed();
                break;

            case HOPS:
                seeds = hopsRunTracker.getSeedsUsed();
                break;

            case FRUIT_TREE:
                seeds = fruitTreeTracker.getSeedsUsed();
                break;

            case TREE:
                seeds = treeTracker.getSeedsUsed();
                break;
        }

        int time = 0;
        int seed = 0;
        for (int i : seeds) {
            if (getStages(patch, i) > time) {
                time = getStages(patch, i);
                seed = i;
            }
        }
        return seed;
    }

    private int getGrowthTime(Farming.Patch patch) {
        switch (patch) {
            case HERB:
                return 1200000;

            case FLOWER:
                return 300000;

            case ALLOTMENT1: case ALLOTMENT2:
                return 600000;

            case HOPS:
                return 600000;

            case FRUIT_TREE:
                return 9600000;

            case TREE:
                return 2400000;

            case CACTUS:
                return 4800000;
        }
        return -1;
    }

    private int getStages(Farming.Patch patch, int seed) {
        switch (patch) {
            case ALLOTMENT1: case ALLOTMENT2:
                switch (seed) {
                    case Items.WATERMELON_SEED:
                        return 8;
                    case Items.SNAPE_GRASS_SEED:
                        return 7;
                    case Items.SWEETCORN_SEED: case Items.STRAWBERRY_SEED:
                        return 6;
                    default:
                        return 4;
                }

            case HOPS:
                switch (seed) {
                    case Items.ASGARNIAN_SEED: case Items.JUTE_SEED:
                        return 5;
                    case Items.YANILLIAN_SEED:
                        return 6;
                    case Items.KRANDORIAN_SEED:
                        return 7;
                    case Items.WILDBLOOD_SEED:
                        return 8;
                    default:
                        return 4;
                }

            case FRUIT_TREE:
                return 6;

            case TREE:
                switch (seed) {
                    case Items.OAK_SAPLING:
                        return 5;

                    case Items.WILLOW_SAPLING:
                        return 7;

                    case Items.MAPLE_SAPLING:
                        return 8;

                    case Items.YEW_SAPLING:
                        return 10;

                    case Items.MAGIC_SAPLING:
                        return 12;
                }
                return -1;

            default: // all herbs and flowers have 4 stages
                return 4;
        }
    }

    public String getAbsolutePath() {
        return absolutePath;
    }
}
