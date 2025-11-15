package com.comp2042.tetris.game;

import com.comp2042.tetris.model.ClearRow;
import com.comp2042.tetris.model.ViewData;

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
}
