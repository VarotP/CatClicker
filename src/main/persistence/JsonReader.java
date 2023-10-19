package persistence;

import model.Game;

public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    public Game read() {
        System.out.println("lol");
        return new Game();
    }
}
