package model;

public class Animal extends Upgradable {

    //REQUIRES:
    //EFFECTS: creates upgradable
    public Animal(String name, int cost, int persec, int perclick, String special) {
        super(name, cost, persec, perclick, special);
    }
}
