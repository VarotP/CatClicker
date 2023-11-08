package ui;

//import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
//import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import persistence.JsonReader;
import persistence.JsonWriter;


import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

import static java.lang.System.exit;


public class ZooGame extends JFrame {
    //fields
    boolean keepGoing = true;
    private Game game;
    private Screen screen;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/testSave.json";
    public static final int TICKS_PER_SECOND = 10;

//    private GamePanel gp;
//    private ScorePanel sp;
//    private ShopPanel shp;
//    private Timer t;

    //graphic fields
    TextGraphics scoreGraphic;
    TextGraphics availUpgradeGraphic;
    TextGraphics ownedUpgradeGraphic;
    TextGraphics perClickGraphic;
    TextGraphics perSecGraphic;

    //Upgrades and Animals

    public ZooGame() {
        jsonReader = new JsonReader(JSON_STORE, TICKS_PER_SECOND);
        jsonWriter = new JsonWriter(JSON_STORE);
        try {
            runGame();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //EFFECTS: runs the game
    public void runGame() throws InterruptedException, IOException {
        mainMenu();
        exit(0); // game is over, we can exit the app
    }

    //MODIFIES: this
    //EFFECTS: displays main menu
    private void mainMenu() throws IOException, InterruptedException {

        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        while (keepGoing) {
            System.out.println("New = n, Load = l, Exit = e");
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("e")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("See you again!");
    }


    //EFFECTS: starts the screen and keeps the game running
    private void startGame(Game game) throws IOException, InterruptedException {
        startScreen(game);
        while (keepGoing) {   // (*)
            tick();                                                     // update the game
            Thread.sleep(1000L / TICKS_PER_SECOND);                // (**)
        }
    }


    //EFFECTS: initializes initial game state
    private void startScreen(Game game) throws IOException {
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(
                new TerminalSize(150, 50)).createTerminal();
        screen = new TerminalScreen(terminal);
        screen.startScreen();
    }

    //MODIFIES: this
    //EFFECTS: processes user command
    private void processCommand(String command) throws IOException, InterruptedException {
        if (command.equals("n")) {
            System.out.println("Enter your name");
            String name = input.next();
            game = new Game(TICKS_PER_SECOND);
            game.setName(name);
            startGame(game);
        } else if (command.equals("l")) {
            loadGame();
        } else {
            System.out.println("invalid selection");
        }
    }

    //MODIFIES: this
    //EFFECTS: loads game from local save file
    private void loadGame() throws IOException, InterruptedException {
        try {
            game = jsonReader.read();
            System.out.println("Loaded " + game.getName() + " from " + JSON_STORE);
            startGame(game);
        } finally {
            System.out.println("Placeholder");
        }
//        catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
//        }
    }

    //EFFECTS: display menu for saving or not saving game
    private void endGame() {
        String command = null;
        input = new Scanner(System.in);
        System.out.println("Save Game? y = yes, n = no");
        command = input.next();
        command = command.toLowerCase();
        if (command.equals("y")) {
            saveGame();
            System.out.println("Game successfully saved!");
        }
        System.out.println("See you again!");
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
    //EFFECTS: handles a single tick of the game and renders it
    private void tick() throws IOException {
        try {
            handleUserInput();
            game.tick();
            render();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //MODIFIES: this
    //EFFECTS: Handles user input (clicks)
    @SuppressWarnings("methodlength")
    private void handleUserInput() throws IOException {
        KeyStroke stroke = screen.pollInput();
        if (stroke != null) {
            //Close game
            if (stroke.getKeyType() == KeyType.Escape) {
                screen.close();
                endGame();
                exit(0);
            }

            // SPACE BAR: get money
            if (stroke.getCharacter() == ' ') {
                game.click();
            }

            //buy upgrade1
            if (stroke.getCharacter() == '1') {
                game.buyUpgrade("OnePerClick");
            }

            //buy upgrade2
            if (stroke.getCharacter() == '2') {
                game.buyUpgrade("FivePerClick");
                render();
            }

            //buy monkey
            if (stroke.getCharacter() == '3') {
                game.buyAnimal("Cat", 1);
                render();
            }

            //buy monkey
            if (stroke.getCharacter() == '4') {
                game.buyAnimal("Dog", 1);
                render();
            }
        }

    }


    //EFFECTS: Renders the scores and availUpgrades and everything
    private void render() throws IOException {
        TextGraphics nameGraphic = screen.newTextGraphics();
        TextGraphics scoreGraphic = screen.newTextGraphics();
        TextGraphics availUpgradeGraphic = screen.newTextGraphics();
        TextGraphics ownedUpgradeGraphic = screen.newTextGraphics();
        TextGraphics perClickGraphic = screen.newTextGraphics();
        TextGraphics perSecGraphic = screen.newTextGraphics();

        nameGraphic.putString(5,3, game.getName());
        scoreGraphic.putString(5,5, "Score = " + game.getScoreInt());
        perSecGraphic.putString(5,7, "Score per second: " + game.getPerSec());
        perClickGraphic.putString(5,8, "Score per click: " + game.getPerClick());
        ownedUpgradeGraphic.putString(5,9, game.getOwnedString());
        availUpgradeGraphic.putString(5, 10, game.getAvailString());


        screen.refresh();
    }

}
