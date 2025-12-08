package com.tetris.game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents the player's score in the Tetris game, using JavaFX properties for binding.
 */
public final class Score {

    /** The current score value as a JavaFX property. */
    private final IntegerProperty score = new SimpleIntegerProperty(0);

    /**
     * Returns the score property for binding.
     * @return the score IntegerProperty
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Adds the specified amount to the score.
     * @param i the amount to add
     */
    public void add(int i){
        score.setValue(score.getValue() + i);
    }

    /**
     * Resets the score to zero.
     */
    public void reset() {
        score.setValue(0);
    }
}
