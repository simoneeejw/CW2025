package com.tetris.game;

import java.awt.*;

// Holds the state of the game board, including the matrix and current brick offset
public class BoardState {
    private int[][] currentGameMatrix;
    private Point currentOffset;

    public BoardState(int width, int height) {
        this.currentGameMatrix = new int[width][height];
        this.currentOffset = new Point(0, 0);
    }

    public int[][] getCurrentGameMatrix() {
        return currentGameMatrix;
    }

    public void setCurrentGameMatrix(int[][] matrix) {
        this.currentGameMatrix = matrix;
    }

    public Point getCurrentOffset() {
        return currentOffset;
    }

    public void setCurrentOffset(Point offset) {
        this.currentOffset = offset;
    }

    public void resetMatrix(int width, int height) {
        this.currentGameMatrix = new int[width][height];
    }

    public void reset(int width, int height) {
        this.currentGameMatrix = new int[width][height];
        this.currentOffset = new Point(0, 0);
    }
}
