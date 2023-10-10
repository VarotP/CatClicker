package model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int TICKS_PER_SECOND = 1;
    private Player player1 = new Player("Placeholder", 1, null);
    private int score = 0;
    private List<Location> locations = new ArrayList<>();

    //EFFECTS: adds up all the money per sec and ocassionally spawns specials
    public void tick() {
        if (player1.getAnimals() != null) {
            for (Animal a: player1.getAnimals()) {
                score += a.getPerSec();
            }
        }

        for (Location l: locations) {
            for (Animal a: l.getAnimals()) {
                score += a.getPerSec();
            }
        }
    }

    public void click() {
        score += player1.getPerClick();
    }

    public int getScore() {
        return score;
    }
}
