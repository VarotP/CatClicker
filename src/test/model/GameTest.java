package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    Game newGame;
    List<Location> loclist;
    @BeforeEach
    public void setup() {
        newGame = new Game();
        loclist = new ArrayList<>();
    }

    @Test
    public void initGame() {
        assertEquals(0, newGame.getScore());
        assertEquals(loclist, newGame.getLocations());
    }

    @Test
    public void clickTest() {
        assertEquals(0, newGame.getScore());
        newGame.click();
        assertEquals(1, newGame.getScore());
        newGame.click();
        assertEquals(2, newGame.getScore());

        newGame.getPlayer1().setPerClick(2);
        newGame.click();
        assertEquals(4, newGame.getScore());
    }

    @Test
    public void tickTest() {
        assertEquals(0, newGame.getScore());
        newGame.tick();
        assertEquals(0, newGame.getScore());

        Animal cat = new Animal("Cat", 50, 1, 0, null);
        newGame.getPlayer1().addAnimal(cat);

        assertEquals(0, newGame.getScore());
        newGame.tick();
        assertEquals(1, newGame.getScore());

        Animal dog = new Animal("Dog", 75, 2, 0, null);
        newGame.getPlayer1().addAnimal(dog);

        assertEquals(1, newGame.getScore());
        newGame.tick();
        assertEquals(4, newGame.getScore());

        Location cafe = new Location("Animal Cafe", 10, 50, null);
        List<Location> dummy = new ArrayList<>();
        dummy.add(cafe);
        newGame.setLocations(dummy);

        assertEquals(4, newGame.getScore());
        newGame.tick();
        assertEquals(17, newGame.getScore());

    }

    @Test
    public void displayAvailTest() {
        Animal cat = new Animal("Cat", 50, 1, 0, null);
        Upgrade animalBuff = new Upgrade("AnimalBuff", 50, 5, 0, null);
        List<Upgrade> uaList = new ArrayList<>();
        uaList.add(animalBuff);
        cat.setAvailUpgrades(uaList);
        newGame.getPlayer1().setAvailUpgrades(uaList);
        newGame.setScore(1000);
        newGame.getPlayer1().buyAnimal(newGame.getScore(), cat);
        assertEquals("", newGame.displayAvailUpgrades());
    }

    @Test
    public void displayStatsTest() {
        Animal cat = new Animal("Cat", 50, 1, 0, null);
        Upgrade onePerClickU = new Upgrade("OnePerClick", 5, 0, 1, null);
        Upgrade animalBuff = new Upgrade("AnimalBuff", 50, 5, 0, null);
        cat.addUpgrade(onePerClickU);
        newGame.getPlayer1().addAnimal(cat);
        newGame.getPlayer1().addUpgrade(onePerClickU);
        assertEquals("", newGame.displayStats());
    }
}
