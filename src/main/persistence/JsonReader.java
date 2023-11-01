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
import java.util.List;
import java.util.stream.Stream;

public class JsonReader {
    //Upgrades and Animals
    private final String source;
    private final ZooGame papa;

    //EFFECTS: constructs jsonReader object
    public JsonReader(ZooGame papa, String source) {
        this.source = source;
        this.papa = papa;
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
        Game thisGame = papa.initGame(gameName);
        buildGame(thisGame, jsonObject);
        return thisGame;
    }

    //EFFECTS: builds game object from json file
    private void buildGame(Game game, JSONObject jsonObject) {
        JSONObject playerObject = jsonObject.getJSONObject("player");
        Player newPlayer = game.getPlayer1();
        newPlayer.setPerSec(jsonObject.getInt("perSec"));
        addUpgradesAndAnimals(game, newPlayer, playerObject);

        game.setScore(jsonObject.getDouble("score"));
        game.setPerSec(jsonObject.getInt("perSec"));
        game.setUnlockedCafe(jsonObject.getBoolean("unlocked-cafe"));
        game.setUnlockedZoo(jsonObject.getBoolean("unlocked-zoo"));
    }

    //EFFECTS: adds animals and upgrades from json file to game object
    private void addUpgradesAndAnimals(Game game, Player player, JSONObject playerObject) {

        JSONArray jsonUpgradeList = playerObject.getJSONArray("upgrades");
        JSONArray jsonAnimalList = playerObject.getJSONArray("player-animals");

        player.setPerClick(playerObject.getInt("perClick"));
        player.setUpgrades(getUpgradesFromJson(game, jsonUpgradeList));
        player.setAnimals(getAnimalsFromJson(game, jsonAnimalList));
        game.setPlayer1(player);
    }

    //EFFECTS: creates upgrades list and sets it to playerobject
    private List<Upgrade> getUpgradesFromJson(Game game, JSONArray jsonUpgradeList) {
        List<Upgrade> upgrades = new ArrayList<>();
        for (Object json : jsonUpgradeList) {
            JSONObject nextUpgrade = (JSONObject) json;

            String upgradeName = nextUpgrade.getString("name");
            int count = nextUpgrade.getInt("count");
            int cost = nextUpgrade.getInt("cost");
            int perSec = nextUpgrade.getInt("perSec");
            int perClick = nextUpgrade.getInt("perClick");
            double scalingFactor = nextUpgrade.getDouble("scalingFactor");
            Upgrade newUpgrade = new Upgrade(upgradeName, cost, perSec, perClick, scalingFactor, null);
            newUpgrade.setCount(count);
            upgrades.add(newUpgrade);

            //sets availAnimal to cost of animal from saved game
            game.getPlayer1().findUpgrade(newUpgrade, game.getPlayer1().getAvailUpgrades()).setCost(cost);
        }
        return upgrades;
    }

    //EFFECTS: creates animal list and adds to playerobject
    private List<Animal> getAnimalsFromJson(Game game, JSONArray jsonAnimalList) {
        List<Animal> animals = new ArrayList<>();
        for (Object json : jsonAnimalList) {
            JSONObject nextAnimal = (JSONObject) json;

            String animalName = nextAnimal.getString("name");
            int count = nextAnimal.getInt("count");
            int cost = nextAnimal.getInt("cost");
            int perSec = nextAnimal.getInt("perSec");
            int perClick = nextAnimal.getInt("perClick");
            double scalingFactor = nextAnimal.getDouble("scalingFactor");
            Animal newAnimal = new Animal(animalName, cost, perSec, perClick, scalingFactor, null);
            newAnimal.setCount(count);
            animals.add(newAnimal);

            //sets availAnimal to cost of animal from saved game
            game.getPlayer1().findAnimal(newAnimal, game.getPlayer1().getAvailAnimals()).setCost(cost);
        }
        return animals;
    }
}
