package com.tetris.game;

import com.tetris.model.ClearRow;
import com.tetris.model.DownData;
import com.tetris.model.EventSource;
import com.tetris.model.MoveEvent;
import com.tetris.model.ViewData;
import com.tetris.gui.GuiController;
import com.tetris.util.GameConstants;

public class GameController implements GameEventListener {

    private final Board board = new TetrisBoard(GameConstants.BOARD_HEIGHT, GameConstants.BOARD_WIDTH);

    private final GuiController viewGuiController;

    public GameController(GuiController c) {
        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());

        // Bind level to GUI
        TetrisBoard tetrisBoard = (TetrisBoard) board;
        viewGuiController.bindLevel(tetrisBoard.getLevelManager().currentLevelProperty());

        // Set initial game speed
        viewGuiController.updateGameSpeed(tetrisBoard.getLevelManager().getFallSpeed(), 1.0);
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                // Apply level and power-up multipliers to score
                TetrisBoard tetrisBoard = (TetrisBoard) board;
                int baseScore = clearRow.getScoreBonus();
                int levelMultiplier = tetrisBoard.getLevelManager().getScoreMultiplier();
                int powerUpMultiplier = tetrisBoard.getPowerUpManager().getScoreMultiplier();
                int finalScore = baseScore * levelMultiplier * powerUpMultiplier;

                board.getScore().add(finalScore);

                // Check for level progression
                boolean leveledUp = tetrisBoard.getLevelManager().updateLevel(board.getScore().scoreProperty().get());
                if (leveledUp) {
                    viewGuiController.updateGameSpeed(tetrisBoard.getLevelManager().getFallSpeed(),
                                                     tetrisBoard.getPowerUpManager().getSpeedMultiplier());
                    viewGuiController.showLevelUp(tetrisBoard.getLevelManager().getCurrentLevel(),
                                                 tetrisBoard.getLevelManager().getLevelDifficulty());
                }

                // Show Tetris notification for 4-line clear
                if (clearRow.getLinesRemoved() == 4) {
                    viewGuiController.showTetrisNotification();
                }

                // Show power-up notification if one was triggered
                if (clearRow.getPowerUp() != null) {
                    viewGuiController.showPowerUpNotification(clearRow.getPowerUp().getName());
                }

                // Update status display
                viewGuiController.updateStatus(tetrisBoard.getLevelManager().getCurrentLevel(),
                                              tetrisBoard.getLevelManager().getLevelDifficulty(),
                                              board.getScore().scoreProperty().get());
            }
            if (!board.createNewBrick()) {
                viewGuiController.gameOver();
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix());

        } else {
            if (event.getEventSource() == EventSource.USER) {
                TetrisBoard tetrisBoard = (TetrisBoard) board;
                int dropScore = GameConstants.SCORE_PER_DROP;
                int levelMultiplier = tetrisBoard.getLevelManager().getScoreMultiplier();
                int powerUpMultiplier = tetrisBoard.getPowerUpManager().getScoreMultiplier();
                board.getScore().add(dropScore * levelMultiplier * powerUpMultiplier);
            }
        }

        // Update power-up timers
        ((TetrisBoard) board).getPowerUpManager().update();

        return new DownData(clearRow, board.getViewData());
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }

    @Override
    public ViewData onHoldEvent(MoveEvent event) {
        board.holdBrick();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
        return board.getViewData();
    }

    @Override
    public void createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());

        // Reset game speed to level 1
        TetrisBoard tetrisBoard = (TetrisBoard) board;
        viewGuiController.updateGameSpeed(tetrisBoard.getLevelManager().getFallSpeed(), 1.0);
    }
}
