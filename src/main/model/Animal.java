package model;

import java.util.List;

public class Animal {
    private String name;
    private int cost;
    private int perSec;
    private int perClick;
    private String special;
    private List<Upgrade> upgrades;
    private List<Upgrade> availUpgrades;

    //REQUIRES:
    //EFFECTS: creates upgradable
    public Animal(String name, int cost, int persec, int perclick, String special) {
        this.name = name;
        this.cost = cost;
        this.perSec = persec;
        this.perClick = perclick;
        this.special = special;
    }

    //REQUIRES: Upgrade is in availUpgrades, money >= upgrade.getCost
    //MODIFIES: this
    //EFFECTS: adds upgrade to upgrades, subtracts money, returns amount of remaining money
    public int buyUpgrades(int money, Upgrade upgrade) {
        upgrades.add(upgrade);
        return money - upgrade.getCost();
    }

    //get and set methods

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
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


}
