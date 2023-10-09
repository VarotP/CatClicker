package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    private Animal monkey;
    private Upgrade testUpgrade;
    private Upgrade testUpgrade2;
    private List<Upgrade> upgrades = new ArrayList<>();

    @BeforeEach
    public void setup() {
        monkey = new Animal("Monkey", 100, 1, 0, null);
        testUpgrade = new Upgrade("one", 10, 1, 0, null);
        testUpgrade2 = new Upgrade("two", 20, 0, 2, null);
        upgrades.add(testUpgrade);
        upgrades.add(testUpgrade2);
        monkey.setAvailUpgrades(upgrades);
    }

    @Test
    public void buyUpgradeTest() {
        List<Upgrade> empty = new ArrayList<>();
        assertEquals(empty, monkey.getUpgrades());
        assertEquals(0, monkey.buyUpgrades(10, testUpgrade));
        assertEquals(upgrades, monkey.getUpgrades());
        assertEquals(2, monkey.getPerSec());
        assertEquals(0, monkey.getPerClick());
        assertNull(monkey.getSpecial());
    }

    @Test
    public void buyUpgradeMoneyRemainTest() {
        List<Upgrade> empty = new ArrayList<>();
        assertEquals(empty, monkey.getUpgrades());
        assertEquals(10, monkey.buyUpgrades(20, testUpgrade));
        assertEquals(upgrades, monkey.getUpgrades());
        assertEquals(2, monkey.getPerSec());
        assertEquals(0, monkey.getPerClick());
        assertNull(monkey.getSpecial());
    }

    @Test
    public void buyUpgradeMultiple() {
        List<Upgrade> empty = new ArrayList<>();
        assertEquals(empty, monkey.getUpgrades());
        assertEquals(20, monkey.buyUpgrades(30, testUpgrade));
        empty.add(testUpgrade);
        assertEquals(empty, monkey.getUpgrades());
        assertEquals(10, monkey.buyUpgrades(20, testUpgrade));
        empty.add(testUpgrade);
        assertEquals(empty, monkey.getUpgrades());

    }


    @Test
    void nameTest() {
        assertEquals("Monkey", monkey.getName());
        monkey.setName("Monke");
        assertEquals("Monke", monkey.getName());
    }

    @Test
    void costTest() {
        assertEquals(10, monkey.getCost());
        monkey.setCost(20);
        assertEquals(20, monkey.getCost());
    }

    @Test
    void perSecTest() {
        assertEquals(1, monkey.getPerSec());
        monkey.setPerSec(2);
        assertEquals(2, monkey.getPerSec());
    }

    @Test
    void perClickTest() {
        assertEquals(0, monkey.getPerClick());
        monkey.setPerClick(1);
        assertEquals(1, monkey.getPerClick());
    }

    @Test
    void specialTest() {
        assertNull(monkey.getSpecial());
        monkey.setSpecial("YA!");
        assertEquals("YA!", monkey.getSpecial());
    }

    @Test
    void setUpgradeTest() {
        List<Upgrade> empty = new ArrayList<>();
        assertEquals(empty, monkey.getUpgrades());
        upgrades.add(testUpgrade);
        monkey.setUpgrades(upgrades);
        assertEquals(upgrades, monkey.getUpgrades());
    }

    @Test
    void setAvailUpgradesTest() {
        List<Upgrade> empty = new ArrayList<>();
        empty.add(testUpgrade);
        empty.add(testUpgrade2);
        assertEquals(empty, monkey.getAvailUpgrades());
    }
}