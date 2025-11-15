package com.comp2042.tetris.logic.bricks;

// Strategy interface for brick generation
public interface BrickGenerator {

    // Get and consume next brick
    Brick getBrick();

    // Preview next brick without consuming
    Brick getNextBrick();
}
