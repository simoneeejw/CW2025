package com.tetris.gui;

import com.tetris.model.ViewData;
import com.tetris.game.GameEventListener;
import com.tetris.game.GameController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.binding.Bindings;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MultiplayerGuiController implements Initializable {

    // Player 1 UI
    @FXML private GridPane gamePanel1;
    @FXML private Label scoreLabel1;
    @FXML private Label linesLabel1;
    @FXML private Label levelLabel1;
    @FXML private Label levelNameLabel1;
    @FXML private GridPane nextBlockPanel1;
    @FXML private GridPane heldBlockPanel1;

    // Player 2 UI
    @FXML private GridPane gamePanel2;
    @FXML private Label scoreLabel2;
    @FXML private Label linesLabel2;
    @FXML private Label levelLabel2;
    @FXML private Label levelNameLabel2;
    @FXML private GridPane nextBlockPanel2;
    @FXML private GridPane heldBlockPanel2;

    // Shared UI
    @FXML private Label winnerLabel;

    private GameController player1Controller;
    private GameController player2Controller;

    // Properties for binding
    private final IntegerProperty scoreProp1 = new SimpleIntegerProperty(0);
    private final IntegerProperty linesProp1 = new SimpleIntegerProperty(0);
    private final IntegerProperty levelProp1 = new SimpleIntegerProperty(1);

    private final IntegerProperty scoreProp2 = new SimpleIntegerProperty(0);
    private final IntegerProperty linesProp2 = new SimpleIntegerProperty(0);
    private final IntegerProperty levelProp2 = new SimpleIntegerProperty(1);

    private GameOverDialog gameOverDialog;
    private Runnable onRestartCallback;
    private Runnable onMainMenuCallback;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Bind labels to properties
        scoreLabel1.textProperty().bind(Bindings.concat("Score: ", scoreProp1));
        linesLabel1.textProperty().bind(Bindings.concat("Lines: ", linesProp1));
        levelLabel1.textProperty().bind(Bindings.concat("Level: ", levelProp1));

        scoreLabel2.textProperty().bind(Bindings.concat("Score: ", scoreProp2));
        linesLabel2.textProperty().bind(Bindings.concat("Lines: ", linesProp2));
        levelLabel2.textProperty().bind(Bindings.concat("Level: ", levelProp2));

        // Show instructions for 2 seconds
        winnerLabel.setText("Player 1: A S D W Space Tab\nPlayer 2: Arrow Keys Enter Backspace");
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(2));
        pause.setOnFinished(e -> winnerLabel.setText(""));
        pause.play();

        // Initialize game over dialog
        gameOverDialog = new GameOverDialog();
    }

    public void initializeGame() {
        // Create two game controllers for multiplayer
        Player1Listener player1Listener = new Player1Listener();
        Player2Listener player2Listener = new Player2Listener();

        player1Controller = new GameController(player1Listener);
        player2Controller = new GameController(player2Listener);

        // Start the games
        player1Controller.createNewGame();
        player2Controller.createNewGame();

        // Set up key handling for multiplayer
        setupKeyHandling();
    }

    private void setupKeyHandling() {
        // Get the scene from one of the UI elements
        if (gamePanel1.getScene() != null) {
            gamePanel1.getScene().setOnKeyPressed(event -> {
                // Player 1 controls: A S D W Space Tab
                switch (event.getCode()) {
                    case A:
                        player1Controller.onLeftEvent(new com.tetris.model.MoveEvent(com.tetris.model.EventType.LEFT, com.tetris.model.EventSource.USER));
                        break;
                    case S:
                        player1Controller.onDownEvent(new com.tetris.model.MoveEvent(com.tetris.model.EventType.DOWN, com.tetris.model.EventSource.USER));
                        break;
                    case D:
                        player1Controller.onRightEvent(new com.tetris.model.MoveEvent(com.tetris.model.EventType.RIGHT, com.tetris.model.EventSource.USER));
                        break;
                    case W:
                        player1Controller.onRotateEvent(new com.tetris.model.MoveEvent(com.tetris.model.EventType.ROTATE, com.tetris.model.EventSource.USER));
                        break;
                    case SPACE:
                        // Hard drop for player 1
                        boolean keepDropping = true;
                        while (keepDropping) {
                            com.tetris.model.DownData downData = player1Controller.onDownEvent(new com.tetris.model.MoveEvent(com.tetris.model.EventType.DOWN, com.tetris.model.EventSource.USER));
                            if (downData.getClearRow() != null) {
                                keepDropping = false;
                            }
                        }
                        break;
                    case TAB:
                        player1Controller.onHoldEvent(new com.tetris.model.MoveEvent(com.tetris.model.EventType.HOLD, com.tetris.model.EventSource.USER));
                        break;

                    // Player 2 controls: LEFT RIGHT DOWN UP ENTER BACKSPACE
                    case LEFT:
                        player2Controller.onLeftEvent(new com.tetris.model.MoveEvent(com.tetris.model.EventType.LEFT, com.tetris.model.EventSource.USER));
                        break;
                    case RIGHT:
                        player2Controller.onRightEvent(new com.tetris.model.MoveEvent(com.tetris.model.EventType.RIGHT, com.tetris.model.EventSource.USER));
                        break;
                    case DOWN:
                        player2Controller.onDownEvent(new com.tetris.model.MoveEvent(com.tetris.model.EventType.DOWN, com.tetris.model.EventSource.USER));
                        break;
                    case UP:
                        player2Controller.onRotateEvent(new com.tetris.model.MoveEvent(com.tetris.model.EventType.ROTATE, com.tetris.model.EventSource.USER));
                        break;
                    case ENTER:
                        // Hard drop for player 2
                        keepDropping = true;
                        while (keepDropping) {
                            com.tetris.model.DownData downData = player2Controller.onDownEvent(new com.tetris.model.MoveEvent(com.tetris.model.EventType.DOWN, com.tetris.model.EventSource.USER));
                            if (downData.getClearRow() != null) {
                                keepDropping = false;
                            }
                        }
                        break;
                    case BACK_SPACE:
                        player2Controller.onHoldEvent(new com.tetris.model.MoveEvent(com.tetris.model.EventType.HOLD, com.tetris.model.EventSource.USER));
                        break;
                }
                event.consume();
            });
        }
    }

    // Inner classes to handle each player's game events
    private class Player1Listener implements GameEventListener {
        @Override
        public void initGameView(int[][] boardMatrix, ViewData viewData) {
            // Initialize player 1's game view
            // Similar to GuiController.initGameView
        }

        @Override
        public void bindScore(IntegerProperty scoreProperty) {
            scoreLabel1.textProperty().bind(scoreProperty.asString("Score: %d"));
        }

        @Override
        public void bindLevel(IntegerProperty levelProperty) {
            levelLabel1.textProperty().bind(levelProperty.asString("Level: %d"));
        }

        @Override
        public void updateGameSpeed(long fallSpeed, double speedMultiplier) {
            // Update player 1's game speed
        }

        @Override
        public void updateStatus(int level, String levelName, int score, int lines) {
            scoreProp1.set(score);
            linesProp1.set(lines);
            levelProp1.set(level);
            levelNameLabel1.setText(levelName);
        }

        @Override
        public void refreshGameBackground(int[][] boardMatrix) {
            // Refresh player 1's board
        }

        @Override
        public void showLevelUp(int level, String levelName) {
            // Show level up for player 1
        }

        @Override
        public void scheduleGhostRowRemoval(int delayMs) {
            // Schedule ghost row removal for player 1
        }

        @Override
        public void showTetrisNotification() {
            // Show tetris notification for player 1
        }

        @Override
        public void showPowerUpNotification(String powerUpName) {
            // Show power-up notification for player 1
        }

        @Override
        public void gameOver() {
            // Handle player 1 game over - player 2 wins
            if (gamePanel1.getScene() != null && gameOverDialog != null) {
                gameOverDialog.show((Stage) gamePanel1.getScene().getWindow(), scoreProp1.get(), scoreProp2.get(), onRestartCallback, onMainMenuCallback);
            }
        }
    }

    private class Player2Listener implements GameEventListener {
        @Override
        public void initGameView(int[][] boardMatrix, ViewData viewData) {
            // Initialize player 2's game view
        }

        @Override
        public void bindScore(IntegerProperty scoreProperty) {
            scoreLabel2.textProperty().bind(scoreProperty.asString("Score: %d"));
        }

        @Override
        public void bindLevel(IntegerProperty levelProperty) {
            levelLabel2.textProperty().bind(levelProperty.asString("Level: %d"));
        }

        @Override
        public void updateGameSpeed(long fallSpeed, double speedMultiplier) {
            // Update player 2's game speed
        }

        @Override
        public void updateStatus(int level, String levelName, int score, int lines) {
            scoreProp2.set(score);
            linesProp2.set(lines);
            levelProp2.set(level);
            levelNameLabel2.setText(levelName);
        }

        @Override
        public void refreshGameBackground(int[][] boardMatrix) {
            // Refresh player 2's board
        }

        @Override
        public void showLevelUp(int level, String levelName) {
            // Show level up for player 2
        }

        @Override
        public void scheduleGhostRowRemoval(int delayMs) {
            // Schedule ghost row removal for player 2
        }

        @Override
        public void showTetrisNotification() {
            // Show tetris notification for player 2
        }

        @Override
        public void showPowerUpNotification(String powerUpName) {
            // Show power-up notification for player 2
        }

        @Override
        public void gameOver() {
            // Handle player 2 game over - player 1 wins
            if (gamePanel2.getScene() != null && gameOverDialog != null) {
                gameOverDialog.show((Stage) gamePanel2.getScene().getWindow(), scoreProp1.get(), scoreProp2.get(), onRestartCallback, onMainMenuCallback);
            }
        }
    }

    public void restartGame() {
        // Reset properties
        scoreProp1.set(0);
        linesProp1.set(0);
        levelProp1.set(1);
        scoreProp2.set(0);
        linesProp2.set(0);
        levelProp2.set(1);

        // Restart the games
        player1Controller.createNewGame();
        player2Controller.createNewGame();

        // Clear winner label
        winnerLabel.setText("");
    }

    public void setOnRestartCallback(Runnable callback) {
        this.onRestartCallback = callback;
    }

    public void setOnMainMenuCallback(Runnable callback) {
        this.onMainMenuCallback = callback;
    }
}
