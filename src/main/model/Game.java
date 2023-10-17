package model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int TICKS_PER_SECOND = 10;
    private Player player1 = new Player("Placeholder", 1, null);
    private int score = 0;
    private int perSec = 0;
    private List<Location> locations = new ArrayList<>();
    private final int unlockCafe = 1000;
    private boolean unlockedCafe = false;
    private final int unlockZoo = 10000;
    private boolean unlockedZoo = false;
    private final Location cafe = new Location("Animal Cafe", 10, 50, null);
    private final Location zoo = new Location("Zoo", 100, 100, null);

    //MODIFIES: this
    //EFFECTS: adds up all the money per sec and ocassionally spawns specials,
    // also checks for unlocks and makes them available to player
    public void tick() {
        checkUnlocks();
        if (player1.getAnimals().size() != 0) {
            for (Animal a: player1.getAnimals()) {
                score += a.getPerSec() * a.getCount();
            }
        }

        if (locations.size() > 0) {
            for (Location l: locations) {
                score += l.getPerSec();
                for (Animal a: l.getAnimals()) {
                    score += a.getPerSec() * a.getCount();
                }
            }
        }
    }

    //MODIFIES: This
    //EFFECT: earn money
    public void click() {
        score += player1.getPerClick();
    }

    //EFFECT: returns list of available upgrades for player, locations, and animals
    public String displayAvailUpgrades() {
        List<String> output = new ArrayList<>();
        output.add("Player Upgrades:");
        for (Upgrade u: player1.getAvailUpgrades()) {
            output.add(u.getName());
        }
        output.add("Available Animals:");
        output.addAll(returnAnimals(player1.getAvailAnimals()));
        if (player1.getAnimals().size() != 0) {
            output.addAll(returnAnimalUpgrades(player1.getAnimals()));
        }
        if (locations.size() != 0) {
            for (Location l: locations) {
                output.add(l.getName() + " Upgrades:");
                for (Upgrade u: l.getAvailUpgrades()) {
                    output.add(u.getName());
                }
                output.add("Available Animals in " + l.getName() + ": ");
                output.addAll(returnAnimals(l.getAvailAnimals()));
            }
        }
        return listToString(output);
    }

    private String listToString(List<String> input) {
        String newOutput = "";
        for (String i: input) {
            newOutput += i;
            newOutput += ' ';
        }
        return newOutput;
    }

    //EFFECTS: display current upgrades, animals, and locations
    public String displayStats() {
        List<String> output = new ArrayList<>();

        output.add("Owned Upgrades:");
        output.addAll(returnOwnedUpgrades(player1));

        output.add("Player Animals:");
        output.addAll(returnOwnedAnimals(player1));


        output.add("Owned Locations:");
        if (locations.size() != 0) {
            for (Location l: locations) {
                output.add(l.getName());
                output.add(l.getName() + " Upgrades:");
                for (Upgrade u: l.getUpgrades()) {
                    output.add(u.getName());
                }
                output.add(l.getName() + " Animals:");
                output.addAll(returnOwnedAnimals(l));
            }
        }
        String newOutput = "";
        for (String i: output) {
            newOutput += i;
            newOutput += ' ';
        }
        return newOutput;
    }

    //MODIFIES: this
    //EFFECT: adds more upgradables to availUpgrades/animals/locations when score reaches a threshold
    private void checkUnlocks() {
        if (score >= unlockCafe && (!unlockedCafe)) {
            System.out.println("Unlocked Cafe");
            locations.add(cafe);
            unlockedCafe = true;
        }
        if (score >= unlockZoo && (!unlockedZoo)) {
            System.out.println("Unlocked Zoo");
            locations.add(zoo);
            unlockedZoo = true;
        }
    }


    //MODIFIES: this
    //EFFECTS: calculates the perSec of entire game
    public int getPerSec() {
        perSec = 0;
        if (player1.getAnimals().size() != 0) {
            for (Animal a: player1.getAnimals()) {
                perSec += a.getPerSec() * a.getCount();
            }
        }

        if (locations.size() > 0) {
            for (Location l: locations) {
                perSec += l.getPerSec();
                for (Animal a: l.getAnimals()) {
                    perSec += a.getPerSec() * a.getCount();
                }
            }
        }
        return perSec;
    }

    //EFFECTS: returns all animals in list as list of String
    private List<String> returnAnimals(List<Animal> a) {
        List<String> output = new ArrayList<>();
        for (Animal ani: a) {
            output.add(ani.getName());
        }
        return output;
    }

    //EFFECTS: returns all animals and their respective upgrades
    private List<String> returnAnimalUpgrades(List<Animal> a) {
        List<String> output = new ArrayList<>();
        for (Animal thisone: a) {
            output.add(thisone.getName() + " Upgrades:");
            for (Upgrade u: thisone.getAvailUpgrades()) {
                output.add(u.getName());
            }
        }
        return output;
    }


    //EFFECTS: returns owned upgrades
    private List<String> returnOwnedUpgrades(Upgradable u) {
        List<String> output = new ArrayList<>();
        if (u.getUpgrades().size() != 0) {
            for (Upgrade s: u.getUpgrades()) {
                output.add(s.getName() + " x" + s.getCount());
            }
        }
        return output;
    }

    //EFFECTS: returns owned animals
    private List<String> returnOwnedAnimals(Location l) {
        List<String> output = new ArrayList<>();
        if (l.getAnimals().size() != 0) {
            for (Animal a: l.getAnimals()) {
                output.add(a.getName() + " x" + a.getCount());
                output.add(a.getName() + " Upgrades:");
                output.addAll(returnOwnedUpgrades(a));
            }
        }
        return output;
    }

    //MODIFIES: this
    //EFFECTS: adds location to list of locations
    public void addLocation(Location location) {
        locations.add(location);
    }

    //getter and setters

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Player getPlayer1() {
        return player1;
    }
}
