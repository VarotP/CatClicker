package persistance;

import model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonWriter;
import ui.ZooGame;
import ui.ZooGame2;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    JsonWriter writer;
    JsonWriter writer2;
    ZooGame2 zoogame;
    Game game;

    @BeforeEach
    void setup() {
        zoogame = new ZooGame2();
        game = new Game( 10);
        writer = new JsonWriter("./data/testOutput2.json");
    }

    @Test
    void fileNotFound() {
        writer2 = new JsonWriter("./dumb/testOutput2.json");
        try {
            writer2.open();
            fail();
        } catch (FileNotFoundException e) {
            System.out.println("test passed");
        }
    }

    @Test
    void writeSuccessCloseTest() {
        try {
            writer.open();
            writer.write(game);
            writer.close();
        } catch (FileNotFoundException e) {
            fail();
        }
    }
}
