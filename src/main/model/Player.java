package model;

import java.util.List;

public class Player {
    private int money;
    private String name;
    private int perClick;
    private String special;
    private List<Upgrade> upgrades;
    private List<Upgrade> availUpgrades;

    //REQUIRES:
    //EFFECTS: creates upgradable
    public void location(String name, int perclick, String special) {
        this.name = name;
        this.perClick = perclick;
        this.special = special;
    }

    //MODIFIES: this
    //EFFECTS: earns money equal to perclick
    public void earnMoney() {
        money += perClick;
    }

    //REQUIRES: Upgrade is in availUpgrades
    //MODIFIES: this
    //EFFECTS: adds upgrade to upgrades, subtracts money, returns true if animal is bought, false if isnt,
    public boolean buyUpgrades() {
        return false;
    }

    //get and set methods

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
