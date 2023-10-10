package model;

public class Player extends Location {

    //EFFECTS: creates upgradable
    public Player(String name, int perclick, String special) {
        super(name, 0, perclick, special);
    }
}
