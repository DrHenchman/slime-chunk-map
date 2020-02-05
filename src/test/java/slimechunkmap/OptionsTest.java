package slimechunkmap;

import org.junit.jupiter.api.Test;
import slimechunkmap.Options;

import static org.junit.jupiter.api.Assertions.*;

class OptionsTest {

    @Test
    void ring1() {
        Options options = Options.parse("--seed", "0", "--ring", "1");
        assertEquals(1, options.mapNumberStart);
        assertEquals(1, options.mapNumberEnd);
    }

    @Test
    void ring2() {
        Options options = Options.parse("--seed", "0", "--ring", "2");
        assertEquals(2, options.mapNumberStart);
        assertEquals(9, options.mapNumberEnd);
    }

    @Test
    void ring3() {
        Options options = Options.parse("--seed", "0", "--ring", "3");
        assertEquals(10, options.mapNumberStart);
        assertEquals(25, options.mapNumberEnd);
    }

    @Test
    void ring4() {
        Options options = Options.parse("--seed", "0", "--ring", "4");
        assertEquals(26, options.mapNumberStart);
        assertEquals(49, options.mapNumberEnd);
    }

}