package com.tetris.game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Manages automatic level progression based on lines cleared.
 * Players start at Level 1 and automatically advance when reaching line thresholds.
 * Implements four difficulty levels with increasing speed.
 */
public class LevelManager {

    private final IntegerProperty currentLevel = new SimpleIntegerProperty(1);
    private int totalLinesCleared = 0;

    // Line thresholds to advance to next level (Level 1: 0-4, Level 2: 5-9, Level 3: 10-19, Level 4: 20+)
    private static final int[] LINE_THRESHOLDS = {0, 5, 10, 20};

    // Fall speed for each level (milliseconds) - starts at 500ms, reduces by 100ms per level
    private static final long[] FALL_SPEEDS = {500, 400, 300, 200};

    // Score multiplier for each level
    private static final int[] SCORE_MULTIPLIERS = {1, 2, 3, 4};

    private static final int MAX_LEVEL = 4;

    public LevelManager() {
        this.currentLevel.set(1);
        this.totalLinesCleared = 0;
    }

    /**
     * Adds cleared lines and checks if level should advance.
     * @param linesCleared Number of lines cleared in this move
     * @return true if level advanced, false otherwise
     */
    public boolean addLinesCleared(int linesCleared) {
        if (linesCleared <= 0) {
            return false;
        }

        int previousLevel = currentLevel.get();
        totalLinesCleared += linesCleared;

        // Check current level based on total lines cleared
        int newLevel = calculateLevel();
        if (newLevel != previousLevel) {
            currentLevel.set(newLevel);
            return true;
        }

        return false;
    }

    /**
     * Calculates the current level based on total lines cleared.
     * @return Current level (1-4)
     */
    private int calculateLevel() {
        for (int i = MAX_LEVEL - 1; i >= 0; i--) {
            if (totalLinesCleared >= LINE_THRESHOLDS[i]) {
                return i + 1;
            }
        }
        return 1;
    }

    /**
     * Gets the total number of lines cleared.
     * @return Total lines cleared
     */
    public int getTotalLinesCleared() {
        return totalLinesCleared;
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
     * Gets the lines needed to advance to the next level.
     * @return Lines needed for next level, or 0 if at max level
     */
    public int getLinesNeededForNextLevel() {
        int level = currentLevel.get();
        if (level >= MAX_LEVEL) {
            return 0;
        }
        return LINE_THRESHOLDS[level] - totalLinesCleared;
    }

    /**
     * Gets the line threshold for the next level.
     * @return Line threshold, or 0 if at max level
     */
    public int getNextLevelThreshold() {
        int level = currentLevel.get();
        if (level >= MAX_LEVEL) {
            return 0;
        }
        return LINE_THRESHOLDS[level];
    }

    /**
     * Resets the level manager to initial state (Level 1).
     */
    public void reset() {
        currentLevel.set(1);
        totalLinesCleared = 0;
    }

    /**
     * Gets the level name based on the level number.
     * @return Level name
     */
    public String getLevelName() {
        switch (currentLevel.get()) {
            case 1: return "Level 1";
            case 2: return "Level 2";
            case 3: return "Level 3";
            case 4: return "Level 4";
            default: return "Unknown";
        }
    }

    /**
     * Gets a description of the current level difficulty.
     * @return Difficulty description
     */
    public String getLevelDifficulty() {
        switch (currentLevel.get()) {
            case 1: return "Beginner";
            case 2: return "Intermediate";
            case 3: return "Advanced";
            case 4: return "Expert";
            default: return "Unknown";
        }
    }
}

