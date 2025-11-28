package com.tetris.game;

import com.tetris.model.ClearRow;
import com.tetris.model.ViewData;

// Interface for Tetris game board operations
public interface Board {

    boolean moveBrickDown();
    boolean moveBrickLeft();
    boolean moveBrickRight();
    boolean rotateLeftBrick();
    boolean createNewBrick();
    int[][] getBoardMatrix();
    ViewData getViewData();
    void mergeBrickToBackground();
    ClearRow clearRows();
    Score getScore();
    void newGame();

    void holdBrick();

    int getGhostYPosition();
}
