package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

public class Game implements Writable {
    private String name;
    private double perClick;
    private double score;
    private double perSec;
    private final double ticks;

    // animal and count
    private HashMap<Upgradable, Integer> animals;
    // unlockable animal and unlocked?
    private HashMap<Upgradable, Boolean> availAnimals;
    private HashSet<Upgrade> upgrades;
    private HashMap<Upgrade, Boolean> availUpgrades;

    //Upgrades and Animals

    private final Upgrade onePerClickU = new Upgrade("OnePerClick", 10, 0, 1, 0,1.4,null);
    private final Upgrade fivePerClickU = new Upgrade("FivePerClick", 5000, 0, 5, 5000, 1.4, null);
    private final Upgradable cat = new Upgradable("Cat", 50, 1, 0, 0, 1.2, null);
    private final Upgradable dog = new Upgradable("Dog", 200, 2, 0, 0, 1.2, null);
    private final Upgradable capybara = new Upgradable("Capybara", 200, 2, 0,  500,1.2, null);

    //MODIFIES: this
    //EFFECTS: constructs game object and inits the available upgrade and animal list
    public Game(String name, int ticks) {
        this.name = name;
        this.perClick = 1;
        this.perSec = 0;
        this.score = 0;
        this.animals = new HashMap<>();
        this.availAnimals = new HashMap<>();
        this.upgrades = new HashSet<>();
        this.availUpgrades = new HashMap<>();
        this.ticks = ticks;
        initAvails();
    }

    //MODIFIES: this
    //EFFECTS inits avail animals and upgrades
    private void initAvails() {
        availAnimals.put(cat, true);
        availAnimals.put(dog, true);
        availAnimals.put(capybara, false);
        availUpgrades.put(onePerClickU, true);
        availUpgrades.put(fivePerClickU, false);
    }


    //MODIFIES: this
    //EFFECTS: adds up all the money per sec and ocassionally spawns specials,
    // also checks for unlocks and makes them available to player
    public void tick() {
        checkUnlocks();
        for (Upgradable animal : animals.keySet()) {
            // score += each animals's per second multiplied by their count
            perSec = (animal.getPerSec() * animals.get(animal) / ticks);
            score += (animal.getPerSec() * animals.get(animal) / ticks);
        }
    }

    //MODIFIES: This
    //EFFECT: earn money
    public void click() {
        score += perClick;
    }

    //EFFECTS: adds upgrade to upgrades if score >= item cost and if upgrade is unlocked
    public void buyUpgrade(String upgradeName) {
        Upgrade buyUpgrade = null;
        Upgrade temp = new Upgrade(upgradeName, 0, 0, 0, 0, 0, null);
        for (Upgrade u : availUpgrades.keySet()) {
            if (u.equals(temp)) {
                buyUpgrade = u;
            }
        }
        if (buyUpgrade == null) {
            System.out.println("upgrade not found");
        } else if (upgrades.contains(buyUpgrade)) {
            System.out.println("upgrade already bought");
        } else if (score >= buyUpgrade.getCost() && availUpgrades.get(buyUpgrade)) {
            score -= buyUpgrade.getCost();
            upgrades.add(buyUpgrade);
            updatePerClick();
            System.out.println(buyUpgrade.getName() + " bought");
        } else if (!availUpgrades.get(buyUpgrade)) {
            System.out.println("upgrade not unlocked");
        } else {
            System.out.println("not enough money");
        }
    }

    //MODIFIES: this
    //EFFECTS: updates the perClick variable
    private void updatePerClick() {
        for (Upgrade u : upgrades) {
            perClick += u.getPerClick();
        }
    }

    //EFFECTS: adds upgrade to upgrades if score >= item cost and if upgrade is unlocked
    public void buyAnimal(String animalName, Integer quantity) {
        Upgradable buyAnimal = findAnimal(animalName);
        if (buyAnimal == null) {
            System.out.println("animal not found");
        } else if (!animals.containsKey(buyAnimal)
                && score >= buyAnimal.getCost() * quantity && availAnimals.get(buyAnimal)) {
            score -= buyAnimal.getCost() * quantity;
            animals.put(buyAnimal, quantity);
            System.out.println(buyAnimal.getName() + " bought");
        } else if (animals.containsKey(buyAnimal)
                && score >= buyAnimal.getCost() * quantity && availAnimals.get(buyAnimal)) {
            score -= buyAnimal.getCost() * quantity;
            animals.replace(buyAnimal, animals.get(buyAnimal) + quantity);
            System.out.println(buyAnimal.getName() + " x" + quantity + " bought");
        } else if (!availAnimals.get(buyAnimal)) {
            System.out.println("animal not unlocked");
        } else {
            System.out.println("not enough money");
        }
    }

