package com.tetris.model;

import com.tetris.util.matrix.MatrixOperations;

/**
 * View data for rendering current brick and preview info.
 */
public final class ViewData {

    /** The current brick's shape data. */
    private final int[][] brickData;
    /** The X position of the current brick. */
    private final int xPosition;
    /** The Y position of the current brick. */
    private final int yPosition;
    /** The next brick's shape data. */
    private final int[][] nextBrickData;
    /** The held brick's shape data, or null if none. */
    private final int[][] heldBrickData;

    /**
     * Constructs ViewData with brick positions and preview data.
     * @param brickData the current brick data
     * @param xPosition the X position
     * @param yPosition the Y position
     * @param nextBrickData the next brick data
     * @param heldBrickData the held brick data
     */
    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData, int[][] heldBrickData) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
        this.heldBrickData = heldBrickData;
    }

    /**
     * Gets a copy of the current brick data.
     * @return the brick data
     */
    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    /**
     * Gets the X position of the current brick.
     * @return the X position
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * Gets the Y position of the current brick.
     * @return the Y position
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * Gets a copy of the next brick data.
     * @return the next brick data
     */
    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }

    /**
     * Gets a copy of the held brick data, or null if none.
     * @return the held brick data
     */
    public int[][] getHeldBrickData() {
        return heldBrickData != null ? MatrixOperations.copy(heldBrickData) : null;
    }
}
