package com.tetris.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Main menu for the Tetris game with Quick Play, High Scores, Settings, and Play Game options.
 */
public class MainMenu {

    private Stage stage;
    private boolean startGame = false;
    private SettingsDialog settingsDialog;
    private HighScoresDialog highScoresDialog;
    private HelpDialog helpDialog;

    public MainMenu(Stage stage) {
        this.stage = stage;
        this.settingsDialog = new SettingsDialog();
        this.highScoresDialog = new HighScoresDialog();
        this.helpDialog = new HelpDialog();
    }

    /**
     * Shows the main menu.
     */
    public void show() {
        VBox mainLayout = new VBox(0);
        mainLayout.setPadding(new Insets(40));
        mainLayout.setAlignment(Pos.CENTER);
        // Set background image
        try {
            Image bgImage = new Image(getClass().getResourceAsStream("/background_image.png"));
            BackgroundImage bgImg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(820, 700, false, false, false, false));
            Background bg = new Background(bgImg);
            mainLayout.setBackground(bg);
        } catch (Exception e) {
            // Fallback to gradient if image not found
            mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #0a0033, #000000);");
        }

        // === HEADER SECTION ===
        VBox header = createHeader();

        // === QUICK ACTIONS SECTION ===
        VBox quickActions = createQuickActions();

        // === MAIN PLAY BUTTON ===
        Button playButton = createPlayButton();

        // === EXIT BUTTON ===
        Button exitButton = createExitButton();

        // Add spacing
        Region spacer1 = new Region();
        VBox.setVgrow(spacer1, Priority.NEVER);
        Region spacer2 = new Region();
        VBox.setVgrow(spacer2, Priority.ALWAYS);

        // === CREDITS FOOTER ===
        VBox creditsFooter = createCreditsFooter();

        mainLayout.getChildren().addAll(
                header,
                spacer1,
                playButton,
                quickActions,
                spacer2,
                exitButton,
                creditsFooter
        );

        // Apply custom margins for tighter spacing
        VBox.setMargin(playButton, new Insets(0, 0, 0, 0));  // Add some space below header
        VBox.setMargin(quickActions, new Insets(0, 0, 0, 0));

