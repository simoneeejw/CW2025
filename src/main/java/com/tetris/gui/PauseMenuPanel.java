package com.tetris.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Pause menu panel that appears when the game is paused.
 */
public class PauseMenuPanel extends VBox {

    private Button resumeButton;
    private Button optionsButton;
    private Button quitButton;

    private Runnable onResumeAction;
    private Runnable onOptionsAction;
    private Runnable onQuitAction;

    public PauseMenuPanel() {
        setAlignment(Pos.CENTER);
        setPadding(new Insets(20));
        setSpacing(15);
        setVisible(false);

        // Dark gradient background matching tutorial menu
        setStyle("-fx-background-color: linear-gradient(to bottom, rgba(26, 26, 46, 0.95), rgba(22, 33, 62, 0.95), rgba(15, 52, 96, 0.95)); " +
                "-fx-border-color: #e94560; " +
                "-fx-border-width: 3; " +
                "-fx-border-radius: 15; " +
                "-fx-background-radius: 15;");

        setPrefSize(400, 470);
        setMaxSize(400, 470);

        createMenuContent();
    }

    private void createMenuContent() {
        // "PAUSED" title matching tutorial menu style
        Label pausedLabel = new Label("⏸️ PAUSED ⏸️");
        pausedLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        pausedLabel.setTextFill(Color.web("#FFD700"));
        pausedLabel.setStyle("-fx-effect: dropshadow(gaussian, #FFA500, 20, 0.8, 2, 2); " +
                "-fx-background-color: rgba(0, 0, 0, 0.5); " +
                "-fx-padding: 10 20; " +
                "-fx-background-radius: 10;");

        VBox titleBox = new VBox(pausedLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(20, 0, 30, 0));
        titleBox.setPrefHeight(120);

        // Resume button
        resumeButton = createMenuButton("RESUME");
        resumeButton.setOnAction(e -> {
            com.tetris.util.SoundManager.getInstance().playButtonClickSound();
            if (onResumeAction != null) onResumeAction.run();
        });

        // Options button
        optionsButton = createMenuButton("OPTIONS");
        optionsButton.setOnAction(e -> {
            com.tetris.util.SoundManager.getInstance().playButtonClickSound();
            if (onOptionsAction != null) onOptionsAction.run();
        });

        // Quit button
        quitButton = createMenuButton("QUIT");
        quitButton.setOnAction(e -> {
            com.tetris.util.SoundManager.getInstance().playButtonClickSound();
            if (onQuitAction != null) onQuitAction.run();
        });

        getChildren().addAll(titleBox, resumeButton, optionsButton, quitButton);
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text.toUpperCase());
        button.setFont(Font.font("System", FontWeight.BOLD, 16));
        button.setPrefSize(150, 45);
        button.setMaxSize(150, 45);

        // All buttons use the same green style as tutorial menu
        button.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-color: #66BB6A; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 8;"
        );

        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #66BB6A; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-color: #66BB6A; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 8; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0.5, 0, 0);"
        ));

        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-color: #66BB6A; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 8;"
        ));

        return button;
    }

    public void setOnResumeAction(Runnable action) {
        this.onResumeAction = action;
    }

    public void setOnOptionsAction(Runnable action) {
        this.onOptionsAction = action;
    }

    public void setOnQuitAction(Runnable action) {
        this.onQuitAction = action;
    }

    public void show() {
        setVisible(true);
        toFront();
    }

    public void hide() {
        setVisible(false);
    }
}
