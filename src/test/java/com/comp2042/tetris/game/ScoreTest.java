package com.comp2042.tetris.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Score functionality.
 * Tests score tracking, addition, and reset operations.
 */
class ScoreTest {

    private Score score;

    @BeforeEach
    void setUp() {
        score = new Score();
    }

    @Test
    @DisplayName("New score should start at zero")
    void testInitialScore() {
        assertEquals(0, score.scoreProperty().get());
    }

    @Test
    @DisplayName("Add should increase score by specified amount")
    void testAdd() {
        score.add(10);
        assertEquals(10, score.scoreProperty().get());

        score.add(5);
        assertEquals(15, score.scoreProperty().get());
    }

    @Test
    @DisplayName("Add should handle large numbers")
    void testAddLargeNumber() {
        score.add(1000);
        assertEquals(1000, score.scoreProperty().get());
    }

    @Test
    @DisplayName("Add should accumulate multiple additions")
    void testMultipleAdditions() {
        score.add(50);
        score.add(100);
        score.add(200);
        assertEquals(350, score.scoreProperty().get());
    }

    @Test
    @DisplayName("Reset should set score back to zero")
    void testReset() {
        score.add(500);
        assertEquals(500, score.scoreProperty().get());

        score.reset();
        assertEquals(0, score.scoreProperty().get());
    }

    @Test
    @DisplayName("Reset should work when score is already zero")
    void testResetAtZero() {
        score.reset();
        assertEquals(0, score.scoreProperty().get());
    }

    @Test
    @DisplayName("Score property should be observable")
    void testScorePropertyIsObservable() {
        assertNotNull(score.scoreProperty());

        final int[] observedValue = {0};
        score.scoreProperty().addListener((obs, oldVal, newVal) -> {
            observedValue[0] = newVal.intValue();
        });

        score.add(42);
        assertEquals(42, observedValue[0]);
    }
}

