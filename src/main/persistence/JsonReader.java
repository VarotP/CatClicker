package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import ui.ZooGame;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

// reads json from file and creates game object
public class JsonReader {
    //Upgrades and Animals
    private final String source;
    private int ticks;

    public JsonReader(String source, int ticks) {
        this.source = source;
        this.ticks = ticks;
    }

    //EFFECTS: read file driver
    public Game read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameFile(jsonObject);
    }

    //EFFECTS: reads file from source
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //EFFECTS: parses json object and creates game object
    private Game parseGameFile(JSONObject jsonObject) {
        String gameName = jsonObject.getString("name");
        Game thisGame = new Game(ticks);
        thisGame.setName(gameName);
        buildGame(thisGame, jsonObject);
        return thisGame;
    }

    //EFFECTS: builds game object from json file
    private void buildGame(Game game, JSONObject jsonObject) {
        game.setScore(jsonObject.getDouble("score"));
        game.setPerSec(jsonObject.getInt("perSec"));
        game.setAnimals(getAnimalsJson(jsonObject.getJSONArray("animals")));
        game.setAvailAnimals(getAvailAnimalsJson(jsonObject.getJSONArray("availAnimals")));
        game.setUpgrades(getUpgradesJson(jsonObject.getJSONArray("upgrades")));
        game.setAvailUpgrades(getAvailUpgradesJson(jsonObject.getJSONArray("availUpgrades")));
    }

    // EFFECTS: returns animals as a hashmap to build game object
    private HashMap<Upgradable, Integer> getAnimalsJson(JSONArray jsonArray) {
        HashMap<Upgradable, Integer> myHashMap = new HashMap<>();
        for (Object json : jsonArray) {
            JSONObject nextAnimal = (JSONObject) json;
            String animalName = nextAnimal.getString("name");
            int cost = nextAnimal.getInt("cost");
            int perSec = nextAnimal.getInt("perSec");
            int perClick = nextAnimal.getInt("perClick");
            double scalingFactor = nextAnimal.getDouble("scalingFactor");
            int unlockedAt = nextAnimal.getInt("unlockedAt");
            int quantity = nextAnimal.getInt("quantity");
            Upgradable newAnimal = new Upgradable(animalName, cost, perSec, perClick, unlockedAt, scalingFactor, null);

            myHashMap.put(newAnimal, quantity);
        }
        return myHashMap;
    }

    // EFFECTS: returns avail animals as hashmap to build game object
    private HashMap<Upgradable, Boolean> getAvailAnimalsJson(JSONArray jsonArray) {
        HashMap<Upgradable, Boolean> myHashMap = new HashMap<>();
        for (Object json : jsonArray) {
            JSONObject nextAnimal = (JSONObject) json;
            String animalName = nextAnimal.getString("name");
            int cost = nextAnimal.getInt("cost");
            int perSec = nextAnimal.getInt("perSec");
            int perClick = nextAnimal.getInt("perClick");
            double scalingFactor = nextAnimal.getDouble("scalingFactor");
            int unlockedAt = nextAnimal.getInt("unlockedAt");
            boolean unlocked = nextAnimal.getBoolean("unlocked");
            Upgradable newAnimal = new Upgradable(animalName, cost, perSec, perClick, unlockedAt, scalingFactor, null);

            myHashMap.put(newAnimal, unlocked);
        }
        return myHashMap;
    }

    // EFFECTS: returns upgrades as hashmap to build game object
    private HashSet<Upgrade> getUpgradesJson(JSONArray jsonArray) {
        HashSet<Upgrade> myHashSet = new HashSet<>();
        for (Object json : jsonArray) {
            JSONObject nextUpgrade = (JSONObject) json;
            String upgradeName = nextUpgrade.getString("name");
            int cost = nextUpgrade.getInt("cost");
            int perSec = nextUpgrade.getInt("perSec");
            int perClick = nextUpgrade.getInt("perClick");
            double scalingFactor = nextUpgrade.getDouble("scalingFactor");
            int unlockedAt = nextUpgrade.getInt("unlockedAt");
            Upgrade newUpgrade = new Upgrade(upgradeName, cost, perSec, perClick, unlockedAt, scalingFactor, null);

            myHashSet.add(newUpgrade);
        }
        return myHashSet;
    }

    // EFFECTS: returns avail upgrades as hashmap to build game object
    private HashMap<Upgrade, Boolean> getAvailUpgradesJson(JSONArray jsonArray) {
        HashMap<Upgrade, Boolean> myHashMap = new HashMap<>();
        for (Object json : jsonArray) {
            JSONObject nextUpgrade = (JSONObject) json;
            String upgradeName = nextUpgrade.getString("name");
            int cost = nextUpgrade.getInt("cost");
            int perSec = nextUpgrade.getInt("perSec");
            int perClick = nextUpgrade.getInt("perClick");
            double scalingFactor = nextUpgrade.getDouble("scalingFactor");
            int unlockedAt = nextUpgrade.getInt("unlockedAt");
            boolean unlocked = nextUpgrade.getBoolean("unlocked");
            Upgrade newUpgrade = new Upgrade(upgradeName, cost, perSec, perClick, unlockedAt, scalingFactor, null);

            myHashMap.put(newUpgrade, unlocked);
        }
        return myHashMap;
    }
}
