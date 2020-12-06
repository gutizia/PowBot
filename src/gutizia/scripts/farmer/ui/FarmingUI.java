package gutizia.scripts.farmer.ui;

import gutizia.scripts.farmer.ui.panels.*;
import gutizia.scripts.farmer.ui.panels.Panel;
import gutizia.util.resources.PropertyUtil;
import gutizia.util.userInterface.TabUI;
import org.powerbot.script.rt4.ClientContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static gutizia.util.trackers.CompostTracker.compostTracker;
import static gutizia.util.trackers.FarmingTracker.farmingTracker;
import static gutizia.util.trackers.FruitTreeTracker.fruitTreeTracker;
import static gutizia.util.trackers.HerbRunTracker.herbRunTracker;
import static gutizia.util.trackers.HopsRunTracker.hopsRunTracker;
import static gutizia.util.trackers.TreeTracker.treeTracker;

public class FarmingUI extends TabUI {
    public static FarmingUI farmingUi;

    private JComboBox<String> blBucket;
    private JCheckBox useblBucket;
    private JCheckBox teleportWithSpell;

    private final static String BL_CONTENT = "bottomless.bucket.content";
    private final static String USE_BL = "use.bottomless.bucket";
    private final static String TELEPORT_WITH_SPELL = "teleport.with.spell";

    private PropertyUtil propertyUtil;

    private ArrayList<Panel> panels;

    public FarmingUI(ClientContext ctx) {
        super(ctx, "gutFarmer");
        propertyUtil = new PropertyUtil(System.getProperty("java.io.tmpdir") + "gutFarmer.properties", getDefaultProperty());
        init();
    }

    @Override
    protected void initTab() {
        panels = new ArrayList<>();
        panels.add(new Herbs());
        panels.add(new Flowers());
        panels.add(new Allotments());
        panels.add(new Hops());
        panels.add(new FruitTrees());
        panels.add(new Trees());

        tab = new JTabbedPane();
        tab.setBounds(0, 0, 650, 300);
        for (Panel panel : panels) {
            tab.addTab(panel.getTitle(), panel.getPanel());
        }
        frame.add(tab);
    }

    private void init() {
        Label label = new Label("Bottomless Bucket");
        label.setBounds(20, frame.getHeight() - 150, 120, 20);
        frame.add(label);

        blBucket = new JComboBox<>(new String[] {"none","normal","super","ultra"});
        blBucket.setSelectedItem(propertyUtil.getProperty(BL_CONTENT));
        blBucket.setBounds(20, frame.getHeight() - 130, 120, 20);
        blBucket.addActionListener((ActionEvent e) -> propertyUtil.updateProperty(BL_CONTENT, (String)blBucket.getSelectedItem()));
        frame.add(blBucket);

        useblBucket = new JCheckBox("use bottomless bucket", propertyUtil.getProperty(USE_BL).equals("true"));
        useblBucket.setBounds(frame.getWidth() - 160, frame.getHeight()     - 150, 140, 20);
        useblBucket.addActionListener((ActionEvent e) -> propertyUtil.updateProperty(USE_BL, useblBucket.isSelected()));
        frame.add(useblBucket);

        teleportWithSpell = new JCheckBox("teleport with spell", propertyUtil.getProperty(TELEPORT_WITH_SPELL).equals("true"));
        teleportWithSpell.setBounds(frame.getWidth() - 160, frame.getHeight() - 130, 130, 20);
        teleportWithSpell.addActionListener((ActionEvent e) -> propertyUtil.updateProperty(TELEPORT_WITH_SPELL, teleportWithSpell.isSelected()));
        frame.add(teleportWithSpell);
    }

    private Map<String, String> getDefaultProperty() {
        Map<String, String> properties = new HashMap<>();
        properties.put(TELEPORT_WITH_SPELL, "false");
        properties.put(USE_BL, "false");
        properties.put(BL_CONTENT, "none");
        return properties;
    }

    @Override
    protected void startScript() {

    }

    @Override
    protected boolean errorCheck() {
        error = Error.NONE;

        if (useblBucket.isSelected()) {
            String compost = (String)blBucket.getSelectedItem();
            if (compost == null || !(compost.equals("normal") || compost.equals("super") || compost.equals("ultra"))) {
                error = Error.BOTTOMLESS_WITHOUT_SETTING_BOTTOMLESS;
            }
        }

        int activated = 0;
        for (Panel panel : panels) {
            if (panel.isActivated()) {
                activated++;
            }
        }
        if (activated == 0) {
            error = Error.NO_ACTIVATED_PRODUCE;
        }

        return error.equals(Error.NONE);
    }

    @Override
    protected void displayErrorMessage() {
        makeDialogErrorMessage(error.getText());
    }

    @Override
    protected void saveSettings() {
        farmingTracker.setUsingRunes(teleportWithSpell.isSelected());
        compostTracker.setUsingBottomlessBucket(useblBucket.isSelected());

        for (Panel panel : panels) {
            String[] sPrio = panel.getPropertyUtil().getProperty(PropertyUtil.PRIO).split(",");
            int [] iPrio = new int[sPrio.length];
            for (int i = 0; i < sPrio.length; i++) {
                iPrio[i] = Integer.parseInt(sPrio[i]);
            }

            if (panel.getClass() == Herbs.class) {
                herbRunTracker.setHerbPriority(iPrio);

            } else if (panel.getClass() == Flowers.class) {
                herbRunTracker.setFlowerPriority(iPrio);

            } else if (panel.getClass() == Allotments.class) {
                herbRunTracker.setAllotmentPriority(iPrio);

            } else if (panel.getClass() == Hops.class) {
                hopsRunTracker.setPriority(iPrio);

            } else if (panel.getClass() == FruitTrees.class) {
                fruitTreeTracker.setPriority(iPrio);

            } else if (panel.getClass() == Trees.class) {
                treeTracker.setPriority(iPrio);

            }
        }

        compostTracker.setBlCompostType(propertyUtil.getProperty(BL_CONTENT));
    }
}
