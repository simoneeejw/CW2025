package com.tetris.game;

import com.tetris.model.ClearRow;
import com.tetris.util.matrix.MatrixOperations;
import com.tetris.model.NextShapeInfo;
import com.tetris.model.ViewData;

import java.awt.*;

/**
 * Main implementation of the Tetris game board, managing the game state, brick movements, scoring, and special features like ghost rows.
 */
public class TetrisBoard implements Board {

    /** The width of the game board. */
    private final int width;
    /** The height of the game board. */
    private final int height;
    /** The current state of the board. */
    private final BoardState boardState;
    /** Manager for brick operations. */
    private final BrickManager brickManager;
    /** The current score. */
    private final Score score;
    /** Manager for game levels. */
    private final LevelManager levelManager;
    /** Track which row is a ghost row (-1 = none). */
    private int ghostRow = -1;

    /**
     * Constructs a new TetrisBoard with the specified dimensions.
     * @param width the width of the board
     * @param height the height of the board
     */
    public TetrisBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.boardState = new BoardState(width, height);
        this.brickManager = new BrickManager();
        this.score = new Score();
        this.levelManager = new LevelManager();
        this.ghostRow = -1;
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

    /**
     * Attempts to move the current brick by the given delta.
     * @param dx delta x
     * @param dy delta y
     * @return true if the move was successful, false if blocked
     */
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

        return new ClearRow(clearRow.getLinesRemoved(), clearRow.getNewMatrix(), clearRow.getScoreBonus());
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
    public int getGhostYPosition() {
        Point currentPos = new Point(boardState.getCurrentOffset());
        int[][] currentMatrix = MatrixOperations.copy(boardState.getCurrentGameMatrix());
        int[][] currentShape = brickManager.getCurrentShape();

        // Keep moving down until we hit a collision
        int ghostY = (int) currentPos.getY();
        while (!MatrixOperations.intersect(currentMatrix, currentShape, (int) currentPos.getX(), ghostY + 1)) {
            ghostY++;
        }

        return ghostY;
    }

    @Override
    public void newGame() {
        boardState.reset(width, height);
        score.reset();
        brickManager.resetHeldBrick();
        levelManager.reset();
        createNewBrick();
    }

    /**
     * Gets the level manager.
     * @return LevelManager instance
     */
    public LevelManager getLevelManager() {
        return levelManager;
    }

    /**
     * Adds a ghost row at a random position (for Level 4).
     * Ghost rows are filled and will be removed after 3 seconds.
     */
    public void addGhostRow() {
        if (ghostRow != -1) {
            return; // Already have a ghost row
        }

        int[][] matrix = boardState.getCurrentGameMatrix();
        java.util.Random random = new java.util.Random();

        // Find a random empty or partially filled row (not top 2 rows, not bottom row)
        int rowIndex = random.nextInt(matrix.length - 4) + 2;

        // Fill the row with a special color (8 for ghost)
        for (int j = 0; j < matrix[rowIndex].length; j++) {
            matrix[rowIndex][j] = 8; // Special ghost color
        }

        ghostRow = rowIndex;
        boardState.setCurrentGameMatrix(matrix);
    }

    /**
     * Removes the current ghost row.
     */
    public void removeGhostRow() {
        if (ghostRow == -1) {
            return; // No ghost row to remove
        }

        int[][] matrix = boardState.getCurrentGameMatrix();

        // Clear the ghost row
        for (int j = 0; j < matrix[ghostRow].length; j++) {
            matrix[ghostRow][j] = 0;
        }

        ghostRow = -1;
        boardState.setCurrentGameMatrix(matrix);
    }

    /**
     * Checks if there is currently a ghost row.
     * @return true if ghost row exists
     */
    public boolean hasGhostRow() {
        return ghostRow != -1;
    }
}
