package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UpgradeTest {
    private Upgrade testUpgrade;

    @BeforeEach
    public void setup() {
        testUpgrade = new Upgrade("one", 10, 1, 0, null);
    }

    @Test
    void nameTest() {
        assertEquals("one", testUpgrade.getName());
        testUpgrade.setName("two");
        assertEquals("two", testUpgrade.getName());
    }

    @Test
    void costTest() {
        assertEquals(10, testUpgrade.getCost());
        testUpgrade.setCost(20);
        assertEquals(20, testUpgrade.getCost());
    }

    @Test
    void perSecTest() {
        assertEquals(1, testUpgrade.getPerSec());
        testUpgrade.setPerSec(2);
        assertEquals(2, testUpgrade.getPerSec());
    }

    @Test
    void perClickTest() {
        assertEquals(0, testUpgrade.getPerClick());
        testUpgrade.setPerClick(1);
        assertEquals(1, testUpgrade.getPerClick());
    }

    @Test
    void specialTest() {
        assertNull(testUpgrade.getSpecial());
        testUpgrade.setSpecial("YA!");
        assertEquals("YA!", testUpgrade.getSpecial());
    }
}