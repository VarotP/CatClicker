package ui;

import model.Game;

import javax.swing.*;

// ui for the score panel
public class ScorePanel extends JPanel {
    private ZooGame2 zooGame2;
    private Game game;

    private JLabel playerName;
    private JLabel score;
    private JLabel scorePerSecond;
    private JLabel scorePerClick;
    private JLabel ownedStuff;
    private JLabel availStuff;

    // MODIFIES: this
    // EFFECTS: constructor
    public ScorePanel(ZooGame2 zooGame2, Game game) {
        this.zooGame2 = zooGame2;
        this.game = game;

        this.playerName = new JLabel("Player name: ");
        this.add(playerName);

        this.score = new JLabel("Score: ");
        this.add(score);

        this.scorePerSecond = new JLabel("Score Per Second: ");
        this.add(scorePerSecond);

        this.scorePerClick = new JLabel("Score Per Click: ");
        this.add(scorePerClick);

        this.ownedStuff = new JLabel("Owned Animals: ");
        this.add(ownedStuff);

        this.availStuff = new JLabel("Owned Upgrades: ");
        this.add(availStuff);
    }

    // EFFECTS: updates the panel
    public void update() {
        setTexts();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: changes text to corresponding numbers
    private void setTexts() {
        score.setText("Score: " + game.getScoreInt());
        scorePerSecond.setText("Score Per Second: " +  + game.getPerSec());
        scorePerClick.setText("Score per click: " + game.getPerClick());
        ownedStuff.setText(game.getOwnedString());
        availStuff.setText(game.getAvailString());
    }
}
