package ui.buttons;

import model.Game;
import model.Upgradable;
import ui.ZooGame2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpgradableButton extends Button {
    private Game game;
    private Upgradable upgradable;

    public UpgradableButton(ZooGame2 zooGame2, Game game, JComponent parent, Upgradable upgradable) {
        super(zooGame2, parent);
        this.game = game;
        this.upgradable = upgradable;
        button.setText(upgradable.getName());
    }

    @Override
    public void createButton(JComponent parent) {
        button = new JButton("upgradable");
        addToParent(parent);
    }

    public void addListener() {
        button.addActionListener(new ButtonClickHandler());
    }

    public static class ButtonClickHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Clicked!");
        }
    }

}
