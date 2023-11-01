package persistence;

import model.Game;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    //EFFECTS: constructor
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //EFFECTS: opens the file
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //EFFECTS: writes game json to the file
    public void write(Game game) {
        JSONObject json = game.toJson();
        saveToFile(json.toString(TAB));
    }

    //EFFECTS: closes the file
    public void close() {
        writer.close();
    }

    //EFFECTS: saves the file
    public void saveToFile(String json) {
        writer.print(json);
    }
}
