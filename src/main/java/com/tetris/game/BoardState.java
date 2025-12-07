package com.tetris.game;

import java.awt.*;

/**
 * Holds the state of the game board, including the matrix and current brick offset.
 */
public class BoardState {
    /** The current game board matrix. */
    private int[][] currentGameMatrix;
    /** The current offset position of the falling brick. */
    private Point currentOffset;

    /**
     * Constructs a BoardState with an empty matrix of given dimensions.
     * @param width the width of the board
     * @param height the height of the board
     */
    public BoardState(int width, int height) {
        this.currentGameMatrix = new int[width][height];
        this.currentOffset = new Point(0, 0);
    }

    /**
     * Gets the current game matrix.
     * @return the game matrix
     */
    public int[][] getCurrentGameMatrix() {
        return currentGameMatrix;
    }

    /**
     * Sets the current game matrix.
     * @param matrix the new game matrix
     */
    public void setCurrentGameMatrix(int[][] matrix) {
        this.currentGameMatrix = matrix;
    }

    /**
     * Gets the current brick offset.
     * @return the offset point
     */
    public Point getCurrentOffset() {
        return currentOffset;
    }

    /**
     * Sets the current brick offset.
     * @param offset the new offset point
     */
    public void setCurrentOffset(Point offset) {
        this.currentOffset = offset;
    }

    /**
     * Resets the matrix to an empty state with given dimensions.
     * @param width the width
     * @param height the height
     */
    public void resetMatrix(int width, int height) {
        this.currentGameMatrix = new int[width][height];
    }

    /**
     * Resets both matrix and offset to initial state.
     * @param width the width
     * @param height the height
     */
    public void reset(int width, int height) {
        this.currentGameMatrix = new int[width][height];
        this.currentOffset = new Point(0, 0);
    }
}
