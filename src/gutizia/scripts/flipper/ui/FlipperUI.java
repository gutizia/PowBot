package gutizia.scripts.flipper.ui;

import gutizia.scripts.Script;
import gutizia.util.WebScraper;
import gutizia.util.ge.Offer;
import gutizia.util.userInterface.AbstractUI;
import org.powerbot.script.rt4.ClientContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class FlipperUI extends AbstractUI {

    private final ActionListener openStartOffer = (ActionEvent e) -> this.makeStartOfferDialog();

    Script main;

    public FlipperUI(ClientContext ctx, String fTitle, Script main) {
        super(ctx, fTitle);
        initButtons();
        this.main = main;
    }


    private void initButtons() {
        int buttonX = 20;
        int buttonStartY = 60;
        int buttonDeltaY = 70;
        int buttonWidth = 100;
        int buttonHeight = 40;
        int buttonNumber = 0;

        Button button = new Button("create offer");
        frame.add(button);
        button.addActionListener(openStartOffer);
        button.setBounds(buttonX, buttonStartY + (buttonNumber++ * buttonDeltaY),buttonWidth,buttonHeight);

        /*
        Button button1 = new Button("view offers");
        frame.add(button1);
        button1.addActionListener(openPrioritySettings);
        button1.setBounds(buttonX, buttonStartY + (buttonNumber * buttonDeltaY),buttonWidth,buttonHeight);
        */
    }

    private void makeStartOfferDialog() {
        if (dialog != null) {
            dialog.dispose();
        }
        dialog = new Dialog(frame, "Start Offer");

        dialog.setLayout(null);
        dialog.setSize(260,165);

        JLabel label = new JLabel("Enter name of item");
        label.setBounds(60, 35, 120, 20);
        dialog.add(label);

        TextField textField = new TextField();
        textField.setBounds(60, 60, 120, 20);
        dialog.add(textField);

        Button buyButton = new Button("Buy");
        dialog.add(buyButton);
        buyButton.addActionListener((ActionEvent e) -> makeCreateOfferDialog(WebScraper.initOffer(textField.getText(), true, main)));
        buyButton.setBounds((dialog.getWidth() / 2) - 100,dialog.getHeight() - 60,80,40);

        Button sellButton = new Button("Sell");
        dialog.add(sellButton);
        sellButton.addActionListener((ActionEvent e) -> makeCreateOfferDialog(WebScraper.initOffer(textField.getText(), false, main)));
        sellButton.setBounds((dialog.getWidth() / 2) + 20,dialog.getHeight() - 60,80,40);

        dialog.setLayout(null);
        dialog.setVisible(true);
    }

    private void makeCreateOfferDialog(Offer offer) {
        if (dialog != null) {
            dialog.dispose();
        }
        dialog = new Dialog(frame, "Create " + (offer.isBuy() ? "buy" : "sell") + " Offer");

        dialog.setLayout(null);
        dialog.setSize(600,470);



        // item panel
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(null);
        itemPanel.setBounds(dialog.getX() + 10, dialog.getY() + 10, 180, 60);

        JLabel itemImg = new JLabel();
        BufferedImage img = offer.getBufferedImage();
        itemImg.setIcon(new ImageIcon(img));
        itemImg.setBounds(10, 10, img.getWidth(), img.getHeight());
        itemPanel.add(itemImg);

        itemPanel.setBackground(Color.GRAY);

        JLabel itemName = new JLabel(offer.getItem().getName());
        itemPanel.add(itemName);
        itemName.setFont(new Font(itemName.getFont().getName(), Font.BOLD, 16));
        itemName.setHorizontalTextPosition(SwingConstants.CENTER);
        itemName.setBounds(itemImg.getX() + itemImg.getWidth() + 10, itemImg.getY() - itemImg.getHeight() / 2 + 20, 200, 40);

        dialog.add(itemPanel);

        // profit panel
        JPanel profit = new JPanel();
        profit.setLayout(null);
        profit.setBounds(dialog.getX() + 10, dialog.getY() + 150, 120, 90);

        JLabel totalPrice = new JLabel("Total Price");
        profit.add(totalPrice);
        totalPrice.setFont(new Font(totalPrice.getFont().getName(), Font.BOLD, 14));
        totalPrice.setHorizontalTextPosition(SwingConstants.CENTER);
        totalPrice.setBounds(30, 0, profit.getWidth(), 20);

        JTextField totalPriceTextField = new JTextField();
        profit.add(totalPriceTextField);
        totalPriceTextField.setEditable(false);
        totalPriceTextField.setBounds( 0, 28, profit.getWidth(), 20);

        JLabel profitLabel = new JLabel("profit");
        profit.add(profitLabel);
        profitLabel.setFont(new Font(profit.getFont().getName(), Font.BOLD, 14));
        profitLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        profitLabel.setBounds(40, 43, profit.getWidth(), 20);

        JTextField profitTextField = new JTextField();
        profit.add(profitTextField);
        profitTextField.setEditable(false);
        profitTextField.setBounds( 0, 70, profit.getWidth(), 20);


        if (offer.isBuy()) {


        } else {

        }

        profit.setBackground(Color.GRAY);
        dialog.add(profit);

        // price panel
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(null);
        pricePanel.setBounds(dialog.getX() + 210, dialog.getY() + 10, 260, 110);

        int width = 30;
        int height = 30;
        int heightDelta = 8;
        int widthDelta = 8;

        JLabel pricePerItem = new JLabel("Price Per Item");
        pricePanel.add(pricePerItem);
        pricePerItem.setFont(new Font(pricePerItem.getFont().getName(), Font.BOLD, 14));
        pricePerItem.setHorizontalTextPosition(SwingConstants.CENTER);
        pricePerItem.setBounds(10, 10, 200, 20);

        JTextField pricePerItemTextField = new JTextField(Integer.toString(offer.getPrice().getPrice()));
        pricePanel.add(pricePerItemTextField);
        pricePerItemTextField.setEditable(false);
        pricePerItemTextField.setBounds( 10, 40, 200, 20);

        JButton plus5PercentPrice = new JButton("+5%");
        pricePanel.add(plus5PercentPrice);
        plus5PercentPrice.addActionListener((ActionEvent e) -> {
            offer.setPrice(offer.getPrice().plusFivePercent());
            pricePerItemTextField.setText(Integer.toString(offer.getPrice().getPrice()));
        });
        plus5PercentPrice.setBounds(pricePerItemTextField.getX() + pricePerItemTextField.getWidth() - width, pricePerItemTextField.getY() + pricePerItemTextField.getHeight() + heightDelta, width, height);
        plus5PercentPrice.setMargin(new Insets(0, 0, 0, 0));

        JButton minus5PercentPrice = new JButton("-5%");
        pricePanel.add(minus5PercentPrice);
        minus5PercentPrice.addActionListener((ActionEvent e) -> {
            offer.setPrice(offer.getPrice().minusFivePercent());
            pricePerItemTextField.setText(Integer.toString(offer.getPrice().getPrice()));
        });
        minus5PercentPrice.setBounds(pricePerItemTextField.getX(), pricePerItemTextField.getY() + pricePerItemTextField.getHeight() + heightDelta, width, height);
        minus5PercentPrice.setMargin(new Insets(0, 0, 0, 0));

        JButton customPrice = new JButton("Custom");
        pricePanel.add(customPrice);
        customPrice.addActionListener((ActionEvent e) -> {
            makeIntegerInputDialog(pricePerItemTextField);
            offer.setPrice(offer.getPrice().setCustomPrice(Integer.parseInt(pricePerItemTextField.getText())));
            pricePerItemTextField.setText(Integer.toString(offer.getPrice().getPrice()));
        });
        customPrice.setBounds(pricePerItemTextField.getX() + (pricePerItemTextField.getWidth() / 2) - (width / 2), pricePerItemTextField.getY() + pricePerItemTextField.getHeight() + heightDelta, width, height);
        customPrice.setMargin(new Insets(0, 0, 0, 0));

        JButton plusOnePrice = new JButton("+1");
        pricePanel.add(plusOnePrice);
        plusOnePrice.addActionListener((ActionEvent e) -> {
            offer.setPrice(offer.getPrice().plusOne());
            pricePerItemTextField.setText(Integer.toString(offer.getPrice().getPrice()));
        });
        plusOnePrice.setBounds(plus5PercentPrice.getX() - widthDelta - width, pricePerItemTextField.getY() + pricePerItemTextField.getHeight() + heightDelta, width, height);
        plusOnePrice.setMargin(new Insets(0, 0, 0, 0));

        JButton minusOnePrice = new JButton("-1");
        pricePanel.add(minusOnePrice);
        minusOnePrice.addActionListener((ActionEvent e) -> {
            offer.setPrice(offer.getPrice().minusOne());
            pricePerItemTextField.setText(Integer.toString(offer.getPrice().getPrice()));
        });
        minusOnePrice.setBounds(minus5PercentPrice.getX() + width + widthDelta, pricePerItemTextField.getY() + pricePerItemTextField.getHeight() + heightDelta, width, height);
        minusOnePrice.setMargin(new Insets(0, 0, 0, 0));


        pricePanel.setBackground(Color.GRAY);
        dialog.add(pricePanel);


        // amount panel
        JPanel amount = new JPanel();
        amount.setLayout(null);
        amount.setBounds(dialog.getX() + 210, dialog.getY() + 150, 260, 110);

        JLabel amountLabel = new JLabel("Amount");
        amount.add(amountLabel);
        amountLabel.setFont(new Font(amountLabel.getFont().getName(), Font.BOLD, 14));
        amountLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        amountLabel.setBounds(10, 10, 200, 20);

        JTextField amountTextField = new JTextField(Integer.toString(offer.getItem().getAmount()));
        amount.add(amountTextField);
        amountTextField.setEditable(false);
        amountTextField.setBounds( 10, 40, 200, 20);

        JButton plus5Amount = new JButton("+5");
        amount.add(plus5Amount);
        plus5Amount.addActionListener((ActionEvent e) -> {
            offer.setSellAll(false);
            offer.getItem().setAmount(offer.getItem().getAmount() + 5);
            amountTextField.setText(Integer.toString(offer.getItem().getAmount()));

        });
        plus5Amount.setBounds(amountTextField.getX() + amountTextField.getWidth() - width, amountTextField.getY() + amountTextField.getHeight() + heightDelta, width, height);
        plus5Amount.setMargin(new Insets(0, 0, 0, 0));

        JButton minus5Amount = new JButton("-5");
        amount.add(minus5Amount);
        minus5Amount.addActionListener((ActionEvent e) -> {
            offer.setSellAll(false);
            offer.getItem().setAmount(offer.getItem().getAmount() - 5);
            amountTextField.setText(Integer.toString(offer.getItem().getAmount()));
        });
        minus5Amount.setBounds(amountTextField.getX(), amountTextField.getY() + amountTextField.getHeight() + heightDelta, width, height);
        minus5Amount.setMargin(new Insets(0, 0, 0, 0));

        JButton customAmount = new JButton("Custom");
        amount.add(customAmount);
        customAmount.addActionListener((ActionEvent e) -> {
            offer.setSellAll(false);
            makeIntegerInputDialog(amountTextField);
            offer.getItem().setAmount(Integer.parseInt(amountTextField.getText()));
            amountTextField.setText(Integer.toString(offer.getItem().getAmount()));
        });
        customAmount.setBounds(amountTextField.getX() + (amountTextField.getWidth() / 2) - (width / 2), amountTextField.getY() + amountTextField.getHeight() + heightDelta, width, height);
        customAmount.setMargin(new Insets(0, 0, 0, 0));

        JButton plusOneAmount = new JButton("+1");
        amount.add(plusOneAmount);
        plusOneAmount.addActionListener((ActionEvent e) -> {
            offer.setSellAll(false);
            offer.getItem().setAmount(offer.getItem().getAmount() + 1);
            amountTextField.setText(Integer.toString(offer.getItem().getAmount()));
        });
        plusOneAmount.setBounds(plus5Amount.getX() - widthDelta - width, amountTextField.getY() + amountTextField.getHeight() + heightDelta, width, height);
        plusOneAmount.setMargin(new Insets(0, 0, 0, 0));

        JButton minusOneAmount = new JButton("-1");
        amount.add(minusOneAmount);
        minusOneAmount.addActionListener((ActionEvent e) -> {
            offer.setSellAll(false);
            offer.getItem().setAmount(offer.getItem().getAmount() - 1);
            amountTextField.setText(Integer.toString(offer.getItem().getAmount()));
        });
        minusOneAmount.setBounds(minus5Amount.getX() + width + widthDelta, amountTextField.getY() + amountTextField.getHeight() + heightDelta, width, height);
        minusOneAmount.setMargin(new Insets(0, 0, 0, 0));

        if (offer.isBuy()) {
            JButton limit = new JButton("Limit");
            amount.add(limit);
            limit.addActionListener((ActionEvent e) -> {
                offer.getItem().setAmount(offer.getBuyLimit());
                amountTextField.setText(Integer.toString(offer.getItem().getAmount()));
            });
            limit.setBounds(amountTextField.getX() + amountTextField.getWidth() + widthDelta, amountTextField.getY(), width, amountTextField.getHeight());
            limit.setMargin(new Insets(0, 0, 0, 0));

        } else {
            JButton all = new JButton("all");
            amount.add(all);
            all.addActionListener((ActionEvent e) -> {
                offer.setSellAll(true);
                amountTextField.setText("ALL");
            });
            all.setBounds(amountTextField.getX() + amountTextField.getWidth() + widthDelta, amountTextField.getY(), width, amountTextField.getHeight());
            all.setMargin(new Insets(0, 0, 0, 0));
        }

        amount.setBackground(Color.GRAY);
        dialog.add(amount);


        // bottom of dialog
        Button saveButton = new Button("Create");
        dialog.add(saveButton);
        saveButton.addActionListener(null);
        saveButton.setBounds((dialog.getWidth() / 2) - 100,dialog.getHeight() - 60,80,40);

        Button closeButton = new Button("Cancel");
        dialog.add(closeButton);
        closeButton.addActionListener((ActionEvent e) -> dialog.dispose());
        closeButton.setBounds((dialog.getWidth() / 2) + 20,dialog.getHeight() - 60,80,40);

        dialog.setLayout(null);
        dialog.setVisible(true);
    }


    @Override
    protected void startScript() {

    }

    @Override
    protected void displayErrorMessage() {

    }

    @Override
    protected boolean errorCheck() {
        return false;
    }

    @Override
    protected void saveSettings() {

    }
}
