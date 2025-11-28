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
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle("High Scores");
        dialog.setResizable(false);

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #1a0066, #000000);");

        // Title
        Label title = new Label("🏆 HIGH SCORES");
        title.setFont(Font.font("System", FontWeight.BOLD, 32));
        title.setTextFill(Color.web("#FFD700"));
        title.setStyle("-fx-effect: dropshadow(gaussian, #FFA500, 15, 0.7, 0, 0);");

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
        closeButton.setOnAction(e -> dialog.close());

        layout.getChildren().addAll(title, table, closeButton);

        Scene scene = new Scene(layout, 500, 550);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private TableView<ScoreEntry> createScoresTable() {
        TableView<ScoreEntry> table = new TableView<>();
        table.setPrefHeight(400);
        table.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        // Rank column
        TableColumn<ScoreEntry, Integer> rankCol = new TableColumn<>("Rank");
        rankCol.setCellValueFactory(new PropertyValueFactory<>("rank"));
        rankCol.setPrefWidth(60);
        rankCol.setStyle("-fx-alignment: CENTER;");

        // Name column
        TableColumn<ScoreEntry, String> nameCol = new TableColumn<>("Player");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(180);

        // Score column
        TableColumn<ScoreEntry, Integer> scoreCol = new TableColumn<>("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreCol.setPrefWidth(100);
        scoreCol.setStyle("-fx-alignment: CENTER;");

        // Level column
        TableColumn<ScoreEntry, Integer> levelCol = new TableColumn<>("Level");
        levelCol.setCellValueFactory(new PropertyValueFactory<>("level"));
        levelCol.setPrefWidth(80);
        levelCol.setStyle("-fx-alignment: CENTER;");

        table.getColumns().addAll(rankCol, nameCol, scoreCol, levelCol);

        // Load top 10 scores
        int rank = 1;
        for (ScoreEntry entry : scores) {
            entry.setRank(rank++);
            table.getItems().add(entry);
            if (rank > 10) break;
        }

        // If no scores, show placeholder
        if (scores.isEmpty()) {
            table.setPlaceholder(new Label("No scores yet. Be the first!"));
        }

        return table;
    }

    /**
     * Adds a new score to the high scores list.
     */
    public void addScore(String playerName, int score, int level) {
        scores.add(new ScoreEntry(0, playerName, score, level));
        Collections.sort(scores, (a, b) -> Integer.compare(b.getScore(), a.getScore()));

        // Keep only top 100
        if (scores.size() > 100) {
            scores = scores.subList(0, 100);
        }

        saveScores();
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

