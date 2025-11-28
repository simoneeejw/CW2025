package com.tetris.gui;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Panel to display level-up and power-up notifications.
 */
public class LevelUpPanel extends StackPane {

    private final Text messageText;

    public LevelUpPanel() {
        this.messageText = new Text();
        setupUI();
    }

    private void setupUI() {
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); " +
                 "-fx-background-radius: 10; " +
                 "-fx-padding: 20;");

        messageText.setFont(Font.font("System", FontWeight.BOLD, 36));
        messageText.setFill(Color.GOLD);
        messageText.setStyle("-fx-effect: dropshadow(gaussian, black, 10, 0.5, 0, 0);");

        getChildren().add(messageText);
        setVisible(false);
        setMouseTransparent(true);
    }

    /**
     * Shows a level-up message with animation.
     * @param level The new level number
     */
    public void showLevelUp(int level) {
        messageText.setText("LEVEL " + level + "!");
        messageText.setFill(Color.GOLD);
        animateMessage();
    }

    /**
     * Shows a power-up message with animation.
     * @param powerUpName The name of the power-up
     */
    public void showPowerUp(String powerUpName) {
        messageText.setText(powerUpName + "!");
        messageText.setFill(Color.CYAN);
        animateMessage();
    }

    /**
     * Shows a Tetris (4-line clear) message.
     */
    public void showTetris() {
        messageText.setText("TETRIS!");
        messageText.setFill(Color.ORANGE);
        animateMessage();
    }

    private void animateMessage() {
        setVisible(true);
        setOpacity(1.0);
        setScaleX(0.1);
        setScaleY(0.1);

        // Scale up animation
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(300), this);
        scaleUp.setToX(1.2);
        scaleUp.setToY(1.2);

        // Scale down to normal
        ScaleTransition scaleNormal = new ScaleTransition(Duration.millis(200), this);
        scaleNormal.setToX(1.0);
        scaleNormal.setToY(1.0);

        // Hold
        ScaleTransition hold = new ScaleTransition(Duration.millis(1000), this);

        // Fade out
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), this);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> setVisible(false));

        SequentialTransition sequence = new SequentialTransition(
                scaleUp, scaleNormal, hold, fadeOut
        );
        sequence.play();
    }
}

