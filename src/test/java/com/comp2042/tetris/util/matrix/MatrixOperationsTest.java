package com.comp2042.tetris.util.matrix;

import com.comp2042.tetris.model.ClearRow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MatrixOperations utility.
 * Tests matrix manipulation methods including collision detection, merging, and row clearing.
 */
class MatrixOperationsTest {

    @Test
    @DisplayName("Copy should create a deep copy of the matrix")
    void testCopy() {
        int[][] original = {
            {1, 2, 3},
            {4, 5, 6}
        };

        int[][] copy = MatrixOperations.copy(original);

        // Verify same values
        assertArrayEquals(original[0], copy[0]);
        assertArrayEquals(original[1], copy[1]);

        // Verify it's a deep copy (changing copy doesn't affect original)
        copy[0][0] = 999;
        assertEquals(1, original[0][0]);
        assertEquals(999, copy[0][0]);
    }

    @Test
    @DisplayName("Intersect should detect collision with filled cells")
    void testIntersectWithFilledCell() {
        int[][] matrix = new int[5][5];
        matrix[2][2] = 1; // Filled cell

        int[][] brick = {
            {1, 0},
            {0, 0}
        };

        // Brick at position (2,2) should collide
        assertTrue(MatrixOperations.intersect(matrix, brick, 2, 2));
    }

    @Test
    @DisplayName("Intersect should not detect collision with empty cells")
    void testIntersectWithEmptyCell() {
        int[][] matrix = new int[5][5]; // All zeros

        int[][] brick = {
            {1, 0},
            {0, 0}
        };

        // No collision in empty space
        assertFalse(MatrixOperations.intersect(matrix, brick, 2, 2));
    }

    @Test
    @DisplayName("Intersect should detect out of bounds on left edge")
    void testIntersectOutOfBoundsLeft() {
        int[][] matrix = new int[5][5];
        int[][] brick = {{1}};

        assertTrue(MatrixOperations.intersect(matrix, brick, -1, 0));
    }

    @Test
    @DisplayName("Intersect should detect out of bounds on right edge")
    void testIntersectOutOfBoundsRight() {
        int[][] matrix = new int[5][5];
        int[][] brick = {{1}};

        assertTrue(MatrixOperations.intersect(matrix, brick, 5, 0));
    }

    @Test
    @DisplayName("Intersect should detect out of bounds on bottom")
    void testIntersectOutOfBoundsBottom() {
        int[][] matrix = new int[5][5];
        int[][] brick = {{1}};

        assertTrue(MatrixOperations.intersect(matrix, brick, 0, 5));
    }

    @Test
    @DisplayName("Merge should correctly combine brick with matrix")
    void testMerge() {
        int[][] matrix = new int[5][5];
        int[][] brick = {
            {1, 1},
            {0, 1}
        };

        int[][] result = MatrixOperations.merge(matrix, brick, 1, 1);

        assertEquals(1, result[1][1]);
        assertEquals(1, result[1][2]);
        assertEquals(1, result[2][2]);
        assertEquals(0, result[2][1]);
    }

    @Test
    @DisplayName("Merge should not modify original matrix")
    void testMergeDoesNotModifyOriginal() {
        int[][] matrix = new int[5][5];
        int[][] brick = {{1}};

        MatrixOperations.merge(matrix, brick, 2, 2);

        assertEquals(0, matrix[2][2]); // Original unchanged
    }

    @Test
    @DisplayName("CheckRemoving should clear one complete row")
    void testCheckRemovingSingleRow() {
        int[][] matrix = new int[5][5];
        // Fill bottom row completely
        for (int i = 0; i < 5; i++) {
            matrix[4][i] = 1;
        }

        ClearRow result = MatrixOperations.checkRemoving(matrix);

        assertEquals(1, result.getLinesRemoved());
        assertEquals(50, result.getScoreBonus()); // 50 * 1 * 1

        // Verify bottom row is now empty
        int[][] newMatrix = result.getNewMatrix();
        for (int i = 0; i < 5; i++) {
            assertEquals(0, newMatrix[4][i]);
        }
    }

    @Test
    @DisplayName("CheckRemoving should clear multiple complete rows")
    void testCheckRemovingMultipleRows() {
        int[][] matrix = new int[5][5];
        // Fill bottom two rows
        for (int i = 0; i < 5; i++) {
            matrix[3][i] = 1;
            matrix[4][i] = 1;
        }

        ClearRow result = MatrixOperations.checkRemoving(matrix);

        assertEquals(2, result.getLinesRemoved());
        assertEquals(200, result.getScoreBonus()); // 50 * 2 * 2
    }

    @Test
    @DisplayName("CheckRemoving should not clear incomplete rows")
    void testCheckRemovingIncompleteRow() {
        int[][] matrix = new int[5][5];
        // Fill row except last cell
        for (int i = 0; i < 4; i++) {
            matrix[4][i] = 1;
        }

        ClearRow result = MatrixOperations.checkRemoving(matrix);

        assertEquals(0, result.getLinesRemoved());
        assertEquals(0, result.getScoreBonus());
    }

    @Test
    @DisplayName("CheckRemoving should move rows down after clearing")
    void testCheckRemovingMovesRowsDown() {
        int[][] matrix = new int[5][5];
        // Put a brick in row 2
        matrix[2][2] = 1;
        // Fill bottom row (row 4)
        for (int i = 0; i < 5; i++) {
            matrix[4][i] = 1;
        }

        ClearRow result = MatrixOperations.checkRemoving(matrix);
        int[][] newMatrix = result.getNewMatrix();

        // The brick from row 2 should move down to row 3
        assertEquals(1, newMatrix[3][2]);
        assertEquals(0, newMatrix[2][2]);
    }

    @Test
    @DisplayName("DeepCopyList should create copies of all matrices in list")
    void testDeepCopyList() {
        int[][] matrix1 = {{1, 2}, {3, 4}};
        int[][] matrix2 = {{5, 6}, {7, 8}};
        var list = java.util.List.of(matrix1, matrix2);

        var copiedList = MatrixOperations.deepCopyList(list);

        assertEquals(2, copiedList.size());
        assertArrayEquals(matrix1[0], copiedList.get(0)[0]);

        // Verify deep copy
        copiedList.get(0)[0][0] = 999;
        assertEquals(1, matrix1[0][0]);
    }
}

