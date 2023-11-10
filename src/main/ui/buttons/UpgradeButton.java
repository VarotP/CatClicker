package ui.buttons;

import model.Game;
import model.Upgrade;
import ui.ZooGame2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpgradeButton extends Button {
    private Game game;

    public UpgradeButton(ZooGame2 zooGame2, Game game, JComponent parent, Upgrade upgrade) {
        super(zooGame2, parent);
        this.game = game;
        this.upgrade = upgrade;
        button.setText(upgrade.getName());
        name = upgrade.getName();
    }

    @Override
    public void createButton(JComponent parent) {
        button = new JButton("upgrade");
        addToParent(parent);
    }

    public void addListener() {
        button.addActionListener(new UpgradeButton.ButtonClickHandler());
    }

    public class ButtonClickHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            game.buyUpgrade(upgrade.getName());
        }
    }

}
