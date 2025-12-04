package com.tetris.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Game over dialog showing final score and options to restart or return to menu.
 */
public class GameOverDialog {

    private HighScoresDialog highScoresDialog;

    public GameOverDialog() {
        this.highScoresDialog = new HighScoresDialog();
    }

    /**
     * Shows the game over dialog for single player.
     */
    public void show(Stage owner, int score, int level, int linesCleared, Runnable onRestart, Runnable onMainMenu) {
        showDialog(owner, score, level, linesCleared, -1, -1, false, onRestart, onMainMenu);
    }

    /**
     * Shows the game over dialog for multiplayer.
     */
    public void show(Stage owner, int player1Score, int player2Score, Runnable onRestart, Runnable onMainMenu) {
        showDialog(owner, player1Score, -1, -1, player2Score, -1, true, onRestart, onMainMenu);
    }

    private void showDialog(Stage owner, int score, int level, int lines, int player2Score, int player2Level, boolean isMultiplayer, Runnable onRestart, Runnable onMainMenu) {
        // Reload scores to ensure we have the latest data for high score checking
        highScoresDialog.reloadScores();

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle("Game Over");
        dialog.setResizable(false);

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);

        // Set background image or gradient
        try {
            Image bgImage = new Image(getClass().getResourceAsStream("/background_image.png"));
            BackgroundImage bgImg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(600, 500, false, false, false, false));
            Background bg = new Background(bgImg);
            layout.setBackground(bg);
        } catch (Exception e) {
            layout.setStyle("-fx-background-color: linear-gradient(to bottom, #0a0033, #000000);");
        }

        // Title
        Label title = new Label("GAME OVER");
        title.setFont(Font.font("System", FontWeight.BOLD, 48));
        title.setTextFill(Color.web("#FF0000"));
        title.setStyle("-fx-effect: dropshadow(gaussian, #FF4500, 20, 0.8, 0, 0);");

        VBox centerGroup = new VBox(20);
        centerGroup.setAlignment(Pos.CENTER);

        VBox statsBox = new VBox(10);
        statsBox.setAlignment(Pos.CENTER);

        if (isMultiplayer) {
            // Multiplayer stats
            Label winnerLabel = new Label(score > player2Score ? "Player 1 Wins!" : score < player2Score ? "Player 2 Wins!" : "It's a Tie!");
            winnerLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
            winnerLabel.setTextFill(Color.web("#FFD700"));

            Label p1Score = new Label("Player 1 Score: " + score);
            p1Score.setFont(Font.font("System", FontWeight.BOLD, 18));
            p1Score.setTextFill(Color.web("#FFFFFF"));

            Label p2Score = new Label("Player 2 Score: " + player2Score);
            p2Score.setFont(Font.font("System", FontWeight.BOLD, 18));
            p2Score.setTextFill(Color.web("#FFFFFF"));

            statsBox.getChildren().addAll(winnerLabel, p1Score, p2Score);
        } else {
            // Single player stats
            Label scoreLabel = new Label("Final Score: " + score);
            scoreLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
            scoreLabel.setTextFill(Color.web("#FFD700"));

            Label levelLabel = new Label("Level Reached: " + level);
            levelLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
            levelLabel.setTextFill(Color.web("#FFFFFF"));

            Label linesLabel = new Label("Lines Cleared: " + lines);
            linesLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
            linesLabel.setTextFill(Color.web("#FFFFFF"));

            statsBox.getChildren().addAll(scoreLabel, levelLabel, linesLabel);

            // Check for high score
            boolean isNewHighScore = highScoresDialog.isHighScore(score);
            if (isNewHighScore) {
                Label highScoreLabel = new Label("🎉 New High Score!");
                highScoreLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
                highScoreLabel.setTextFill(Color.web("#00FF00"));
                statsBox.getChildren().add(highScoreLabel);
            }
        }

        // Buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        Button restartButton = new Button("PLAY AGAIN");
        restartButton.setFont(Font.font("System", FontWeight.BOLD, 18));
        restartButton.setPrefSize(150, 50);
        restartButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FFD700, #FFA500); -fx-text-fill: #000000; -fx-background-radius: 10;");
        restartButton.setOnAction(e -> {
            com.tetris.util.SoundManager.getInstance().playButtonClickSound();
            dialog.close();
            onRestart.run();
        });

        Button mainMenuButton = new Button("MAIN MENU");
        mainMenuButton.setFont(Font.font("System", FontWeight.BOLD, 18));
        mainMenuButton.setPrefSize(150, 50);
        mainMenuButton.setStyle("-fx-background-color: linear-gradient(to bottom, #2196F3, #1976D2); -fx-text-fill: #FFFFFF; -fx-background-radius: 10;");
        mainMenuButton.setOnAction(e -> {
            com.tetris.util.SoundManager.getInstance().playButtonClickSound();
            dialog.close();
            onMainMenu.run();
        });

        buttonBox.getChildren().addAll(restartButton, mainMenuButton);

        centerGroup.getChildren().addAll(statsBox, buttonBox);
        layout.getChildren().addAll(title, centerGroup);

        Scene scene = new Scene(layout, 600, 500);
        dialog.setScene(scene);
        dialog.show();

        // After dialog closes, prompt for name if it was a high score (single player only)
        if (!isMultiplayer && highScoresDialog.isHighScore(score)) {
            TextInputDialog nameDialog = new TextInputDialog("Player");
            nameDialog.setTitle("New High Score");
            nameDialog.setHeaderText("Congratulations! Enter your name:");
            nameDialog.setContentText("Name:");
            nameDialog.initOwner(owner);
            nameDialog.showAndWait().ifPresent(name -> {
                highScoresDialog.addScore(name, score, level);
            });
        }
    }
}
