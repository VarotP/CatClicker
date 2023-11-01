package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UpgradableTest {
    private Upgradable testUpgradable;
    private Upgrade testUpgrade;

    @BeforeEach
    public void Setup() {
        testUpgradable = new Upgradable("lmao", 100, 1, 1, 1.5, null);
        testUpgrade = new Upgrade("one", 10, 1, 0, 1.4, null);
    }

    @Test
    void findUpgradeFailTest() {
        List<Upgrade> ulist = new ArrayList<>();
        assertNull(testUpgradable.findUpgrade(testUpgrade, ulist));
    }

    @Test
    void findUpgradeSuccessTest() {
        testUpgradable.addUpgrade(testUpgrade);
        assertEquals(testUpgrade, testUpgradable.findUpgrade(testUpgrade, testUpgradable.getUpgrades()));
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
    void setScaleFactorTest() {
        testUpgradable.setScalingFactor(1.5);
        assertEquals(1.5, testUpgradable.getScalingFactor());
    }
}
