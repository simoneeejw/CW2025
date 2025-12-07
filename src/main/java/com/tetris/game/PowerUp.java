package com.tetris.game;

/**
 * Enumeration of available power-ups in the game.
 */
public enum PowerUp {
    SLOW_MOTION("Slow Motion", "Slows down falling speed for 5 seconds"),
    CLEAR_BOTTOM("Clear Bottom", "Clears the bottom row instantly"),
    DOUBLE_POINTS("Double Points", "Doubles score for 10 seconds"),
    GHOST_PIECE("Ghost Piece", "Shows where piece will land");

    /** Display name of the power-up. */
    private final String name;
    /** Description of the power-up effect. */
    private final String description;

    /**
     * Constructs a PowerUp with name and description.
     * @param name the display name
     * @param description the effect description
     */
    PowerUp(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Gets the display name of the power-up.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the power-up effect.
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}
