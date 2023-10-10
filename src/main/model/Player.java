package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int money;
    private String name;
    private int perClick;
    private String special;
    private List<Upgrade> upgrades;
    private List<Upgrade> availUpgrades;
    private List<Animal> animals;
    private List<Animal> availAnimals;

    //EFFECTS: creates upgradable
    public Player(String name, int perclick, String special) {
        this.name = name;
        this.perClick = perclick;
        this.special = special;
        this.money = 0;
        this.availUpgrades = new ArrayList<>();
        this.upgrades = new ArrayList<>();
        this.availAnimals = new ArrayList<>();
        this.animals = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: earns money equal to perclick
    public void earnMoney() {
        money += perClick;
    }

    //REQUIRES: Upgrade is in availUpgrades, money >= upgrade cost
    //MODIFIES: this
    //EFFECTS: adds upgrade to upgrades, subtracts money, returns true if animal is bought, false if isnt,
    public boolean buyUpgrades(Upgrade upgrade) {
        if (money >= upgrade.getCost()) {
            upgrades.add(upgrade);
            this.perClick += upgrade.getPerClick();
            money -= upgrade.getCost();
            return true;
        } else {
            return false;
        }
    }

    //REQUIRES: Animal is in availUpgrades, money >= animal cost
    //MODIFIES: this
    //EFFECTS: adds Animal to animals, subtracts money, returns true if animal is bought, false if isnt,
    public boolean buyAnimal(Animal animal) {
        if (money >= animal.getCost()) {
            animals.add(animal);
            money -= animal.getCost();
            return true;
        } else {
            return false;
        }
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

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public List<Animal> getAvailAnimals() {
        return availAnimals;
    }

    public void setAvailAnimals(List<Animal> availAnimals) {
        this.availAnimals = availAnimals;
    }
}
