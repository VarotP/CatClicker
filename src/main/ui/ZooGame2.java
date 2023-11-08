package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ZooGame2 extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private static final int INTERVAL = 10;
    private Game game;
//    private GamePanel gp;
//    private ScorePanel sp;
    private ShopPanel shp;
    private Timer timer;
    private List<Button> buttons;

    public ZooGame2() {
        super("ZooGame");
        initFields();
        initGraphics();
        startGame();
    }

    private void initFields() {
//        timer = new Timer(INTERVAL, );
        buttons = new ArrayList<Button>();
        game = new Game(INTERVAL);
    }

    private void initGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        createButtons();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startGame() {
        //set name of game
    }

    private void createButtons() {
        JPanel buttonArea = new JPanel();
        buttonArea.setLayout(new GridLayout(0, 1));
        buttonArea.setSize(new Dimension(0, 0));
        add(buttonArea, BorderLayout.EAST);

        Button catButton = new UpgradableButton(game, buttonArea, game.cat);
        buttons.add(catButton);

        Button dogButton = new UpgradableButton(game, buttonArea, game.dog);
        buttons.add(dogButton);
    }
}
