package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

// ui for the game area
public class GameArea extends JPanel {
    JPanel gameArea;
    ImageIcon catIcon;
    ImageIcon catSmileIcon;
    JButton catButton;
    ZooGame2 zooGame2;
    Game game;

    // MODIFIES: this
    // EFFECTS: constructor
    public GameArea(ZooGame2 zooGame2, Game game) {
        this.zooGame2 = zooGame2;
        this.game = game;
        gameArea = new JPanel();

        BufferedImage buttonIcon = null;
        initCatButton();
        this.add(catButton);
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: creates the cat button
    private void initCatButton() {
        catIcon = new ImageIcon("./data/cat.png");
        catSmileIcon = new ImageIcon("./data/catSmile2.png");
        Image image = catIcon.getImage(); // transform it
        Image image2 = catSmileIcon.getImage();
        Image newimg = image.getScaledInstance(468,320,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        Image newimg2 = image2.getScaledInstance(468,320,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        catIcon = new ImageIcon(newimg);
        catSmileIcon = new ImageIcon(newimg2);
        catButton = new JButton(catIcon);
        catButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.click();
                gameArea.repaint();
            }
        });
        catButton.setDisabledIcon(catSmileIcon);
        catButton.setPressedIcon(catSmileIcon);
        catButton.setSelectedIcon(catSmileIcon);
        catButton.setDisabledSelectedIcon(catSmileIcon);
        catButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catButton.setBorder(BorderFactory.createEmptyBorder());
        catButton.setContentAreaFilled(false);
    }

    // EFFECTS: updates the game area
    public void update() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.setFont(new Font("ARIAL", Font.PLAIN,20));
//        g.setColor(new Color(50, 50, 50));
//        g.drawString("+1", 650, 100);


        //        for (Graphics p : sprites) {
//            b.draw
//        }
    }
}
