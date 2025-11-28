package com.tetris.game;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import java.util.Random;

/**
 * Manages power-ups in the game.
 * Power-ups are randomly triggered when clearing 4 lines simultaneously (Tetris).
 */
public class PowerUpManager {

    private final Random random = new Random();
    private PowerUp activePowerUp = null;
    private long powerUpEndTime = 0;

    private final BooleanProperty slowMotionActive = new SimpleBooleanProperty(false);
    private final BooleanProperty doublePointsActive = new SimpleBooleanProperty(false);
    private final BooleanProperty ghostPieceActive = new SimpleBooleanProperty(false);

    private static final long SLOW_MOTION_DURATION = 5000; // 5 seconds
    private static final long DOUBLE_POINTS_DURATION = 10000; // 10 seconds

    /**
     * Triggers a random power-up when player clears 4 lines.
     * @return The activated power-up
     */
    public PowerUp triggerRandomPowerUp() {
        PowerUp[] powerUps = PowerUp.values();
        PowerUp selected = powerUps[random.nextInt(powerUps.length)];
        activatePowerUp(selected);
        return selected;
    }

    /**
     * Activates a specific power-up.
     * @param powerUp The power-up to activate
     */
    public void activatePowerUp(PowerUp powerUp) {
        activePowerUp = powerUp;

        switch (powerUp) {
            case SLOW_MOTION:
                slowMotionActive.set(true);
                powerUpEndTime = System.currentTimeMillis() + SLOW_MOTION_DURATION;
                break;
            case DOUBLE_POINTS:
                doublePointsActive.set(true);
                powerUpEndTime = System.currentTimeMillis() + DOUBLE_POINTS_DURATION;
                break;
            case GHOST_PIECE:
                ghostPieceActive.set(true);
                // Ghost piece is permanent once activated
                powerUpEndTime = Long.MAX_VALUE;
                break;
            case CLEAR_BOTTOM:
                // Instant effect, no duration
                powerUpEndTime = 0;
                break;
        }
    }

    /**
     * Updates power-up timers. Should be called regularly.
     */
    public void update() {
        long currentTime = System.currentTimeMillis();

        if (slowMotionActive.get() && currentTime >= powerUpEndTime) {
            slowMotionActive.set(false);
            activePowerUp = null;
        }

        if (doublePointsActive.get() && currentTime >= powerUpEndTime) {
            doublePointsActive.set(false);
            activePowerUp = null;
        }
    }

    /**
     * Gets the speed multiplier based on active power-ups.
     * @return Speed multiplier (0.5 for slow motion, 1.0 otherwise)
     */
    public double getSpeedMultiplier() {
        return slowMotionActive.get() ? 0.5 : 1.0;
    }

    /**
     * Gets the score multiplier based on active power-ups.
     * @return Score multiplier (2 for double points, 1 otherwise)
     */
    public int getScoreMultiplier() {
        return doublePointsActive.get() ? 2 : 1;
    }

    /**
     * Checks if slow motion is currently active.
     * @return true if slow motion is active
     */
    public boolean isSlowMotionActive() {
        return slowMotionActive.get();
    }

    /**
     * Checks if double points is currently active.
     * @return true if double points is active
     */
    public boolean isDoublePointsActive() {
        return doublePointsActive.get();
    }

    /**
     * Checks if ghost piece is currently active.
     * @return true if ghost piece is active
     */
    public boolean isGhostPieceActive() {
        return ghostPieceActive.get();
    }

    /**
     * Gets the currently active power-up.
     * @return Active power-up or null if none
     */
    public PowerUp getActivePowerUp() {
        return activePowerUp;
    }

    /**
     * Gets the remaining time for the current power-up in milliseconds.
     * @return Remaining time or 0 if no active power-up
     */
    public long getRemainingTime() {
        if (activePowerUp == null || powerUpEndTime == 0) {
            return 0;
        }
        long remaining = powerUpEndTime - System.currentTimeMillis();
        return Math.max(0, remaining);
    }

    /**
     * Resets all power-ups.
     */
    public void reset() {
        activePowerUp = null;
        powerUpEndTime = 0;
        slowMotionActive.set(false);
        doublePointsActive.set(false);
        ghostPieceActive.set(false);
    }

    /**
     * Property for UI binding - slow motion state.
     */
    public BooleanProperty slowMotionActiveProperty() {
        return slowMotionActive;
    }

    /**
     * Property for UI binding - double points state.
     */
    public BooleanProperty doublePointsActiveProperty() {
        return doublePointsActive;
    }

    /**
     * Property for UI binding - ghost piece state.
     */
    public BooleanProperty ghostPieceActiveProperty() {
        return ghostPieceActive;
    }
}

