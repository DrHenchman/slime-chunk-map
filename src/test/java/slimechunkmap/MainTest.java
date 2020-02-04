package slimechunkmap;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    @Test
    void ring1() {
        checkCalculateRing(1, 1);
        assertEquals(1, Main.calculateRing(1), "Expected calculateRing(1) == 1");
    }

    @Test
    void ring2() {
        assertAll(IntStream.rangeClosed(2, 9).mapToObj(mapNum -> () -> checkCalculateRing(mapNum, 2)));
    }

    @Test
    void ring3() {
        assertAll(IntStream.rangeClosed(10, 25).mapToObj(mapNum -> () -> checkCalculateRing(mapNum, 3)));
    }

    @Test
    void ring4() {
        assertAll(IntStream.rangeClosed(26, 49).mapToObj(mapNum -> () -> checkCalculateRing(mapNum, 4)));
    }

    private void checkCalculateRing(int mapNum, int expectedRing) {
        assertEquals(expectedRing, Main.calculateRing(mapNum), "Expected calculateRing(" + mapNum + ") == " + expectedRing);
    }

}