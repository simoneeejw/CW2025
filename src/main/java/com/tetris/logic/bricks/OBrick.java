package com.tetris.logic.bricks;

import com.tetris.util.matrix.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the O-shaped Tetris brick, implementing the square piece with one rotation state.
 */
final class OBrick implements Brick {

    /** List containing the 2D matrices for each rotation of the O brick. */
    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Constructs an OBrick by initializing its rotation matrix.
     */
    public OBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 4, 4, 0},
                {0, 4, 4, 0},
                {0, 0, 0, 0}
        });
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }

}
