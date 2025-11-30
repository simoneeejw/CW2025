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
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e, #0f3460); " +
                "-fx-border-color: #e94560; " +
                "-fx-border-width: 3; " +
                "-fx-border-radius: 15; " +
                "-fx-background-radius: 15;");

        // Title
        Label title = new Label("📚 TUTORIAL 📚");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        title.setTextFill(Color.web("#FFD700"));
        title.setStyle("-fx-effect: dropshadow(gaussian, #FFA500, 20, 0.8, 2, 2); " +
                "-fx-background-color: rgba(0, 0, 0, 0.5); " +
                "-fx-padding: 10 20; " +
                "-fx-background-radius: 10;");

        // Game info
        VBox gameInfo = new VBox(10);
        gameInfo.setAlignment(Pos.CENTER_LEFT);
        gameInfo.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); " +
                "-fx-padding: 20; " +
                "-fx-border-color: #FFD700; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 10; " +
                "-fx-background-radius: 10; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0.5, 0, 0);");

        Label intro = new Label("Tetris is a puzzle game where you arrange falling blocks (tetrominoes) to clear lines.");
        Label objective = new Label("🎯 Objective: Clear as many lines as possible to achieve high scores!");
        Label step1 = new Label("1. Use arrow keys or WASD to move and rotate pieces.");
        Label step2 = new Label("2. Fill entire rows horizontally to clear them.");
        Label step3 = new Label("3. Clearing multiple lines at once gives bonus points.");
        Label step4 = new Label("4. Clear 4 lines in one go for a TETRIS - the highest score!");
        Label step5 = new Label("5. As you progress, the game speeds up - stay focused!");
        Label step6 = new Label("6. Use the Hold feature (R key) to save a piece for later.");
        Label step7 = new Label("7. Power-ups may appear when clearing lines - collect them!");
        Label step8 = new Label("8. Pause the game with ESC if you need a break.");
        Label tipsTitle = new Label("💡 TIPS:");
        Label tip1 = new Label("• Plan ahead: Think about where the next pieces will fit.");
        Label tip2 = new Label("• Clear lines efficiently to avoid building up too high.");
        Label tip3 = new Label("• Practice makes perfect - keep playing to improve!");

        // Base styling for all labels: white text for visibility on dark background
        Label[] labels = {intro, objective, step1, step2, step3, step4, step5, step6, step7, step8, tip1, tip2, tip3};
        for (Label label : labels) {
            label.setFont(Font.font("System", FontWeight.NORMAL, 14));
            label.setTextFill(Color.WHITE);
            label.setWrapText(true);
        }

        // Special styling for tipsTitle: bold and gold for emphasis
        tipsTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        tipsTitle.setTextFill(Color.web("#FFD700"));
        tipsTitle.setWrapText(true);

        gameInfo.getChildren().addAll(intro, objective, step1, step2, step3, step4, step5, step6, step7, step8, tipsTitle, tip1, tip2, tip3);

        // Close button
        Button closeButton = new Button("GOT IT!");
        closeButton.setFont(Font.font("System", FontWeight.BOLD, 16));
        closeButton.setPrefSize(150, 45);
        closeButton.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-color: #66BB6A; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 8;"
        );

        closeButton.setOnMouseEntered(e -> closeButton.setStyle(
                "-fx-background-color: #66BB6A; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-color: #66BB6A; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 8; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0.5, 0, 0);"
        ));

        closeButton.setOnMouseExited(e -> closeButton.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-color: #66BB6A; " +
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