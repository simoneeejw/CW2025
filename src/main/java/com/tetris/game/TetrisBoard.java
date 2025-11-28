package com.tetris.game;

import com.tetris.model.ClearRow;
import com.tetris.util.matrix.MatrixOperations;
import com.tetris.model.NextShapeInfo;
import com.tetris.model.ViewData;

import java.awt.*;

public class TetrisBoard implements Board {

    private final int width;
    private final int height;
    private final BoardState boardState;
    private final BrickManager brickManager;
    private final Score score;
    private final LevelManager levelManager;
    private final PowerUpManager powerUpManager;

    public TetrisBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.boardState = new BoardState(width, height);
        this.brickManager = new BrickManager();
        this.score = new Score();
        this.levelManager = new LevelManager();
        this.powerUpManager = new PowerUpManager();
    }

    @Override
    public boolean moveBrickDown() {
        return tryMove(0, 1);
    }

    @Override
    public boolean moveBrickLeft() {
        return tryMove(-1, 0);
    }

    @Override
    public boolean moveBrickRight() {
        return tryMove(1, 0);
    }

    private boolean tryMove(int dx, int dy) {
        Point p = new Point(boardState.getCurrentOffset());
        p.translate(dx, dy);
        int[][] currentMatrix = MatrixOperations.copy(boardState.getCurrentGameMatrix());
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickManager.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (!conflict) {
            boardState.setCurrentOffset(p);
        }
        return !conflict;
    }

    @Override
    public boolean rotateLeftBrick() {
        return brickManager.rotateLeftBrick(boardState.getCurrentGameMatrix(), boardState.getCurrentOffset());
    }

    @Override
    public boolean createNewBrick() {
        return brickManager.createNewBrick(boardState.getCurrentGameMatrix(), boardState.getCurrentOffset());
    }

    @Override
    public int[][] getBoardMatrix() {
        return boardState.getCurrentGameMatrix();
    }

    @Override
    public ViewData getViewData() {
        int[][] heldData = brickManager.getHeldBrick() != null ? brickManager.getHeldBrick().getShapeMatrix().get(0) : null;
        return new ViewData(brickManager.getCurrentShape(), (int) boardState.getCurrentOffset().getX(), (int) boardState.getCurrentOffset().getY(), brickManager.getNextBrick().getShapeMatrix().get(0), heldData);
    }

    @Override
    public void mergeBrickToBackground() {
        boardState.setCurrentGameMatrix(MatrixOperations.merge(boardState.getCurrentGameMatrix(), brickManager.getCurrentShape(), (int) boardState.getCurrentOffset().getX(), (int) boardState.getCurrentOffset().getY()));
    }

    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(boardState.getCurrentGameMatrix());
        boardState.setCurrentGameMatrix(clearRow.getNewMatrix());

        // Trigger power-up if 4 lines cleared (Tetris)
        if (clearRow.getLinesRemoved() == 4) {
            PowerUp powerUp = powerUpManager.triggerRandomPowerUp();
            // Handle CLEAR_BOTTOM power-up immediately
            if (powerUp == PowerUp.CLEAR_BOTTOM) {
                clearBottomRow();
            }
        }

        return clearRow;
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public void holdBrick() {
        brickManager.holdBrick(boardState.getCurrentGameMatrix(), boardState.getCurrentOffset());
    }

    @Override
    public void newGame() {
        boardState.reset(width, height);
        score.reset();
        brickManager.resetHeldBrick();
        levelManager.reset();
        powerUpManager.reset();
        createNewBrick();
    }

    /**
     * Clears the bottom row of the board (power-up effect).
     */
    private void clearBottomRow() {
        int[][] matrix = boardState.getCurrentGameMatrix();
        int bottomRow = matrix.length - 1;

        // Clear the bottom row
        for (int j = 0; j < matrix[bottomRow].length; j++) {
            matrix[bottomRow][j] = 0;
        }

        boardState.setCurrentGameMatrix(matrix);
    }

    /**
     * Gets the level manager.
     * @return LevelManager instance
     */
    public LevelManager getLevelManager() {
        return levelManager;
    }

    /**
     * Gets the power-up manager.
     * @return PowerUpManager instance
     */
    public PowerUpManager getPowerUpManager() {
        return powerUpManager;
    }
}
