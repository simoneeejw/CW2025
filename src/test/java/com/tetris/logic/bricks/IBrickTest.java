package com.tetris.logic.bricks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IBrickTest {

    @Test
    void testGetShapeMatrix() {
        IBrick brick = new IBrick();
        var shapes = brick.getShapeMatrix();
        assertNotNull(shapes);
        assertTrue(shapes.size() > 0);
        for (int[][] shape : shapes) {
            assertNotNull(shape);
        }
    }

    @Test
    void testShapeMatrixHasTwoRotations() {
        IBrick brick = new IBrick();
        var shapes = brick.getShapeMatrix();
        assertEquals(2, shapes.size()); // I-brick has 2 rotation states
    }

    @Test
    void testShapeMatrixDimensions() {
        IBrick brick = new IBrick();
        var shapes = brick.getShapeMatrix();
        for (int[][] shape : shapes) {
            assertTrue(shape.length > 0);
            assertTrue(shape[0].length > 0);
        }
    }
}
