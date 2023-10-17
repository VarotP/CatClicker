package ui;

//import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;


public class ZooGame {
    //fields
    boolean keepGoing = true;
    private Game game;
    private Screen screen;

    //Upgrades and Animals

    private final Upgrade onePerClickU = new Upgrade("OnePerClick", 200, 0, 1, null);
    private final Upgrade fivePerClickU = new Upgrade("FivePerClick", 5000, 0, 5, null);
    private final Upgrade animalBuff = new Upgrade("AnimalBuff", 50, 5, 0, null);
    private final Animal cat = new Animal("Cat", 100, 1, 0, null);
    private final Animal dog = new Animal("Dog", 200, 2, 0, null);
    private final List<Upgrade> uplist = new ArrayList<>();
    private final List<Animal> anList = new ArrayList<>();
    private final List<Upgrade> uaList = new ArrayList<>();
    TextGraphics scoreGraphic;
    TextGraphics availUpgradeGraphic;
    TextGraphics ownedUpgradeGraphic;
    TextGraphics perClickGraphic;
    TextGraphics perSecGraphic;

    //EFFECTS: runs the game
    public void runGame() throws InterruptedException, IOException {
        initGame();
        while (keepGoing) {   // (*)
            tick();                                                     // update the game
            Thread.sleep(1000L / Game.TICKS_PER_SECOND);                // (**)
        }

        exit(0); // game is over, we can exit the app
    }


    //EFFECTS: initializes initial game state
    private void initGame() throws IOException {
        game = new Game();
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(
                new TerminalSize(150, 50)).createTerminal();
        screen = new TerminalScreen(terminal);
        screen.startScreen();

        //init upgrade relationships
        uplist.add(onePerClickU);
        uplist.add(fivePerClickU);
        game.getPlayer1().setAvailUpgrades(uplist);
        uaList.add(animalBuff);
        cat.setAvailUpgrades(uaList);
        dog.setAvailUpgrades(uaList);
        anList.add(cat);
        anList.add(dog);
        game.getPlayer1().setAvailAnimals(anList);



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
        TextGraphics scoreGraphic = screen.newTextGraphics();
        TextGraphics availUpgradeGraphic = screen.newTextGraphics();
        TextGraphics ownedUpgradeGraphic = screen.newTextGraphics();
        TextGraphics perClickGraphic = screen.newTextGraphics();
        TextGraphics perSecGraphic = screen.newTextGraphics();

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
