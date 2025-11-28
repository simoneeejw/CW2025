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
        // Level 1 -> Level 2 at 5 lines
        assertFalse(levelManager.addLinesCleared(4));
        assertEquals(1, levelManager.getCurrentLevel());
        assertEquals(4, levelManager.getTotalLinesCleared());

        assertTrue(levelManager.addLinesCleared(1));
        assertEquals(2, levelManager.getCurrentLevel());
        assertEquals(5, levelManager.getTotalLinesCleared());
        assertEquals("Level 2", levelManager.getLevelName());
        assertEquals("Intermediate", levelManager.getLevelDifficulty());
    }

    @Test
    void testLevelAdvancementToLevel3() {
        // Level 1 -> Level 2 at 5 lines
        levelManager.addLinesCleared(5);
        assertEquals(2, levelManager.getCurrentLevel());

        // Level 2 -> Level 3 at 10 lines total
        assertFalse(levelManager.addLinesCleared(4));
        assertEquals(2, levelManager.getCurrentLevel());
        assertEquals(9, levelManager.getTotalLinesCleared());

        assertTrue(levelManager.addLinesCleared(1));
        assertEquals(3, levelManager.getCurrentLevel());
        assertEquals(10, levelManager.getTotalLinesCleared());
        assertEquals("Level 3", levelManager.getLevelName());
        assertEquals("Advanced", levelManager.getLevelDifficulty());
    }

    @Test
    void testLevelAdvancementToLevel4() {
        // Advance to Level 4 at 20 lines
        levelManager.addLinesCleared(19);
        assertEquals(3, levelManager.getCurrentLevel());

        assertTrue(levelManager.addLinesCleared(1));
        assertEquals(4, levelManager.getCurrentLevel());
        assertEquals(20, levelManager.getTotalLinesCleared());
    }

    @Test
    void testMaxLevelReached() {
        // Advance to max level
        levelManager.addLinesCleared(20);
        assertEquals(4, levelManager.getCurrentLevel());

        // Should not advance beyond level 4
        assertFalse(levelManager.addLinesCleared(100));
        assertEquals(4, levelManager.getCurrentLevel());
        assertEquals(120, levelManager.getTotalLinesCleared());
    }

    @Test
    void testFallSpeedDecreases() {
        long level1Speed = levelManager.getFallSpeed();
        assertEquals(500, level1Speed);

        levelManager.addLinesCleared(5);
        long level2Speed = levelManager.getFallSpeed();
        assertEquals(400, level2Speed);
        assertTrue(level2Speed < level1Speed);

        levelManager.addLinesCleared(5);
        long level3Speed = levelManager.getFallSpeed();
        assertEquals(300, level3Speed);
        assertTrue(level3Speed < level2Speed);

        levelManager.addLinesCleared(10);
        long level4Speed = levelManager.getFallSpeed();
        assertEquals(200, level4Speed);
        assertTrue(level4Speed < level3Speed);
    }

    @Test
    void testScoreMultiplierIncreases() {
        assertEquals(1, levelManager.getScoreMultiplier());

        levelManager.addLinesCleared(5);
        assertEquals(2, levelManager.getScoreMultiplier());

        levelManager.addLinesCleared(5);
        assertEquals(3, levelManager.getScoreMultiplier());

        levelManager.addLinesCleared(10);
        assertEquals(4, levelManager.getScoreMultiplier());
    }

    @Test
    void testLinesNeededForNextLevel() {
        assertEquals(5, levelManager.getLinesNeededForNextLevel());

        levelManager.addLinesCleared(3);
        assertEquals(2, levelManager.getLinesNeededForNextLevel());

        levelManager.addLinesCleared(2);
        assertEquals(5, levelManager.getLinesNeededForNextLevel());

        levelManager.addLinesCleared(5);
        assertEquals(10, levelManager.getLinesNeededForNextLevel());

        levelManager.addLinesCleared(10);
        assertEquals(0, levelManager.getLinesNeededForNextLevel());
    }

    @Test
    void testNextLevelThreshold() {
        assertEquals(5, levelManager.getNextLevelThreshold());

        levelManager.addLinesCleared(5);
        assertEquals(10, levelManager.getNextLevelThreshold());

        levelManager.addLinesCleared(5);
        assertEquals(20, levelManager.getNextLevelThreshold());

        levelManager.addLinesCleared(10);
        assertEquals(0, levelManager.getNextLevelThreshold());
    }

    @Test
    void testReset() {
        // Advance to level 4
        levelManager.addLinesCleared(20);
        assertEquals(4, levelManager.getCurrentLevel());
        assertEquals(20, levelManager.getTotalLinesCleared());

        levelManager.reset();
        assertEquals(1, levelManager.getCurrentLevel());
        assertEquals(0, levelManager.getTotalLinesCleared());
        assertEquals(500, levelManager.getFallSpeed());
        assertEquals(1, levelManager.getScoreMultiplier());
    }

    @Test
    void testLevelPropertyBinding() {
        assertNotNull(levelManager.currentLevelProperty());
        assertEquals(1, levelManager.currentLevelProperty().get());

        levelManager.addLinesCleared(5);
        assertEquals(2, levelManager.currentLevelProperty().get());
    }

    @Test
    void testMultipleLineClears() {
        // Test clearing 4 lines at once (Tetris) - doesn't level up yet
        assertFalse(levelManager.addLinesCleared(4));
        assertEquals(1, levelManager.getCurrentLevel());
        assertEquals(4, levelManager.getTotalLinesCleared());

        // Clear 1 more to reach level 2
        assertTrue(levelManager.addLinesCleared(1));
        assertEquals(2, levelManager.getCurrentLevel());
        assertEquals(5, levelManager.getTotalLinesCleared());
    }
}

