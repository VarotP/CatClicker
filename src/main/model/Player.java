package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int perClick;
    private int perSec;
    private String special;
    private List<Upgrade> upgrades;
    private List<Upgrade> availUpgrades;
    private List<Animal> animals;
    private List<Animal> availAnimals;

    //EFFECTS: creates upgradable
    public Player(String name, int perclick, String special) {
        this.name = name;
        this.perSec = 0;
        this.perClick = perclick;
        this.special = special;
        this.availUpgrades = new ArrayList<>();
        this.upgrades = new ArrayList<>();
        this.availAnimals = new ArrayList<>();
        this.animals = new ArrayList<>();
    }

    //REQUIRES: Upgrade is in availUpgrades, money >= upgrade.getCost
    //MODIFIES: this
    //EFFECTS: adds upgrade to upgrades, subtracts money, returns amount of remaining money
    public int buyUpgrades(int money, Upgrade upgrade) {
        upgrades.add(upgrade);
        this.perSec += upgrade.getPerSec();
        this.perClick += upgrade.getPerClick();
        return money - upgrade.getCost();
    }

    //REQUIRES: Animal is in availAnimals, money >= animal cost
    //EFFECTS: adds animal to animals, subtracts money, returns remaining money
    public int buyAnimal(int money, Animal animal) {
        animals.add(animal);
        this.perSec += animal.getPerSec();
        this.perClick += animal.getPerClick();

        return money - animal.getCost();
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

    public int getPerSec() {
        return perSec;
    }

    public void setPerSec(int perSec) {
        this.perSec = perSec;
    }
}
