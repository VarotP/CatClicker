package ui;

import model.Game;
import model.Upgradable;
import model.Upgrade;
import ui.buttons.Button;
import ui.buttons.UpgradableButton;
import ui.buttons.UpgradeButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// ui for the shop panel on the right of the game screen
public class ShopPanel extends JPanel {
    private ZooGame2 zooGame2;
    private Game game;
    private List<Button> buttons;
    private List<String> buyOptions;
    private int buyOptionIndex;
    private JButton buyOptionButton;

    // MODIFIES: this
    // EFFECTS: constructor
    public ShopPanel(ZooGame2 zooGame2, Game game) {
        this.buttons = new ArrayList<>();
        this.zooGame2 = zooGame2;
        this.game = game;
        this.buyOptions = new ArrayList<>();
        buyOptions.add("Buy x1");
        buyOptions.add("Buy x10");
        buyOptions.add("Buy x100");
        buyOptions.add("Buy Max");
        this.buyOptionIndex = 0;

        initButtons();
        update();
    }

    // MODIFIES: this
    // EFEFCTS: initializes buy buttons
    private void initButtons() {
        buyOptionButton = new JButton("Buy x1");
        buyOptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cycleBuyOption();
            }
        });
        add(buyOptionButton);

        Button catButton = new UpgradableButton(zooGame2, game, this, game.cat);
        buttons.add(catButton);

        Button dogButton = new UpgradableButton(zooGame2, game, this, game.dog);
        buttons.add(dogButton);

        Button capyButton = new UpgradableButton(zooGame2, game, this, game.capybara);
        buttons.add(capyButton);

        Button onePerClickButton = new UpgradeButton(zooGame2, game, this, game.onePerClickU);
        buttons.add(onePerClickButton);

        Button fivePerClickButton = new UpgradeButton(zooGame2, game, this, game.fivePerClickU);
        buttons.add(fivePerClickButton);
    }

    // MODIFIES: game, this
    // EFFECTS: refreshes the shop panel
    public void update() {
        game.checkUnlocks();
        activateButtons();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: activates buttons based on if certain score is reached for each button
    private void activateButtons() {
        Map<Upgradable, Boolean> animalMap = game.getAvailAnimals();
        Map<Upgrade, Boolean> upgradeMap = game.getAvailUpgrades();
        for (Button b : buttons) {
            int cost;
            Boolean activated;
            // sets buttons to be enabled based on value of animal from hashmap
            if (b.getClass() == UpgradableButton.class) {
                UpgradableButton upButton = (UpgradableButton) b;
                activated = animalMap.get(game.findAnimal(upButton.getName()));
                cost = (upButton.getUpgradable().getCost() * upButton.getQuantity());
                upButton.setEnabled(game.getScore() >= cost && activated);
            } else {
                activated = upgradeMap.get(game.findUpgrade(b.getName()));
                b.setEnabled(game.getScore() >= b.getUpgrade().getCost() && activated
                        && !game.getUpgrades().contains(b.getUpgrade()));
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: cycles the buy options for all buttons
    private void cycleBuyOption() {
        buyOptionIndex += 1;
        buyOptionIndex = buyOptionIndex % 4;

        buyOptionButton.setText(buyOptions.get(buyOptionIndex));
        if (buyOptionIndex == 0) {
            setButtonQuantity(1);
        } else if (buyOptionIndex == 1) {
            setButtonQuantity(10);
        } else if (buyOptionIndex == 2) {
            setButtonQuantity(100);
        } else if (buyOptionIndex == 3) {
            setButtonQuantity("Max");
        }
    }

    // MODIFIES: this
    // EFFECTS: cycles buy option according to buyOption index
    private void setButtonQuantity(int quantity) {
        for (Button b : buttons) {
            if (b.getClass() == UpgradableButton.class) {
                UpgradableButton upButton = (UpgradableButton) b;
                upButton.setQuantity(quantity);
                upButton.updateCost();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: cycles buy option the max quantity of item that could be bought
    private void setButtonQuantity(String max) {
        for (Button b : buttons) {
            if (b.getClass() == UpgradableButton.class) {
                UpgradableButton upButton = (UpgradableButton) b;
                int cost = game.getScoreInt() / upButton.getUpgradable().getCost();
                if (cost != 0) {
                    upButton.setQuantity(game.getScoreInt() / upButton.getUpgradable().getCost());
                }
                upButton.updateCost();
            }
        }
    }

}
