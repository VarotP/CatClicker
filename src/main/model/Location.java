package model;

import java.util.List;

public class Location {
    private String name;
    private int perSec;
    private int perClick;
    private String special;
    private List<Upgrade> upgrades;
    private List<Animal> animals;
    private List<Upgrade> availUpgrades;
    private List<Animal> availAnimals;

    //REQUIRES:
    //EFFECTS: creates upgradable
    public Location(String name, int persec, int perclick, String special) {
        this.name = name;
        this.perSec = persec;
        this.perClick = perclick;
        this.special = special;
    }

    //REQUIRES: Upgrade is in availUpgrades, money >= upgrade.cost
    //MODIFIES: this
    //EFFECTS: adds upgrade to upgrades, subtracts money, returns remaining monney
    public int buyUpgrades(int money, Upgrade upgrade) {
        upgrades.add(upgrade);
        return money - upgrade.getCost();
    }

    //REQUIRES: Animal is in availAnimals, money >= animal cost
    //EFFECTS: adds animal to animals, subtracts money, returns remaining monney
    public int buyAnimal(int money, Animal animal) {
        animals.add(animal);
        return money - animal.getCost();
    }

    //get and set methods

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public List<Upgrade> getAvailUpgrades() {
        return availUpgrades;
    }

    public void setAvailUpgrades(List<Upgrade> availUpgrades) {
        this.availUpgrades = availUpgrades;
    }

    public List<Animal> getAvailAnimals() {
        return availAnimals;
    }

    public void setAvailAnimals(List<Animal> availAnimals) {
        this.availAnimals = availAnimals;
    }
}

