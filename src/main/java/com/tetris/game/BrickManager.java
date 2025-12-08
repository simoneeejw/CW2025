package com.tetris.game;

import com.tetris.logic.bricks.Brick;
import com.tetris.logic.bricks.BrickGenerator;
import com.tetris.logic.bricks.RandomBrickGenerator;
import com.tetris.util.BrickRotator;
import com.tetris.util.GameConstants;
import com.tetris.util.matrix.MatrixOperations;
import com.tetris.model.NextShapeInfo;

import java.awt.*;

/**
 * Manages brick generation, rotation, and holding functionality for the Tetris game.
 */
public class BrickManager {
    /** Generator for creating random bricks. */
    private final BrickGenerator brickGenerator;
    /** Rotator for handling brick rotations. */
    private final BrickRotator brickRotator;
    /** Currently held brick for hold functionality. */
    private Brick heldBrick = null;

    /**
     * Constructs a BrickManager with a random brick generator and rotator.
     */
    public BrickManager() {
        this.brickGenerator = new RandomBrickGenerator();
        this.brickRotator = new BrickRotator();
    }

    /**
     * Creates a new brick and positions it at the spawn point.
     * @param currentGameMatrix the current game board matrix
     * @param currentOffset the current brick offset
     * @return true if the brick was placed successfully, false if collision
     */
    public boolean createNewBrick(int[][] currentGameMatrix, Point currentOffset) {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        currentOffset.setLocation(GameConstants.SPAWN_X, GameConstants.SPAWN_Y);
        return !MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    /**
     * Gets the current shape matrix of the brick.
     * @return the current shape matrix
     */
    public int[][] getCurrentShape() {
        return brickRotator.getCurrentShape();
    }

    /**
     * Sets the current shape position.
     * @param position the rotation position
     */
    public void setCurrentShape(int position) {
        brickRotator.setCurrentShape(position);
    }

    /**
     * Gets the current brick.
     * @return the current brick
     */
    public Brick getBrick() {
        return brickRotator.getBrick();
    }

    /**
     * Sets the current brick.
     * @param brick the brick to set
     */
    public void setBrick(Brick brick) {
        brickRotator.setBrick(brick);
    }

    /**
     * Gets the next shape information for rotation preview.
     * @return next shape info
     */
    public NextShapeInfo getNextShape() {
        return brickRotator.getNextShape();
    }

    /**
     * Attempts to rotate the brick left.
     * @param currentGameMatrix the current game board matrix
     * @param currentOffset the current brick offset
     * @return true if rotation successful, false if blocked
     */
    public boolean rotateLeftBrick(int[][] currentGameMatrix, Point currentOffset) {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        NextShapeInfo nextShape = brickRotator.getNextShape();

        // Try rotation at current position
        if (!MatrixOperations.intersect(currentMatrix, nextShape.getShape(), (int) currentOffset.getX(), (int) currentOffset.getY())) {
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }

        // Try wall kicks: shift left by 1
        if (!MatrixOperations.intersect(currentMatrix, nextShape.getShape(), (int) currentOffset.getX() - 1, (int) currentOffset.getY())) {
            currentOffset.x -= 1;
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }

        // Try wall kicks: shift right by 1
        if (!MatrixOperations.intersect(currentMatrix, nextShape.getShape(), (int) currentOffset.getX() + 1, (int) currentOffset.getY())) {
            currentOffset.x += 1;
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }

        // Try wall kicks: shift left by 2 (for larger pieces)
        if (!MatrixOperations.intersect(currentMatrix, nextShape.getShape(), (int) currentOffset.getX() - 2, (int) currentOffset.getY())) {
            currentOffset.x -= 2;
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }

        // Try wall kicks: shift right by 2
        if (!MatrixOperations.intersect(currentMatrix, nextShape.getShape(), (int) currentOffset.getX() + 2, (int) currentOffset.getY())) {
            currentOffset.x += 2;
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }

        return false; // Rotation blocked
    }

    /**
     * Holds the current brick, swapping with the held brick if any.
     * @param currentGameMatrix the current game board matrix
     * @param currentOffset the current brick offset
     */
    public void holdBrick(int[][] currentGameMatrix, Point currentOffset) {
        Brick currentBrick = brickRotator.getBrick();
        if (heldBrick == null) {
            heldBrick = currentBrick;
            createNewBrick(currentGameMatrix, currentOffset);
        } else {
            Brick temp = heldBrick;
            heldBrick = currentBrick;
            brickRotator.setBrick(temp);
            currentOffset.setLocation(GameConstants.SPAWN_X, GameConstants.SPAWN_Y);
        }
    }

    /**
     * Gets the currently held brick.
     * @return the held brick or null
     */
    public Brick getHeldBrick() {
        return heldBrick;
    }

    /**
     * Gets the next brick from the generator.
     * @return the next brick
     */
    public Brick getNextBrick() {
        return brickGenerator.getNextBrick();
    }

    /**
     * Resets the held brick to null.
     */
    public void resetHeldBrick() {
        heldBrick = null;
    }
}
