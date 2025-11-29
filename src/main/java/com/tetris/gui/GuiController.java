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
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.stage.Stage;

public class GuiController implements Initializable, GameEventListener {

    private static final int BRICK_SIZE = GameConstants.BRICK_SIZE_PIXELS;

    @FXML
    private GridPane gamePanel;

    @FXML
    private javafx.scene.control.Label scoreLabel;

    @FXML
    private javafx.scene.control.Label linesLabel;

    @FXML
    private javafx.scene.control.Label levelLabel;

    @FXML
    private javafx.scene.control.Label levelNameLabel;

    @FXML
    private javafx.scene.control.Button pauseButton;

    @FXML
    private GridPane nextBlockPanel;

    @FXML
    private GridPane heldBlockPanel;

    private Rectangle[][] displayMatrix;

    private GameController gameController;

    private Rectangle[][] heldRectangles;

    private Rectangle[][] nextRectangles;

    private Timeline timeLine;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    private final IntegerProperty scoreProp = new SimpleIntegerProperty(0);

    private final IntegerProperty linesProp = new SimpleIntegerProperty(0);

    private final IntegerProperty levelProp = new SimpleIntegerProperty(1);

    private long currentFallSpeed = GameConstants.DEFAULT_FALL_SPEED_MS;
    private double speedMultiplier = 1.0;

    // Theme variables
    private Theme currentTheme;
    private Rectangle themeBackground;
    private Timeline glowPulseTimeline;

    private GameOverDialog gameOverDialog;

    private PauseMenuPanel pauseMenuPanel;

