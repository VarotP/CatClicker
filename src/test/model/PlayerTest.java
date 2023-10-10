package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerTest {
    private Player player1;
    private Upgrade testUpgrade;
    private Upgrade testUpgrade2;
    private Animal monkey;
    private Animal capybara;
    private Animal tiger;
    private final List<Upgrade> upgrades = new ArrayList<>();
    private final List<Animal> animals = new ArrayList<>();

    @BeforeEach
    public void setup() {
        player1 = new Player("Gom", 1, null);
        testUpgrade = new Upgrade("one", 10, 0, 1, null);
        testUpgrade2 = new Upgrade("two", 20, 0, 2, null);
        upgrades.add(testUpgrade);
        upgrades.add(testUpgrade2);
        monkey = new Animal("Monkey", 100, 1, 0, null);
        capybara = new Animal("Capybara", 200, 2, 1, null);
        tiger = new Animal("Tiger", 1200, 2, 1, null);
        animals.add(monkey);
        animals.add(capybara);
        animals.add(tiger);
        player1.setAvailUpgrades(upgrades);
        player1.setAvailAnimals(animals);
    }

    @Test
    public void buyUpgradeTest() {
        List<Upgrade> empty = new ArrayList<>();
        assertEquals(empty, player1.getUpgrades());
        assertEquals(0, player1.buyUpgrades(10, testUpgrade));
        empty.add(testUpgrade);
        assertEquals(empty, player1.getUpgrades());
        assertEquals(0, player1.getPerSec());
        assertEquals(2, player1.getPerClick());
        assertNull(player1.getSpecial());
    }

    @Test
    public void buyUpgradeMoneyRemainTest() {
        List<Upgrade> empty = new ArrayList<>();
        assertEquals(empty, player1.getUpgrades());
        assertEquals(10, player1.buyUpgrades(20, testUpgrade));
        empty.add(testUpgrade);
        assertEquals(empty, player1.getUpgrades());
        assertEquals(0, player1.getPerSec());
        assertEquals(2, player1.getPerClick());
        assertNull(player1.getSpecial());
    }

    @Test
    public void buyUpgradeMultiple() {
        List<Upgrade> empty = new ArrayList<>();
        assertEquals(empty, player1.getUpgrades());
        assertEquals(20, player1.buyUpgrades(30, testUpgrade));
        empty.add(testUpgrade);
        assertEquals(empty, player1.getUpgrades());
        assertEquals(10, player1.buyUpgrades(20, testUpgrade));
        empty.add(testUpgrade);
        assertEquals(empty, player1.getUpgrades());

    }

    @Test
    public void buyAnimalTest() {
        List<Animal> empty = new ArrayList<>();
        assertEquals(empty, player1.getAnimals());
        assertEquals(0, player1.buyAnimal(100, monkey));
        empty.add(monkey);
        assertEquals(empty, player1.getAnimals());
        assertEquals(2, player1.getPerSec());
        assertEquals(1, player1.getPerClick());
        assertNull(player1.getSpecial());
    }

    @Test
    public void buyAnimalMoneyRemainTest() {
        List<Animal> empty = new ArrayList<>();
        assertEquals(empty, player1.getAnimals());
        assertEquals(250, player1.buyAnimal(350, monkey));
        empty.add(monkey);
        assertEquals(empty, player1.getAnimals());
        assertEquals(1, player1.getPerSec());
        assertEquals(1, player1.getPerClick());
        assertNull(player1.getSpecial());
    }

    @Test
    public void buyAnimalMultiple() {
        List<Animal> empty = new ArrayList<>();
        assertEquals(empty, player1.getAnimals());
        assertEquals(250, player1.buyAnimal(350, monkey));
        empty.add(monkey);
        assertEquals(empty, player1.getAnimals());
        assertEquals(50, player1.buyAnimal(250, capybara));
        empty.add(capybara);
        assertEquals(empty, player1.getAnimals());

    }

    @Test
    void nameTest() {
        assertEquals("Gom", player1.getName());
        player1.setName("Mika");
        assertEquals("Mika", player1.getName());
    }

    @Test
    void perClickTest() {
        assertEquals(1, player1.getPerClick());
        player1.setPerClick(2);
        assertEquals(2, player1.getPerClick());
    }

    @Test
    void specialTest() {
        assertNull(player1.getSpecial());
        player1.setSpecial("YA!");
        assertEquals("YA!", player1.getSpecial());
    }
    @Test
    void setAvailUpgradesTest() {
        List<Upgrade> empty = new ArrayList<>();
        empty.add(testUpgrade);
        empty.add(testUpgrade2);
        assertEquals(empty, player1.getAvailUpgrades());

        List<Upgrade> empty2 = new ArrayList<>();
        empty2.add(testUpgrade);

        List<Upgrade> empty3 = new ArrayList<>();
        empty3.add(testUpgrade);
        player1.setAvailUpgrades(empty3);
        assertEquals(empty2, player1.getAvailUpgrades());
    }

    @Test
    void setAvailAnimalsTest() {
        List<Animal> empty = new ArrayList<>();
        empty.add(tiger);
        assertEquals(animals, player1.getAvailAnimals());
        player1.setAvailAnimals(empty);
        List<Animal> empty2 = new ArrayList<>();
        empty2.add(tiger);
        assertEquals(empty2, player1.getAvailAnimals());
    }

}