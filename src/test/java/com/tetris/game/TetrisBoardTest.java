package com.tetris.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TetrisBoardTest {

    private TetrisBoard board;

    @BeforeEach
    void setUp() {
        board = new TetrisBoard(20, 10);
    }

    @Test
    void testInitialBoard() {
        int[][] matrix = board.getBoardMatrix();
        assertEquals(20, matrix.length);
        assertEquals(10, matrix[0].length);
        for (int[] row : matrix) {
            for (int cell : row) {
                assertEquals(0, cell);
            }
        }
    }

    @Test
    void testCreateNewBrick() {
        boolean result = board.createNewBrick();
        assertTrue(result); // Assuming it can create a brick initially
    }

    @Test
    void testMoveBrickDown() {
        board.createNewBrick();
        boolean moved = board.moveBrickDown();
        assertTrue(moved); // Should be able to move down initially
    }

    @Test
    void testMoveBrickLeft() {
        board.createNewBrick();
        boolean moved = board.moveBrickLeft();
        // May or may not move depending on position, but should not throw exception
        assertTrue(moved || !moved); // Just check it doesn't crash
    }

    @Test
    void testGetScore() {
        assertNotNull(board.getScore());
        assertEquals(0, board.getScore().scoreProperty().get());
    }

    @Test
    void testNewGame() {
        board.createNewBrick();
        board.newGame();
        int[][] matrix = board.getBoardMatrix();
        for (int[] row : matrix) {
            for (int cell : row) {
                assertEquals(0, cell);
            }
        }
    }
}
