package com.tetris.util;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Maps brick type numbers to display colors.
 */
public class BrickColorMapper {

    /** Private constructor to prevent instantiation. */
    private BrickColorMapper() {}

    /**
     * Returns color for brick type (0-7).
     * @param brickType the brick type number
     * @return the corresponding Paint color
     */
    public static Paint getColor(int brickType) {
        return switch (brickType) {
            case 0 -> Color.TRANSPARENT;  // Empty cell
            case 1 -> Color.AQUA;         // I-brick
            case 2 -> Color.BLUEVIOLET;   // J-brick
            case 3 -> Color.DARKGREEN;    // L-brick
            case 4 -> Color.YELLOW;       // O-brick
            case 5 -> Color.RED;          // S-brick
            case 6 -> Color.BEIGE;        // T-brick
            case 7 -> Color.BURLYWOOD;    // Z-brick
            default -> Color.WHITE;       // Default/unknown
        };
    }
}
