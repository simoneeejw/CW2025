package com.tetris.logic.bricks;

import com.tetris.util.matrix.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the L-shaped Tetris brick, implementing the L piece with four rotation states.
 */
final class LBrick implements Brick {

    /** List containing the 2D matrices for each rotation of the L brick. */
    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Constructs an LBrick by initializing its rotation matrices.
     */
    public LBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 3, 3, 3},
                {0, 3, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 3, 3, 0},
                {0, 0, 3, 0},
                {0, 0, 3, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 3, 0},
                {3, 3, 3, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 3, 0, 0},
                {0, 3, 0, 0},
                {0, 3, 3, 0},
                {0, 0, 0, 0}
        });
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
