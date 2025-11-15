package com.comp2042.tetris.util;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for BrickColorMapper utility.
 * Tests color mapping for different brick types.
 */
class BrickColorMapperTest {

    @Test
    @DisplayName("Type 0 should return transparent color")
    void testEmptyCell() {
        Paint color = BrickColorMapper.getColor(0);
        assertEquals(Color.TRANSPARENT, color);
    }

    @Test
    @DisplayName("Type 1 (I-brick) should return aqua color")
    void testIBrick() {
        Paint color = BrickColorMapper.getColor(1);
        assertEquals(Color.AQUA, color);
    }

    @Test
    @DisplayName("Type 2 (J-brick) should return blue violet color")
    void testJBrick() {
        Paint color = BrickColorMapper.getColor(2);
        assertEquals(Color.BLUEVIOLET, color);
    }

    @Test
    @DisplayName("Type 3 (L-brick) should return dark green color")
    void testLBrick() {
        Paint color = BrickColorMapper.getColor(3);
        assertEquals(Color.DARKGREEN, color);
    }

    @Test
    @DisplayName("Type 4 (O-brick) should return yellow color")
    void testOBrick() {
        Paint color = BrickColorMapper.getColor(4);
        assertEquals(Color.YELLOW, color);
    }

    @Test
    @DisplayName("Type 5 (S-brick) should return red color")
    void testSBrick() {
        Paint color = BrickColorMapper.getColor(5);
        assertEquals(Color.RED, color);
    }

    @Test
    @DisplayName("Type 6 (T-brick) should return beige color")
    void testTBrick() {
        Paint color = BrickColorMapper.getColor(6);
        assertEquals(Color.BEIGE, color);
    }

    @Test
    @DisplayName("Type 7 (Z-brick) should return burlywood color")
    void testZBrick() {
        Paint color = BrickColorMapper.getColor(7);
        assertEquals(Color.BURLYWOOD, color);
    }

    @Test
    @DisplayName("Invalid type should return white color as default")
    void testInvalidType() {
        Paint color = BrickColorMapper.getColor(99);
        assertEquals(Color.WHITE, color);
    }

    @Test
    @DisplayName("Negative type should return white color as default")
    void testNegativeType() {
        Paint color = BrickColorMapper.getColor(-1);
        assertEquals(Color.WHITE, color);
    }

    @Test
    @DisplayName("All valid brick types should return non-null colors")
    void testAllValidTypesReturnNonNull() {
        for (int i = 0; i <= 7; i++) {
            Paint color = BrickColorMapper.getColor(i);
            assertNotNull(color, "Color for type " + i + " should not be null");
        }
    }
}

