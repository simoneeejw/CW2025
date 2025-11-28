package com.tetris.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LevelManagerTest {

    private LevelManager levelManager;

    @BeforeEach
    void setUp() {
        levelManager = new LevelManager();
    }

    @Test
    void testInitialLevel() {
        assertEquals(1, levelManager.getCurrentLevel());
        assertEquals("Level 1", levelManager.getLevelName());
        assertEquals("Beginner", levelManager.getLevelDifficulty());
    }

    @Test
    void testLevelAdvancementToLevel2() {
        // Level 1 -> Level 2 at 1000 points
        assertFalse(levelManager.updateLevel(999));
        assertEquals(1, levelManager.getCurrentLevel());

        assertTrue(levelManager.updateLevel(1000));
        assertEquals(2, levelManager.getCurrentLevel());
        assertEquals("Level 2", levelManager.getLevelName());
        assertEquals("Intermediate", levelManager.getLevelDifficulty());
    }

    @Test
    void testLevelAdvancementToLevel3() {
        // Level 1 -> Level 2 at 1000 points
        levelManager.updateLevel(1000);
        assertEquals(2, levelManager.getCurrentLevel());

        // Level 2 -> Level 3 at 3000 points
        assertFalse(levelManager.updateLevel(2999));
        assertEquals(2, levelManager.getCurrentLevel());

        assertTrue(levelManager.updateLevel(3000));
        assertEquals(3, levelManager.getCurrentLevel());
        assertEquals("Level 3", levelManager.getLevelName());
        assertEquals("Expert", levelManager.getLevelDifficulty());
    }

    @Test
    void testMaxLevelReached() {
        // Advance to max level
        levelManager.updateLevel(1000);
        levelManager.updateLevel(3000);
        assertEquals(3, levelManager.getCurrentLevel());

        // Should not advance beyond level 3
        assertFalse(levelManager.updateLevel(10000));
        assertEquals(3, levelManager.getCurrentLevel());
    }

    @Test
    void testFallSpeedDecreases() {
        long level1Speed = levelManager.getFallSpeed();
        assertEquals(1000, level1Speed);

        levelManager.updateLevel(1000);
        long level2Speed = levelManager.getFallSpeed();
        assertEquals(700, level2Speed);
        assertTrue(level2Speed < level1Speed);

        levelManager.updateLevel(3000);
        long level3Speed = levelManager.getFallSpeed();
        assertEquals(400, level3Speed);
        assertTrue(level3Speed < level2Speed);
    }

    @Test
    void testScoreMultiplierIncreases() {
        assertEquals(1, levelManager.getScoreMultiplier());

        levelManager.updateLevel(1000);
        assertEquals(2, levelManager.getScoreMultiplier());

        levelManager.updateLevel(3000);
        assertEquals(3, levelManager.getScoreMultiplier());
    }

    @Test
    void testScoreNeededForNextLevel() {
        assertEquals(500, levelManager.getScoreNeededForNextLevel(500));
        assertEquals(0, levelManager.getScoreNeededForNextLevel(1000));

        levelManager.updateLevel(1000);
        assertEquals(1500, levelManager.getScoreNeededForNextLevel(1500));
        assertEquals(0, levelManager.getScoreNeededForNextLevel(3000));

        levelManager.updateLevel(3000);
        assertEquals(0, levelManager.getScoreNeededForNextLevel(10000));
    }

    @Test
    void testNextLevelThreshold() {
        assertEquals(1000, levelManager.getNextLevelThreshold());

        levelManager.updateLevel(1000);
        assertEquals(3000, levelManager.getNextLevelThreshold());

        levelManager.updateLevel(3000);
        assertEquals(0, levelManager.getNextLevelThreshold());
    }

    @Test
    void testReset() {
        // Advance to level 2, then level 3
        levelManager.updateLevel(1000);
        levelManager.updateLevel(3000);
        assertEquals(3, levelManager.getCurrentLevel());

        levelManager.reset();
        assertEquals(1, levelManager.getCurrentLevel());
        assertEquals(1000, levelManager.getFallSpeed());
        assertEquals(1, levelManager.getScoreMultiplier());
    }

    @Test
    void testLevelPropertyBinding() {
        assertNotNull(levelManager.currentLevelProperty());
        assertEquals(1, levelManager.currentLevelProperty().get());

        levelManager.updateLevel(1000);
        assertEquals(2, levelManager.currentLevelProperty().get());
    }
}

