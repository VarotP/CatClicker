//package model;
//
//import org.json.JSONObject;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class GameTest {
//    Animal cat;
//    Animal dog;
//    Location cafe;
//    Upgrade onePerClickU;
//    Upgrade animalBuff;
//
//    Game newGame;
//    List<Location> loclist;
//    @BeforeEach
//    public void setup() {
//        newGame = new Game("Val");
//        loclist = new ArrayList<>();
//        cat = new Animal("Cat", 50, 1, 0,1.2, null);
//        dog = new Animal("Dog", 75, 2, 0,1.2,  null);
//        cafe = new Location("Animal Cafe", 10, 50, null);
//        onePerClickU = new Upgrade("OnePerClick", 5, 0, 1,1.2, null);
//        animalBuff = new Upgrade("AnimalBuff", 50, 5, 0, 1.2, null);
//    }
//
//    @Test
//    public void initGame() {
//        assertEquals(0, newGame.getScore());
//        assertEquals(loclist, newGame.getLocations());
//    }
//
//    @Test
//    public void clickTest() {
//        assertEquals(0, newGame.getScore());
//        newGame.click();
//        assertEquals(1, newGame.getScore());
//        newGame.click();
//        assertEquals(2, newGame.getScore());
//
//        newGame.getPlayer1().setPerClick(2);
//        newGame.click();
//        assertEquals(4, newGame.getScore());
//    }
//
//    @Test
//    public void tickTest() {
//        assertEquals(0, newGame.getScore());
//        for (int i = 0; i < 10; i++) {
//            newGame.tick();
//        }
//        assertEquals(0, newGame.getScore());
//
//        newGame.getPlayer1().addAnimal(cat);
//        newGame.getPlayer1().getAnimals().get(0).setCount(1);
//
//        assertEquals(0, newGame.getScore());
//        for (int i = 0; i < 11; i++) {
//            newGame.tick();
//        }
//        assertEquals(1, (int) newGame.getScore());
//
//        newGame.getPlayer1().addAnimal(dog);
//        newGame.getPlayer1().getAnimals().get(1).setCount(1);
//
//        assertEquals(1, (int) newGame.getScore());
//        for (int i = 0; i < 11; i++) {
//            newGame.tick();
//        }
//        assertEquals(4, (int) newGame.getScore());
//
//
//        List<Location> dummy = new ArrayList<>();
//        dummy.add(cafe);
//        newGame.setLocations(dummy);
//
//        assertEquals(4, (int) newGame.getScore());
//        for (int i = 0; i < 10; i++) {
//            newGame.tick();
//        }
//        assertEquals(17, (int) newGame.getScore());
//
//    }
//
//    @Test
//    public void tickNoAnimalYesLocTest() {
//        assertEquals(0, newGame.getScore());
//        for (int i = 0; i < 11; i++) {
//            newGame.tick();
//        }
//        assertEquals(0, (int) newGame.getScore());
//
//        List<Location> dummy = new ArrayList<>();
//        dummy.add(cafe);
//        newGame.setLocations(dummy);
//
//        assertEquals(0, newGame.getScore());
//        for (int i = 0; i < 10; i++) {
//            newGame.tick();
//        };
//        assertEquals(10, (int) newGame.getScore());
//
//        assertEquals(10, newGame.getScore());
//        for (int i = 0; i < 10; i++) {
//            newGame.tick();
//        }
//        assertEquals(20, (int) newGame.getScore());
//    }
//
//    @Test
//    public void tickYesAnimalNoLocTest() {
//        assertEquals(0, newGame.getScore());
//        for (int i = 0; i < 11; i++) {
//            newGame.tick();
//        }
//        assertEquals(0, newGame.getScore());
//
//        newGame.getPlayer1().addAnimal(cat);
//        newGame.getPlayer1().getAnimals().get(0).setCount(1);
//
//        assertEquals(0, (int) newGame.getScore());
//        for (int i = 0; i < 11; i++) {
//            newGame.tick();
//        }
//        assertEquals(1, (int) newGame.getScore());
//    }
//
//    @Test
//    public void tickNoAnimalYesLocTestYesAnimalInLoc() {
//        assertEquals(0, newGame.getScore());
//        for (int i = 0; i < 11; i++) {
//            newGame.tick();
//        }
//        assertEquals(0, newGame.getScore());
//
//        cafe.addAnimal(cat);
//        cafe.getAnimals().get(0).setCount(1);
//        List<Location> dummy = new ArrayList<>();
//        dummy.add(cafe);
//        newGame.setLocations(dummy);
//
//        assertEquals(0, newGame.getScore());
//        for (int i = 0; i < 11; i++) {
//            newGame.tick();
//        }
//        assertEquals(12, (int) newGame.getScore());
//
//        assertEquals(12, (int) newGame.getScore());
//        for (int i = 0; i < 11; i++) {
//            newGame.tick();
//        }
//        assertEquals(24, (int) newGame.getScore());
//    }
//
//
//    @Test
//    public void displayAvailTest() {
//        List<Upgrade> uaList = new ArrayList<>();
//        uaList.add(animalBuff);
//        cat.setAvailUpgrades(uaList);
//
//        String output = "Player Upgrades: AnimalBuff: 50 Available Animals: Cat Upgrades: AnimalBuff ";
//
//        newGame.getPlayer1().setAvailUpgrades(uaList);
//        newGame.setScore(1000);
//        newGame.getPlayer1().buyAnimal(newGame.getScore(), cat);
//        assertEquals(output, newGame.displayAvailUpgrades());
//    }
//
//    @Test
//    public void displayStatsTest() {
//
//        cat.addUpgrade(onePerClickU);
//
//        String output = "Owned Upgrades: OnePerClick x0 Player Animals: Cat x0 Cat Upgrades: OnePerClick x0 Owned Locations: ";
//
//        newGame.getPlayer1().addAnimal(cat);
//        newGame.getPlayer1().addUpgrade(onePerClickU);
//        assertEquals(output, newGame.displayStats());
//
//        dog.addUpgrade(animalBuff);
//        newGame.getPlayer1().addAnimal(dog);
//        output = "Owned Upgrades: OnePerClick x0 Player Animals: Cat x0 Cat Upgrades: OnePerClick x0 Dog x0 Dog Upgrades: AnimalBuff x0 Owned Locations: ";
//        assertEquals(output, newGame.displayStats());
//
//        newGame.addLocation(cafe);
//        output = "Owned Upgrades: OnePerClick x0 Player Animals: Cat x0 Cat Upgrades: OnePerClick x0 Dog x0 Dog Upgrades: AnimalBuff x0 Owned Locations: Animal Cafe Animal Cafe Upgrades: Animal Cafe Animals: ";
//        assertEquals(output, newGame.displayStats());
//    }
//
//    @Test
//    void displayStatsLocationUpgradeTest() {
//        String output = "Owned Upgrades: Player Animals: Owned Locations: Animal Cafe Animal Cafe Upgrades: OnePerClick Animal Cafe Animals: ";
//
//        cafe.addUpgrade(onePerClickU);
//        newGame.addLocation(cafe);
//        assertEquals(output, newGame.displayStats());
//    }
//
//    @Test
//    void displayStatsNoAnimalsNoUpgradesTest() {
//        String output = "Owned Upgrades: Player Animals: Owned Locations: ";
//        assertEquals(output, newGame.displayStats());
//    }
//
//    @Test
//    public void displayAvailNoAnimalsNoLocationsTest() {
//        String output = "Player Upgrades: Available Animals: ";
//        assertEquals(output, newGame.displayAvailUpgrades());
//    }
//
//    @Test
//    public void displayAvailYesAnimalsNoLocTest() {
//        String output = "Player Upgrades: Available Animals: Dog: 75 Cat Upgrades: ";
//        List<Animal> test = new ArrayList<>();
//        test.add(dog);
//        newGame.getPlayer1().addAnimal(cat);
//        newGame.getPlayer1().setAvailAnimals(test);
//        assertEquals(output, newGame.displayAvailUpgrades());
//    }
//
//    @Test
//    public void displayAvailNoAnimalsYesLocTest() {
//        String output = "Player Upgrades: Available Animals: Animal Cafe Upgrades: Available Animals in Animal Cafe:  ";
//        newGame.addLocation(cafe);
//        assertEquals(output, newGame.displayAvailUpgrades());
//    }
//
//    @Test
//    public void displayAvailLocwithUpgradeTest() {
//        String output = "Player Upgrades: Available Animals: Animal Cafe Upgrades: OnePerClick: 5 Available Animals in Animal Cafe:  ";
//        List<Upgrade> uList = new ArrayList<>();
//        uList.add(onePerClickU);
//        cafe.setAvailUpgrades(uList);
//        newGame.addLocation(cafe);
//        assertEquals(output, newGame.displayAvailUpgrades());
//    }
//
//    @Test
//    void checkUnlockTest() {
//        assertFalse(newGame.isUnlockedZoo());
//        assertFalse(newGame.isUnlockedCafe());
//        newGame.setScore(1000);
//        newGame.tick();
//        assertTrue(newGame.isUnlockedCafe());
//        assertFalse(newGame.isUnlockedZoo());
//        newGame.setScore(10000);
//        newGame.tick();
//        assertTrue(newGame.isUnlockedCafe());
//        assertTrue(newGame.isUnlockedZoo());
//    }
//
//    @Test
//    void getPerSecTest() {
//        newGame.getPlayer1().buyAnimal(10000, cat);
//        newGame.getPlayer1().buyAnimal(10000, dog);
//        newGame.setScore(10000);
//        newGame.tick();
//        newGame.getLocations().get(0).buyAnimal(10000, cat);
//        assertEquals(117, newGame.getPerSec());
//    }
//
//    @Test
//    void toJsonTest() {
//        JSONObject json = new JSONObject();
//        json.put("name", "Val");
//        json.put("player", newGame.getPlayer1().toJson());
//        json.put("score", 0);
//        json.put("perSec", 0);
//        json.put("unlocked-cafe", false);
//        json.put("unlocked-zoo", false);
//        assertEquals(json.getString("name"), newGame.toJson().getString("name"));
//        assertEquals(json.getInt("score"), newGame.toJson().getInt("score"));
//        assertEquals(json.getInt("perSec"), newGame.toJson().getInt("perSec"));
//        assertEquals(json.getBoolean("unlocked-cafe"), newGame.toJson().getBoolean("unlocked-cafe"));
//        assertEquals(json.getBoolean("unlocked-zoo"), newGame.toJson().getBoolean("unlocked-zoo"));
//    }
//
//    @Test
//    void getAndSetsTest() {
//        assertEquals(0, newGame.getScoreInt());
//        assertEquals("Val", newGame.getName());
//        newGame.setPerSec(10);
//        assertEquals(0, newGame.getPerSec());
//        newGame.setUnlockedCafe(true);
//        newGame.setUnlockedZoo(true);
//        assertTrue(newGame.isUnlockedCafe());
//        assertTrue(newGame.isUnlockedZoo());
//
//        Player player1 = new Player(100, "LOL");
//        newGame.setPlayer1(player1);
//        assertEquals(player1, newGame.getPlayer1());
//
//    }
//
//}
