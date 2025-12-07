package com.tetris.model;

import com.tetris.game.PowerUp;
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
    /** Power-up triggered, if any. */
    private final PowerUp powerUp;

    /**
     * Constructs ClearRow without power-up.
     * @param linesRemoved number of lines removed
     * @param newMatrix the new matrix
     * @param scoreBonus the score bonus
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {
        this(linesRemoved, newMatrix, scoreBonus, null);
    }

    /**
     * Constructs ClearRow with optional power-up.
     * @param linesRemoved number of lines removed
     * @param newMatrix the new matrix
     * @param scoreBonus the score bonus
     * @param powerUp the triggered power-up
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus, PowerUp powerUp) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
        this.powerUp = powerUp;
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

    /**
     * Gets the triggered power-up.
     * @return the power-up or null
     */
    public PowerUp getPowerUp() {
        return powerUp;
    }
}
