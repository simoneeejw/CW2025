package com.tetris.game;

import com.tetris.model.ClearRow;
import com.tetris.model.DownData;
import com.tetris.model.EventSource;
import com.tetris.model.MoveEvent;
import com.tetris.model.ViewData;
import com.tetris.util.GameConstants;
import com.tetris.util.SoundManager;

public class GameController {

    private final Board board = new TetrisBoard(GameConstants.BOARD_HEIGHT, GameConstants.BOARD_WIDTH);

    private final GameEventListener guiListener;

    public GameController(GameEventListener guiListener) {
        this.guiListener = guiListener;
        board.createNewBrick();
        guiListener.initGameView(board.getBoardMatrix(), board.getViewData());
        guiListener.bindScore(board.getScore().scoreProperty());

        // Bind level to GUI
        TetrisBoard tetrisBoard = (TetrisBoard) board;
        guiListener.bindLevel(tetrisBoard.getLevelManager().currentLevelProperty());

        // Set initial game speed
        guiListener.updateGameSpeed(tetrisBoard.getLevelManager().getFallSpeed(), 1.0);

        // Initialize status display
        guiListener.updateStatus(
            tetrisBoard.getLevelManager().getCurrentLevel(),
            tetrisBoard.getLevelManager().getLevelDifficulty(),
            board.getScore().scoreProperty().get(),
            tetrisBoard.getLevelManager().getTotalLinesCleared()
        );
    }

    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            board.mergeBrickToBackground();

            // Play piece drop sound when piece locks
            SoundManager.getInstance().playPieceDropSound();

            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                // Play line clear sound
                if (clearRow.getLinesRemoved() == 4) {
                    SoundManager.getInstance().playTetrisSound();
                } else {
                    SoundManager.getInstance().playLineClearSound();
                }

                // Apply level and power-up multipliers to score
                TetrisBoard tetrisBoard = (TetrisBoard) board;
                int baseScore = clearRow.getScoreBonus();
                int levelMultiplier = tetrisBoard.getLevelManager().getScoreMultiplier();
                int powerUpMultiplier = tetrisBoard.getPowerUpManager().getScoreMultiplier();
                int finalScore = baseScore * levelMultiplier * powerUpMultiplier;

                board.getScore().add(finalScore);

                // Track lines cleared and check for level progression
                boolean leveledUp = tetrisBoard.getLevelManager().addLinesCleared(clearRow.getLinesRemoved());
                if (leveledUp) {
                    SoundManager.getInstance().playLevelUpSound();
                    guiListener.updateGameSpeed(tetrisBoard.getLevelManager().getFallSpeed(),
                                                     tetrisBoard.getPowerUpManager().getSpeedMultiplier());
                    guiListener.showLevelUp(tetrisBoard.getLevelManager().getCurrentLevel(),
                                                 tetrisBoard.getLevelManager().getLevelDifficulty());

                    // Add ghost row on Level 4 (randomly, 30% chance)
                    int currentLevel = tetrisBoard.getLevelManager().getCurrentLevel();
                    if (currentLevel >= 4) {
                        java.util.Random random = new java.util.Random();
                        double ghostChance = 0.3 + (currentLevel - 4) * 0.1; // 30% at level 4, +10% per level
                        if (random.nextDouble() < ghostChance) {
                            tetrisBoard.addGhostRow();
                            guiListener.scheduleGhostRowRemoval(3000); // Remove after 3 seconds
                        }
                    }
                }

                // Show Tetris notification for 4-line clear
                if (clearRow.getLinesRemoved() == 4) {
                    guiListener.showTetrisNotification();
                }

                // Show power-up notification if one was triggered
                if (clearRow.getPowerUp() != null) {
                    guiListener.showPowerUpNotification(clearRow.getPowerUp().getName());
                }

                // Update status display
                guiListener.updateStatus(tetrisBoard.getLevelManager().getCurrentLevel(),
                                              tetrisBoard.getLevelManager().getLevelDifficulty(),
                                              board.getScore().scoreProperty().get(),
                                              tetrisBoard.getLevelManager().getTotalLinesCleared());
            }
            if (!board.createNewBrick()) {
                SoundManager.getInstance().playGameOverSound();
                guiListener.gameOver();
            }

            guiListener.refreshGameBackground(board.getBoardMatrix());

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

    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }

    public ViewData onHoldEvent(MoveEvent event) {
        board.holdBrick();
        guiListener.refreshGameBackground(board.getBoardMatrix());
        return board.getViewData();
    }

    public void createNewGame() {
        board.newGame();
        guiListener.refreshGameBackground(board.getBoardMatrix());

        // Reset game speed to level 1
        TetrisBoard tetrisBoard = (TetrisBoard) board;
        guiListener.updateGameSpeed(tetrisBoard.getLevelManager().getFallSpeed(), 1.0);

        // Reset status display
        guiListener.updateStatus(
            tetrisBoard.getLevelManager().getCurrentLevel(),
            tetrisBoard.getLevelManager().getLevelDifficulty(),
            board.getScore().scoreProperty().get(),
            tetrisBoard.getLevelManager().getTotalLinesCleared()
        );
    }

    /**
     * Gets the board instance (for ghost row removal).
     * @return Board instance
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the Y position where the current piece would land (for ghost piece).
     * @return Ghost Y position
     */
    public int getGhostYPosition() {
        return board.getGhostYPosition();
    }
}
