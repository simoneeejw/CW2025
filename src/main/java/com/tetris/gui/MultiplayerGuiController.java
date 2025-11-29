package com.tetris.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Show instructions for 2 seconds
        winnerLabel.setText("Player 1: A S D W Space Tab\nPlayer 2: Arrow Keys Enter Backspace");
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(2));
        pause.setOnFinished(e -> winnerLabel.setText(""));
        pause.play();
    }

    public void initializeGame() {
        // For now, just show the instructions
        // Full implementation would create two GuiController instances
        System.out.println("Multiplayer mode initialized - Full implementation pending");
    }
}
