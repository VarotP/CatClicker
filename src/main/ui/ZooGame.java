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


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;


public class ZooGame {
    //fields
    boolean keepGoing = true;
    private Game game;
    private Screen screen;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/testSave.json";

    //graphic fields
    TextGraphics scoreGraphic;
    TextGraphics availUpgradeGraphic;
    TextGraphics ownedUpgradeGraphic;
    TextGraphics perClickGraphic;
    TextGraphics perSecGraphic;

    //Upgrades and Animals

    private final Upgrade onePerClickU = new Upgrade("OnePerClick", 200, 0, 1, 1.4,null);
    private final Upgrade fivePerClickU = new Upgrade("FivePerClick", 5000, 0, 5, 1.4, null);
    private final Upgrade animalBuff = new Upgrade("AnimalBuff", 50, 5, 0, 1.4, null);
    private final Animal cat = new Animal("Cat", 50, 1, 0, 1.2, null);
    private final Animal dog = new Animal("Dog", 200, 2, 0,  1.2, null);
    private final List<Upgrade> uplist = new ArrayList<>();
    private final List<Animal> anList = new ArrayList<>();
    private final List<Upgrade> uaList = new ArrayList<>();


    //EFFECTS: runs the game
    public void runGame() throws InterruptedException, IOException {
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        mainMenu();
        exit(0); // game is over, we can exit the app
    }

    private void startGame(Game game) throws IOException, InterruptedException {
        initGame(game);
        while (keepGoing) {   // (*)
            tick();                                                     // update the game
            Thread.sleep(1000L / Game.TICKS_PER_SECOND);                // (**)
        }
    }


    //EFFECTS: initializes initial game state
    private void initGame(Game game) throws IOException {

        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(
                new TerminalSize(150, 50)).createTerminal();
        screen = new TerminalScreen(terminal);
        screen.startScreen();



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

    //MODIFIES: this
    //EFFECTS: processes user command
    private void processCommand(String command) throws IOException, InterruptedException {
        if (command.equals("n")) {
            System.out.println("Enter your name");
            String name = input.next();
            game = new Game(name);
            startGame(game);
        } else if (command.equals("l")) {
            loadGame();
        } else {
            System.out.println("invalid selection");
        }
    }

    private void loadGame() throws IOException, InterruptedException {
        try {
            game = jsonReader.read();
            System.out.println("Loaded " + game.getPlayer1().getName() + " from " + JSON_STORE);
            startGame(game);
        } finally {
            System.out.println("Placeholder");
        }
//        catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
//        }
    }

    /**
     * Handles one cycle in the game by taking user input,
     * ticking the game internally, and rendering the effects
     */

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
                exit(0);
            }

            // SPACE BAR: get money
            if (stroke.getCharacter() == ' ') {
                game.click();
            }

            //buy upgrade1
            if (stroke.getCharacter() == '1') {
                buyUpgrade(game.getPlayer1(), onePerClickU);
            }

            //buy upgrade2
            if (stroke.getCharacter() == '2') {
                buyUpgrade(game.getPlayer1(), fivePerClickU);
                render();
            }

            //buy monkey
            if (stroke.getCharacter() == '3') {
                buyAnimal(game.getPlayer1(), cat);
                render();
            }

            //buy monkey
            if (stroke.getCharacter() == '4') {
                buyAnimal(game.getPlayer1(), dog);
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
        perClickGraphic.putString(5,8, "Score per click: " + game.getPlayer1().getPerClick());
        ownedUpgradeGraphic.putString(5,9, game.displayStats());
        availUpgradeGraphic.putString(5, 10, game.displayAvailUpgrades());


        screen.refresh();
    }

    //MODIFIES: this
    //EFFECTS: adds upgrade to list of animals if never owned, +1 to upgrade's count if already owned
    private void buyUpgrade(Upgradable who, Upgrade thisupgrade) {
        if (who.getAvailUpgrades().contains(thisupgrade)
                && game.getScore() >= thisupgrade.getCost()) {
            System.out.println(thisupgrade.getName() + " upgrade bought");
            game.setScore(who.buyUpgrades(game.getScore(), thisupgrade));
        } else {
            System.out.println("Insufficient Money");
        }
    }

    //MODIFIES: this
    //EFFECTS: adds animal to list of animals if never owned, +1 to animal's count if already owned
    private void buyAnimal(Location who, Animal thisanimal) {
        if (who.getAvailAnimals().contains(thisanimal)
                && game.getScore() >= thisanimal.getCost()) {
            System.out.println(thisanimal.getName() + " bought");
            game.setScore(who.buyAnimal(game.getScore(), thisanimal));
        } else {
            System.out.println("Insufficient Money");
        }
    }



}
