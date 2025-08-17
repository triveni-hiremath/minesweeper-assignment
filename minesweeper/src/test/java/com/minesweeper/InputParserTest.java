package com.minesweeper;

import com.minesweeper.model.Coordinates;
import com.minesweeper.utility.InputParser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InputParserTest {
    @Test
    void parsesValid() {
        Coordinates c = InputParser.parse("B3", 5);
        assertEquals(1, c.getRow());
        assertEquals(2, c.getCol());
        assertEquals("B3", InputParser.format(c));
    }

    @Test
    void rejectsOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> InputParser.parse("Z1", 4));
        assertThrows(IllegalArgumentException.class, () -> InputParser.parse("A0", 4));
        assertThrows(IllegalArgumentException.class, () -> InputParser.parse("A5", 4));
        assertThrows(IllegalArgumentException.class, () -> InputParser.parse("A", 4));
        assertThrows(IllegalArgumentException.class, () -> InputParser.parse("AA", 4));
    }
}