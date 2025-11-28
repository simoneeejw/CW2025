package com.tetris.gui;

import com.tetris.model.DownData;
import com.tetris.model.EventSource;
import com.tetris.model.EventType;
import com.tetris.model.MoveEvent;
import com.tetris.model.ViewData;
import com.tetris.game.GameEventListener;
import com.tetris.game.GameController;
import com.tetris.game.TetrisBoard;
import com.tetris.util.BrickColorMapper;
import com.tetris.util.GameConstants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    private static final int BRICK_SIZE = GameConstants.BRICK_SIZE_PIXELS;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GridPane heldPanel;

    @FXML
    private GameOverPanel gameOverPanel;

    @FXML
    private javafx.scene.control.Label scoreLabel;

    private LevelUpPanel levelUpPanel;
    private StatusPanel statusPanel;
    private GridPane ghostPanel; // Shadow piece

    private Rectangle[][] displayMatrix;

    private GameEventListener eventListener;

    private Rectangle[][] rectangles;

    private Rectangle[][] heldRectangles;
    private Rectangle[][] ghostRectangles;

    private Timeline timeLine;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    private long currentFallSpeed = GameConstants.DEFAULT_FALL_SPEED_MS;
    private double speedMultiplier = 1.0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                        keyEvent.consume();
                    } else if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    } else if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                        keyEvent.consume();
                    } else if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    } else if (keyEvent.getCode() == KeyCode.C) {
                        refreshBrick(eventListener.onHoldEvent(new MoveEvent(EventType.HOLD, EventSource.USER)));
                        keyEvent.consume();
                    } else if (keyEvent.getCode() == KeyCode.SPACE) {
                        // Hard drop - keep moving down until piece locks
                        boolean keepDropping = true;
                        while (keepDropping) {
                            DownData downData = eventListener.onDownEvent(new MoveEvent(EventType.DOWN, EventSource.USER));
                            refreshBrick(downData.getViewData());
                            // Stop if piece has locked (clearRow check indicates piece merged)
                            if (downData.getClearRow() != null) {
                                keepDropping = false;
                            }
                        }
                        keyEvent.consume();
                    }
                } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    togglePause();
                    keyEvent.consume();
                } else if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                    keyEvent.consume();
                }
            }
        });
        gameOverPanel.setVisible(false);

        // Initialize level-up panel
        levelUpPanel = new LevelUpPanel();
        levelUpPanel.setLayoutX(100);
        levelUpPanel.setLayoutY(200);
        levelUpPanel.setPrefSize(400, 150);
        groupNotification.getChildren().add(levelUpPanel);

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        // Initialize status panel and add to root Pane
        if (statusPanel == null) {
            statusPanel = new StatusPanel();
            statusPanel.setLayoutX(420);
            statusPanel.setLayoutY(30);
            statusPanel.setPrefSize(220, 350);
            statusPanel.setVisible(true);

            // Add to root pane
            javafx.scene.layout.Pane rootPane = (javafx.scene.layout.Pane) gamePanel.getScene().getRoot();
            rootPane.getChildren().add(statusPanel);
            statusPanel.toFront(); // Bring to front
        }
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);


        heldRectangles = new Rectangle[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                heldRectangles[i][j] = rectangle;
                heldPanel.add(rectangle, j, i);
            }
        }

        // Initialize ghost panel (shadow piece)
        ghostPanel = new GridPane();
        ghostPanel.setHgap(1);
        ghostPanel.setVgap(1);
        ghostRectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                ghostRectangles[i][j] = rectangle;
                ghostPanel.add(rectangle, j, i);
            }
        }
        // Add ghost panel to root - get parent of gamePanel
        javafx.scene.layout.Pane rootPane = (javafx.scene.layout.Pane) gamePanel.getParent().getParent();
        rootPane.getChildren().add(ghostPanel);
        ghostPanel.toBack(); // Send to back so it's behind actual piece

        timeLine = new Timeline(new KeyFrame(
                Duration.millis(GameConstants.DEFAULT_FALL_SPEED_MS),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    private Paint getFillColor(int i) {
        Paint returnPaint;
        switch (i) {
            case 0:
                returnPaint = Color.TRANSPARENT;
                break;
            case 1:
                returnPaint = Color.AQUA;
                break;
            case 2:
                returnPaint = Color.BLUEVIOLET;
                break;
            case 3:
                returnPaint = Color.DARKGREEN;
                break;
            case 4:
                returnPaint = Color.YELLOW;
                break;
            case 5:
                returnPaint = Color.RED;
                break;
            case 6:
                returnPaint = Color.BEIGE;
                break;
            case 7:
                returnPaint = Color.BURLYWOOD;
                break;
            case 8:
                // Ghost row - semi-transparent gray
                returnPaint = Color.rgb(150, 150, 150, 0.5);
                break;
            default:
                returnPaint = Color.WHITE;
                break;
        }
        return returnPaint;
    }


    private void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
                }
            }
            int[][] heldData = brick.getHeldBrickData();
            if (heldData != null) {
                for (int i = 0; i < heldData.length; i++) {
                    for (int j = 0; j < heldData[i].length; j++) {
                        setRectangleData(heldData[i][j], heldRectangles[i][j]);
                    }
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        heldRectangles[i][j].setFill(Color.TRANSPARENT);
                    }
                }
            }

            // Update ghost piece (shadow)
            updateGhostPiece(brick);
        }
    }

    /**
     * Updates the ghost piece (shadow) position to show where brick will land.
     */
    private void updateGhostPiece(ViewData brick) {
        if (ghostPanel == null) return;

        // Calculate ghost position by simulating drops until collision
        int ghostY = calculateGhostYPosition(brick);

        // Position ghost panel
        ghostPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * ghostPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        ghostPanel.setLayoutY(-42 + gamePanel.getLayoutY() + ghostY * ghostPanel.getHgap() + ghostY * BRICK_SIZE);

        // Update ghost rectangles with semi-transparent version of brick
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                if (brick.getBrickData()[i][j] != 0) {
                    // Make it semi-transparent
                    Color color = (Color) getFillColor(brick.getBrickData()[i][j]);
                    ghostRectangles[i][j].setFill(Color.rgb(
                        (int)(color.getRed() * 255),
                        (int)(color.getGreen() * 255),
                        (int)(color.getBlue() * 255),
                        0.3 // 30% opacity
                    ));
                    ghostRectangles[i][j].setArcHeight(9);
                    ghostRectangles[i][j].setArcWidth(9);
                } else {
                    ghostRectangles[i][j].setFill(Color.TRANSPARENT);
                }
            }
        }
    }

    /**
     * Calculates where the ghost piece should be positioned (where piece will land).
     */
    private int calculateGhostYPosition(ViewData brick) {
        // Get actual ghost position from game controller
        if (eventListener instanceof GameController) {
            GameController gc = (GameController) eventListener;
            return gc.getGhostYPosition();
        }

        // Fallback to current position if unable to calculate
        return brick.getyPosition();
    }

    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    public void setEventListener(GameEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void bindScore(IntegerProperty integerProperty) {
        if (scoreLabel != null) {
            scoreLabel.textProperty().bind(integerProperty.asString("Score: %d"));
        }
    }

    public void bindLevel(IntegerProperty levelProperty) {
        // Level binding can be added to UI labels if needed
    }

    /**
     * Updates the game speed based on level and power-up multipliers.
     * @param baseFallSpeed Base fall speed from level
     * @param powerUpMultiplier Speed multiplier from power-ups
     */
    public void updateGameSpeed(long baseFallSpeed, double powerUpMultiplier) {
        this.currentFallSpeed = baseFallSpeed;
        this.speedMultiplier = powerUpMultiplier;

        // Update timeline with new speed
        if (timeLine != null) {
            timeLine.stop();
            long adjustedSpeed = (long) (baseFallSpeed * powerUpMultiplier);
            timeLine = new Timeline(new KeyFrame(
                    Duration.millis(adjustedSpeed),
                    ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
            ));
            timeLine.setCycleCount(Timeline.INDEFINITE);
            if (!isPause.get() && !isGameOver.get()) {
                timeLine.play();
            }
        }
    }

    public void gameOver() {
        timeLine.stop();
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);
    }

    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }

    public void pauseGame(ActionEvent actionEvent) {
        togglePause();
        gamePanel.requestFocus();
    }

    /**
     * Toggles game pause state.
     */
    private void togglePause() {
        if (isGameOver.get()) {
            return; // Can't pause if game is over
        }

        if (isPause.get()) {
            // Resume game
            isPause.setValue(Boolean.FALSE);
            timeLine.play();
            System.out.println("Game Resumed");
        } else {
            // Pause game
            isPause.setValue(Boolean.TRUE);
            timeLine.pause();
            System.out.println("Game Paused");
        }
    }

    /**
     * Shows level-up notification.
     * @param level New level number
     * @param difficulty Difficulty name
     */
    public void showLevelUp(int level, String difficulty) {
        if (levelUpPanel != null) {
            levelUpPanel.showLevelUp(level);
        }
        if (statusPanel != null) {
            statusPanel.updateLevel(level, difficulty);
        }
    }

    /**
     * Shows power-up notification.
     * @param powerUpName Name of the power-up
     */
    public void showPowerUpNotification(String powerUpName) {
        if (levelUpPanel != null) {
            levelUpPanel.showPowerUp(powerUpName);
        }
    }

    /**
     * Shows Tetris (4-line clear) notification.
     */
    public void showTetrisNotification() {
        if (levelUpPanel != null) {
            levelUpPanel.showTetris();
        }
    }

    /**
     * Updates status panel with current game state.
     * @param level Current level
     * @param difficulty Difficulty name
     * @param score Current score
     * @param linesCleared Total lines cleared
     */
    public void updateStatus(int level, String difficulty, int score, int linesCleared) {
        if (statusPanel != null) {
            statusPanel.updateLevel(level, difficulty);
            statusPanel.updateScore(score);
            statusPanel.updateLinesCleared(linesCleared);
        }
    }

    /**
     * Schedules ghost row removal after a delay.
     * @param delayMs Delay in milliseconds
     */
    public void scheduleGhostRowRemoval(long delayMs) {
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(
            javafx.util.Duration.millis(delayMs)
        );
        pause.setOnFinished(e -> {
            if (eventListener instanceof GameController) {
                GameController gc = (GameController) eventListener;
                if (gc.getBoard() instanceof TetrisBoard) {
                    TetrisBoard tb = (TetrisBoard) gc.getBoard();
                    tb.removeGhostRow();
                    refreshGameBackground(tb.getBoardMatrix());
                }
            }
        });
        pause.play();
    }
}
