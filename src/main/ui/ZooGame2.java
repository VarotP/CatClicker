package ui;

import model.Game;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.buttons.Button;
import ui.buttons.UpgradableButton;
import ui.buttons.UpgradeButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
//    private ScorePanel scp;
    boolean keepGoing = true;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/testSave.json";
    private Timer timer;


    public ZooGame2() {
        super("ZooGame");
        startMenu();
//        startGame();
    }

    private void startMenu() {
        JFrame menuframe = new JFrame("mainMenu");
        menuframe.setLayout(new BorderLayout());
        menuframe.setMinimumSize(new Dimension(400, 400));
        JPanel menuPanel = new JPanel();

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(); // Exits the application
            }
        });
        menuPanel.add(newGameButton);

        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame();
            }
        });
        menuPanel.add(loadGameButton);

        JButton exitGameButton = new JButton("Exit Game");
        exitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exits the application
            }
        });
        menuPanel.add(exitGameButton);


        menuframe.add(menuPanel);
        menuframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuframe.setLocationRelativeTo(null);
        menuframe.setVisible(true);
    }

    private void initFields() {
//        timer = new Timer(INTERVAL, );
        game = new Game(INTERVAL);
        shp = new ShopPanel(this, game);
    }

    private void initGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        shp.setLayout(new GridLayout(0, 1));
        shp.setSize(new Dimension(0, 0));
        add(shp, BorderLayout.EAST);
        createExitButtons();
        initScorePanel();
        initGameArea();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void startGame() {
        //set name of game
        initFields();
        initGraphics();
        addTimer();
        timer.start();
    }

    private void addTimer() {
        timer = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.tick();

            }
        });
    }

    public void saveGame() {
        System.out.println("game saved");
    }

    public void loadGame() {
        System.out.println("game loaded");
    }

    private void initScorePanel() {
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.PAGE_AXIS));
        scorePanel.setSize(new Dimension(0, 0));

        JLabel playerName = new JLabel("Player name: ");
        scorePanel.add(playerName);

        JLabel score = new JLabel("Score: ");
        scorePanel.add(score);

        JLabel ownedAnimals = new JLabel("Owned Animals: ");
        scorePanel.add(ownedAnimals);

        JLabel ownedUpgrades = new JLabel("Owned Upgrades: ");
        scorePanel.add(ownedUpgrades);

        add(scorePanel, BorderLayout.WEST);
    }

    private void initGameArea() {
        JPanel gameArea = new JPanel();
        JButton button = new JButton("Click me");
        gameArea.add(button);
        add(gameArea, BorderLayout.SOUTH);
    }

    private void createExitButtons() {
        JPanel exitButtons = new JPanel();

        JButton saveButton = new JButton("Save and Exit");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
                System.exit(0); // Exits the application
            }
        });

        JButton exitButton = new JButton("Exit without Saving");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exits the application
            }
        });

        exitButtons.add(saveButton);
        exitButtons.add(exitButton);

        add(exitButtons);
    }
}
