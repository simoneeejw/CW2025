package com.tetris.game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Manages automatic level progression based on score.
 * Players start at Level 1 and automatically advance when reaching score thresholds.
 * Implements three difficulty levels with increasing speed and score multipliers.
 */
public class LevelManager {

    private final IntegerProperty currentLevel = new SimpleIntegerProperty(1);

    // Score thresholds to advance to next level
    private static final int[] SCORE_THRESHOLDS = {1000, 3000, 6000};

    // Fall speed for each level (milliseconds)
    private static final long[] FALL_SPEEDS = {1000, 700, 400};

    // Score multiplier for each level
    private static final int[] SCORE_MULTIPLIERS = {1, 2, 3};

    private static final int MAX_LEVEL = 3;

    public LevelManager() {
        this.currentLevel.set(1);
    }

    /**
     * Checks and updates level based on current score.
     * Automatically advances to next level when score threshold is reached.
     * @param currentScore The player's current score
     * @return true if level advanced, false otherwise
     */
    public boolean updateLevel(int currentScore) {
        int level = currentLevel.get();

        // Check if should advance to Level 2
        if (level == 1 && currentScore >= SCORE_THRESHOLDS[0]) {
            currentLevel.set(2);
            return true;
        }
        // Check if should advance to Level 3
        else if (level == 2 && currentScore >= SCORE_THRESHOLDS[1]) {
            currentLevel.set(3);
            return true;
        }

        return false;
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
     * Gets the score needed to advance to the next level.
     * @param currentScore The player's current score
     * @return Score needed for next level, or 0 if at max level
     */
    public int getScoreNeededForNextLevel(int currentScore) {
        int level = currentLevel.get();
        if (level >= MAX_LEVEL) {
            return 0;
        }
        return SCORE_THRESHOLDS[level - 1] - currentScore;
    }

    /**
     * Gets the score threshold for the next level.
     * @return Score threshold, or 0 if at max level
     */
    public int getNextLevelThreshold() {
        int level = currentLevel.get();
        if (level >= MAX_LEVEL) {
            return 0;
        }
        return SCORE_THRESHOLDS[level - 1];
    }

    /**
     * Resets the level manager to initial state (Level 1).
     */
    public void reset() {
        currentLevel.set(1);
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
            case 3: return "Expert";
            default: return "Unknown";
        }
    }
}

