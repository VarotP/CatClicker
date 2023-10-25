package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

public class Player extends Location implements Writable {

    //EFFECTS: creates upgradable
    public Player(int perclick, String special) {
        super("Player", 0, perclick, special);
    }

    @Override
    //EFFECTS: returns player object as json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("perClick", getPerClick());
        json.put("upgrades", upgradesToJson());
        json.put("player-animals", animalsToJson());
        return json;
    }

    //EFFECTS: returns all upgrades as json objects in a json array
    public JSONArray upgradesToJson() {
        JSONArray upgradesJson = new JSONArray();
        for (Upgrade u: getUpgrades()) {
            upgradesJson.put(u.toJson());
        }
        return upgradesJson;
    }

    //EFFECTS: returns all animals as json objects in a json array
    public JSONArray animalsToJson() {
        JSONArray animalsJson = new JSONArray();
        for (Animal a: getAnimals()) {
            animalsJson.put(a.toJson());
        }
        return animalsJson;
    }


}
