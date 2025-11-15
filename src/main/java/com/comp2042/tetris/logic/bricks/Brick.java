package com.comp2042.tetris.logic.bricks;

import java.util.List;

// Represents a Tetris brick with rotation states
public interface Brick {

    // Returns list of rotation state matrices
    List<int[][]> getShapeMatrix();
}
