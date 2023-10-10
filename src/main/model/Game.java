package model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int TICKS_PER_SECOND = 1;
    private Player player1 = new Player("Placeholder", 1, null);
    private int score = 0;
    private List<Location> locations = new ArrayList<>();

    //EFFECTS: init game object
    public Game() {
        Upgrade onePerClickU = new Upgrade("OnePerClick", 5, 0, 1, null);
        Upgrade fivePerClickU = new Upgrade("FivePerClick", 25, 0, 5, null);
        Upgrade animalBuff = new Upgrade("AnimalBuff", 50, 5, 0, null);
        Animal cat = new Animal("Cat", 50, 1, 0, null);
        Animal dog = new Animal("Dog", 75, 2, 0, null);
        Location cafe = new Location("Animal Cafe", 10, 50, null);
        Location zoo = new Location("Zoo", 100, 100, null);
        List<Upgrade> uplist = new ArrayList<>();
        uplist.add(onePerClickU);
        uplist.add(fivePerClickU);
        player1.setAvailUpgrades(uplist);
        List<Animal> anList = new ArrayList<>();
        List<Upgrade> uaList = new ArrayList<>();
        uaList.add(animalBuff);
        cat.setAvailUpgrades(uaList);
        dog.setAvailUpgrades(uaList);
        anList.add(cat);
        anList.add(dog);
        player1.setAvailAnimals(anList);
        locations.add(cafe);
        locations.add(zoo);
    }

    //MODIFIES: this
    //EFFECTS: adds up all the money per sec and ocassionally spawns specials
    public void tick() {
        if (player1.getAnimals() != null) {
            for (Animal a: player1.getAnimals()) {
                score += a.getPerSec();
            }
        }

        for (Location l: locations) {
            for (Animal a: l.getAnimals()) {
                score += a.getPerSec();
            }
        }
    }

    //MODIFIES: This
    //EFFECT: earn money
    public void click() {
        score += player1.getPerClick();
    }

    //EFFECT: returns list of available upgrades for player, locations, and animals
    public List<String> displayUpgrades() {
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
