package com.tetris.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PowerUpManagerTest {

    private PowerUpManager powerUpManager;

    @BeforeEach
    void setUp() {
        powerUpManager = new PowerUpManager();
    }

    @Test
    void testInitialState() {
        assertNull(powerUpManager.getActivePowerUp());
        assertFalse(powerUpManager.isSlowMotionActive());
        assertFalse(powerUpManager.isDoublePointsActive());
        assertFalse(powerUpManager.isGhostPieceActive());
        assertEquals(1.0, powerUpManager.getSpeedMultiplier());
        assertEquals(1, powerUpManager.getScoreMultiplier());
    }

    @Test
    void testSlowMotionActivation() {
        powerUpManager.activatePowerUp(PowerUp.SLOW_MOTION);

        assertTrue(powerUpManager.isSlowMotionActive());
        assertEquals(PowerUp.SLOW_MOTION, powerUpManager.getActivePowerUp());
        assertEquals(0.5, powerUpManager.getSpeedMultiplier());
        assertEquals(1, powerUpManager.getScoreMultiplier());
        assertTrue(powerUpManager.getRemainingTime() > 0);
    }

    @Test
    void testDoublePointsActivation() {
        powerUpManager.activatePowerUp(PowerUp.DOUBLE_POINTS);

        assertTrue(powerUpManager.isDoublePointsActive());
        assertEquals(PowerUp.DOUBLE_POINTS, powerUpManager.getActivePowerUp());
        assertEquals(1.0, powerUpManager.getSpeedMultiplier());
        assertEquals(2, powerUpManager.getScoreMultiplier());
        assertTrue(powerUpManager.getRemainingTime() > 0);
    }

    @Test
    void testGhostPieceActivation() {
        powerUpManager.activatePowerUp(PowerUp.GHOST_PIECE);

        assertTrue(powerUpManager.isGhostPieceActive());
        assertEquals(PowerUp.GHOST_PIECE, powerUpManager.getActivePowerUp());
        // Ghost piece doesn't affect multipliers
        assertEquals(1.0, powerUpManager.getSpeedMultiplier());
        assertEquals(1, powerUpManager.getScoreMultiplier());
    }

    @Test
    void testClearBottomActivation() {
        powerUpManager.activatePowerUp(PowerUp.CLEAR_BOTTOM);

        assertEquals(PowerUp.CLEAR_BOTTOM, powerUpManager.getActivePowerUp());
        assertEquals(0, powerUpManager.getRemainingTime()); // Instant effect
    }

    @Test
    void testRandomPowerUpGeneration() {
        PowerUp powerUp = powerUpManager.triggerRandomPowerUp();

        assertNotNull(powerUp);
        assertEquals(powerUp, powerUpManager.getActivePowerUp());
        assertTrue(powerUp == PowerUp.SLOW_MOTION ||
                   powerUp == PowerUp.DOUBLE_POINTS ||
                   powerUp == PowerUp.GHOST_PIECE ||
                   powerUp == PowerUp.CLEAR_BOTTOM);
    }

    @Test
    void testSlowMotionExpires() throws InterruptedException {
        powerUpManager.activatePowerUp(PowerUp.SLOW_MOTION);
        assertTrue(powerUpManager.isSlowMotionActive());

        // Simulate time passing (5 seconds + buffer)
        Thread.sleep(5100);
        powerUpManager.update();

        assertFalse(powerUpManager.isSlowMotionActive());
        assertNull(powerUpManager.getActivePowerUp());
        assertEquals(1.0, powerUpManager.getSpeedMultiplier());
    }

    @Test
    void testDoublePointsExpires() throws InterruptedException {
        powerUpManager.activatePowerUp(PowerUp.DOUBLE_POINTS);
        assertTrue(powerUpManager.isDoublePointsActive());

        // Simulate time passing (10 seconds + buffer)
        Thread.sleep(10100);
        powerUpManager.update();

        assertFalse(powerUpManager.isDoublePointsActive());
        assertNull(powerUpManager.getActivePowerUp());
        assertEquals(1, powerUpManager.getScoreMultiplier());
    }

    @Test
    void testReset() {
        powerUpManager.activatePowerUp(PowerUp.DOUBLE_POINTS);
        assertTrue(powerUpManager.isDoublePointsActive());

        powerUpManager.reset();

        assertNull(powerUpManager.getActivePowerUp());
        assertFalse(powerUpManager.isSlowMotionActive());
        assertFalse(powerUpManager.isDoublePointsActive());
        assertFalse(powerUpManager.isGhostPieceActive());
        assertEquals(1.0, powerUpManager.getSpeedMultiplier());
        assertEquals(1, powerUpManager.getScoreMultiplier());
        assertEquals(0, powerUpManager.getRemainingTime());
    }

    @Test
    void testPropertyBinding() {
        assertNotNull(powerUpManager.slowMotionActiveProperty());
        assertNotNull(powerUpManager.doublePointsActiveProperty());
        assertNotNull(powerUpManager.ghostPieceActiveProperty());

        powerUpManager.activatePowerUp(PowerUp.SLOW_MOTION);
        assertTrue(powerUpManager.slowMotionActiveProperty().get());

        powerUpManager.reset();
        powerUpManager.activatePowerUp(PowerUp.DOUBLE_POINTS);
        assertTrue(powerUpManager.doublePointsActiveProperty().get());
    }

    @Test
    void testPowerUpEnumProperties() {
        assertEquals("Slow Motion", PowerUp.SLOW_MOTION.getName());
        assertEquals("Clear Bottom", PowerUp.CLEAR_BOTTOM.getName());
        assertEquals("Double Points", PowerUp.DOUBLE_POINTS.getName());
        assertEquals("Ghost Piece", PowerUp.GHOST_PIECE.getName());

        assertNotNull(PowerUp.SLOW_MOTION.getDescription());
        assertNotNull(PowerUp.CLEAR_BOTTOM.getDescription());
        assertNotNull(PowerUp.DOUBLE_POINTS.getDescription());
        assertNotNull(PowerUp.GHOST_PIECE.getDescription());
    }
}