    private Upgradable findAnimal(String animalName) {
        Upgradable buyAnimal = null;
        Upgradable temp = new Upgradable(animalName, 0, 0, 0, 0, 0, null);
        for (Upgradable u : availAnimals.keySet()) {
            if (u.equals(temp)) {
                buyAnimal = u;
            }
        }
        return buyAnimal;
    }

    //MODIFIES: this
    //EFFECT: sets unlocked to true in hashmap when score >= unlockedAt for each upgrade/upgradable
    private void checkUnlocks() {
        for (Upgradable u : availAnimals.keySet()) {
            if (u.getUnlockedAt() >= score) {
                availAnimals.replace(u, true);
            }
        }
        for (Upgrade u : availUpgrades.keySet()) {
            if (u.getUnlockedAt() >= score) {
                availUpgrades.replace(u, true);
            }
        }
    }

    //EFFECTS: returns owned items as a string
    public String getOwnedString() {
        StringBuilder newString = new StringBuilder("Owned Animals: ");
        for (Upgradable u : animals.keySet()) {
            newString.append(u.getName()).append(" ");
        }
        newString.append("Owned Upgrades: ");
        for (Upgrade u : upgrades) {
            newString.append(u.getName()).append(" ");
        }
        return newString.toString();
    }

    //EFFECTS: returns available items as string
    public String getAvailString() {
        StringBuilder newString = new StringBuilder("Available Animals: ");
        for (Upgradable u : availAnimals.keySet()) {
            newString.append(u.getName()).append(" ");
        }
        newString.append("Available Upgrades: ");
        for (Upgrade u : availUpgrades.keySet()) {
            newString.append(u.getName()).append(" ");
        }
        return newString.toString();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("score", score);
        json.put("perClick", perClick);
        json.put("perSec", perSec);
        json.put("animals", getAnimalsJson());
        json.put("availAnimals", getAvailAnimalsJson());
        json.put("upgrades", getUpgradesJson());
        json.put("availUpgrades", getAvailUpgradesJson());
        return json;
    }

    //EFFECTS: returns animal hashmap as json array
    private JSONArray getAnimalsJson() {
        JSONArray myArray = new JSONArray();
        for (Upgradable u : animals.keySet()) {
            myArray.put(u.toJson(animals.get(u)));
        }
        return myArray;
    }

    //EFFECTS: returns animal hashmap as json array
    private JSONArray getAvailAnimalsJson() {
        JSONArray myArray = new JSONArray();
        for (Upgradable u : availAnimals.keySet()) {
            myArray.put(u.toJson(availAnimals.get(u)));
        }
        return myArray;
    }

    //EFFECTS: returns animal set as json array
    private JSONArray getUpgradesJson() {
        JSONArray myArray = new JSONArray();
        for (Upgrade u : upgrades) {
            myArray.put(u.toJson());
        }
        return myArray;
    }

    //EFFECTS: returns animal hashmap as json array
    private JSONArray getAvailUpgradesJson() {
        JSONArray myArray = new JSONArray();
        for (Upgrade u : availUpgrades.keySet()) {
            myArray.put(u.toJson(availUpgrades.get(u)));
        }
        return myArray;
    }

    //getter and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPerClick() {
        return perClick;
    }

    public void setPerClick(double perClick) {
        this.perClick = perClick;
    }

    public double getScore() {
        return score;
    }

    public double getScoreInt() {
        return (int) score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getPerSec() {
        return perSec;
    }

    public void setPerSec(double perSec) {
        this.perSec = perSec;
    }

    public HashMap<Upgradable, Integer> getAnimals() {
        return animals;
    }

    public void setAnimals(HashMap<Upgradable, Integer> animals) {
        this.animals = animals;
    }

    public HashMap<Upgradable, Boolean> getAvailAnimals() {
        return availAnimals;
    }

    public void setAvailAnimals(HashMap<Upgradable, Boolean> availAnimals) {
        this.availAnimals = availAnimals;
    }

    public HashSet<Upgrade> getUpgrades() {
        return upgrades;
    }

    public void setUpgrades(HashSet<Upgrade> upgrades) {
        this.upgrades = upgrades;
    }

    public HashMap<Upgrade, Boolean> getAvailUpgrades() {
        return availUpgrades;
    }

    public void setAvailUpgrades(HashMap<Upgrade, Boolean> availUpgrades) {
        this.availUpgrades = availUpgrades;
    }
}
