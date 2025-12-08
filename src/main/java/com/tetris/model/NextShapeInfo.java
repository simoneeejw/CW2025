package com.tetris.model;

import com.tetris.util.matrix.MatrixOperations;

/**
 * Info about next brick rotation state.
 */
public final class NextShapeInfo {

    /** The shape matrix for the next rotation. */
    private final int[][] shape;
    /** The position index of the next rotation. */
    private final int position;

    /**
     * Constructs NextShapeInfo with shape and position.
     * @param shape the shape matrix
     * @param position the position index
     */
    public NextShapeInfo(final int[][] shape, final int position) {
        this.shape = shape;
        this.position = position;
    }

    /**
     * Gets a copy of the shape matrix.
     * @return the shape
     */
    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

    /**
     * Gets the position index.
     * @return the position
     */
    public int getPosition() {
        return position;
    }
}
