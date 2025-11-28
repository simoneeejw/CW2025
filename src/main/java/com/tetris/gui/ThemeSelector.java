package com.tetris.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a1a, #000000);");

        // Try to load logo
        ImageView logo = null;
        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/tetris_logo.png"));
            logo = new ImageView(logoImage);
            logo.setFitWidth(200);
            logo.setPreserveRatio(true);
        } catch (Exception e) {
            // Logo not found, will display text title instead
            System.out.println("Logo not found. Using text title.");
        }

        // Title or Logo
        if (logo != null) {
            layout.getChildren().add(logo);
        }

        Label title = new Label("TETRIS");
        title.setFont(Font.font("System", FontWeight.BOLD, 36));
        title.setTextFill(Color.web("#00FFFF"));
        title.setStyle("-fx-effect: dropshadow(gaussian, #FF00FF, 15, 0.7, 0, 0);");

        Label subtitle = new Label("Choose Your Theme");
        subtitle.setFont(Font.font("System", FontWeight.NORMAL, 18));
        subtitle.setTextFill(Color.WHITE);

        // Theme buttons
        VBox themeButtons = new VBox(10);
        themeButtons.setAlignment(Pos.CENTER);

        for (Theme theme : Theme.values()) {
            Button themeButton = createThemeButton(theme);
            themeButtons.getChildren().add(themeButton);
        }

        Button startButton = new Button("▶ START GAME");
        startButton.setFont(Font.font("System", FontWeight.BOLD, 18));
        startButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 15 40; " +
                           "-fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0.5, 0, 2);");
        startButton.setOnAction(e -> dialog.close());

        // Add hover effect
        startButton.setOnMouseEntered(e -> startButton.setStyle(
            "-fx-background-color: #66BB6A; -fx-text-fill: white; -fx-padding: 15 40; " +
            "-fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(76,175,80,0.8), 15, 0.7, 0, 2);"));
        startButton.setOnMouseExited(e -> startButton.setStyle(
            "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 15 40; " +
            "-fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0.5, 0, 2);"));

        layout.getChildren().addAll(title, subtitle, themeButtons, startButton);

        Scene scene = new Scene(layout, 450, 500);
        dialog.setScene(scene);
        dialog.showAndWait();

        return selectedTheme;
    }

    private Button createThemeButton(Theme theme) {
        Button button = new Button("● " + theme.getDisplayName().toUpperCase());
        button.setFont(Font.font("System", FontWeight.BOLD, 16));
        button.setPrefWidth(280);
        button.setStyle(getUnselectedStyle(theme));

        button.setOnAction(e -> {
            selectedTheme = theme;
            // Update button styles to show selection
            updateButtonStyles(button, theme);
        });

        // Hover effect
        button.setOnMouseEntered(e -> {
            if (!theme.equals(selectedTheme)) {
                button.setStyle(getHoverStyle(theme));
            }
        });
        button.setOnMouseExited(e -> {
            if (!theme.equals(selectedTheme)) {
                button.setStyle(getUnselectedStyle(theme));
            }
        });

        return button;
    }

    private String getUnselectedStyle(Theme theme) {
        return "-fx-background-color: linear-gradient(to right, " +
               theme.getBoardGradientStart() + ", " + theme.getBoardGradientEnd() + "); " +
               "-fx-text-fill: " + theme.getUiAccentColor() + "; " +
               "-fx-border-color: " + theme.getPieceGlowColor() + "; " +
               "-fx-border-width: 2; -fx-padding: 15; -fx-background-radius: 8; " +
               "-fx-border-radius: 8;";
    }

    private String getHoverStyle(Theme theme) {
        return "-fx-background-color: linear-gradient(to right, " +
               theme.getBoardGradientStart() + ", " + theme.getBoardGradientEnd() + "); " +
               "-fx-text-fill: " + theme.getUiAccentColor() + "; " +
               "-fx-border-color: " + theme.getPieceGlowColor() + "; " +
               "-fx-border-width: 3; -fx-padding: 15; -fx-background-radius: 8; " +
               "-fx-border-radius: 8; -fx-effect: dropshadow(gaussian, " +
               theme.getPieceGlowColor() + ", 15, 0.6, 0, 0);";
    }

    private void updateButtonStyles(Button selectedButton, Theme theme) {
        selectedButton.setStyle("-fx-background-color: linear-gradient(to right, " +
                               theme.getBoardGradientStart() + ", " + theme.getBoardGradientEnd() + "); " +
                               "-fx-text-fill: " + theme.getUiAccentColor() + "; " +
                               "-fx-border-color: " + theme.getPieceGlowColor() + "; " +
                               "-fx-border-width: 4; -fx-padding: 15; -fx-background-radius: 8; " +
                               "-fx-border-radius: 8; -fx-effect: dropshadow(gaussian, " +
                               theme.getPieceGlowColor() + ", 20, 0.8, 0, 0); " +
                               "-fx-scale-x: 1.05; -fx-scale-y: 1.05;");
    }
}