        Scene scene = new Scene(mainLayout, 820, 700);  // Match multiplayer window size
        stage.setScene(scene);
        stage.setTitle("TETRIS - Main Menu");
        stage.setMinWidth(820);  // Match multiplayer minimum width
        stage.setMinHeight(700);  // Match multiplayer minimum height
        stage.centerOnScreen();
        stage.show();
    }

    private VBox createHeader() {
        VBox header = new VBox(5); // Reduced spacing from 10 to 5 to bring elements closer
        header.setAlignment(Pos.CENTER);

        // Try to load logo
        ImageView logo = null;
        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/tetris_logo.png"));
            logo = new ImageView(logoImage);
            logo.setFitWidth(360);
            logo.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Logo not found. Using text title.");
        }

        if (logo != null) {
            header.getChildren().add(logo);
        } else {
            // Fallback text logo
            Label titleLabel = new Label("T E T R I S");
            titleLabel.setFont(Font.font("System", FontWeight.BOLD, 48));
            titleLabel.setTextFill(Color.web("#00FFFF"));
            titleLabel.setStyle("-fx-effect: dropshadow(gaussian, #FF00FF, 20, 0.8, 0, 0);");
            header.getChildren().add(titleLabel);
        }

        return header;
    }

    private VBox createQuickActions() {
        VBox quickActions = new VBox(20);
        quickActions.setAlignment(Pos.CENTER);
        quickActions.setPadding(new Insets(20));

        // High Scores Button
        Button scoresBtn = new Button("🏆 HIGH SCORES");
        scoresBtn.setFont(Font.font("System", FontWeight.BOLD, 24));
        scoresBtn.setPrefSize(300, 70);
        String baseBg = "linear-gradient(to bottom, #FFC107, #FFB300)";
        String hoverBg = "linear-gradient(to bottom, #FFECB3, #FFE082)";
        String shadowColorBase = "rgba(255,193,7,0.6)";
        String shadowColorHover = "#FFC107";
        scoresBtn.setStyle(
                "-fx-background-color: " + baseBg + "; " +
                        "-fx-text-fill: #000000; " +
                        "-fx-background-radius: 15; " +
                        "-fx-effect: dropshadow(gaussian, " + shadowColorBase + ", 15, 0.5, 0, 3);"
        );

        scoresBtn.setOnMouseEntered(e -> scoresBtn.setStyle(
                "-fx-background-color: " + hoverBg + "; " +
                        "-fx-text-fill: #000000; " +
                        "-fx-background-radius: 15; " +
                        "-fx-effect: dropshadow(gaussian, " + shadowColorHover + ", 25, 0.8, 0, 0); " +
                        "-fx-scale-x: 1.05; -fx-scale-y: 1.05;"
        ));

        scoresBtn.setOnMouseExited(e -> scoresBtn.setStyle(
                "-fx-background-color: " + baseBg + "; " +
                        "-fx-text-fill: #000000; " +
                        "-fx-background-radius: 15; " +
                        "-fx-effect: dropshadow(gaussian, " + shadowColorBase + ", 15, 0.5, 0, 3);"
        ));

        scoresBtn.setOnAction(e -> highScoresDialog.show(stage));

        // Settings Button
        Button settingsBtn = new Button("⚙ SETTINGS");
        settingsBtn.setFont(Font.font("System", FontWeight.BOLD, 24));
        settingsBtn.setPrefSize(300, 70);
        String baseBgS = "linear-gradient(to bottom, #2196F3, #1976D2)";
        String hoverBgS = "linear-gradient(to bottom, #BBDEFB, #90CAF9)";
        String shadowColorBaseS = "rgba(33,150,243,0.6)";
        String shadowColorHoverS = "#2196F3";
        settingsBtn.setStyle(
                "-fx-background-color: " + baseBgS + "; " +
                        "-fx-text-fill: #000000; " +
                        "-fx-background-radius: 15; " +
                        "-fx-effect: dropshadow(gaussian, " + shadowColorBaseS + ", 15, 0.5, 0, 3);"
        );

        settingsBtn.setOnMouseEntered(e -> settingsBtn.setStyle(
                "-fx-background-color: " + hoverBgS + "; " +
                        "-fx-text-fill: #000000; " +
                        "-fx-background-radius: 15; " +
                        "-fx-effect: dropshadow(gaussian, " + shadowColorHoverS + ", 25, 0.8, 0, 0); " +
                        "-fx-scale-x: 1.05; -fx-scale-y: 1.05;"
        ));

        settingsBtn.setOnMouseExited(e -> settingsBtn.setStyle(
                "-fx-background-color: " + baseBgS + "; " +
                        "-fx-text-fill: #000000; " +
                        "-fx-background-radius: 15; " +
                        "-fx-effect: dropshadow(gaussian, " + shadowColorBaseS + ", 15, 0.5, 0, 3);"
        ));

        settingsBtn.setOnAction(e -> {
            settingsDialog.show(stage);
        });

        // Help Button
        Button helpBtn = new Button("❓ HELP");
        helpBtn.setFont(Font.font("System", FontWeight.BOLD, 24));
        helpBtn.setPrefSize(300, 70);
        String baseBgH = "linear-gradient(to bottom, #4CAF50, #388E3C)";
        String hoverBgH = "linear-gradient(to bottom, #C8E6C9, #A5D6A7)";
        String shadowColorBaseH = "rgba(76,175,80,0.6)";
        String shadowColorHoverH = "#4CAF50";
        helpBtn.setStyle(
                "-fx-background-color: " + baseBgH + "; " +
                        "-fx-text-fill: #000000; " +
                        "-fx-background-radius: 15; " +
                        "-fx-effect: dropshadow(gaussian, " + shadowColorBaseH + ", 15, 0.5, 0, 3);"
        );

        helpBtn.setOnMouseEntered(e -> helpBtn.setStyle(
                "-fx-background-color: " + hoverBgH + "; " +
                        "-fx-text-fill: #000000; " +
                        "-fx-background-radius: 15; " +
                        "-fx-effect: dropshadow(gaussian, " + shadowColorHoverH + ", 25, 0.8, 0, 0); " +
                        "-fx-scale-x: 1.05; -fx-scale-y: 1.05;"
        ));

        helpBtn.setOnMouseExited(e -> helpBtn.setStyle(
                "-fx-background-color: " + baseBgH + "; " +
                        "-fx-text-fill: #000000; " +
                        "-fx-background-radius: 15; " +
                        "-fx-effect: dropshadow(gaussian, " + shadowColorBaseH + ", 15, 0.5, 0, 3);"
        ));

        helpBtn.setOnAction(e -> helpDialog.show(stage));

        quickActions.getChildren().addAll(scoresBtn, settingsBtn, helpBtn);

        return quickActions;
    }

    private Button createPlayButton() {
        Button playButton = new Button("▶ PLAY GAME");
        playButton.setFont(Font.font("System", FontWeight.BOLD, 24));
        playButton.setPrefSize(300, 70);
        playButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #FFD700, #FFA500); " +
                        "-fx-text-fill: #000000; " +
                        "-fx-background-radius: 15; " +
                        "-fx-effect: dropshadow(gaussian, rgba(255,215,0,0.6), 15, 0.5, 0, 3);"
        );

        playButton.setOnMouseEntered(e -> playButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #FFED4E, #FFB84D); " +
                        "-fx-text-fill: #000000; " +
                        "-fx-background-radius: 15; " +
                        "-fx-effect: dropshadow(gaussian, #FFD700, 25, 0.8, 0, 0); " +
                        "-fx-scale-x: 1.05; -fx-scale-y: 1.05;"
        ));

        playButton.setOnMouseExited(e -> playButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #FFD700, #FFA500); " +
                        "-fx-text-fill: #000000; " +
                        "-fx-background-radius: 15; " +
                        "-fx-effect: dropshadow(gaussian, rgba(255,215,0,0.6), 15, 0.5, 0, 3);"
        ));

        playButton.setOnAction(e -> {
            startGame = true;
            loadGame();
        });

        return playButton;
    }

    private Button createExitButton() {
        Button exitButton = new Button("EXIT");
        exitButton.setFont(Font.font("System", FontWeight.NORMAL, 14));
        exitButton.setPrefSize(300, 35);  // Match width of other buttons for consistent centering
        exitButton.setAlignment(Pos.CENTER);  // Center text horizontally
        exitButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: #ffffff; " +
                        "-fx-border-color: #ffffff; " +
                        "-fx-border-width: 1; " +
                        "-fx-border-radius: 5; " +
                        "-fx-background-radius: 5;"
        );

        exitButton.setOnMouseEntered(e -> exitButton.setStyle(
                "-fx-background-color: #FF5252; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-color: #FF5252; " +
                        "-fx-border-width: 1; " +
                        "-fx-border-radius: 5; " +
                        "-fx-background-radius: 5;"
        ));

        exitButton.setOnMouseExited(e -> exitButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: #ffffff; " +
                        "-fx-border-color: #ffffff; " +
                        "-fx-border-width: 1; " +
                        "-fx-border-radius: 5; " +
                        "-fx-background-radius: 5;"
        ));

        exitButton.setOnAction(e -> System.exit(0));

        return exitButton;
    }

    private VBox createCreditsFooter() {
        VBox footer = new VBox(5);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10, 0, 10, 0));

        Label credits = new Label("COMP2042 - Coursework 2025");
        credits.setFont(Font.font("System", FontWeight.NORMAL, 11));
        credits.setTextFill(Color.web("#ffffff"));
        credits.setCursor(javafx.scene.Cursor.HAND);
        credits.setOnMouseClicked(e -> showCredits());

        credits.setOnMouseEntered(e -> credits.setTextFill(Color.web("#AAAAAA")));
        credits.setOnMouseExited(e -> credits.setTextFill(Color.web("#666666")));

        footer.getChildren().add(credits);

        return footer;
    }

    private void showCredits() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Credits");
        alert.setHeaderText("TETRIS - COMP2042 Coursework 2025");
        alert.setContentText(
                "Developed by: Yau Jia Wei\n" +
                        "Student ID: 20718629\n\n" +
                        "University: University of Nottingham Malaysia\n" +
                        "© 2025 All Rights Reserved"
        );
        alert.initOwner(stage);
        alert.showAndWait();
    }

    /**
     * Returns whether the user wants to start the game.
     */
    public boolean isStartGame() {
        return startGame;
    }



    /**
     * Loads the game after menu selection.
     */
    private void loadGame() {
        try {
            // Load single player game
            java.net.URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
            javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(location, null);
            javafx.scene.Parent root = fxmlLoader.load();
            GuiController c = fxmlLoader.getController();
            c.setGameController(new com.tetris.game.GameController(c));

            // Set callbacks for game over dialog
            c.setOnRestartCallback(() -> c.newGame(null));
            c.setOnMainMenuCallback(() -> show());

            stage.setTitle("TETRIS - COMP2042");
            javafx.scene.Scene scene = new javafx.scene.Scene(root, 450, 740);  // Fit content: 220px board + 150px sidebar + 20px spacing + 40px padding + title/footer
            stage.setScene(scene);
            stage.setMinWidth(450);
            stage.setMinHeight(740);
            stage.centerOnScreen();

            // Start background music
            com.tetris.util.SoundManager.getInstance().playBackgroundMusic();

            // Apply theme to GUI
            ThemeManager.getInstance().applyTheme(c);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load game");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
