package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpgradableTest {
    private Upgradable testupgrade;

    @BeforeEach
    public void setup() {
        testupgrade = new Upgradable("one", 10, 1, 0, 0, 1.4, null);
    }

    @Test
    void nameTest() {
        assertEquals("one", testupgrade.getName());
        testupgrade.setName("two");
        assertEquals("two", testupgrade.getName());
        assertEquals(1.4, testupgrade.getScalingFactor());
        testupgrade.setScalingFactor(2);
        assertEquals(2, testupgrade.getScalingFactor());
        assertEquals(0, testupgrade.getUnlockedAt());
        testupgrade.setUnlockedAt(1);
        assertEquals(1, testupgrade.getUnlockedAt());
        assertEquals(115276, testupgrade.hashCode());
    }

    @Test
    void costTest() {
        assertEquals(10, testupgrade.getCost());
        testupgrade.setCost(20);
        assertEquals(20, testupgrade.getCost());
    }

    @Test
    void perSecTest() {
        assertEquals(1, testupgrade.getPerSec());
        testupgrade.setPerSec(2);
        assertEquals(2, testupgrade.getPerSec());
    }

    @Test
    void perClickTest() {
        assertEquals(0, testupgrade.getPerClick());
        testupgrade.setPerClick(1);
        assertEquals(1, testupgrade.getPerClick());
    }

    @Test
    void specialTest() {
        assertNull(testupgrade.getSpecial());
        testupgrade.setSpecial("YA!");
        assertEquals("YA!", testupgrade.getSpecial());
    }

    @Test
    void equalsTest() {
        Upgradable testupgrade2 = new Upgradable("one", 10, 1, 0, 0, 1, null);
        Upgradable testupgrade3 = new Upgradable("two", 10, 1, 0, 0, 1.4, null);
        assertTrue(testupgrade.equals(testupgrade));
        assertTrue(testupgrade.equals(testupgrade2));
        assertFalse(testupgrade.equals(null));
        assertFalse(testupgrade.equals(testupgrade3));
    }

    @Test
    void toJsonTest() {
        JSONObject json = new JSONObject();
        json.put("name", "one");
        json.put("cost", 10);
        json.put("perSec", 1);
        json.put("perClick", 0);
        json.put("scalingFactor", 1.4);
        json.put("unlockedAt", 0);
        assertEquals(json.getString("name"), testupgrade.toJson(10).getString("name"));
        assertEquals(json.getInt("cost"), testupgrade.toJson(10).getInt("cost"));
        assertEquals(json.getInt("perSec"), testupgrade.toJson(10).getInt("perSec"));
        assertEquals(json.getInt("perClick"), testupgrade.toJson(10).getInt("perClick"));
        assertEquals(json.getDouble("scalingFactor"), testupgrade.toJson(10).getDouble("scalingFactor"));
        assertEquals(json.getInt("unlockedAt"), testupgrade.toJson(10).getInt("unlockedAt"));
        assertEquals(10, testupgrade.toJson(10).getInt("quantity"));
    }

    @Test
    void toJsonTestAvail() {
        JSONObject json = new JSONObject();
        json.put("name", "one");
        json.put("cost", 10);
        json.put("perSec", 1);
        json.put("perClick", 0);
        json.put("scalingFactor", 1.4);
        json.put("unlockedAt", 0);
        assertEquals(json.getString("name"), testupgrade.toJson(true).getString("name"));
        assertEquals(json.getInt("cost"), testupgrade.toJson(true).getInt("cost"));
        assertEquals(json.getInt("perSec"), testupgrade.toJson(true).getInt("perSec"));
        assertEquals(json.getInt("perClick"), testupgrade.toJson(true).getInt("perClick"));
        assertEquals(json.getDouble("scalingFactor"), testupgrade.toJson(true).getDouble("scalingFactor"));
        assertEquals(json.getInt("unlockedAt"), testupgrade.toJson(true).getInt("unlockedAt"));
        assertTrue(testupgrade.toJson(true).getBoolean("unlocked"));
    }
}