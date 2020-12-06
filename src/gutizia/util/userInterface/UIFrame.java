package gutizia.util.userInterface;

import org.powerbot.script.Condition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

abstract public class UIFrame {
    protected Frame frame;

    private int buttonNumber = 0;
    private int checkBoxNumber = 0;

    protected JLabel[] labels;
    protected JComboBox[] comboBoxes;
    protected String fileName;
    protected JCheckBox[] checkBoxes;

    private Dialog dialogButtons;
    private Dialog dialogOptions;
    private Dialog dialogError;
    private Dialog dialogClickableLabels;

    private boolean startButtonPressed = false;

    public UIFrame(String name) {
        frame = new Frame(name);
        frame.setSize(650, 450);
        frame.setLayout(null);

        initStartButton();

        frame.setVisible(true);
    }

    /**
     * makes buttons on left side of frame for you
     * @param label what you want to call the button
     * @param actionListener what button should do (usually spawn a dialog window with either options or other buttons for options)
     */
    public void makeButton (String label,ActionListener actionListener) {
        int buttonX = 30;
        int buttonStartY = 60;
        int buttonDeltaY = 70;
        int buttonWidth = 100;
        int buttonHeight = 40;

        Button button = new Button(label);
        frame.add(button);
        button.addActionListener(actionListener);
        button.setBounds(buttonX, buttonStartY + (buttonNumber++ * buttonDeltaY),buttonWidth,buttonHeight);
    }

    public void makeCheckBox (String label) {
        int checkBoxX = 500;
        int checkBoxStartY = 60;
        int checkBoxDeltaY = 30;
        int checkBoxWidth = 120;
        int checkBoxHeight = 20;

        checkBoxes[checkBoxNumber] = new JCheckBox(label);
        frame.add(checkBoxes[checkBoxNumber]);
        checkBoxes[checkBoxNumber].setBounds(checkBoxX,checkBoxStartY + (checkBoxNumber++ * checkBoxDeltaY),checkBoxWidth,checkBoxHeight);
    }

