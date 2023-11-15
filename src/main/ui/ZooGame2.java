package ui;

import model.Game;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.buttons.Button;
import ui.buttons.UpgradableButton;
import ui.buttons.UpgradeButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZooGame2 extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private static final int INTERVAL = 27;
    private Game game;
//    private GamePanel gp;
    private ScorePanel sp;
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
        jsonReader = new JsonReader(JSON_STORE, INTERVAL);
        jsonWriter = new JsonWriter(JSON_STORE);

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
//                menuframe.dispatchEvent(new WindowEvent(menuframe, WindowEvent.WINDOW_CLOSING));
            }
        });
        menuPanel.add(newGameButton);

        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame();
//                menuframe.dispatchEvent(new WindowEvent(menuframe, WindowEvent.WINDOW_CLOSING));
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

    private void initFields(Game game) {
//        timer = new Timer(INTERVAL, );

        shp = new ShopPanel(this, game);
        sp = new ScorePanel(this, game);
    }

    private void initGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        shp.setLayout(new GridLayout(0, 1));
        shp.setSize(new Dimension(0, 0));
        add(shp, BorderLayout.EAST);

        sp.setLayout(new BoxLayout(sp, BoxLayout.PAGE_AXIS));
        sp.setSize(new Dimension(0, 0));
        add(sp, BorderLayout.WEST);
        createExitButtons();
        initGameArea();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void newGame() {
        this.game = new Game(INTERVAL);
        startGame(game);
    }

    public void startGame(Game game) {
        //set name of game
        initFields(game);
        initGraphics();
        addTimer(game);
        timer.start();
    }

    private void addTimer(Game game) {
        timer = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.tick();
                shp.update();
                sp.update();
            }
        });
    }

    //MODIFIES: save file
    //EFFECTS: saves game state to file as json object
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved " + game.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads game from local save file
    private void loadGame() {
        try {
            game = jsonReader.read();
            System.out.println("Loaded " + game.getName() + " from " + JSON_STORE);
            startGame(game);

        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            throw new RuntimeException(e);
        }
    }

    private void initGameArea() {
        JPanel gameArea = new JPanel();

        BufferedImage buttonIcon = null;
        ImageIcon catIcon = new ImageIcon("./data/cat.png");
        ImageIcon catSmileIcon = new ImageIcon("./data/catSmile2.png");
        Image image = catIcon.getImage(); // transform it
        Image image2 = catSmileIcon.getImage();
        Image newimg = image.getScaledInstance(468,320,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        Image newimg2 = image2.getScaledInstance(468,320,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        catIcon = new ImageIcon(newimg);
        catSmileIcon = new ImageIcon(newimg2);
        JButton catButton = new JButton(catIcon);
        catButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.click();
            }
        });
        catButton.setDisabledIcon(catSmileIcon);
        catButton.setPressedIcon(catSmileIcon);
        catButton.setSelectedIcon(catSmileIcon);
        catButton.setDisabledSelectedIcon(catSmileIcon);
        catButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        catButton.setBorder(BorderFactory.createEmptyBorder());
        catButton.setContentAreaFilled(false);
        gameArea.add(catButton);

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
