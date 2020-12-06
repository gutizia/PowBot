package gutizia.scripts.farmer.ui.panels;

import gutizia.util.resources.PropertyUtil;

import java.awt.event.ActionEvent;

import static gutizia.scripts.farmer.ui.FarmingUI.farmingUi;

public class Allotments extends Panel {

    private final String[] allotments = new String[] {"Potato", "Onion", "Cabbage", "Tomato", "Sweetcorn", "Strawberry", "Watermelon", "Snape grass"};


    public Allotments() {
        super("Allotments", "gutAllotments.properties");
        init();
        initPanel();
    }

    private void init() {
        saveCompostSettings = (ActionEvent e) -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < farmingUi.getJComboBoxes().length; i++) {
                sb.append((String)farmingUi.getJComboBoxes()[i].getSelectedItem());
                if (i != farmingUi.getJComboBoxes().length - 1) {
                    sb.append(",");
                }
            }
            propertyUtil.updateProperty(PropertyUtil.COMPOST, sb.toString());
        };

        openCompostSettings = (ActionEvent e) ->
                farmingUi.makeDialogLabelComboBox("Allotments Compost Settings", allotments, composts, propertyUtil.getProperty(PropertyUtil.COMPOST).split(","), saveCompostSettings);

        savePrioritySettings = (ActionEvent e) -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < farmingUi.getList().getModel().getSize(); i++) {
                String produce = farmingUi.getList().getModel().getElementAt(i);
                for (int j = 0; j < allotments.length; j++) {
                    if (produce.equals(allotments[j])) {
                        sb.append(j);
                        if (i != farmingUi.getList().getModel().getSize() - 1) {
                            sb.append(",");
                        }
                        break;
                    }
                }
            }
            propertyUtil.updateProperty(PropertyUtil.PRIO, sb.toString());
        };

        openPrioritySettings = (ActionEvent e) ->
                farmingUi.makePriorityList("Allotments Priority Settings", allotments, propertyUtil.getProperty(PropertyUtil.PRIO).split(","), savePrioritySettings);
    }
}
