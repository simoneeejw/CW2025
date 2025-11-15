package com.comp2042.tetris.model;

import com.comp2042.tetris.util.matrix.MatrixOperations;

// Info about next brick rotation state
public final class NextShapeInfo {

    private final int[][] shape;
    private final int position;


    public NextShapeInfo(final int[][] shape, final int position) {
        this.shape = shape;
        this.position = position;
    }

    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

    public int getPosition() {
        return position;
    }
}

