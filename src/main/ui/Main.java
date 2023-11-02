package ui;

import java.io.IOException;

// runs the game
public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        ZooGame newGame = new ZooGame();
        newGame.runGame();
    }
}
