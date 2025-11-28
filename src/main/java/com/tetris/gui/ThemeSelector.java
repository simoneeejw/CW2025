package com.tetris.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Theme selection dialog shown at game start.
 * Allows players to choose from available visual themes.
 */
public class ThemeSelector {

    private Theme selectedTheme = Theme.NEON_NIGHT;

    /**
     * Shows the theme selection dialog.
     * @param owner The owner stage
     * @return The selected theme, or null if cancelled
     */
    public Theme showAndWait(Stage owner) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle("Select Theme");
        dialog.setResizable(false);

        // Create UI
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a1a, #000000);");

        Label title = new Label("Choose Your Theme");
        title.setFont(Font.font("System", FontWeight.BOLD, 24));
        title.setTextFill(Color.WHITE);

        // Theme buttons
        VBox themeButtons = new VBox(10);
        themeButtons.setAlignment(Pos.CENTER);

        for (Theme theme : Theme.values()) {
            Button themeButton = createThemeButton(theme);
            themeButtons.getChildren().add(themeButton);
        }

        Button startButton = new Button("Start Game");
        startButton.setFont(Font.font("System", FontWeight.BOLD, 16));
        startButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10 20;");
        startButton.setOnAction(e -> dialog.close());

        layout.getChildren().addAll(title, themeButtons, startButton);

        Scene scene = new Scene(layout, 400, 350);
        dialog.setScene(scene);
        dialog.showAndWait();

        return selectedTheme;
    }

    private Button createThemeButton(Theme theme) {
        Button button = new Button(theme.getDisplayName());
        button.setFont(Font.font("System", FontWeight.NORMAL, 16));
        button.setPrefWidth(200);
        button.setStyle("-fx-background-color: " + theme.getBoardGradientStart() + "; " +
                       "-fx-text-fill: " + theme.getUiAccentColor() + "; " +
                       "-fx-border-color: " + theme.getPieceGlowColor() + "; " +
                       "-fx-border-width: 2; -fx-padding: 10;");

        button.setOnAction(e -> {
            selectedTheme = theme;
            // Update button styles to show selection
            updateButtonStyles(button, theme);
        });

        return button;
    }

    private void updateButtonStyles(Button selectedButton, Theme theme) {
        // This would update all buttons to show which one is selected
        // For simplicity, we'll just change the selected button's style
        selectedButton.setStyle("-fx-background-color: " + theme.getBoardGradientEnd() + "; " +
                               "-fx-text-fill: " + theme.getUiAccentColor() + "; " +
                               "-fx-border-color: " + theme.getPieceGlowColor() + "; " +
                               "-fx-border-width: 3; -fx-padding: 10; -fx-effect: dropshadow(gaussian, " +
                               theme.getPieceGlowColor() + ", 10, 0.5, 0, 0);");
    }
}

