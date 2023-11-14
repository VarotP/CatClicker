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

public class ShopPanel extends JPanel {
    private ZooGame2 zooGame2;
    private Game game;
    private List<Button> buttons;
    private List<String> buyOptions;
    private int buyOptionIndex;
    private JButton buyOptionButton;

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

        Button onePerClickButton = new UpgradeButton(zooGame2, game, this, game.onePerClickU);
        buttons.add(onePerClickButton);

        Button fivePerClickButton = new UpgradeButton(zooGame2, game, this, game.fivePerClickU);
        buttons.add(fivePerClickButton);
    }

    public void update() {
        game.checkUnlocks();
        activateButtons();
        repaint();
    }

    private void activateButtons() {
        Map<Upgradable, Boolean> animalMap = game.getAvailAnimals();
        Map<Upgrade, Boolean> upgradeMap = game.getAvailUpgrades();
        for (Button b : buttons) {
            // sets buttons to be enabled based on value of animal from hashmap
            if (b.getClass() == UpgradableButton.class) {
                b.setEnabled(animalMap.get(game.findAnimal(b.getName())));
            } else {
                b.setEnabled(upgradeMap.get(game.findUpgrade(b.getName())));
            }
        }
    }

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

    private void setButtonQuantity(int quantity) {
        for (Button b : buttons) {
            if (b.getClass() == UpgradableButton.class) {
                UpgradableButton uButton = (UpgradableButton) b;
                uButton.setQuantity(quantity);
                uButton.updateCost();
            }
        }
    }

    private void setButtonQuantity(String max) {
        for (Button b : buttons) {
            if (b.getClass() == UpgradableButton.class) {
                UpgradableButton uButton = (UpgradableButton) b;
                uButton.setQuantity(game.getScoreInt() / uButton.getUpgradable().getCost());
                uButton.updateCost();
            }
        }
    }

}
