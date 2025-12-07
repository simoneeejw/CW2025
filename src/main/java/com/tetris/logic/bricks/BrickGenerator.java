package com.tetris.logic.bricks;

/**
 * Strategy interface for generating Tetris bricks.
 */
public interface BrickGenerator {

    /**
     * Retrieves and removes the next brick from the queue.
     * @return the next Brick
     */
    Brick getBrick();

    /**
     * Previews the next brick without removing it from the queue.
     * @return the next Brick
     */
    Brick getNextBrick();
}
