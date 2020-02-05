package slimechunkmap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LootTableGeneratorTest {

    @Test
    void doubleQuoteIsEscaped() {
        assertEquals("\"\\\"\"", LootTableGenerator.quoteString("\""));
    }

    @Test
    void escapedDoubleQuoteIsDoubleEscaped() {
        assertEquals("\"\\\\\\\"\"", LootTableGenerator.quoteString("\\\""));
    }

    @Test
    void doubleEscapedDoubleQuoteIsTripeEscaped() {
        assertEquals("\"\\\\\\\\\\\\\\\"\"", LootTableGenerator.quoteString("\\\\\\\""));
    }

}