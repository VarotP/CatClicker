package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.Game;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;


import java.io.IOException;


public class ZooGame {
    //fields
    boolean keepGoing = true;
    private Game game;
    private Screen screen;

    //
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
    }

    /**
     * Handles one cycle in the game by taking user input,
     * ticking the game internally, and rendering the effects
     */
    private void tick() throws IOException {

        handleUserInput();
        game.tick();
        render();
    }

    //EFFECTS: Handles user input (clicks)
    private void handleUserInput() throws IOException {
        KeyStroke stroke = screen.pollInput();
        if (stroke != null) {
            if (stroke.getKeyType() == KeyType.Enter) {
                game.click();
            }
        }

    }


    //EFFECTS: Renders the scores and availUPgrades and everything
    private void render() {
        System.out.println(game.getScore());
    }


}
