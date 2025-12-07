package com.tetris.logic.bricks;

import com.tetris.util.matrix.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the S-shaped Tetris brick, implementing the S piece with two rotation states.
 */
final class SBrick implements Brick {

    /** List containing the 2D matrices for each rotation of the S brick. */
    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Constructs an SBrick by initializing its rotation matrices.
     */
    public SBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 5, 5, 0},
                {5, 5, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {5, 0, 0, 0},
                {5, 5, 0, 0},
                {0, 5, 0, 0},
                {0, 0, 0, 0}
        });
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
