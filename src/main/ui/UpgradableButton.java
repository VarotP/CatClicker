package ui;

import model.Game;
import model.Upgradable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpgradableButton implements Button {
    private JButton button;
    private Game game;
    private Upgradable upgradable;
    private boolean active;

    public UpgradableButton(Game game, JComponent parent, Upgradable upgradable) {
        this.game = game;
        this.upgradable = upgradable;
        createButton(parent);
        addToParent(parent);
        active = false;
        addListener();
    }

    private void createButton(JComponent parent) {
        button = new JButton(upgradable.getName());
        addToParent(parent);
    }

    private void addToParent(JComponent parent) {
        parent.add(button);
    }

    private void addListener() {
        button.addActionListener(new ButtonClickHandler());
    }

    private static class ButtonClickHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Clicked!");
        }
    }

}
