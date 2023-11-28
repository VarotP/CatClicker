package ui;

import model.EventLog;
import model.Event;
import model.Game;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// "main" game controller
public class ZooGame2 extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private static final int INTERVAL = 27;
    private Game game;
//    private GamePanel gp;
    private ScorePanel sp;
    private ShopPanel shp;
    private GameArea ga;
//    private ScorePanel scp;
    boolean keepGoing = true;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/testSave.json";
    private Timer timer;
    private EventLog eventLog = EventLog.getInstance();

    // MODIFIES: this
    // EFFECTS: constructor
    public ZooGame2() {
        super("ZooGame");
        startMenu();
//        startGame();
    }

    // MODIFIES: this
    // EFFECTS: starts the main menu for the game
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
                menuframe.setVisible(false);
//                menuframe.dispatchEvent(new WindowEvent(menuframe, WindowEvent.WINDOW_CLOSING));
            }
        });
        menuPanel.add(newGameButton);
        addMainMenuButtons(menuPanel, menuframe);

        menuframe.add(menuPanel);
        menuframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuframe.setLocationRelativeTo(null);
        menuframe.setVisible(true);
    }

    // EFFECTS; creates main menu buttons and adds to the menu
    private void addMainMenuButtons(JPanel menuPanel, JFrame menuframe) {
        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame();
                menuframe.setVisible(false);
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
    }

    // MODIFIES: this
    // EFFECTS: inits various panels
    private void initFields(Game game) {
//        timer = new Timer(INTERVAL, );
        ga = new GameArea(this, game);
        shp = new ShopPanel(this, game);
        sp = new ScorePanel(this, game);
    }

    // EFFECTS: sets graphic values for panels
    private void initGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        createExitButtons();

        shp.setLayout(new GridLayout(0, 1));
        shp.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        add(shp, BorderLayout.LINE_END);

        sp.setLayout(new BoxLayout(sp, BoxLayout.PAGE_AXIS));
        sp.setSize(new Dimension(200, 0));
        add(sp, BorderLayout.LINE_START);


        ga.setPreferredSize(new Dimension(100, 100));
        ga.setMinimumSize(new Dimension(100, 100));
        add(ga, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: handles new game option and starts a new game
    public void newGame() {
        this.game = new Game(INTERVAL);
        startGame(game);
    }

    // EFFECTS: starts the game based on game object
    public void startGame(Game game) {
        //set name of game
        initFields(game);
        initGraphics();
        addTimer(game);
        timer.start();
    }

    // EFFECTS: adds timer with update functions for each panel
    private void addTimer(Game game) {
        timer = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.tick();
                shp.update();
                sp.update();
                ga.update();
            }
        });
    }

    //MODIFIES: save file
    //EFFECTS: saves game state to file as json object
    public void saveGame() {
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

    // EFFECTS: creates the exit buttons for the game
    private void createExitButtons() {
        JPanel exitButtons = new JPanel();

        JButton saveButton = new JButton("Save and Exit");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
                for (Event i : eventLog) {
                    System.out.println(i.getDate() + " " + i.getDescription());
                }
                System.exit(0); // Exits the application
            }
        });

        createExitButton(exitButtons);

        exitButtons.add(saveButton);


        add(exitButtons, BorderLayout.PAGE_START);
    }

    private void createExitButton(JPanel exitButtons) {
        JButton exitButton = new JButton("Exit without Saving");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Event i : eventLog) {
                    System.out.println(i.getDate() + " " + i.getDescription());
                }
                System.exit(0); // Exits the application
            }
        });
        exitButtons.add(exitButton);
    }
}
