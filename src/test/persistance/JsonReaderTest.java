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
        reader = new JsonReader("./data/testOutput.json",10);
    }

    @Test
    void readTest() {
        Game newGame = new Game("Val", 10);
        try {
            assertEquals(newGame.getName(), reader.read().getName());
            assertEquals(2, reader.read().getScoreInt());
            assertEquals(0, reader.read().getPerSec());
            assertEquals(2, reader.read().getScoreInt());
        } catch (IOException e) {
            fail();
        }
    }
}
