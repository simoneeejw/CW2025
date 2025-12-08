package com.tetris.game;

import com.tetris.model.ViewData;

/**
 * Listener for player input events during gameplay.
 */
public interface GameEventListener {

    /**
     * Initializes the game view with the board matrix and view data.
     * @param boardMatrix the initial board matrix
     * @param viewData the initial view data
     */
    void initGameView(int[][] boardMatrix, ViewData viewData);

    /**
     * Binds the score property to the GUI.
     * @param scoreProperty the score property
     */
    void bindScore(javafx.beans.property.IntegerProperty scoreProperty);

    /**
     * Binds the level property to the GUI.
     * @param levelProperty the level property
     */
    void bindLevel(javafx.beans.property.IntegerProperty levelProperty);

    /**
     * Updates the game speed with fall speed and multiplier.
     * @param fallSpeed the fall speed in milliseconds
     * @param speedMultiplier the speed multiplier
     */
    void updateGameSpeed(long fallSpeed, double speedMultiplier);

    /**
     * Updates the status display with current game info.
     * @param level the current level
     * @param levelName the level name
     * @param score the current score
     * @param lines the total lines cleared
     */
    void updateStatus(int level, String levelName, int score, int lines);

    /**
     * Refreshes the game background with the updated board matrix.
     * @param boardMatrix the updated board matrix
     */
    void refreshGameBackground(int[][] boardMatrix);

    /**
     * Shows a level up notification.
     * @param level the new level
     * @param levelName the level name
     */
    void showLevelUp(int level, String levelName);

    /**
     * Schedules removal of the ghost row after a delay.
     * @param delayMs the delay in milliseconds
     */
    void scheduleGhostRowRemoval(int delayMs);

    /**
     * Shows a Tetris notification for clearing 4 lines.
     */
    void showTetrisNotification();

    /**
     * Handles game over event.
     */
    void gameOver();
}
