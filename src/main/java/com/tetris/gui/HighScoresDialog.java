package com.tetris.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * High scores dialog showing top 10 scores.
 */
public class HighScoresDialog {

    private static final String SCORES_FILE = "highscores.dat";
    private List<ScoreEntry> scores;

    public HighScoresDialog() {
        loadScores();
    }

    /**
     * Shows the high scores dialog.
     */
    public void show(Stage owner) {
        // Reload scores to get the latest data
        reloadScores();

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle("High Scores");
        dialog.setResizable(false);

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e, #0f3460); " +
                "-fx-border-color: #e94560; " +
                "-fx-border-width: 3; " +
                "-fx-border-radius: 15; " +
                "-fx-background-radius: 15;");

        // Title
        Label title = new Label("🏆 HIGH SCORES 🏆");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        title.setTextFill(Color.web("#FFD700"));
        title.setStyle("-fx-effect: dropshadow(gaussian, #FFA500, 20, 0.8, 2, 2); " +
                "-fx-background-color: rgba(0, 0, 0, 0.5); " +
                "-fx-padding: 10 20; " +
                "-fx-background-radius: 10;");

        // Table
        TableView<ScoreEntry> table = createScoresTable();

        // Close button
        Button closeButton = new Button("CLOSE");
        closeButton.setFont(Font.font("System", FontWeight.BOLD, 14));
        closeButton.setPrefSize(120, 40);
        closeButton.setStyle(
            "-fx-background-color: #4CAF50; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8;"
        );

        closeButton.setOnMouseEntered(e -> closeButton.setStyle(
            "-fx-background-color: #66BB6A; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0.5, 0, 0);"
        ));

        closeButton.setOnMouseExited(e -> closeButton.setStyle(
            "-fx-background-color: #4CAF50; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8;"
        ));

        closeButton.setOnAction(e -> {
            com.tetris.util.SoundManager.getInstance().playButtonClickSound();
            dialog.close();
        });

        layout.getChildren().addAll(title, table, closeButton);

        Scene scene = new Scene(layout, 500, 550);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private TableView<ScoreEntry> createScoresTable() {
        TableView<ScoreEntry> table = new TableView<>();
        table.setPrefHeight(400);
        table.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); " +
                "-fx-border-color: #FFD700; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 10; " +
                "-fx-background-radius: 10; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0.5, 0, 0); " +
                "-fx-text-fill: white;");

        // Rank column
        TableColumn<ScoreEntry, Integer> rankCol = new TableColumn<>("Rank");
        rankCol.setCellValueFactory(new PropertyValueFactory<>("rank"));
        rankCol.setPrefWidth(60);
        rankCol.setStyle("-fx-alignment: CENTER; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Name column
        TableColumn<ScoreEntry, String> nameCol = new TableColumn<>("Player");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(180);
        nameCol.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        // Score column
        TableColumn<ScoreEntry, Integer> scoreCol = new TableColumn<>("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreCol.setPrefWidth(100);
        scoreCol.setStyle("-fx-alignment: CENTER; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Level column
        TableColumn<ScoreEntry, Integer> levelCol = new TableColumn<>("Level");
        levelCol.setCellValueFactory(new PropertyValueFactory<>("level"));
        levelCol.setPrefWidth(80);
        levelCol.setStyle("-fx-alignment: CENTER; -fx-font-size: 14px; -fx-text-fill: white;");

        table.getColumns().addAll(rankCol, nameCol, scoreCol, levelCol);

        // Style the table rows
        table.setRowFactory(tv -> {
            javafx.scene.control.TableRow<ScoreEntry> row = new javafx.scene.control.TableRow<>();
            row.setStyle("-fx-background-color: rgba(255, 255, 255, 0.05);");
            return row;
        });

        // Load top 10 scores
        int rank = 1;
        for (ScoreEntry entry : scores) {
            entry.setRank(rank++);
            table.getItems().add(entry);
            if (rank > 10) break;
        }

        // If no scores, show placeholder
        Label placeholder = new Label("No scores yet. Be the first!");
        placeholder.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 16px;");
        table.setPlaceholder(placeholder);

        return table;
    }

    /**
     * Adds a new score to the high scores list.
     */
    public void addScore(String playerName, int score, int level) {
        System.out.println("Adding new score - Player: " + playerName + ", Score: " + score + ", Level: " + level);
        scores.add(new ScoreEntry(0, playerName, score, level));
        Collections.sort(scores, (a, b) -> Integer.compare(b.getScore(), a.getScore()));

        // Keep only top 100
        if (scores.size() > 100) {
            scores = scores.subList(0, 100);
        }

        saveScores();
        System.out.println("Score saved. Total scores: " + scores.size());
    }

    /**
     * Checks if the given score qualifies as a high score (top 10).
     */
    public boolean isHighScore(int score) {
        if (scores.size() < 10) return true;
        // Check against the 10th best score (index 9)
        return score > scores.get(9).getScore();
    }

    /**
     * Reloads scores from disk to get the latest data.
     */
    public void reloadScores() {
        loadScores();
    }

    private void loadScores() {
        scores = new ArrayList<>();
        File file = new File(SCORES_FILE);

        if (!file.exists()) {
            // Create some default scores
            scores.add(new ScoreEntry(0, "Player 1", 5000, 3));
            scores.add(new ScoreEntry(0, "Player 2", 3500, 2));
            scores.add(new ScoreEntry(0, "Player 3", 2000, 2));
            scores.add(new ScoreEntry(0, "Player 4", 1500, 1));
            scores.add(new ScoreEntry(0, "Player 5", 1000, 1));
            saveScores();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            scores = (List<ScoreEntry>) ois.readObject();
        } catch (Exception e) {
            System.err.println("Error loading scores: " + e.getMessage());
            scores = new ArrayList<>();
        }
    }

    private void saveScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SCORES_FILE))) {
            oos.writeObject(scores);
        } catch (Exception e) {
            System.err.println("Error saving scores: " + e.getMessage());
        }
    }

    /**
     * Score entry class for TableView.
     */
    public static class ScoreEntry implements Serializable {
        private static final long serialVersionUID = 1L;

        private int rank;
        private String name;
        private int score;
        private int level;

        public ScoreEntry(int rank, String name, int score, int level) {
            this.rank = rank;
            this.name = name;
            this.score = score;
            this.level = level;
        }

        public int getRank() { return rank; }
        public void setRank(int rank) { this.rank = rank; }
        public String getName() { return name; }
        public int getScore() { return score; }
        public int getLevel() { return level; }
    }
}
