package model;

import model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
//import ui.ZooGame;
import ui.ZooGame2;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {
    ZooGame2 zoogame;
    JsonReader reader;

    @BeforeEach
    void setup() {
        reader = new JsonReader("./data/testOutput.json",10);
    }

    @Test
    void readTest() {
        Game newGame = new Game( 10);
        try {
            assertEquals(2, reader.read().getScoreInt());
            assertEquals(0, reader.read().getPerSec());
            assertEquals(1, reader.read().getPerClick());
        } catch (IOException e) {
            fail();
        }
    }
}
