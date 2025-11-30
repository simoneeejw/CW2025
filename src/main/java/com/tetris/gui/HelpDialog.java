package com.tetris.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Help dialog showing game controls and instructions.
 */
public class HelpDialog {

    public void show(Stage owner) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle("Help - Controls");
        dialog.setResizable(false);

        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(25));
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setStyle("-fx-background-color: #2A3A6A;");

        // Title
        Label title = new Label("HOW TO PLAY TETRIS:");
        title.setFont(Font.font("System", FontWeight.BOLD, 28));
        title.setTextFill(Color.WHITE);

        // Game info
        VBox gameInfo = new VBox(10);
        gameInfo.setAlignment(Pos.CENTER_LEFT);
        gameInfo.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); " +
                "-fx-padding: 15; " +
                "-fx-border-color: #4A5A8A; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 8; " +
                "-fx-background-radius: 8;");

        Label intro = new Label("Tetris is a puzzle game where you arrange falling blocks (tetrominoes) to clear lines.");
        Label objective = new Label("Objective: Clear as many lines as possible to achieve high scores!");
        Label step1 = new Label("1. Use arrow keys or WASD to move and rotate pieces.");
        Label step2 = new Label("2. Fill entire rows horizontally to clear them.");
        Label step3 = new Label("3. Clearing multiple lines at once gives bonus points.");
        Label step4 = new Label("4. Clear 4 lines in one go for a TETRIS - the highest score!");
        Label step5 = new Label("5. As you progress, the game speeds up - stay focused!");
        Label step6 = new Label("6. Use the Hold feature (C key) to save a piece for later.");
        Label step7 = new Label("7. Power-ups may appear when clearing lines - collect them!");
        Label step8 = new Label("8. Pause the game with ESC if you need a break.");
        Label tipsTitle = new Label("TIPS:");
        Label tip1 = new Label("• Plan ahead: Think about where the next pieces will fit.");
        Label tip2 = new Label("• Clear lines efficiently to avoid building up too high.");
        Label tip3 = new Label("• Practice makes perfect - keep playing to improve!");

        // Base styling for all labels: dark text for high contrast on light background
        Label[] labels = {intro, objective, step1, step2, step3, step4, step5, step6, step7, step8, tip1, tip2, tip3};
        for (Label label : labels) {
            label.setFont(Font.font("System", FontWeight.NORMAL, 14));
            label.setTextFill(Color.BLACK);  // Changed from Color.RED to Color.BLACK for better visibility
            label.setStyle("-fx-text-fill: black;");  // Force black text to prevent CSS override
            label.setWrapText(true);
        }

        // Special styling for tipsTitle: bold and black for consistency
        tipsTitle.setFont(Font.font("System", FontWeight.BOLD, 14));
        tipsTitle.setTextFill(Color.BLACK);  // Changed to black for consistency
        tipsTitle.setStyle("-fx-text-fill: black;");  // Force black text
        tipsTitle.setWrapText(true);

        gameInfo.getChildren().addAll(intro, objective, step1, step2, step3, step4, step5, step6, step7, step8, tipsTitle, tip1, tip2, tip3);

        // Close button
        Button closeButton = new Button("GOT IT!");
        closeButton.setFont(Font.font("System", FontWeight.BOLD, 16));
        closeButton.setPrefSize(150, 45);
        closeButton.setStyle(
                "-fx-background-color: #4A5A8A; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-color: #2A3A6A; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 8;"
        );

        closeButton.setOnMouseEntered(e -> closeButton.setStyle(
                "-fx-background-color: #6A7AAA; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-color: #2A3A6A; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 8; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0.5, 0, 0);"
        ));

        closeButton.setOnMouseExited(e -> closeButton.setStyle(
                "-fx-background-color: #4A5A8A; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-color: #2A3A6A; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 8;"
        ));

        closeButton.setOnAction(e -> dialog.close());

        mainLayout.getChildren().addAll(title, gameInfo, closeButton);

        // ScrollPane for making the dialog scrollable
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background: transparent;");

        Scene scene = new Scene(scrollPane, 600, 700);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
}