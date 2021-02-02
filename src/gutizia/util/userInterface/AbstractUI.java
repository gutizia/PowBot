package gutizia.util.userInterface;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

abstract public class AbstractUI extends ClientAccessor {
    protected JFrame frame;
    protected Dialog dialog = null;
    protected Dialog integerInputDialog = null;

    private JComboBox<String>[] jComboBoxes;
    private JList<String> list;

    private boolean startButtonPressed = false;

    public enum Error {
        NONE(""),
        PREFERENCE_WITHOUT_SETTING_PREFERENCE("please configure what seeds you prefer to plant, \n or deselect \"use preference\""),
        BOTTOMLESS_WITHOUT_SETTING_BOTTOMLESS("please configure what type of compost \n that the bottomless bucket contains, or deselect \n the \"use bottomless bucket\" checkbox"),
        NO_ACTIVATED_PRODUCE("please activate at least 1 type of produce"),
        STARTING_SCRIPT_WITHOUT_BEING_LOGGED_IN("please log in before starting script");

        private String text;

        Error(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public Error error = Error.NONE;

    abstract protected void saveSettings();
    abstract protected boolean errorCheck();
    abstract protected void displayErrorMessage();
    abstract protected void startScript();

    public AbstractUI(ClientContext ctx, String fTitle) {
        super(ctx);
        frame = new JFrame(fTitle);
        frame.setLayout(null);
        frame.setVisible(false);
        frame.setSize(650, 450);

        initStartButton();
    }

    private void initStartButton() {
        Button startButton;

        startButton = new Button("start script");
        frame.add(startButton);
        startButton.addActionListener((ActionEvent e) -> {
            if (errorCheck()) {
                saveSettings();
                startButtonPressed = true;
                frame.dispose();

            } else {
                displayErrorMessage();
            }
        });
        startButton.setBounds(250, 350, 100, 40);
    }

    public void start() {
        frame.setVisible(true);
        while (!startButtonPressed) {
            Condition.sleep(1000);
        }
    }

    /**
     * makes a standard list of labels and comboBoxes next to each other for options with multiple choice where you just want one option
     * @param dialogLabel name of dialog
     * @param labels list of each nameable option
     * @param comboBoxes list of each option
     * @param saveSettings actionListener for what save button should do
     */
    public void makeDialogLabelComboBox(String dialogLabel, String[] labels, String[] comboBoxes, String[] values, ActionListener saveSettings) {
        if (dialog != null) {
            dialog.dispose();
        }
        dialog = new Dialog(frame,dialogLabel);

        dialog.setLayout(null);
        dialog.setSize(400,270);

        JLabel[] jLabels = new JLabel[labels.length];
        jComboBoxes = new JComboBox[labels.length];

        int[] index = new int[values.length];

        for (int i = 0; i < values.length; i++) {
            switch (values[i]) {
                case "none":
                    index[i] = 0;
                    break;
                case "normal":
                    index[i] = 1;
                    break;
                case "super":
                    index[i] = 2;
                    break;
                case "ultra":
                    index[i] = 3;
                    break;
            }
        }

        int labelStartX = 30;
        int comboBoxStartX = 130;
        int startY = 40;
        int labelWidth = 100;
        int labelHeight = 22;
        int comboBoxWidth = 60;
        int comboBoxHeight = 22;
        int deltaX = 170;
        int deltaY = 21;
        int row = 0;
        int column = 0;

        for (int i = 0; i < labels.length; i++) {
            if (column == 2) {
                row++;
                column = 0;
            }

            jLabels[i] = new JLabel(labels[i],SwingConstants.CENTER);
            jLabels[i].setOpaque(true);
            jLabels[i].setBackground(Color.white);
            jLabels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            dialog.add(jLabels[i]);
            jLabels[i].setBounds(labelStartX + (column * deltaX),startY + (row * deltaY),labelWidth,labelHeight);

            jComboBoxes[i] = new JComboBox<>(comboBoxes);
            jComboBoxes[i].
                    setSelectedIndex(index[i]);
            dialog.add(jComboBoxes[i]);
            jComboBoxes[i].setBounds(comboBoxStartX + (column * deltaX),startY + (row * deltaY),comboBoxWidth,comboBoxHeight);

            column++;
        }

        Button saveButton = new Button("save settings");
        dialog.add(saveButton);
        saveButton.addActionListener(saveSettings);
        saveButton.setBounds(50,200,100,40);

        Button closeButton = new Button("close");
        dialog.add(closeButton);
        closeButton.addActionListener((ActionEvent e) -> dialog.dispose());
        closeButton.setBounds(170,200,100,40);

        dialog.setVisible(true);
    }

    public void makePriorityList(String dialogLabel, String[] items, String[] order, ActionListener saveSettings) {
        if (dialog != null) {
            dialog.dispose();
        }
        dialog = new Dialog(frame,dialogLabel);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.setSize(items.length);
        for (int i = 0; i < items.length; i++) {
            listModel.add(Integer.parseInt(order[i]), items[i]);
        }

        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setDragEnabled(true);
        list.setDropMode(DropMode.INSERT);
        list.setBounds(10,31, 120, items.length * 16);
        dialog.add(list);

        int height = list.getHeight() + list.getX() + 100;
        if (height < 350) {
            height = 350;
        }
        dialog.setSize(300, height);

        Button moveTop = new Button("move to top");
        dialog.add(moveTop);
        moveTop.addActionListener((ActionEvent e) -> {
            if (list.getSelectedIndex() == 0) {
                return;
            }
            String value = list.getSelectedValue();
            listModel.remove(list.getSelectedIndex());
            listModel.insertElementAt(value, 0);
            list.setSelectedIndex(0);
        });
        moveTop.setBounds(170, 40, 100, 40);

        Button moveUp = new Button("move up");
        dialog.add(moveUp);
        moveUp.addActionListener((ActionEvent e) -> {
            if (list.getSelectedIndex() == 0) {
                return;
            }
            String value = list.getSelectedValue();
            int index = list.getSelectedIndex();
            listModel.remove(index);
            listModel.insertElementAt(value, index - 1);
            list.setSelectedIndex(index - 1);
        });
        moveUp.setBounds(170, 100, 100, 40);

        Button moveDown = new Button("move down");
        dialog.add(moveDown);
        moveDown.addActionListener((ActionEvent e) -> {
            if (list.getSelectedIndex() == listModel.size() - 1) {
                return;
            }
            String value = list.getSelectedValue();
            int index = list.getSelectedIndex();
            listModel.remove(index);
            listModel.insertElementAt(value, index + 1);
            list.setSelectedIndex(index + 1);
        });
        moveDown.setBounds(170, 180, 100, 40);

        Button moveBot = new Button("move to bottom");
        dialog.add(moveBot);
        moveBot.addActionListener((ActionEvent e) -> {
            if (list.getSelectedIndex() == listModel.size() - 1) {
                return;
            }
            String value = list.getSelectedValue();
            listModel.remove(list.getSelectedIndex());
            listModel.addElement(value);
            list.setSelectedIndex(listModel.size() - 1);
        });
        moveBot.setBounds(170, 240, 100, 40);

        Button saveButton = new Button("save settings");
        dialog.add(saveButton);
        saveButton.addActionListener(saveSettings);
        saveButton.setBounds(50,dialog.getHeight() - 60,100,40);

        Button closeButton = new Button("close");
        dialog.add(closeButton);
        closeButton.addActionListener((ActionEvent e) -> dialog.dispose());
        closeButton.setBounds(170,dialog.getHeight() - 60,100,40);

        dialog.setLayout(null);
        dialog.setVisible(true);
    }

    public void makeDialogErrorMessage(String message) {
        Dialog dialogError = new Dialog(frame,"error");

        dialogError.setLayout(null);
        dialogError.setSize(500,300);

        int deltaY = 20;

        String[] strings = message.split("\n");

        for (int i = 0; i < strings.length;i++) {
            JLabel label = new JLabel(strings[i]);
            dialogError.add(label);
            label.setBounds(50,40 + (deltaY * i),400,20);

        }

        Button button = new Button("ok");
        dialogError.add(button);
        button.addActionListener((ActionEvent e) -> dialogError.dispose());
        button.setBounds(200,220,100,40);


        dialogError.setVisible(true);
    }

    public void makeIntegerInputDialog(JTextField tField) {
        integerInputDialog = new Dialog(frame ,"enter a number");
        integerInputDialog.setLayout(null);
        integerInputDialog.setSize(200, 120);

        JTextField textField = new JTextField();
        textField.setBounds(20, 40, 160, 20);
        integerInputDialog.add(textField);

        JButton okButton = new JButton("Ok");
        okButton.setBounds(15, 75, 75, 30);
        okButton.addActionListener((ActionEvent e) -> {
            tField.setText(textField.getText());
            integerInputDialog.dispose();
        });
        integerInputDialog.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(110, 75, 75, 30);
        cancelButton.addActionListener((ActionEvent e) -> integerInputDialog.dispose());
        integerInputDialog.add(cancelButton);

        integerInputDialog.setVisible(true);
    }


    public JComboBox[] getJComboBoxes() {
        return jComboBoxes;
    }

    public JList<String> getList() {
        return list;
    }
}