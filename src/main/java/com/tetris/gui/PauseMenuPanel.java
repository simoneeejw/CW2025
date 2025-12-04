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

        // Semi-transparent background
        setStyle("-fx-background-color: rgba(100, 120, 180, 0.95); " +
                "-fx-border-color: #4A5A8A; " +
                "-fx-border-width: 4; " +
                "-fx-border-radius: 10; " +
                "-fx-background-radius: 10;");

        setPrefSize(400, 470);
        setMaxSize(400, 470);

        createMenuContent();
    }

    private void createMenuContent() {
        // "PAUSED" title
        Label pausedLabel = new Label("PAUSED");
        pausedLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        pausedLabel.setTextFill(Color.web("#ffffff"));
        pausedLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0.5, 2, 2);");

        VBox titleBox = new VBox(pausedLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(20, 0, 30, 0));
        titleBox.setPrefHeight(120);

        // Resume button
        resumeButton = createMenuButton("resume", true);
        resumeButton.setOnAction(e -> {
            com.tetris.util.SoundManager.getInstance().playButtonClickSound();
            if (onResumeAction != null) onResumeAction.run();
        });

        // Options button
        optionsButton = createMenuButton("options", false);
        optionsButton.setOnAction(e -> {
            com.tetris.util.SoundManager.getInstance().playButtonClickSound();
            if (onOptionsAction != null) onOptionsAction.run();
        });

        // Quit button
        quitButton = createMenuButton("quit", false);
        quitButton.setOnAction(e -> {
            com.tetris.util.SoundManager.getInstance().playButtonClickSound();
            if (onQuitAction != null) onQuitAction.run();
        });

        getChildren().addAll(titleBox, resumeButton, optionsButton, quitButton);
    }

    private Button createMenuButton(String text, boolean isPrimary) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        button.setPrefSize(320, 80);
        button.setMaxSize(320, 80);

        if (isPrimary) {
            // Resume button - darker/highlighted
            button.setPrefSize(340, 90);
            button.setMaxSize(340, 90);
            button.setStyle(
                "-fx-background-color: rgba(230, 240, 255, 0.95); " +
                "-fx-text-fill: #000000; " +
                "-fx-border-color: #4A5A8A; " +
                "-fx-border-width: 4; " +
                "-fx-background-radius: 5; " +
                "-fx-border-radius: 5; " +
                "-fx-font-size: 36px; " +
                "-fx-font-weight: bold;"
            );

            button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 1.0); " +
                "-fx-text-fill: #000000; " +
                "-fx-border-color: #000000; " +
                "-fx-border-width: 4; " +
                "-fx-background-radius: 5; " +
                "-fx-border-radius: 5; " +
                "-fx-font-size: 36px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0.5, 0, 0);"
            ));

            button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: rgba(230, 240, 255, 0.95); " +
                "-fx-text-fill: #000000; " +
                "-fx-border-color: #4A5A8A; " +
                "-fx-border-width: 4; " +
                "-fx-background-radius: 5; " +
                "-fx-border-radius: 5; " +
                "-fx-font-size: 36px; " +
                "-fx-font-weight: bold;"
            ));
        } else {
            // Other buttons - lighter
            button.setStyle(
                "-fx-background-color: rgba(200, 210, 230, 0.9); " +
                "-fx-text-fill: #4A5A8A; " +
                "-fx-border-color: #4A5A8A; " +
                "-fx-border-width: 3; " +
                "-fx-background-radius: 5; " +
                "-fx-border-radius: 5; " +
                "-fx-font-size: 32px; " +
                "-fx-font-weight: bold;"
            );

            button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: rgba(220, 230, 250, 1.0); " +
                "-fx-text-fill: #2A3A6A; " +
                "-fx-border-color: #2A3A6A; " +
                "-fx-border-width: 3; " +
                "-fx-background-radius: 5; " +
                "-fx-border-radius: 5; " +
                "-fx-font-size: 32px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 6, 0.5, 0, 0);"
            ));

            button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: rgba(200, 210, 230, 0.9); " +
                "-fx-text-fill: #4A5A8A; " +
                "-fx-border-color: #4A5A8A; " +
                "-fx-border-width: 3; " +
                "-fx-background-radius: 5; " +
                "-fx-border-radius: 5; " +
                "-fx-font-size: 32px; " +
                "-fx-font-weight: bold;"
            ));
        }

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
