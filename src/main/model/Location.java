package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Location extends Upgradable {
    private List<Animal> animals;
    private List<Animal> availAnimals;

    //EFFECTS: creates upgradable
    public Location(String name, int persec, int perclick, String special) {
        super(name, 0, persec, perclick, 0, special);
        this.animals = new ArrayList<>();
        this.availAnimals = new ArrayList<>();
    }

    //REQUIRES: Upgrade is in availAnimals, money >= animal.getCost
    //MODIFIES: this
    //EFFECTS: adds animal to animals if not owned, otherwise add 1 to count
    //subtracts money, returns amount of remaining money
    public double buyAnimal(double money, Animal animal) {
        if (!checkContainsAnimal(animal)) {
            animals.add(animal);
        } else {
            animal = findAnimal(animal, animals);
        }
        double returnChange = money - animal.getCost();
        animal.setCount(animal.getCount() + 1);
        setPerSec(getPerSec() + animal.getPerSec());
        setPerClick(getPerClick() + animal.getPerClick());
        return returnChange;
    }

    public boolean checkContainsAnimal(Animal a) {
        for (Animal currentAnimal : animals) {
            if (currentAnimal.getName().equals(a.getName())) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: finds animal with same string name and returns it, if no animal is found return null
    public Animal findAnimal(Animal toFind, List<Animal> animalList) {
        for (Animal currentAnimal : animalList) {
            if (currentAnimal.getName().equals(toFind.getName())) {
                return currentAnimal;
            }
        }
        return null;
    }

    //MODIFIES: this
    //EFFECTS: adds animal to animal list
    public void addAnimal(Animal animal) {
        this.animals.add(animal);
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
}

