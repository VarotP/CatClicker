package model;

import org.json.JSONObject;
import persistence.Writable;

import java.io.IOException;
import java.util.*;

// represents an upgradable object
public class Upgradable {
    private String name;

    private int cost;
    private int perSec;
    private int perClick;
    private int unlockedAt;
    private double scalingFactor;
    private String special;
    private String description;



    //REQUIRES:
    //EFFECTS: creates upgradable
    public Upgradable(String name, int cost,
                      int persec, int perclick, int unlockedAt, double scalingfactor,  String special) {
        this.name = name;
        this.cost = cost;
        this.perSec = persec;
        this.perClick = perclick;
        this.unlockedAt = unlockedAt;
        this.scalingFactor = scalingfactor;
        this.special = special;
    }

//    //REQUIRES: Upgrade is in availUpgrades, money >= upgrade.getCost
//    //MODIFIES: this
//    //EFFECTS: adds upgrade to upgrades, subtracts money, returns amount of remaining money
//    public double buyUpgrades(double money, Upgrade upgrade, int quantity) {
//        upgrades.add(upgrade);
//        double returnChange = money - upgrade.getCost();
//        this.perSec += upgrade.getPerSec();
//        this.perClick += upgrade.getPerClick();
//        return returnChange;
//    }

//    public Upgrade findUpgrade(Upgrade toFind, List<Upgrade> upgradeList) {
//        for (Upgrade currentUpgrade : upgradeList) {
//            if (currentUpgrade.getName().equals(toFind.getName())) {
//                return currentUpgrade;
//            }
//        }
//        return null;
//    }

    //get and set methods

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getPerSec() {
        return perSec;
    }

    public void setPerSec(int perSec) {
        this.perSec = perSec;
    }

    public int getPerClick() {
        return perClick;
    }

    public void setPerClick(int perClick) {
        this.perClick = perClick;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public double getScalingFactor() {
        return scalingFactor;
    }

    public void setScalingFactor(double scalingFactor) {
        this.scalingFactor = scalingFactor;
    }

    //EFFECTS: returns animal object as JSON
    public JSONObject toJson(Integer quantity) {
        JSONObject json = new JSONObject();
        json.put("name", getName());
        json.put("cost", getCost());
        json.put("perSec", getPerSec());
        json.put("perClick", getPerClick());
        json.put("scalingFactor", getScalingFactor());
        json.put("unlockedAt", getUnlockedAt());
        json.put("quantity", quantity);
        return json;
    }

    //EFFECTS: returns animal object as JSON with unlocked state
    public JSONObject toJson(Boolean unlocked) {
        JSONObject json = new JSONObject();
        json.put("name", getName());
        json.put("cost", getCost());
        json.put("perSec", getPerSec());
        json.put("perClick", getPerClick());
        json.put("scalingFactor", getScalingFactor());
        json.put("unlockedAt", getUnlockedAt());
        json.put("unlocked", unlocked);
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Upgradable that = (Upgradable) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public int getUnlockedAt() {
        return unlockedAt;
    }

    public void setUnlockedAt(int unlockedAt) {
        this.unlockedAt = unlockedAt;
    }

    //    public boolean checkContainsUpgrade(Upgrade u) {
//        for (Upgrade currentUpgrade : upgrades) {
//            if (currentUpgrade.getName().equals(u.getName())) {
//                return true;
//            }
//        }
//        return false;
//    }
}