package ui.buttons;

import model.Game;
import model.Upgradable;
import ui.ZooGame2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents an upgradable button found in the shop
public class UpgradableButton extends Button {
    private Game game;
    private int quantity;

    // MODIFIES: this
    // EFEFCTSL constructor
    public UpgradableButton(ZooGame2 zooGame2, Game game, JComponent parent, Upgradable upgradable) {
        super(zooGame2, parent);
        this.game = game;
        this.upgradable = upgradable;
        button.setText(upgradable.getName() + "\n $" + upgradable.getCost());
        name = upgradable.getName();
        this.quantity = 1;
        updateCost();
    }

    @Override
    public void createButton(JComponent parent) {
        button = new JButton("upgradable");
        addToParent(parent);
    }

    @Override
    public void addListener() {
        button.addActionListener(new ButtonClickHandler());
    }

    public class ButtonClickHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            game.buyAnimal(upgradable.getName(), quantity);
            updateCost();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets quantity of upgrades being bought
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    // MODIFIES: this
    // EFFECTS: changes text on the button to reflect the new cost based on the quantity
    public void updateCost() {
        button.setText(upgradable.getName() + "\n $" + upgradable.getCost() * quantity);
    }

}
