package persistance;

import model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import ui.ZooGame;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {
    ZooGame zoogame;
    JsonReader reader;

    @BeforeEach
    void setup() {
        zoogame = new ZooGame();
        reader = new JsonReader(zoogame, "./data/testSave.json");
    }

    @Test
    void readTest() {
        Game newGame = new Game("Val");
        try {
            assertEquals(newGame.getName(), reader.read().getName());
            assertEquals(25.7, reader.read().getScore());
            assertEquals(newGame.getLocations(), reader.read().getLocations());
            assertEquals(2, reader.read().getPerSec());
            assertEquals(25, reader.read().getScoreInt());
        } catch (IOException e) {
            fail();
        }
    }
}
