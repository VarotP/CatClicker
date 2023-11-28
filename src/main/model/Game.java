package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// represents a game object
public class Game implements Writable {
    private String name;
    private double perClick;
    private double score;
    private double perSec;
    private final double ticks;
    private EventLog eventLog = EventLog.getInstance();

    // animal and count
    private HashMap<Upgradable, Integer> animals;
    // unlockable animal and unlocked?
    private HashMap<Upgradable, Boolean> availAnimals;
    private HashSet<Upgrade> upgrades;
    private HashMap<Upgrade, Boolean> availUpgrades;

    //Upgrades and Animals

    public final Upgrade onePerClickU = new Upgrade("OnePerClick", 100, 0, 1, 0,1.4,null);
    public final Upgrade fivePerClickU = new Upgrade("FivePerClick", 5000, 0, 5, 5000, 1.4, null);
    public final Upgrade twentyPerClickU = new Upgrade("TwentyPerClick", 10000, 0, 5, 10000, 1.4, null);
    public final Upgradable cat = new Upgradable("Cat", 10, 1, 0, 0, 1.4, null);
    public final Upgradable dog = new Upgradable("Dog", 200, 2, 0, 0, 1.4, null);
    public final Upgradable capybara = new Upgradable("Capybara", 500, 5, 0,  500,1.4, null);

    //MODIFIES: this
    //EFFECTS: constructs game object and inits the available upgrade and animal list
    public Game(int ticks) {
        this.name = "";
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
        availUpgrades.put(twentyPerClickU, false);
    }


    //MODIFIES: this
    //EFFECTS: adds up all the money per sec and ocassionally spawns specials,
    // also checks for unlocks and makes them available to player
    public void tick() {
        double perSecSum = 0;
        for (Upgradable animal : animals.keySet()) {
            // score += each animals's per second multiplied by their count
            perSecSum += (animal.getPerSec() * animals.get(animal) / ticks);
            score += (animal.getPerSec() * animals.get(animal) / ticks);
        }
        perSec = perSecSum;
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
            eventLog.logEvent(new Event("Buy upgrade: upgrade not found"));
        } else if (upgrades.contains(buyUpgrade)) {
            eventLog.logEvent(new Event("Buy upgrade: upgrade already bought"));
        } else if (score >= buyUpgrade.getCost()) {
            score -= buyUpgrade.getCost();
            upgrades.add(buyUpgrade);
            updatePerClick();
            eventLog.logEvent(new Event("Buy upgrade: " + buyUpgrade.getName() + " bought"));
        } else if (!availUpgrades.get(buyUpgrade)) {
            eventLog.logEvent(new Event("Buy upgrade: upgrade not unlocked"));
        } else {
            eventLog.logEvent(new Event("Buy upgrade: not enough money"));
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
            eventLog.logEvent(new Event("Buy animal: animal not found"));
        } else if (!animals.containsKey(buyAnimal)
                && score >= buyAnimal.getCost() * quantity) {
            score -= buyAnimal.getCost() * quantity;
            buyAnimal.setCost((int) (buyAnimal.getCost() * buyAnimal.getScalingFactor()));
            animals.put(buyAnimal, quantity);
            eventLog.logEvent(new Event("Buy animal: " + buyAnimal.getName() + " bought"));
        } else if (animals.containsKey(buyAnimal)
                && score >= buyAnimal.getCost() * quantity) {
            score -= buyAnimal.getCost() * quantity;
            animals.replace(buyAnimal, animals.get(buyAnimal) + quantity);
            buyAnimal.setCost((int) (buyAnimal.getCost() * buyAnimal.getScalingFactor()));
            eventLog.logEvent(new Event("Buy animal: " + buyAnimal.getName() + " x" + quantity + " bought"));
        } else if (!availAnimals.get(buyAnimal)) {
            eventLog.logEvent(new Event("Buy animal: animal not unlocked"));
        } else {
            eventLog.logEvent(new Event("Buy animal: not enough money"));
        }
    }

    // EFFECTS: finds animal with same string name
    public Upgradable findAnimal(String animalName) {
        Upgradable buyAnimal = null;
        Upgradable temp = new Upgradable(animalName, 0, 0, 0, 0, 0, null);
        for (Upgradable u : availAnimals.keySet()) {
            if (u.equals(temp)) {
                buyAnimal = u;
            }
        }
        return buyAnimal;
    }

    // EFEFCTS: finds upgrade with same string name
    public Upgrade findUpgrade(String upgradeName) {
        Upgrade buyUpgrade = null;
        Upgrade temp = new Upgrade(upgradeName, 0, 0, 0, 0, 0, null);
        for (Upgrade u : availUpgrades.keySet()) {
            if (u.equals(temp)) {
                buyUpgrade = u;
            }
        }
        return buyUpgrade;
    }

    //MODIFIES: this
    //EFFECT: sets unlocked to true in hashmap when score >= unlockedAt for each upgrade/upgradable
    public void checkUnlocks() {
        for (Upgradable u : availAnimals.keySet()) {
            if (score >= u.getUnlockedAt() && !availAnimals.get(u)) {
                availAnimals.replace(u, true);
                eventLog.logEvent(new Event(u.getName() + " unlocked"));
            }
        }
        for (Upgrade u : availUpgrades.keySet()) {
            if (score >= u.getUnlockedAt() && !availUpgrades.get(u)) {
                availUpgrades.replace(u, true);
                eventLog.logEvent(new Event(u.getName() + " unlocked"));
            }
        }
    }

    //EFFECTS: returns owned items as a string
    public String getOwnedString() {
        StringBuilder newString = new StringBuilder("<html> <br/> Owned Animals: <br/>");
        for (Upgradable u : animals.keySet()) {
            newString.append(u.getName()).append(" <br/>");
        }
        newString.append(" <br/>");
        newString.append("Owned Upgrades: ");
        newString.append(" <br/>");
        for (Upgrade u : upgrades) {
            newString.append(u.getName()).append(" <br/>");
        }
        newString.append(" <br/>");
        newString.append("</html>");
        return newString.toString();
    }

    //EFFECTS: returns available items as string
    public String getAvailString() {
        StringBuilder newString = new StringBuilder("<html> Available Animals: <br/>");
        for (Upgradable u : availAnimals.keySet()) {
            newString.append(u.getName()).append(" <br/>");
        }
        newString.append(" <br/>");
        newString.append("Available Upgrades: ");
        newString.append(" <br/>");
        for (Upgrade u : availUpgrades.keySet()) {
            newString.append(u.getName()).append(" <br/>");
        }
        newString.append("</html>");
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

    public int getScoreInt() {
        return (int) Math.round(score);
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getPerSec() {
        return (int) Math.round(perSec * ticks);
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
