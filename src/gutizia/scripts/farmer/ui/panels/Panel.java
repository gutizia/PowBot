package gutizia.scripts.farmer.ui.panels;

import gutizia.util.constants.Items;
import gutizia.util.resources.PropertyUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public abstract class Panel {
    private JPanel panel;
    private final String title;

    PropertyUtil propertyUtil;

    final String[] composts = new String[] {"none","normal","super","ultra"};

    private JCheckBox usePreference;
    private JCheckBox loginWhenReady;
    private JCheckBox activate;

    ActionListener openCompostSettings;
    ActionListener saveCompostSettings;
    ActionListener openPrioritySettings;
    ActionListener savePrioritySettings;

    protected Panel(String title, String fileName) {
        this.title = title;
        this.propertyUtil = new PropertyUtil( System.getProperty("java.io.tmpdir") + fileName, getDefaultPropertyValues());
    }

    void initPanel() {
        try {
            panel = new JPanel();
            panel.setLayout(null);
            panel.setBounds(0, 40, 650, 400);
            initCheckboxes();
            initButtons();

            JLabel titleLabel = new JLabel(title);
            panel.add(titleLabel);
            titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 24));
            titleLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            titleLabel.setBounds(panel.getWidth() / 2 - 100, 20, 200, 40);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> getDefaultPropertyValues() {
        Map<String, String> properties = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        if (this.getClass() == Herbs.class) {
            for (int i = Items.HERB_SEEDS.length; i >= 0 ; i--) {
                sb.append(i);
                if (i != 0) {
                    sb.append(",");
                }
            }
            properties.clear();
            properties.put(PropertyUtil.COMPOST, "super,super,super,super,ultra,ultra,super,ultra,ultra,ultra,ultra,ultra,ultra,ultra");
            properties.put(PropertyUtil.PRIO, sb.toString());
            properties.put(PropertyUtil.USE_PREF, "false");
            properties.put(PropertyUtil.LOGIN_DONE, "true");
            properties.put(PropertyUtil.ACTIVATE, "true");

        } else if (this.getClass() == Allotments.class) {
            for (int i = Items.ALLOTMENT_SEEDS.length; i >= 0 ; i--) {
                sb.append(i);
                if (i != 0) {
                    sb.append(",");
                }
            }
            properties.clear();
            properties.put(PropertyUtil.COMPOST, "ultra,ultra,ultra,ultra,ultra,ultra,ultra,ultra");
            properties.put(PropertyUtil.PRIO, sb.toString());
            properties.put(PropertyUtil.USE_PREF, "false");
            properties.put(PropertyUtil.LOGIN_DONE, "false");
            properties.put(PropertyUtil.ACTIVATE, "true");

        } else if (this.getClass() == Flowers.class) {
            for (int i = Items.FLOWER_SEEDS.length; i >= 0 ; i--) {
                sb.append(i);
                if (i != 0) {
                    sb.append(",");
                }
            }
            properties.clear();
            properties.put(PropertyUtil.COMPOST, "normal,normal,normal,normal,super,super");
            properties.put(PropertyUtil.PRIO, sb.toString());
            properties.put(PropertyUtil.USE_PREF, "false");
            properties.put(PropertyUtil.LOGIN_DONE, "false");
            properties.put(PropertyUtil.ACTIVATE, "true");

        } else if (this.getClass() == Hops.class) {
            for (int i = Items.HOPS_SEEDS.length; i >= 0 ; i--) {
                sb.append(i);
                if (i != 0) {
                    sb.append(",");
                }
            }
            properties.clear();
            properties.put(PropertyUtil.COMPOST, "super,super,super,super,super,super,super");
            properties.put(PropertyUtil.PRIO, sb.toString());
            properties.put(PropertyUtil.USE_PREF, "false");
            properties.put(PropertyUtil.LOGIN_DONE, "true");
            properties.put(PropertyUtil.ACTIVATE, "true");

        } else if (this.getClass() == FruitTrees.class) {
            for (int i = Items.FRUIT_TREE_SAPLINGS.length; i >= 0 ; i--) {
                sb.append(i);
                if (i != 0) {
                    sb.append(",");
                }
            }
            properties.clear();
            properties.put(PropertyUtil.COMPOST, "super,super,super,super,super,super,super,super");
            properties.put(PropertyUtil.PRIO, sb.toString());
            properties.put(PropertyUtil.USE_PREF, "false");
            properties.put(PropertyUtil.LOGIN_DONE, "true");
            properties.put(PropertyUtil.ACTIVATE, "true");

        } else if (this.getClass() == Trees.class) {
            for (int i = Items.TREE_SAPLINGS.length; i >= 0 ; i--) {
                sb.append(i);
                if (i != 0) {
                    sb.append(",");
                }
            }
            properties.clear();
            properties.put(PropertyUtil.COMPOST, "super,super,super,super,super");
            properties.put(PropertyUtil.PRIO, sb.toString());
            properties.put(PropertyUtil.USE_PREF, "false");
            properties.put(PropertyUtil.LOGIN_DONE, "true");
            properties.put(PropertyUtil.ACTIVATE, "true");
        }

        return properties;
    }

    private void initCheckboxes() {
        int checkBoxX = 500;
        int checkBoxStartY = 60;
        int checkBoxDeltaY = 30;
        int checkBoxWidth = 120;
        int checkBoxHeight = 20;
        int checkBoxNumber = 0;
        usePreference = new JCheckBox("use preference", propertyUtil.getProperty(PropertyUtil.USE_PREF).equals("true"));
        usePreference.setBounds(checkBoxX,checkBoxStartY + (checkBoxNumber++ * checkBoxDeltaY),checkBoxWidth,checkBoxHeight);
        usePreference.addActionListener((ActionEvent e) -> propertyUtil.updateProperty(PropertyUtil.USE_PREF, usePreference.isSelected()));
        panel.add(usePreference);

        loginWhenReady = new JCheckBox("login when ready", propertyUtil.getProperty(PropertyUtil.LOGIN_DONE).equals("true"));
        loginWhenReady.setBounds(checkBoxX,checkBoxStartY + (checkBoxNumber++ * checkBoxDeltaY),checkBoxWidth,checkBoxHeight);
        loginWhenReady.addActionListener((ActionEvent e) -> propertyUtil.updateProperty(PropertyUtil.LOGIN_DONE, loginWhenReady.isSelected()));
        panel.add(loginWhenReady);

        activate = new JCheckBox("activate", propertyUtil.getProperty(PropertyUtil.ACTIVATE).equals("true"));
        activate.setBounds(checkBoxX,checkBoxStartY + (checkBoxNumber * checkBoxDeltaY),checkBoxWidth,checkBoxHeight);
        activate.addActionListener((ActionEvent e) -> propertyUtil.updateProperty(PropertyUtil.ACTIVATE, activate.isSelected()));
        panel.add(activate);
    }

    private void initButtons() {
        int buttonX = 20;
        int buttonStartY = 60;
        int buttonDeltaY = 70;
        int buttonWidth = 100;
        int buttonHeight = 40;
        int buttonNumber = 0;

        Button button = new Button("compost");
        panel.add(button);
        button.addActionListener(openCompostSettings);
        button.setBounds(buttonX, buttonStartY + (buttonNumber++ * buttonDeltaY),buttonWidth,buttonHeight);

        Button button1 = new Button("priority");
        panel.add(button1);
        button1.addActionListener(openPrioritySettings);
        button1.setBounds(buttonX, buttonStartY + (buttonNumber * buttonDeltaY),buttonWidth,buttonHeight);
    }

    public JComponent getPanel() {
        return panel;
    }

    public String getTitle() {
        return title;
    }

    public boolean isActivated() {
        return activate.isSelected();
    }

    public PropertyUtil getPropertyUtil() {
        return propertyUtil;
    }
}
