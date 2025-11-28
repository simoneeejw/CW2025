package com.tetris.model;

import com.tetris.game.PowerUp;
import com.tetris.util.matrix.MatrixOperations;

// Result of clearing completed rows
public final class ClearRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final int scoreBonus;
    private final PowerUp powerUp; // Power-up triggered (if any)

    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {
        this(linesRemoved, newMatrix, scoreBonus, null);
    }

    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus, PowerUp powerUp) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
        this.powerUp = powerUp;
    }

    public int getLinesRemoved() {
        return linesRemoved;
    }

    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    public int getScoreBonus() {
        return scoreBonus;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }
}

