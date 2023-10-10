package model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int TICKS_PER_SECOND = 2;
    private Player player1 = new Player("Placeholder", 1, null);
    private int score = 0;
    private List<Location> locations = new ArrayList<>();

    //MODIFIES: this
    //EFFECTS: adds up all the money per sec and ocassionally spawns specials
    public void tick() {
        if (player1.getAnimals() != null) {
            for (Animal a: player1.getAnimals()) {
                score += a.getPerSec();
            }
        }

        if (locations.size() > 0) {
            for (Location l: locations) {
                score += l.getPerSec();
                for (Animal a: l.getAnimals()) {
                    score += a.getPerSec();
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
    public List<String> displayAvailUpgrades() {
        List<String> output = new ArrayList<>();
        output.add("Player Upgrades:");
        for (Upgrade u: player1.getAvailUpgrades()) {
            output.add(u.getName());
        }

        output.add("Available Animals:");
        output.addAll(returnAnimals(player1.getAvailAnimals()));

        if (player1.getAnimals() != null) {
            output.addAll(returnAnimalUpgrades(player1.getAnimals()));
        }
        if (locations != null) {
            for (Location l: locations) {
                output.add(l.getName() + " Upgrades:");
                for (Upgrade u: l.getAvailUpgrades()) {
                    output.add(u.getName());
                }
                output.add("Available Animals in" + l.getName() + ": ");
                output.addAll(returnAnimals(l.getAvailAnimals()));
            }
        }
        return output;
    }

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


    //EFFECTS: display current upgrades, animals, and locations
    public List<String> displayStats() {
        List<String> output = new ArrayList<>();

        output.add("Owned Upgrades:");
        output.addAll(returnOwnedUpgrades(player1));

        output.add("Player Animals:");
        output.addAll(returnOwnedAnimals(player1));


        output.add("Owned Locations:");
        if (locations != null) {
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
        return output;
    }


    //EFFECTS: returns owned upgrades
    private List<String> returnOwnedUpgrades(Upgradable u) {
        List<String> output = new ArrayList<>();
        if (u.getUpgrades() != null) {
            for (Upgrade s: u.getUpgrades()) {
                output.add(s.getName());
            }
        }
        return output;
    }

    //EFFECTS: returns owned animals
    private List<String> returnOwnedAnimals(Location l) {
        List<String> output = new ArrayList<>();
        if (l.getAnimals() != null) {
            for (Animal a: l.getAnimals()) {
                output.add(a.getName());
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
