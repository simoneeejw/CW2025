package com.tetris.game;

import com.tetris.model.ClearRow;
import com.tetris.model.ViewData;

// Interface for Tetris game board operations
public interface Board {

    /**
     * Attempts to move the current brick down by one row.
     * @return true if the move was successful, false if blocked.
     */
    boolean moveBrickDown();

    /**
     * Attempts to move the current brick left by one column.
     * @return true if the move was successful, false if blocked.
     */
    boolean moveBrickLeft();

    /**
     * Attempts to move the current brick right by one column.
     * @return true if the move was successful, false if blocked.
     */
    boolean moveBrickRight();

    /**
     * Attempts to rotate the current brick counterclockwise.
     * @return true if the rotation was successful, false if blocked.
     */
    boolean rotateLeftBrick();

    /**
     * Creates a new random brick at the top of the board.
     * @return true if the brick was created successfully, false if game over.
     */
    boolean createNewBrick();

    /**
     * Returns the current state of the game board as a 2D integer array.
     * @return the board matrix where each cell represents a block type.
     */
    int[][] getBoardMatrix();

    /**
     * Returns the view data for rendering the game state.
     * @return ViewData object containing display information.
     */
    ViewData getViewData();

    /**
     * Merges the current brick into the background board.
     */
    void mergeBrickToBackground();

    /**
     * Clears any full rows and updates the score accordingly.
     * @return ClearRow object with details about cleared rows.
     */
    ClearRow clearRows();

    /**
     * Returns the current score of the game.
     * @return the Score object.
     */
    Score getScore();

    /**
     * Resets the board to start a new game.
     */
    void newGame();

    /**
     * Holds the current brick for later use, swapping with the held brick if any.
     */
    void holdBrick();

    /**
     * Calculates and returns the Y position where the current brick would land if dropped instantly.
     * @return the ghost Y position.
     */
    int getGhostYPosition();
}
