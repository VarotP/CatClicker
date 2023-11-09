package ui.buttons;

import ui.ZooGame2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameButton extends Button {

    public NewGameButton(ZooGame2 zooGame2, JComponent parent) {
        super(zooGame2, parent);
    }

    @Override
    public void createButton(JComponent parent) {
        button = new JButton("New Game");
        addToParent(parent);
    }

    public void addListener() {
        button.addActionListener(new NewGameButton.ButtonClickHandler());
    }

    public class ButtonClickHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            zooGame2.startGame();
        }
    }
}
