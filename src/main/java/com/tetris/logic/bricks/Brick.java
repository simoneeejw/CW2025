package com.tetris.logic.bricks;

import java.util.List;

/**
 * Interface representing a Tetris brick with its shape matrices for different rotations.
 */
public interface Brick {

    /**
     * Returns a list of 2D integer arrays representing the brick's shape in each rotation state.
     * @return list of shape matrices
     */
    List<int[][]> getShapeMatrix();
}