    private Runnable onRestartCallback;
    private Runnable onMainMenuCallback;

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
                        refreshBrick(gameController.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                        keyEvent.consume();
                    } else if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        refreshBrick(gameController.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    } else if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        refreshBrick(gameController.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                        keyEvent.consume();
                    } else if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    } else if (keyEvent.getCode() == KeyCode.R) {
                        refreshBrick(gameController.onHoldEvent(new MoveEvent(EventType.HOLD, EventSource.USER)));
                        keyEvent.consume();
                    } else if (keyEvent.getCode() == KeyCode.SPACE) {
                        // Hard drop - keep moving down until piece locks
                        boolean keepDropping = true;
                        while (keepDropping) {
                            DownData downData = gameController.onDownEvent(new MoveEvent(EventType.DOWN, EventSource.USER));
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
                }
            }
        });

        // Bind labels to properties
        scoreLabel.textProperty().bind(Bindings.concat("Score: ", scoreProp));
        linesLabel.textProperty().bind(Bindings.concat("Lines: ", linesProp));
        levelLabel.textProperty().bind(Bindings.concat("Level: ", levelProp));
        levelNameLabel.setText("Beginner");

        // Set pause button action
        pauseButton.setOnAction(e -> togglePause());

        // Load and apply saved theme
        loadSavedTheme();
        initializeThemeBackground();
        applyTheme(currentTheme);

        // Initialize glow pulse animation for active pieces
        initializeGlowPulse();

        // Initialize game over dialog
        gameOverDialog = new GameOverDialog();
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        // Initialize status panel and add to root Pane
        // if (statusPanel == null) {
        //     statusPanel = new StatusPanel();
        //     statusPanel.setLayoutX(420);
        //     statusPanel.setLayoutY(30);
        //     statusPanel.setPrefSize(220, 350);
        //     statusPanel.setVisible(true);

        //     // Add to root pane
        //     javafx.scene.layout.Pane rootPane = (javafx.scene.layout.Pane) gamePanel.getScene().getRoot();
        //     rootPane.getChildren().add(statusPanel);
        //     statusPanel.toFront(); // Bring to front
        // }
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.getStyleClass().add("piece-rect"); // Apply CSS styling for rounded corners and glow
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

        heldRectangles = new Rectangle[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.getStyleClass().add("piece-rect"); // Apply CSS styling for rounded corners and glow
                heldRectangles[i][j] = rectangle;
                heldBlockPanel.add(rectangle, j, i);
            }
        }

        nextRectangles = new Rectangle[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.getStyleClass().add("piece-rect"); // Apply CSS styling for rounded corners and glow
                nextRectangles[i][j] = rectangle;
                nextBlockPanel.add(rectangle, j, i);
            }
        }

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
            case 1: // I-piece - Cyan
                returnPaint = Color.web("#00FFFF");
                break;
            case 2: // T-piece - Purple
                returnPaint = Color.web("#8B00FF");
                break;
            case 3: // S-piece - Green
                returnPaint = Color.web("#00FF00");
                break;
            case 4: // O-piece - Yellow
                returnPaint = Color.web("#FFFF00");
                break;
            case 5: // Z-piece - Red
                returnPaint = Color.web("#FF0000");
                break;
            case 6: // L-piece - Orange
                returnPaint = Color.web("#FF8C00");
                break;
            case 7: // J-piece - Blue
                returnPaint = Color.web("#0000FF");
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

    /**
     * Gets the stroke color for a brick (darker version of fill).
     */
    private Paint getStrokeColor(int i) {
        switch (i) {
            case 1: return Color.web("#00CCCC"); // Cyan
            case 2: return Color.web("#6B00CC"); // Purple
            case 3: return Color.web("#00CC00"); // Green
            case 4: return Color.web("#CCCC00"); // Yellow
            case 5: return Color.web("#CC0000"); // Red
            case 6: return Color.web("#CC7000"); // Orange
            case 7: return Color.web("#0000CC"); // Blue
            default: return Color.TRANSPARENT;
        }
    }


    private void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            // First, refresh the entire game background to show locked pieces
            if (gameController instanceof GameController) {
                GameController gc = (GameController) gameController;
                refreshGameBackground(gc.getBoard().getBoardMatrix());
            }

            // Then overlay the active piece on top
            int[][] brickData = brick.getBrickData();
            int brickX = brick.getxPosition();
            int brickY = brick.getyPosition();

            for (int i = 0; i < brickData.length; i++) {
                for (int j = 0; j < brickData[i].length; j++) {
                    if (brickData[i][j] != 0) {
                        int boardY = brickY + i;
                        int boardX = brickX + j;
                        // Make sure we're within bounds and adjust for the 2-row offset
                        if (boardY >= 2 && boardY < displayMatrix.length &&
                            boardX >= 0 && boardX < displayMatrix[0].length) {
                            setRectangleData(brickData[i][j], displayMatrix[boardY][boardX]);
                        }
                    }
                }
            }

            // Update held piece display
            int[][] heldData = brick.getHeldBrickData();
            if (heldData != null) {
                for (int i = 0; i < heldData.length && i < heldRectangles.length; i++) {
                    for (int j = 0; j < heldData[i].length && j < heldRectangles[i].length; j++) {
                        setRectangleData(heldData[i][j], heldRectangles[i][j]);
                    }
                }
            }

            // Update next piece display
            int[][] nextData = brick.getNextBrickData();
            if (nextData != null) {
                for (int i = 0; i < nextData.length && i < nextRectangles.length; i++) {
                    for (int j = 0; j < nextData[i].length && j < nextRectangles[i].length; j++) {
                        setRectangleData(nextData[i][j], nextRectangles[i][j]);
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
        // Calculate ghost position by simulating drops until collision
        int ghostY = calculateGhostYPosition(brick);

        // Don't draw ghost if it's at the same position as the active piece
        if (ghostY == brick.getyPosition()) {
            return;
        }

        // Draw ghost piece on the board with low opacity
        int[][] brickData = brick.getBrickData();
        int brickX = brick.getxPosition();

        for (int i = 0; i < brickData.length; i++) {
            for (int j = 0; j < brickData[i].length; j++) {
                if (brickData[i][j] != 0) {
                    int boardY = ghostY + i;
                    int boardX = brickX + j;
                    // Make sure we're within bounds and adjust for the 2-row offset
                    if (boardY >= 2 && boardY < displayMatrix.length &&
                        boardX >= 0 && boardX < displayMatrix[0].length) {
                        // Only draw if the cell is empty (don't overwrite locked pieces or active piece)
                        if (displayMatrix[boardY][boardX].getFill() == Color.TRANSPARENT) {
                            Color color = (Color) getFillColor(brickData[i][j]);
                            displayMatrix[boardY][boardX].setFill(Color.rgb(
                                (int)(color.getRed() * 255),
                                (int)(color.getGreen() * 255),
                                (int)(color.getBlue() * 255),
                                0.3 // 30% opacity for ghost
                            ));
                        }
                    }
                }
            }
        }
    }

    /**
     * Calculates where the ghost piece should be positioned (where piece will land).
     */
    private int calculateGhostYPosition(ViewData brick) {
        // Get actual ghost position from game controller
        if (gameController instanceof GameController) {
            GameController gc = (GameController) gameController;
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

        if (color != 0 && color != 8) { // Not transparent or ghost
            rectangle.setStroke(getStrokeColor(color));
            rectangle.setStrokeWidth(1.0);
            rectangle.setArcHeight(4);
            rectangle.setArcWidth(4);
        } else {
            rectangle.setStroke(Color.TRANSPARENT);
            rectangle.setStrokeWidth(0);
            rectangle.setArcHeight(4);
            rectangle.setArcWidth(4);
        }
    }

    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = gameController.onDownEvent(event);
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
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
        isGameOver.setValue(Boolean.TRUE);

        // Play game over sound
        com.tetris.util.SoundManager.getInstance().playGameOverSound();

        // Show game over dialog on JavaFX Application Thread
        javafx.application.Platform.runLater(() -> {
            if (gameOverDialog != null && gamePanel.getScene() != null) {
                gameOverDialog.show((Stage) gamePanel.getScene().getWindow(), scoreProp.get(), levelProp.get(), linesProp.get(), onRestartCallback, onMainMenuCallback);
            }
        });
    }

    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        // gameOverPanel.setVisible(false);
        gameController.createNewGame();
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
        System.out.println("togglePause called - pauseMenuPanel: " + (pauseMenuPanel != null) + ", isGameOver: " + isGameOver.get());

        if (isGameOver.get()) {
            System.out.println("Cannot pause - game is over");
            return; // Can't pause if game is over
        }

        // Initialize pause menu panel if not already done
        if (pauseMenuPanel == null && gamePanel.getScene() != null) {
            System.out.println("Initializing pause menu panel on first pause...");
            pauseMenuPanel = new PauseMenuPanel();

            // Get the center pane and add the pause menu
            javafx.scene.layout.StackPane centerPane = (javafx.scene.layout.StackPane) ((javafx.scene.layout.BorderPane) gamePanel.getScene().getRoot()).getCenter();
            centerPane.getChildren().add(pauseMenuPanel);

            // Ensure the pause menu is centered and on top
            javafx.scene.layout.StackPane.setAlignment(pauseMenuPanel, Pos.CENTER);
            pauseMenuPanel.toFront();

            // Set pause menu actions
            pauseMenuPanel.setOnResumeAction(this::togglePause);
            pauseMenuPanel.setOnOptionsAction(() -> {
                SettingsDialog settings = new SettingsDialog();
                settings.show((Stage) gamePanel.getScene().getWindow());
            });
            pauseMenuPanel.setOnQuitAction(onMainMenuCallback);

            System.out.println("Pause menu panel initialized successfully");
        }

        if (pauseMenuPanel == null) {
            System.out.println("Cannot pause - scene not ready");
            return;
        }

        if (isPause.get()) {
            // Resume game
            isPause.setValue(Boolean.FALSE);
            if (timeLine != null) timeLine.play();
            pauseMenuPanel.hide();
            System.out.println("Game Resumed - menu hidden");
        } else {
            // Pause game
            isPause.setValue(Boolean.TRUE);
            if (timeLine != null) timeLine.pause();
            pauseMenuPanel.show();
            System.out.println("Game Paused - menu should be visible now");
        }
    }

    /**
     * Schedules ghost row removal after a delay.
     * @param delayMs Delay in milliseconds
     */
    public void scheduleGhostRowRemoval(int delayMs) {
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(
            javafx.util.Duration.millis(delayMs)
        );
        pause.setOnFinished(e -> {
            if (gameController != null) {
                GameController gc = gameController;
                if (gc.getBoard() instanceof TetrisBoard) {
                    TetrisBoard tb = (TetrisBoard) gc.getBoard();
                    tb.removeGhostRow();
                    refreshGameBackground(tb.getBoardMatrix());
                }
            }
        });
        pause.play();
    }

    /**
     * Loads the saved theme from preferences.
     */
    private void loadSavedTheme() {
        Preferences prefs = Preferences.userNodeForPackage(GuiController.class);
        String themeName = prefs.get("theme", Theme.NEON_NIGHT.name());
        try {
            currentTheme = Theme.valueOf(themeName);
        } catch (IllegalArgumentException e) {
            currentTheme = Theme.NEON_NIGHT; // Default fallback
        }
    }

    /**
     * Saves the current theme to preferences.
     */
    private void saveTheme() {
        Preferences prefs = Preferences.userNodeForPackage(GuiController.class);
        prefs.put("theme", currentTheme.name());
    }

    /**
     * Initializes the theme background rectangle.
     */
    private void initializeThemeBackground() {
        themeBackground = new Rectangle(800, 600); // Full screen size
        themeBackground.setMouseTransparent(true);

        // Add to root pane as bottom layer
        if (gamePanel.getScene() != null) {
            javafx.scene.layout.Pane rootPane = (javafx.scene.layout.Pane) gamePanel.getScene().getRoot();
            rootPane.getChildren().add(0, themeBackground); // Add at index 0 (bottom)
        }
    }

    /**
     * Initializes the glow pulse animation for active pieces.
     */
    private void initializeGlowPulse() {
        glowPulseTimeline = new Timeline(
            new KeyFrame(Duration.ZERO, e -> setGlowOpacity(0.5)),
            new KeyFrame(Duration.millis(250), e -> setGlowOpacity(1.0)),
            new KeyFrame(Duration.millis(500), e -> setGlowOpacity(0.5))
        );
        glowPulseTimeline.setCycleCount(Timeline.INDEFINITE);
        glowPulseTimeline.play();
    }

    /**
     * Sets the glow opacity for active pieces.
     * @param opacity The opacity value (0.0 to 1.0)
     */
    private void setGlowOpacity(double opacity) {
        if (displayMatrix != null) {
            for (Rectangle[] row : displayMatrix) {
                for (Rectangle rect : row) {
                    if (rect != null && rect.getEffect() instanceof DropShadow) {
                        DropShadow glow = (DropShadow) rect.getEffect();
                        Color glowColor = (Color) glow.getColor();
                        glow.setColor(Color.color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), opacity));
                    }
                }
            }
        }
    }

    /**
     * Applies the specified theme to the UI elements.
     * @param theme The theme to apply
     */
    public void applyTheme(Theme theme) {
        currentTheme = theme;
        saveTheme();

        // Apply gradient background
        if (themeBackground != null) {
            LinearGradient gradient = new LinearGradient(
                0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web(theme.getBoardGradientStart())),
                new Stop(1, Color.web(theme.getBoardGradientEnd()))
            );
            themeBackground.setFill(gradient);
        }

        // Update score label color
        if (scoreLabel != null) {
            scoreLabel.setStyle("-fx-text-fill: " + theme.getUiAccentColor() + ";");
        }

        // Apply glow effects to active pieces
        applyPieceGlow(theme);
    }

    /**
     * Applies glow effects to active pieces.
     * @param theme Current theme
     */
    private void applyPieceGlow(Theme theme) {
        if (displayMatrix != null) {
            for (Rectangle[] row : displayMatrix) {
                for (Rectangle rect : row) {
                    if (rect != null && rect.getFill() != Color.TRANSPARENT) {
                        // Add drop shadow effect
                        DropShadow glow = new DropShadow();
                        glow.setColor(Color.web(theme.getPieceGlowColor()));
                        glow.setRadius(5);
                        glow.setSpread(0.3);
                        rect.setEffect(glow);
                    }
                }
            }
        }
    }

    /**
     * Creates particle burst effect for line clears.
     * @param linesCleared Number of lines cleared
     */
    public void createLineClearParticles(int linesCleared) {
        // Particle effect code removed as per changes
    }

    /**
     * Shows the theme selection dialog.
     */
    public void showThemeSelector() {
        ThemeSelector selector = new ThemeSelector();
        Theme selected = selector.showAndWait(null); // Pass null for now, can be improved
        if (selected != null) {
            applyTheme(selected);
        }
    }

    /**
     * Shows level-up notification.
     * @param level New level number
     * @param difficulty Difficulty name
     */
    public void showLevelUp(int level, String difficulty) {
        levelProp.set(level);
        levelNameLabel.setText(difficulty);
    }

    /**
     * Shows power-up notification.
     * @param powerUpName Name of the power-up
     */
    public void showPowerUpNotification(String powerUpName) {
        // No notification in classic layout
    }

    /**
     * Shows Tetris (4-line clear) notification.
     */
    public void showTetrisNotification() {
        // No notification in classic layout
    }

    /**
     * Updates status panel with current game state.
     * @param level Current level
     * @param difficulty Difficulty name
     * @param score Current score
     * @param linesCleared Total lines cleared
     */
    public void updateStatus(int level, String difficulty, int score, int linesCleared) {
        scoreProp.set(score);
        linesProp.set(linesCleared);
        levelProp.set(level);
        levelNameLabel.setText(difficulty);
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setOnRestartCallback(Runnable callback) {
        this.onRestartCallback = callback;
    }

    public void setOnMainMenuCallback(Runnable callback) {
        this.onMainMenuCallback = callback;
    }
}
