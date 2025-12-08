package com.tetris.util;

import com.tetris.model.NextShapeInfo;
import com.tetris.logic.bricks.Brick;

/**
 * Handles rotation of Tetris bricks.
 */
public class BrickRotator {

    /** The current brick being rotated. */
    private Brick brick;
    /** The index of the current shape in the brick's rotation list. */
    private int currentShape = 0;

    /**
     * Gets the next shape info for rotation preview.
     * @return the next shape info
     */
    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    /**
     * Gets the current shape matrix.
     * @return the current shape
     */
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    /**
     * Sets the current shape index.
     * @param currentShape the shape index
     */
    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    /**
     * Sets the brick to rotate.
     * @param brick the brick
     */
    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }

    /**
     * Gets the current brick.
     * @return the brick
     */
    public Brick getBrick() {
        return brick;
    }
}
