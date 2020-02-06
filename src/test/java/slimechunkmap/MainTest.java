package slimechunkmap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    @ParameterizedTest(name = "Ring #{0} -> Map {1}..={2}")
    @CsvSource({
            "1,1,1",
            "2,2,9",
            "3,10,25",
            "4,26,29"
    })
    void ring(int ring, int startMapNum, int endMapNum) {
        assertAll(IntStream.rangeClosed(startMapNum, endMapNum).mapToObj(mapNum -> () -> checkCalculateRing(mapNum, ring)));
    }

    private void checkCalculateRing(int mapNum, int expectedRing) {
        assertEquals(expectedRing, Main.calculateRing(mapNum), "Expected calculateRing(" + mapNum + ") == " + expectedRing);
    }

    @Test
    void calculateMapCenter() {
        int value = Main.calculateMapCenter(0, 2);
        assertEquals(192, value);
    }

    @ParameterizedTest(name = "[{0}]")
    @CsvSource({
            "10,-832,-832", "11,-320,-832", "12,192,-832", "13,704,-832", "14,1216,-832",
            "25,-832,-320", "2,-320,-320", "3,192,-320", "4,704,-320", "15,1216,-320",
            "24,-832,192", "9,-320,192", "1,192,192", "5,704,192", "16,1216,192",
            "23,-832,704", "8,-320,704", "7,192,704", "6,704,704", "17,1216,704",
            "22,-832,1216", "21,-320,1216", "20,192,1216", "19,704,1216", "18,1216,1216",
    })
    void centerMap(int mapNum, int targetX, int targetZ) {
        int x = Main.calculateCenterXFromOrigin(mapNum, 2, 192);
        int z = Main.calculateCenterZFromOrigin(mapNum, 2, 192);
        assertEquals(targetX, x);
        assertEquals(targetZ, z);
    }

}