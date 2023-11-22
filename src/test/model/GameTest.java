package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    Game game;

    @BeforeEach
    void setup() {
        game = new Game(10);
    }

    @Test
    void constructorTest() {
        assertEquals(1, game.getPerClick());
        assertEquals(0, game.getPerSec());
        assertEquals(0, game.getScore());
    }

    @Test
    void tickTest() {
        game.tick();
        assertEquals(0, game.getScore());
        assertEquals(0, game.getPerSec());
        game.setScore(50);
        game.buyAnimal("Cat", 1);
        for (int i = 0; i < 10; i ++) {
            game.tick();
        }
        assertEquals(41, game.getScoreInt());
        assertEquals(1, game.getPerSec());
    }

    @Test
    void clickTest() {
        assertEquals(0, game.getScoreInt());
        game.click();
        assertEquals(1, game.getScoreInt());
    }

    @Test
    void buyUpgradeTest() {
        game.buyUpgrade("lmaoo");
        assertEquals("<html> <br/> Owned Animals: <br/> <br/>Owned Upgrades:  <br/> <br/></html>", game.getOwnedString());
        game.buyUpgrade("OnePerClick");
        assertEquals("<html> <br/> Owned Animals: <br/> <br/>Owned Upgrades:  <br/> <br/></html>", game.getOwnedString());
        game.setScore(500);
        game.buyUpgrade("OnePerClick");
        assertEquals("<html> <br/> Owned Animals: <br/> <br/>Owned Upgrades:  <br/>OnePerClick <br/> <br/></html>", game.getOwnedString());
        game.buyUpgrade("OnePerClick");
        assertEquals("<html> <br/> Owned Animals: <br/> <br/>Owned Upgrades:  <br/>OnePerClick <br/> <br/></html>", game.getOwnedString());
        game.buyUpgrade("TwentyPerClick");
        assertEquals("<html> <br/> Owned Animals: <br/> <br/>Owned Upgrades:  <br/>OnePerClick <br/> <br/></html>", game.getOwnedString());
    }

    @Test
    void buyAnimalTest() {
        game.buyAnimal("lmaoo", 1);
        assertEquals("<html> <br/> Owned Animals: <br/> <br/>Owned Upgrades:  <br/> <br/></html>", game.getOwnedString());
        game.buyAnimal("Cat", 1);
        assertEquals("<html> <br/> Owned Animals: <br/> <br/>Owned Upgrades:  <br/> <br/></html>", game.getOwnedString());
        game.setScore(200);
        game.buyAnimal("Cat", 1);
        assertEquals("<html> <br/> Owned Animals: <br/>Cat <br/> <br/>Owned Upgrades:  <br/> <br/></html>", game.getOwnedString());
        game.buyAnimal("Cat", 1);
        assertEquals("<html> <br/> Owned Animals: <br/>Cat <br/> <br/>Owned Upgrades:  <br/> <br/></html>", game.getOwnedString());
        game.buyAnimal("Capybara", 1);
        assertEquals("<html> <br/> Owned Animals: <br/>Cat <br/> <br/>Owned Upgrades:  <br/> <br/></html>", game.getOwnedString());
        game.setScore(0);
        game.buyAnimal("Cat", 1);
        assertEquals("<html> <br/> Owned Animals: <br/>Cat <br/> <br/>Owned Upgrades:  <br/> <br/></html>", game.getOwnedString());
    }

    @Test
    void getOwnedStringTest() {
        game.setScore(10000);
        game.buyAnimal("Cat", 2);
        game.buyUpgrade("OnePerClick");
        assertEquals("<html> <br/> Owned Animals: <br/>Cat <br/> <br/>Owned Upgrades:  <br/>OnePerClick <br/> <br/></html>", game.getOwnedString());
    }

    @Test
    void getAvailStringTest() {
        assertEquals("<html> Available Animals: <br/>Capybara <br/>Cat <br/>Dog <br/> <br/>Available Upgrades:  <br/>OnePerClick <br/>FivePerClick <br/>TwentyPerClick <br/></html>", game.getAvailString());
    }

    @Test
    void toJsonTest() {
        game.setName("Gom");
        game.setPerClick(5);
        game.setPerSec(10);
        game.setScore(10000);
        game.buyAnimal("Cat", 1);
        game.buyUpgrade("OnePerClick");
        JSONObject json = game.toJson();
        assertEquals("Gom", json.getString("name"));
        assertEquals(9890, json.getDouble("score"));
        assertEquals(6, json.getDouble("perClick"));
        assertEquals(10, json.getDouble("perSec"));
    }

    @Test
    void getterAndSetterTest() {
        game.setScore(10000);
        HashMap<Upgradable, Integer> myHashMap = new HashMap<>();
        HashSet<Upgrade> myHashSet = new HashSet<>();
        assertEquals(myHashMap, game.getAnimals());
        assertEquals(myHashSet, game.getUpgrades());

        myHashMap.put(game.findAnimal("Cat"), 1);
        game.buyAnimal("Cat", 1);
        assertEquals(myHashMap, game.getAnimals());
        myHashMap = game.getAnimals();
        assertEquals(myHashMap, game.getAnimals());
        game.setAnimals(myHashMap);
        assertEquals(myHashMap, game.getAnimals());
        HashMap<Upgradable, Boolean> lmao = game.getAvailAnimals();
        game.setAvailAnimals(lmao);
        assertEquals(lmao, game.getAvailAnimals());

        HashMap<Upgrade, Boolean> lmao2 = game.getAvailUpgrades();
        game.setAvailUpgrades(lmao2);
        assertEquals(lmao2, game.getAvailUpgrades());
        game.setUpgrades(myHashSet);
        assertEquals(myHashSet, game.getUpgrades());


    }

    @Test
    void findUpradeAndCheckUnlockTest() {
        assertEquals(game.onePerClickU, game.findUpgrade("OnePerClick"));
        game.setScore(500);
        game.checkUnlocks();
        assertEquals(game.getAvailAnimals().get(game.findAnimal("Capybara")), true);
        assertEquals("", game.getName());
    }

    @Test
    void restOfTheTests() {
        game.buyUpgrade("OnePerClick");
        game.buyAnimal("Cat", 1);
        game.setScore(500);
        game.buyAnimal("Cat", 1);
        game.buyAnimal("Cat", 1000);
        assertEquals("", game.getName());
    }


}
