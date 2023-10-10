package model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int TICKS_PER_SECOND = 1;
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
        List<Upgrade> upgrades = new ArrayList<>();
        output.add("Player Upgrades:");
        for (Upgrade u: player1.getAvailUpgrades()) {
            output.add(u.getName());
        }

        if (player1.getAnimals() != null) {
            for (Animal a: player1.getAnimals()) {
                output.add(a.getName() + " Upgrades:");
                for (Upgrade u: a.getAvailUpgrades()) {
                    output.add(u.getName());
                }
            }
        }
        if (locations != null) {
            for (Location l: locations) {
                output.add(l.getName() + " Upgrades:");
                for (Upgrade u: l.getAvailUpgrades()) {
                    output.add(u.getName());
                }
            }
        }
        return output;
    }

    //EFFECTS: display current upgrades, animals, and locations
    @SuppressWarnings("methodlength")
    public List<String> displayStats() {
        List<String> output = new ArrayList<>();

        output.add("Owned Upgrades:");
        if (player1.getUpgrades() != null) {
            for (Upgrade u: player1.getUpgrades()) {
                output.add(u.getName());
            }
        }
        output.add("Owned Animals:");
        if (player1.getAnimals() != null) {
            for (Animal a: player1.getAnimals()) {
                output.add(a.getName());
                output.add(a.getName() + " Upgrades:");
                for (Upgrade u: a.getUpgrades()) {
                    output.add(u.getName());
                }
            }
        }
        output.add("Owned Locations:");
        if (locations != null) {
            for (Location l: locations) {
                output.add(l.getName());
                output.add(l.getName() + " Upgrades:");
                for (Upgrade u: l.getUpgrades()) {
                    output.add(u.getName());
                }
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

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }
}
