package com.tetris.util;

/**
 * Game configuration constants.
 */
public final class GameConstants {

    /** Private constructor to prevent instantiation. */
    private GameConstants() {}

    /** The width of the game board in cells. */
    public static final int BOARD_WIDTH = 10;
    /** The height of the game board in cells. */
    public static final int BOARD_HEIGHT = 25;
    /** The number of visible rows on the board. */
    public static final int VISIBLE_ROWS = 23;
    /** The size of each brick in pixels. */
    public static final int BRICK_SIZE_PIXELS = 20;
    /** The maximum size of a brick matrix. */
    public static final int MAX_BRICK_SIZE = 4;
    /** The default fall speed in milliseconds. */
    public static final int DEFAULT_FALL_SPEED_MS = 400;
    /** Score awarded per manual drop. */
    public static final int SCORE_PER_DROP = 1;
    /** Multiplier for score bonus per cleared line. */
    public static final int SCORE_BONUS_MULTIPLIER = 50;
    /** The X spawn position for new bricks. */
    public static final int SPAWN_X = 4;
    /** The Y spawn position for new bricks. */
    public static final int SPAWN_Y = 1;
    /** Y offset for the brick panel. */
    public static final int BRICK_PANEL_Y_OFFSET = -42;
}
