package com.tetris.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #A0B0D0, #8090B0);");

        // Title
        Label title = new Label("GAME CONTROLS");
        title.setFont(Font.font("System", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#2A3A6A"));

        // Controls grid
        GridPane controlsGrid = new GridPane();
        controlsGrid.setHgap(20);
        controlsGrid.setVgap(15);
        controlsGrid.setAlignment(Pos.CENTER);
        controlsGrid.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); " +
                "-fx-padding: 20; " +
                "-fx-border-color: #4A5A8A; " +
                "-fx-border-width: 3; " +
                "-fx-border-radius: 10; " +
                "-fx-background-radius: 10;");

        String[][] controls = {
            {"Move Left", "← or A"},
            {"Move Right", "→ or D"},
            {"Rotate", "↑ or W"},
            {"Soft Drop", "↓ or S"},
            {"Hard Drop", "SPACE"},
            {"Hold Piece", "C"},
            {"Pause", "ESC"},
            {"New Game", "N"}
        };

        for (int i = 0; i < controls.length; i++) {
            Label actionLabel = new Label(controls[i][0] + ":");
            actionLabel.setFont(Font.font("System", FontWeight.NORMAL, 16));
            actionLabel.setTextFill(Color.web("#2A3A6A"));

            Label keyLabel = new Label(controls[i][1]);
            keyLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
            keyLabel.setTextFill(Color.web("#4A5A8A"));
            keyLabel.setStyle(
                "-fx-background-color: #D0E0FF; " +
                "-fx-padding: 8 15; " +
                "-fx-background-radius: 5; " +
                "-fx-border-color: #4A5A8A; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 5;"
            );

            controlsGrid.add(actionLabel, 0, i);
            controlsGrid.add(keyLabel, 1, i);
        }

        // Game info
        VBox gameInfo = new VBox(10);
        gameInfo.setAlignment(Pos.CENTER_LEFT);
        gameInfo.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8); " +
                "-fx-padding: 15; " +
                "-fx-border-color: #4A5A8A; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 8; " +
                "-fx-background-radius: 8;");

        Label infoTitle = new Label("HOW TO PLAY:");
        infoTitle.setFont(Font.font("System", FontWeight.BOLD, 18));
        infoTitle.setTextFill(Color.web("#2A3A6A"));

        Label info1 = new Label("• Clear lines by filling them completely with blocks");
        Label info2 = new Label("• Clear 4 lines at once for a TETRIS!");
        Label info3 = new Label("• Progress through levels to increase difficulty");
        Label info4 = new Label("• Use Hold (C) to save a piece for later");
        Label info5 = new Label("• Power-ups appear randomly when clearing lines");

        info1.setFont(Font.font("System", FontWeight.NORMAL, 14));
        info2.setFont(Font.font("System", FontWeight.NORMAL, 14));
        info3.setFont(Font.font("System", FontWeight.NORMAL, 14));
        info4.setFont(Font.font("System", FontWeight.NORMAL, 14));
        info5.setFont(Font.font("System", FontWeight.NORMAL, 14));

        info1.setTextFill(Color.web("#2A3A6A"));
        info2.setTextFill(Color.web("#2A3A6A"));
        info3.setTextFill(Color.web("#2A3A6A"));
        info4.setTextFill(Color.web("#2A3A6A"));
        info5.setTextFill(Color.web("#2A3A6A"));

        gameInfo.getChildren().addAll(infoTitle, info1, info2, info3, info4, info5);

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

        mainLayout.getChildren().addAll(title, controlsGrid, gameInfo, closeButton);

        Scene scene = new Scene(mainLayout, 550, 650);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
}

