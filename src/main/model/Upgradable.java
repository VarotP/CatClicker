package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Upgradable {
    private String name;
    private int count;
    private int cost;
    private int perSec;
    private int perClick;
    private double scalingFactor;
    private String special;
    private List<Upgrade> upgrades;
    private List<Upgrade> availUpgrades;
    private String description;

    //REQUIRES:
    //EFFECTS: creates upgradable
    public Upgradable(String name, int cost, int persec, int perclick, double scalingfactor,  String special) {
        this.name = name;
        this.count = 0;
        this.cost = cost;
        this.perSec = persec;
        this.perClick = perclick;
        this.scalingFactor = scalingfactor;
        this.special = special;
        this.availUpgrades = new ArrayList<>();
        this.upgrades = new ArrayList<>();
    }

    //REQUIRES: Upgrade is in availUpgrades, money >= upgrade.getCost
    //MODIFIES: this
    //EFFECTS: adds upgrade to upgrades, subtracts money, returns amount of remaining money
    public double buyUpgrades(double money, Upgrade upgrade) {
        if (!checkContainsUpgrade(upgrade)) {
            upgrades.add(upgrade);
        } else {
            upgrade = findUpgrade(upgrade, upgrades);
        }
        double returnChange = money - upgrade.getCost();
        upgrade.setCount(upgrade.getCount() + 1);
        this.perSec += upgrade.getPerSec();
        this.perClick += upgrade.getPerClick();
        return returnChange;
    }

    //EFFECTS: searches for upgrade with same String name and returns it, if not found return null
    public Upgrade findUpgrade(Upgrade toFind, List<Upgrade> upgradeList) {
        for (Upgrade currentUpgrade : upgradeList) {
            if (currentUpgrade.getName().equals(toFind.getName())) {
                return currentUpgrade;
            }
        }
        return null;
    }

    //MODIFIES: this
    //EFFECTS: adds upgrade to upgrade list
    public void addUpgrade(Upgrade upgrade) {
        this.upgrades.add(upgrade);
    }

    //EFFECTS: returns true if there is a upgrade with same name in upgrades, else return false
    public boolean checkContainsUpgrade(Upgrade u) {
        for (Upgrade currentUpgrade : upgrades) {
            if (currentUpgrade.getName().equals(u.getName())) {
                return true;
            }
        }
        return false;
    }

    //get and set methods

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return (int) (cost * Math.pow(scalingFactor, count));
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

    public List<Upgrade> getUpgrades() {
        return upgrades;
    }

    public void setUpgrades(List<Upgrade> upgrades) {
        this.upgrades = upgrades;
    }

    //EFFECTS: returns list of available upgrades
    public List<Upgrade> getAvailUpgrades() {
        return this.availUpgrades;
    }

    public void setAvailUpgrades(List<Upgrade> thislist) {
        this.availUpgrades = thislist;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getScalingFactor() {
        return scalingFactor;
    }

    public void setScalingFactor(double scalingFactor) {
        this.scalingFactor = scalingFactor;
    }
}