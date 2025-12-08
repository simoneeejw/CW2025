package com.tetris.util.matrix;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MatrixOperationsTest {

    @Test
    void testCopy() {
        int[][] original = {{1, 2}, {3, 4}};
        int[][] copy = MatrixOperations.copy(original);
        assertArrayEquals(original, copy);
        assertNotSame(original, copy); // Ensure it's a deep copy
    }

    @Test
    void testIntersect() {
        int[][] board = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        int[][] brick = {{1, 1}, {1, 1}};
        boolean intersects = MatrixOperations.intersect(board, brick, 0, 0);
        assertFalse(intersects); // No overlap

        board[0][0] = 1;
        intersects = MatrixOperations.intersect(board, brick, 0, 0);
        assertTrue(intersects); // Overlap
    }

    @Test
    void testMerge() {
        int[][] board = {{0, 0}, {0, 0}};
        int[][] brick = {{1, 1}, {1, 1}};
        int[][] merged = MatrixOperations.merge(board, brick, 0, 0);
        int[][] expected = {{1, 1}, {1, 1}};
        assertArrayEquals(expected, merged);
    }

    @Test
    void testCheckRemoving() {
        int[][] board = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 1, 1}, // Full row
            {0, 0, 0, 0}
        };
        com.tetris.model.ClearRow clearRow = MatrixOperations.checkRemoving(board);
        assertEquals(1, clearRow.getLinesRemoved());
        // After clearing, the full row should be removed
        int[][] newMatrix = clearRow.getNewMatrix();
        assertEquals(0, newMatrix[2][0]); // The row should be cleared
    }
}
