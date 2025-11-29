package com.tetris.game;

import com.tetris.logic.bricks.Brick;
import com.tetris.logic.bricks.BrickGenerator;
import com.tetris.logic.bricks.RandomBrickGenerator;
import com.tetris.util.BrickRotator;
import com.tetris.util.GameConstants;
import com.tetris.util.matrix.MatrixOperations;
import com.tetris.model.NextShapeInfo;

import java.awt.*;

// Manages brick generation, rotation, and holding
public class BrickManager {
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private Brick heldBrick = null;

    public BrickManager() {
        this.brickGenerator = new RandomBrickGenerator();
        this.brickRotator = new BrickRotator();
    }

    public boolean createNewBrick(int[][] currentGameMatrix, Point currentOffset) {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        currentOffset.setLocation(GameConstants.SPAWN_X, GameConstants.SPAWN_Y);
        return !MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    public int[][] getCurrentShape() {
        return brickRotator.getCurrentShape();
    }

    public void setCurrentShape(int position) {
        brickRotator.setCurrentShape(position);
    }

    public Brick getBrick() {
        return brickRotator.getBrick();
    }

    public void setBrick(Brick brick) {
        brickRotator.setBrick(brick);
    }

    public NextShapeInfo getNextShape() {
        return brickRotator.getNextShape();
    }

    public boolean rotateLeftBrick(int[][] currentGameMatrix, Point currentOffset) {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        NextShapeInfo nextShape = brickRotator.getNextShape();
        boolean conflict = MatrixOperations.intersect(currentMatrix, nextShape.getShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
        if (!conflict) {
            brickRotator.setCurrentShape(nextShape.getPosition());
        }
        return !conflict;
    }

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

    public Brick getHeldBrick() {
        return heldBrick;
    }

    public Brick getNextBrick() {
        return brickGenerator.getNextBrick();
    }

    public void resetHeldBrick() {
        heldBrick = null;
    }
}
