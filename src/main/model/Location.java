package model;

import java.util.ArrayList;
import java.util.List;

public class Location extends Upgradable {
    private List<Animal> animals;
    private List<Animal> availAnimals;

    //REQUIRES:
    //EFFECTS: creates upgradable
    public Location(String name, int persec, int perclick, String special) {
        super(name, 0, persec, perclick, special);
        this.animals = new ArrayList<>();
        this.availAnimals = new ArrayList<>();
    }

    //REQUIRES: animal is in availAnimals and money >= animal cost
    //MODIFIES: this
    //EFFECTS: adds animal to owned animal list, increases perSec and perClick by Animal stats
    public int buyAnimal(int money, Animal animal) {
        animals.add(animal);
        setPerSec(getPerSec() + animal.getPerSec());
        setPerClick(getPerClick() + animal.getPerClick());

        return money - animal.getCost();
    }

    //get and set methods

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

    //MODIFIES: this
    //EFFECTS: adds animal to animal list
    public void addAnimal(Animal animal) {
        this.animals.add(animal);
    }

}

