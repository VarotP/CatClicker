package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpgradeTest {
    private Upgrade testUpgrade;

    @BeforeEach
    public void setup() {
        testUpgrade = new Upgrade("one", 10, 1, 0, 0, 1.4, null);
    }

    @Test
    void nameTest() {
        assertEquals("one", testUpgrade.getName());
        testUpgrade.setName("two");
        assertEquals("two", testUpgrade.getName());
        assertEquals(0, testUpgrade.getUnlockedAt());
        testUpgrade.setUnlockedAt(10);
        assertEquals(10, testUpgrade.getUnlockedAt());
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

    @Test
    void equalsTest() {
        Upgrade testUpgrade2 = new Upgrade("one", 0, 0, 0, 0, 1.4, null);
        Upgrade testUpgrade3 = new Upgrade("two", 0, 0, 0, 0, 1.4, null);
        assertTrue(testUpgrade.equals(testUpgrade));
        assertTrue(testUpgrade.equals(testUpgrade2));
        assertFalse(testUpgrade.equals(testUpgrade3));
        assertEquals(110182, testUpgrade.hashCode());
    }

    @Test
    void toJsonTest() {
        JSONObject json = new JSONObject();
        assertEquals("one", testUpgrade.toJson().getString("name"));
        assertEquals(10, testUpgrade.toJson().getInt("cost"));
        assertEquals(1, testUpgrade.toJson().getInt("perSec"));
        assertEquals(0, testUpgrade.toJson().getInt("perClick"));
        assertEquals(0, testUpgrade.toJson().getInt("unlockedAt"));
        assertEquals(1.4, testUpgrade.toJson().getDouble("scalingFactor"));
    }

    @Test
    void toJsonTestAvail() {
        JSONObject json = new JSONObject();
        assertEquals("one", testUpgrade.toJson(true).getString("name"));
        assertEquals(10, testUpgrade.toJson(true).getInt("cost"));
        assertEquals(1, testUpgrade.toJson(true).getInt("perSec"));
        assertEquals(0, testUpgrade.toJson(true).getInt("perClick"));
        assertEquals(0, testUpgrade.toJson(true).getInt("unlockedAt"));
        assertEquals(1.4, testUpgrade.toJson(true).getDouble("scalingFactor"));
        assertTrue(testUpgrade.toJson(true).getBoolean("unlocked"));
    }
}