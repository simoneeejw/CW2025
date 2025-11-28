package com.tetris.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Panel to display game status including level, score, and active power-ups.
 */
public class StatusPanel extends VBox {

    private final Text levelText;
    private final Text scoreText;
    private final Text powerUpText;
    private final Text controlsText;

    public StatusPanel() {
        this.levelText = new Text("Level: 1");
        this.scoreText = new Text("Score: 0");
        this.powerUpText = new Text("");
        this.controlsText = new Text();
        setupUI();
    }

    private void setupUI() {
        setAlignment(Pos.TOP_LEFT);
        setPadding(new Insets(20));
        setSpacing(15);
        setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); " +
                 "-fx-background-radius: 10;");

        // Level text
        levelText.setFont(Font.font("System", FontWeight.BOLD, 24));
        levelText.setFill(Color.GOLD);

        // Score text
        scoreText.setFont(Font.font("System", FontWeight.BOLD, 20));
        scoreText.setFill(Color.WHITE);

        // Power-up text
        powerUpText.setFont(Font.font("System", FontWeight.BOLD, 18));
        powerUpText.setFill(Color.CYAN);
        powerUpText.setVisible(false);

        // Controls text
        controlsText.setFont(Font.font("System", FontWeight.NORMAL, 12));
        controlsText.setFill(Color.LIGHTGRAY);
        controlsText.setText("Controls:\n" +
                            "← → : Move\n" +
                            "↑ : Rotate\n" +
                            "↓ : Soft Drop\n" +
                            "SPACE : Hard Drop\n" +
                            "C : Hold\n" +
                            "ESC : Pause\n" +
                            "N : New Game");

        getChildren().addAll(levelText, scoreText, powerUpText, controlsText);
    }

    /**
     * Updates the level display.
     * @param level Current level
     * @param difficulty Difficulty name
     */
    public void updateLevel(int level, String difficulty) {
        levelText.setText("Level: " + level + " (" + difficulty + ")");
    }

    /**
     * Updates the score display.
     * @param score Current score
     */
    public void updateScore(int score) {
        scoreText.setText("Score: " + score);
    }

    /**
     * Shows active power-up with remaining time.
     * @param powerUpName Name of the power-up
     * @param secondsRemaining Seconds remaining
     */
    public void showPowerUp(String powerUpName, long secondsRemaining) {
        if (secondsRemaining > 0) {
            powerUpText.setText("⚡ " + powerUpName + " (" + secondsRemaining + "s)");
            powerUpText.setVisible(true);
        } else {
            powerUpText.setVisible(false);
        }
    }

    /**
     * Hides the power-up display.
     */
    public void hidePowerUp() {
        powerUpText.setVisible(false);
    }
}

