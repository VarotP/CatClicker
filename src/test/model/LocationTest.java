package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationTest {
    private Location cafe;
    private Upgrade testUpgrade;
    private Upgrade testUpgrade2;
    private Animal monkey;
    private Animal capybara;
    private Animal tiger;
    private final List<Upgrade> upgrades = new ArrayList<>();
    private final List<Animal> animals = new ArrayList<>();

    @BeforeEach
    public void setup() {
        cafe = new Location("Animal Cafe", 10, 10, null);
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
        cafe.setAvailUpgrades(upgrades);
        cafe.setAvailAnimals(animals);
    }

    @Test
    public void constructorTest() {
        assertEquals("Animal Cafe", cafe.getName());
        assertEquals(10, cafe.getPerSec());
        assertEquals(10, cafe.getPerClick());
        assertNull(cafe.getSpecial());
    }

    @Test
    public void buyUpgradeTest() {
        List<Upgrade> empty = new ArrayList<>();
        assertEquals(empty, cafe.getUpgrades());
        assertEquals(0, cafe.buyUpgrades(10, testUpgrade));
        empty.add(testUpgrade);
        assertEquals(empty, cafe.getUpgrades());
        assertEquals(10, cafe.getPerSec());
        assertEquals(11, cafe.getPerClick());
        assertNull(cafe.getSpecial());
    }

    @Test
    public void buyUpgradeMoneyRemainTest() {
        List<Upgrade> empty = new ArrayList<>();
        assertEquals(empty, cafe.getUpgrades());
        assertEquals(10, cafe.buyUpgrades(20, testUpgrade));
        empty.add(testUpgrade);
        assertEquals(empty, cafe.getUpgrades());
        assertEquals(10, cafe.getPerSec());
        assertEquals(11, cafe.getPerClick());
        assertNull(cafe.getSpecial());
    }

    @Test
    public void buyUpgradeMultiple() {
        List<Upgrade> empty = new ArrayList<>();
        assertEquals(empty, cafe.getUpgrades());
        assertEquals(20, cafe.buyUpgrades(30, testUpgrade));
        empty.add(testUpgrade);
        assertEquals(empty, cafe.getUpgrades());
        assertEquals(0, cafe.buyUpgrades(20, testUpgrade2));
        empty.add(testUpgrade2);
        assertEquals(empty, cafe.getUpgrades());

    }

    @Test
    public void buyAnimalTest() {
        List<Animal> empty = new ArrayList<>();
        assertEquals(empty, cafe.getAnimals());
        assertEquals(0, cafe.buyAnimal(100, monkey));
        empty.add(monkey);
        assertEquals(empty, cafe.getAnimals());
        assertEquals(11, cafe.getPerSec());
        assertEquals(10, cafe.getPerClick());
        assertNull(cafe.getSpecial());
    }

    @Test
    public void buyAnimalMoneyRemainTest() {
        List<Animal> empty = new ArrayList<>();
        assertEquals(empty, cafe.getAnimals());
        assertEquals(250, cafe.buyAnimal(350, monkey));
        empty.add(monkey);
        assertEquals(empty, cafe.getAnimals());
        assertEquals(11, cafe.getPerSec());
        assertEquals(10, cafe.getPerClick());
        assertNull(cafe.getSpecial());
    }

    @Test
    public void buyAnimalMultiple() {
        List<Animal> empty = new ArrayList<>();
        assertEquals(empty, cafe.getAnimals());
        assertEquals(250, cafe.buyAnimal(350, monkey));
        empty.add(monkey);
        assertEquals(empty, cafe.getAnimals());
        assertEquals(50, cafe.buyAnimal(250, capybara));
        empty.add(capybara);
        assertEquals(empty, cafe.getAnimals());

    }

    @Test
    void addUpgradeTest() {
        List<Upgrade> empty = new ArrayList<>();
        assertEquals(empty, cafe.getUpgrades());
        cafe.addUpgrade(testUpgrade);
        cafe.addUpgrade(testUpgrade2);
        empty.add(testUpgrade);
        empty.add(testUpgrade2);
        assertEquals(empty, cafe.getUpgrades());
    }

    @Test
    void addAnimalTest() {
        List<Animal> empty = new ArrayList<>();
        assertEquals(empty, cafe.getAnimals());
        cafe.addAnimal(monkey);
        cafe.addAnimal(capybara);
        empty.add(monkey);
        empty.add(capybara);
        assertEquals(empty, cafe.getAnimals());
    }

    @Test
    void nameTest() {
        assertEquals("Animal Cafe", cafe.getName());
        cafe.setName("Animal Shelter");
        assertEquals("Animal Shelter", cafe.getName());
    }

    @Test
    void perSecTest() {
        assertEquals(10, cafe.getPerSec());
        cafe.setPerSec(20);
        assertEquals(20, cafe.getPerSec());
    }

    @Test
    void perClickTest() {
        assertEquals(10, cafe.getPerClick());
        cafe.setPerClick(20);
        assertEquals(20, cafe.getPerClick());
    }

    @Test
    void specialTest() {
        assertNull(cafe.getSpecial());
        cafe.setSpecial("YA!");
        assertEquals("YA!", cafe.getSpecial());
    }

    @Test
    void setAvailUpgradesTest() {
        List<Upgrade> empty = new ArrayList<>();
        empty.add(testUpgrade);
        empty.add(testUpgrade2);
        assertEquals(empty, cafe.getAvailUpgrades());

        List<Upgrade> empty2 = new ArrayList<>();
        empty2.add(testUpgrade);

        List<Upgrade> empty3 = new ArrayList<>();
        empty3.add(testUpgrade);
        cafe.setAvailUpgrades(empty3);
        assertEquals(empty2, cafe.getAvailUpgrades());
    }

    @Test
    void setAvailAnimalsTest() {
        List<Animal> empty = new ArrayList<>();
        empty.add(tiger);
        assertEquals(animals, cafe.getAvailAnimals());
        cafe.setAvailAnimals(empty);
        List<Animal> empty2 = new ArrayList<>();
        empty2.add(tiger);
        assertEquals(empty2, cafe.getAvailAnimals());
    }


}