    public void makeDialogErrorMessage(String message) {
        dialogError = new Dialog(frame,"error");

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
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogError.dispose();
            }
        });
        button.setBounds(200,220,100,40);


        dialogError.setVisible(true);


    }

    public void makeDialogClickableLabels(String dialogLabel, String[] textFieldLabels,ActionListener saveSettings) {
        dialogClickableLabels = new Dialog(frame,dialogLabel);
        dialogClickableLabels.setLayout(null);

        labels = new JLabel[textFieldLabels.length];

        int startX = 40;
        int startY = 60;
        int deltaY = 21;
        int labelWidth = 100;
        int deltaX = 120;
        int labelHeight = 22;
        int row = 0;
        int column = 0;

        for (int i = 0; i < labels.length;i++) {
            if (column == 3) {
                row++;
                column = 0;
            }

            labels[i] = new JLabel(textFieldLabels[i],SwingConstants.CENTER);
            labels[i].addMouseListener(clickableLabelsListener);
            labels[i].setOpaque(true);
            labels[i].setBounds(startX + (column * deltaX), startY + (row * deltaY),labelWidth,labelHeight);
            labels[i].setBackground(Color.lightGray);
            labels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            dialogClickableLabels.add(labels[i]);
            column++;
        }

        Button saveButton = new Button("save settings");
        dialogClickableLabels.add(saveButton);
        saveButton.addActionListener(saveSettings);

        dialogClickableLabels.setSize(500, startY + (deltaY * row) + 120);
        saveButton.setBounds(dialogClickableLabels.getWidth() / 2 - 50,dialogClickableLabels.getHeight() - 60,100,40);
        dialogClickableLabels.setVisible(true);
    }

    private MouseListener clickableLabelsListener = new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e) {

            changeColorOfLabel(e.getSource());
        }

        @Override
        public void mousePressed(MouseEvent e) {


        }

        @Override
        public void mouseReleased(MouseEvent e) {


        }

        @Override
        public void mouseEntered(MouseEvent e) {


        }

        @Override
        public void mouseExited(MouseEvent e) {


        }
    };

    // sees if clicked on a label, then changes that label to the opposite color of what it was
    private void changeColorOfLabel(Object myObject) {
        for (JLabel label : labels) {
            if (myObject.equals(label)) {
                if (label.getBackground() == Color.green) {
                    label.setBackground(Color.lightGray);
                } else {
                    label.setBackground(Color.green);
                }
            }
        }
    }

    /**
     * makes a standard list of labels and comboBoxes next to each other for options with multiple choice where you just want one option
     * @param dialogLabel name of dialog
     * @param labels list of each nameable option
     * @param comboBoxes list of each option
     * @param actionListener actionListener for what save button should do
     */
    protected void makeDialogLabelComboBox(String dialogLabel,String[] labels,String[] comboBoxes,ActionListener actionListener) {
        dialogOptions = new Dialog(frame,dialogLabel);

        dialogOptions.setLayout(null);
        dialogOptions.setSize(400,270);

        this.labels = new JLabel[labels.length];
        this.comboBoxes = new JComboBox[labels.length];

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

            this.labels[i] = new JLabel(labels[i],SwingConstants.CENTER);
            this.labels[i].setOpaque(true);
            this.labels[i].setBackground(Color.white);
            this.labels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            dialogOptions.add(this.labels[i]);
            this.labels[i].setBounds(labelStartX + (column * deltaX),startY + (row * deltaY),labelWidth,labelHeight);

            this.comboBoxes[i] = new JComboBox(comboBoxes);
            dialogOptions.add(this.comboBoxes[i]);
            this.comboBoxes[i].setBounds(comboBoxStartX + (column * deltaX),startY + (row * deltaY),comboBoxWidth,comboBoxHeight);

            column++;
        }

        Button saveButton = new Button("save settings");
        dialogOptions.add(saveButton);
        saveButton.addActionListener(actionListener);
        saveButton.setBounds(150,200,100,40);

        dialogOptions.setVisible(true);
    }

    /**
     * makes a dialog with multiple button. has dynamic height depending on how many buttons
     * @param dialogLabel name of dialog
     * @param buttonLabels list of name for buttons
     * @param actionListeners list of action for buttons
     */
    protected void makeDialogButtons(String dialogLabel,String[] buttonLabels,ActionListener[] actionListeners) {
        dialogButtons = new Dialog(frame,dialogLabel);

        int buttonX = 50;
        int buttonStartY = 40;
        int buttonDeltaY = 50;
        int buttonWidth = 120;
        int buttonHeight = 40;

        int buttonNr = 0;
        for (int i = 0;i < buttonLabels.length;i++) {
            Button button = new Button(buttonLabels[i]);
            dialogButtons.add(button);
            button.addActionListener(actionListeners[i]);
            button.setBounds(buttonX, buttonStartY + (buttonNr++ * buttonDeltaY), buttonWidth, buttonHeight);
        }

        int height = buttonStartY + (buttonDeltaY * buttonLabels.length) + (buttonHeight * buttonLabels.length);

        dialogButtons.setLayout(null);
        dialogButtons.setSize(200,height);

        Button saveButton = new Button("Done");
        dialogButtons.add(saveButton);
        saveButton.addActionListener(done);
        saveButton.setBounds(buttonX, dialogButtons.getHeight() - 65,buttonWidth,buttonHeight);

        dialogButtons.setVisible(true);

    }

    /**
     * labeled combo boxes for settings/option. 5 combo boxes per row
     * @param dialogLabel name of dialog
     * @param labels label to describe combo boxes
     * @param comboBoxes combo boxes to display
     * @param saveSettings actionListener to save settings
     */
    protected void makeDialogLabelComboBoxes(String dialogLabel,String[] labels,JComboBox[] comboBoxes,ActionListener saveSettings) {
        dialogOptions = new Dialog(frame,dialogLabel);

        int width = 150;
        int height = 25;
        int startX = 40;
        int labelStartY = 40;
        int deltaX = 200;
        int deltaY = 60;
        int comboBoxStartY = 70;
        int row = 0;
        int column = 0;
        int buttonWidth = 100;
        int buttonHeight = 40;

        this.labels = new JLabel[labels.length];
        this.comboBoxes = new JComboBox[comboBoxes.length];

        for (int i = 0; i < labels.length; i++) {
            if (column == 5) {
                row++;
                column = 0;
            }

            this.labels[i] = new JLabel(labels[i]);
            dialogOptions.add(this.labels[i]);
            this.labels[i].setBounds(startX + (column * deltaX),labelStartY + (row * deltaY),width,height);

            this.comboBoxes[i] = comboBoxes[i];
            dialogOptions.add(this.comboBoxes[i]);
            this.comboBoxes[i].setBounds(startX + (column * deltaX),comboBoxStartY + (row * deltaY),width,height);

            column++;
        }

        int foo;

        if (row > 0) {
            foo = 5;
        } else {
            foo = column;
        }

        /*
         * extra size (static), width of labels * amount of labels,
         * */

        int dialogWidth = 80 + (deltaX * (foo));
        int dialogHeight = 75 + buttonHeight + comboBoxStartY + (deltaY * row) + (height * row);

        Button saveButton = new Button("save settings");
        dialogOptions.add(saveButton);
        saveButton.addActionListener(saveSettings);
        saveButton.setBounds(dialogWidth / 2 - buttonWidth / 2,dialogHeight - (20 + buttonHeight),buttonWidth,buttonHeight);

        dialogOptions.setLayout(null);
        dialogOptions.setSize(dialogWidth,dialogHeight);
        dialogOptions.setVisible(true);
    }

    abstract protected boolean startErrorCheck();
    abstract protected void displayErrorMessage();

    private void initStartButton() {
        Button startButton;

        startButton = new Button("start script");
        frame.add(startButton);
        startButton.addActionListener((ActionEvent e) -> {
            if (startErrorCheck()) {
                startButtonPressed = true;
                frame.dispose();

            } else {
                displayErrorMessage();
            }
        });
        startButton.setBounds(250, 350, 100, 40);
    }

    private ActionListener done = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO add check if properly finished mandatory actions
            dialogButtons.dispose();
        }
    };

    protected void disposeDialogOptions() {
        dialogOptions.dispose();
    }

    protected void disposeDialogClickableLabels() {
        dialogClickableLabels.dispose();
    }

    public void start() {
        while (!startButtonPressed) {
            Condition.sleep(1000);
        }
    }
}