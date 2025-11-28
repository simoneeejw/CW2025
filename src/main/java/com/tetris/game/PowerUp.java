package com.tetris.game;

/**
 * Enumeration of available power-ups in the game.
 */
public enum PowerUp {
    SLOW_MOTION("Slow Motion", "Slows down falling speed for 5 seconds"),
    CLEAR_BOTTOM("Clear Bottom", "Clears the bottom row instantly"),
    DOUBLE_POINTS("Double Points", "Doubles score for 10 seconds"),
    GHOST_PIECE("Ghost Piece", "Shows where piece will land");

    private final String name;
    private final String description;

    PowerUp(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

