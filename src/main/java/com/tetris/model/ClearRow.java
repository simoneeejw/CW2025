package com.tetris.model;

import com.tetris.util.matrix.MatrixOperations;

/**
 * Result of clearing completed rows.
 */
public final class ClearRow {

    /** Number of lines removed. */
    private final int linesRemoved;
    /** The new board matrix after clearing. */
    private final int[][] newMatrix;
    /** Score bonus for the cleared lines. */
    private final int scoreBonus;

    /**
     * Constructs ClearRow.
     * @param linesRemoved number of lines removed
     * @param newMatrix the new matrix
     * @param scoreBonus the score bonus
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
    }

    /**
     * Gets the number of lines removed.
     * @return lines removed
     */
    public int getLinesRemoved() {
        return linesRemoved;
    }

    /**
     * Gets a copy of the new matrix.
     * @return the new matrix
     */
    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    /**
     * Gets the score bonus.
     * @return score bonus
     */
    public int getScoreBonus() {
        return scoreBonus;
    }
}
