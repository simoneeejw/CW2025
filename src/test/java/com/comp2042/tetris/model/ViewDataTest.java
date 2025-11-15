package com.comp2042.tetris.model;

import com.comp2042.tetris.util.matrix.MatrixOperations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ViewData model.
 * Tests immutability and data encapsulation of view data.
 */
class ViewDataTest {

    @Test
    @DisplayName("ViewData should store and retrieve brick data correctly")
    void testGetBrickData() {
        int[][] brickData = {{1, 0}, {1, 1}};
        int[][] nextBrick = {{2, 2}};
        ViewData viewData = new ViewData(brickData, 5, 10, nextBrick, null);

        int[][] retrieved = viewData.getBrickData();
        assertArrayEquals(brickData[0], retrieved[0]);
        assertArrayEquals(brickData[1], retrieved[1]);
    }

    @Test
    @DisplayName("ViewData should return deep copy of brick data")
    void testBrickDataImmutability() {
        int[][] brickData = {{1, 0}, {1, 1}};
        int[][] nextBrick = {{2, 2}};
        ViewData viewData = new ViewData(brickData, 5, 10, nextBrick, null);

        int[][] retrieved = viewData.getBrickData();
        retrieved[0][0] = 999; // Modify copy

        // Original should be unchanged
        int[][] retrieved2 = viewData.getBrickData();
        assertEquals(1, retrieved2[0][0]);
    }

    @Test
    @DisplayName("ViewData should store position correctly")
    void testGetPosition() {
        int[][] brickData = {{1}};
        int[][] nextBrick = {{2}};
        ViewData viewData = new ViewData(brickData, 7, 15, nextBrick, null);

        assertEquals(7, viewData.getxPosition());
        assertEquals(15, viewData.getyPosition());
    }

    @Test
    @DisplayName("ViewData should return deep copy of next brick data")
    void testNextBrickDataImmutability() {
        int[][] brickData = {{1}};
        int[][] nextBrick = {{2, 2}};
        ViewData viewData = new ViewData(brickData, 0, 0, nextBrick, null);

        int[][] retrieved = viewData.getNextBrickData();
        retrieved[0][0] = 999;

        int[][] retrieved2 = viewData.getNextBrickData();
        assertEquals(2, retrieved2[0][0]);
    }

    @Test
    @DisplayName("ViewData should handle null held brick data")
    void testNullHeldBrickData() {
        int[][] brickData = {{1}};
        int[][] nextBrick = {{2}};
        ViewData viewData = new ViewData(brickData, 0, 0, nextBrick, null);

        assertNull(viewData.getHeldBrickData());
    }

    @Test
    @DisplayName("ViewData should return deep copy of held brick data")
    void testHeldBrickDataImmutability() {
        int[][] brickData = {{1}};
        int[][] nextBrick = {{2}};
        int[][] heldBrick = {{3, 3}};
        ViewData viewData = new ViewData(brickData, 0, 0, nextBrick, heldBrick);

        int[][] retrieved = viewData.getHeldBrickData();
        assertNotNull(retrieved);
        retrieved[0][0] = 999;

        int[][] retrieved2 = viewData.getHeldBrickData();
        assertEquals(3, retrieved2[0][0]);
    }
}

