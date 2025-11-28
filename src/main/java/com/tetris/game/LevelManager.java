package com.tetris.game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Manages game levels and difficulty progression.
 * Implements three difficulty levels: Easy, Medium, and Hard.
 */
public class LevelManager {

    private final IntegerProperty currentLevel = new SimpleIntegerProperty(1);
    private int linesCleared = 0;

    // Lines needed to advance to next level
    private static final int[] LINES_TO_ADVANCE = {10, 25, 50};

    // Fall speed for each level (milliseconds)
    private static final long[] FALL_SPEEDS = {1000, 700, 400};

    // Score multiplier for each level
    private static final int[] SCORE_MULTIPLIERS = {1, 2, 3};

    private static final int MAX_LEVEL = 3;

    public LevelManager() {
        this.currentLevel.set(1);
        this.linesCleared = 0;
    }

    /**
     * Increments the count of lines cleared.
     * @param lines Number of lines cleared
     */
    public void addLinesCleared(int lines) {
        linesCleared += lines;
        checkLevelAdvancement();
    }

    /**
     * Checks if the player should advance to the next level.
     */
    private void checkLevelAdvancement() {
        int level = currentLevel.get();
        if (level < MAX_LEVEL && linesCleared >= LINES_TO_ADVANCE[level - 1]) {
            advanceLevel();
        }
    }

    /**
     * Advances to the next level.
     */
    private void advanceLevel() {
        if (currentLevel.get() < MAX_LEVEL) {
            currentLevel.set(currentLevel.get() + 1);
            linesCleared = 0; // Reset for next level
        }
    }

    /**
     * Gets the current level.
     * @return Current level (1-3)
     */
    public int getCurrentLevel() {
        return currentLevel.get();
    }

    /**
     * Gets the level property for binding to UI.
     * @return Level property
     */
    public IntegerProperty currentLevelProperty() {
        return currentLevel;
    }

    /**
     * Gets the fall speed for the current level.
     * @return Fall speed in milliseconds
     */
    public long getFallSpeed() {
        return FALL_SPEEDS[currentLevel.get() - 1];
    }

    /**
     * Gets the score multiplier for the current level.
     * @return Score multiplier
     */
    public int getScoreMultiplier() {
        return SCORE_MULTIPLIERS[currentLevel.get() - 1];
    }

    /**
     * Gets the number of lines needed to advance to the next level.
     * @return Lines needed, or 0 if at max level
     */
    public int getLinesNeededForNextLevel() {
        if (currentLevel.get() >= MAX_LEVEL) {
            return 0;
        }
        return LINES_TO_ADVANCE[currentLevel.get() - 1] - linesCleared;
    }

    /**
     * Gets the total lines cleared in the current level.
     * @return Total lines cleared
     */
    public int getLinesCleared() {
        return linesCleared;
    }

    /**
     * Resets the level manager to initial state.
     */
    public void reset() {
        currentLevel.set(1);
        linesCleared = 0;
    }

    /**
     * Gets the level name based on the level number.
     * @return Level name
     */
    public String getLevelName() {
        switch (currentLevel.get()) {
            case 1: return "Easy";
            case 2: return "Medium";
            case 3: return "Hard";
            default: return "Unknown";
        }
    }
}

