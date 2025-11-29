package com.tetris.game;

import com.tetris.model.ViewData;

// Listener for player input events during gameplay
public interface GameEventListener {

    // GUI update methods
    void initGameView(int[][] boardMatrix, ViewData viewData);
    void bindScore(javafx.beans.property.IntegerProperty scoreProperty);
    void bindLevel(javafx.beans.property.IntegerProperty levelProperty);
    void updateGameSpeed(long fallSpeed, double speedMultiplier);
    void updateStatus(int level, String levelName, int score, int lines);
    void refreshGameBackground(int[][] boardMatrix);
    void showLevelUp(int level, String levelName);
    void scheduleGhostRowRemoval(int delayMs);
    void showTetrisNotification();
    void showPowerUpNotification(String powerUpName);
    void gameOver();
}
