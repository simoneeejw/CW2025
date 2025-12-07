package com.tetris.logic.bricks;

import com.tetris.util.matrix.MatrixOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Z-shaped Tetris brick, implementing the Z piece with two rotation states.
 */
final class ZBrick implements Brick {

    /** List containing the 2D matrices for each rotation of the Z brick. */
    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Constructs a ZBrick by initializing its rotation matrices.
     */
    public ZBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {7, 7, 0, 0},
                {0, 7, 7, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 7, 0, 0},
                {7, 7, 0, 0},
                {7, 0, 0, 0},
                {0, 0, 0, 0}
        });
    }

    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
