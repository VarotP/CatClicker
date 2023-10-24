package model;

public class Player extends Location {

    //EFFECTS: creates upgradable
    public Player(int perclick, String special) {
        super("Player", 0, perclick, special);
    }
}
