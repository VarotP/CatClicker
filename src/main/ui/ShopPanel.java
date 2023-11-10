package ui;

import model.Game;
import model.Upgradable;
import model.Upgrade;
import ui.buttons.Button;
import ui.buttons.UpgradableButton;
import ui.buttons.UpgradeButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShopPanel extends JPanel {
    private ZooGame2 zooGame2;
    private Game game;
    private List<Button> buttons;

    public ShopPanel(ZooGame2 zooGame2, Game game) {
        this.buttons = new ArrayList<>();
        this.zooGame2 = zooGame2;
        this.game = game;

        Button catButton = new UpgradableButton(zooGame2, game, this, game.cat);
        buttons.add(catButton);

        Button dogButton = new UpgradableButton(zooGame2, game, this, game.dog);
        buttons.add(dogButton);

        Button onePerClickButton = new UpgradeButton(zooGame2, game, this, game.onePerClickU);
        buttons.add(onePerClickButton);

        Button fivePerClickButton = new UpgradeButton(zooGame2, game, this, game.fivePerClickU);
        buttons.add(fivePerClickButton);

        update();
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

}
