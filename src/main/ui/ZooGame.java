package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.Animal;
import model.Game;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import model.Location;
import model.Upgrade;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ZooGame {
    //fields
    boolean keepGoing = true;
    private Game game;
    private Screen screen;

    //Upgrades and Animals

    private final Upgrade onePerClickU = new Upgrade("OnePerClick", 5, 0, 1, null);
    private final Upgrade fivePerClickU = new Upgrade("FivePerClick", 25, 0, 5, null);
    private final Upgrade animalBuff = new Upgrade("AnimalBuff", 50, 5, 0, null);
    private final Animal cat = new Animal("Cat", 50, 1, 0, null);
    private final Animal dog = new Animal("Dog", 75, 2, 0, null);
    private final Location cafe = new Location("Animal Cafe", 10, 50, null);
    private final Location zoo = new Location("Zoo", 100, 100, null);
    private final List<Upgrade> uplist = new ArrayList<>();
    private final List<Animal> anList = new ArrayList<>();
    private final List<Upgrade> uaList = new ArrayList<>();

    //EFFECTS: runs the game
    public void runGame() throws InterruptedException, IOException {
        initGame();
        while (keepGoing) {   // (*)
            tick();                                                     // update the game
            Thread.sleep(1000L / Game.TICKS_PER_SECOND);                // (**)
        }

        System.exit(0); // game is over, we can exit the app
    }


    //EFFECTS: initializes initial game state
    private void initGame() throws IOException {
        game = new Game();
        screen = new DefaultTerminalFactory().createScreen();
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

        handleUserInput();
        game.tick();
        render();
    }

    //MODIFIES: this
    //EFFECTS: Handles user input (clicks)
    private void handleUserInput() throws IOException {
        KeyStroke stroke = screen.pollInput();
        if (stroke != null) {
            //get money
            if (stroke.getKeyType() == KeyType.Enter) {
                game.click();
            }

            //view list of avail upgrades
            if (stroke.getKeyType() == KeyType.Backspace) {
                System.out.println(game.displayAvailUpgrades());
            }

            //view list of owned upgrades
            if (stroke.getKeyType() == KeyType.ArrowUp) {
                System.out.println(game.displayStats());
            }

            //buy upgrade1
            if (stroke.getKeyType() == KeyType.ArrowDown) {
                if (game.getPlayer1().getAvailUpgrades().contains(onePerClickU)
                        && game.getScore() >= onePerClickU.getCost()) {
                    System.out.println("Upgrade bought");
                    game.setScore(game.getPlayer1().buyUpgrades(game.getScore(), onePerClickU));
                } else {
                    System.out.println("Insufficient Money");
                }
            }
        }

    }


    //EFFECTS: Renders the scores and availUPgrades and everything
    private void render() {
        System.out.println("Score =" + game.getScore());
    }


}
