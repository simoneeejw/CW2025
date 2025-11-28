package com.tetris.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

/**
 * Settings dialog for theme selection, volume, and key bindings.
 */
public class SettingsDialog {

    private Theme selectedTheme;
    private Preferences prefs;

    public SettingsDialog() {
        prefs = Preferences.userNodeForPackage(SettingsDialog.class);
    }

    /**
     * Shows the settings dialog.
     * @param owner The owner stage
     * @param currentTheme The current theme
     * @return The selected theme, or null if cancelled
     */
    public Theme show(Stage owner, Theme currentTheme) {
        selectedTheme = currentTheme;

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle("Settings");
        dialog.setResizable(false);

        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(25));
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setStyle("-fx-background-color: #1a1a1a;");

        // Title
        Label title = new Label("⚙ SETTINGS");
        title.setFont(Font.font("System", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#00FFFF"));

        // Create tabs
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setPrefSize(500, 400);

        // Theme Tab
        Tab themeTab = new Tab("Theme");
        themeTab.setContent(createThemePanel());

        // Audio Tab
        Tab audioTab = new Tab("Audio");
        audioTab.setContent(createAudioPanel());

        // Controls Tab
        Tab controlsTab = new Tab("Controls");
        controlsTab.setContent(createControlsPanel());

        tabPane.getTabs().addAll(themeTab, audioTab, controlsTab);

        // Buttons
        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(15, 0, 0, 0));

        Button saveButton = new Button("SAVE");
        styleButton(saveButton, "#4CAF50", "#66BB6A");
        saveButton.setOnAction(e -> {
            saveSettings();
            dialog.close();
        });

        Button cancelButton = new Button("CANCEL");
        styleButton(cancelButton, "#F44336", "#EF5350");
        cancelButton.setOnAction(e -> {
            selectedTheme = null;
            dialog.close();
        });

        buttons.getChildren().addAll(saveButton, cancelButton);

        mainLayout.getChildren().addAll(title, tabPane, buttons);

        Scene scene = new Scene(mainLayout, 550, 550);
        dialog.setScene(scene);
        dialog.showAndWait();

        return selectedTheme;
    }

    private VBox createThemePanel() {
        VBox themePanel = new VBox(15);
        themePanel.setPadding(new Insets(20));
        themePanel.setAlignment(Pos.TOP_CENTER);
        themePanel.setStyle("-fx-background-color: #2a2a2a;");

        Label instruction = new Label("Select your preferred visual theme:");
        instruction.setFont(Font.font("System", FontWeight.NORMAL, 14));
        instruction.setTextFill(Color.WHITE);

        ToggleGroup themeGroup = new ToggleGroup();
        VBox radioContainer = new VBox(10);

        for (Theme theme : Theme.values()) {
            HBox themeBox = createThemeOption(theme, themeGroup);
            radioContainer.getChildren().add(themeBox);
        }

        themePanel.getChildren().addAll(instruction, radioContainer);

        return themePanel;
    }

    private HBox createThemeOption(Theme theme, ToggleGroup group) {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(10));
        box.setPrefWidth(400);
        box.setStyle(
            "-fx-background-color: linear-gradient(to right, " +
            theme.getBoardGradientStart() + ", " + theme.getBoardGradientEnd() + "); " +
            "-fx-border-color: " + theme.getPieceGlowColor() + "; " +
            "-fx-border-width: 2; " +
            "-fx-border-radius: 8; " +
            "-fx-background-radius: 8;"
        );

        RadioButton radio = new RadioButton();
        radio.setToggleGroup(group);
        radio.setUserData(theme);
        if (theme == selectedTheme) {
            radio.setSelected(true);
        }

        VBox textBox = new VBox(5);
        Label themeName = new Label(theme.getDisplayName());
        themeName.setFont(Font.font("System", FontWeight.BOLD, 16));
        themeName.setTextFill(Color.web(theme.getUiAccentColor()));

        Label themeDesc = new Label(getThemeDescription(theme));
        themeDesc.setFont(Font.font("System", FontWeight.NORMAL, 11));
        themeDesc.setTextFill(Color.web(theme.getUiAccentColor()));
        themeDesc.setOpacity(0.8);

        textBox.getChildren().addAll(themeName, themeDesc);

        box.getChildren().addAll(radio, textBox);
        box.setCursor(javafx.scene.Cursor.HAND);
        box.setOnMouseClicked(e -> {
            radio.setSelected(true);
            selectedTheme = theme;
        });

        radio.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                selectedTheme = theme;
                box.setStyle(
                    "-fx-background-color: linear-gradient(to right, " +
                    theme.getBoardGradientStart() + ", " + theme.getBoardGradientEnd() + "); " +
                    "-fx-border-color: " + theme.getPieceGlowColor() + "; " +
                    "-fx-border-width: 4; " +
                    "-fx-border-radius: 8; " +
                    "-fx-background-radius: 8; " +
                    "-fx-effect: dropshadow(gaussian, " + theme.getPieceGlowColor() + ", 15, 0.7, 0, 0);"
                );
            } else {
                box.setStyle(
                    "-fx-background-color: linear-gradient(to right, " +
                    theme.getBoardGradientStart() + ", " + theme.getBoardGradientEnd() + "); " +
                    "-fx-border-color: " + theme.getPieceGlowColor() + "; " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 8; " +
                    "-fx-background-radius: 8;"
                );
            }
        });

        return box;
    }

    private String getThemeDescription(Theme theme) {
        switch (theme) {
            case CLASSIC_RETRO:
                return "Dark theme with golden accents - Classic arcade feel";
            case NEON_NIGHT:
                return "Cyberpunk vibes with cyan and magenta glows";
            case ZEN_MINIMAL:
                return "Clean, light theme for distraction-free play";
            default:
                return "";
        }
    }

    private VBox createAudioPanel() {
        VBox audioPanel = new VBox(20);
        audioPanel.setPadding(new Insets(20));
        audioPanel.setAlignment(Pos.TOP_CENTER);
        audioPanel.setStyle("-fx-background-color: #2a2a2a;");

        Label title = new Label("Audio Settings");
        title.setFont(Font.font("System", FontWeight.BOLD, 18));
        title.setTextFill(Color.web("#00FFFF"));

        VBox controls = new VBox(15);
        controls.setAlignment(Pos.TOP_LEFT);

        // Background Music Toggle
        HBox musicBox = new HBox(15);
        musicBox.setAlignment(Pos.CENTER_LEFT);
        Label musicLabel = new Label("Background Music:");
        musicLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
        musicLabel.setTextFill(Color.WHITE);
        musicLabel.setPrefWidth(150);

        CheckBox musicCheckBox = new CheckBox();
        musicCheckBox.setSelected(prefs.getBoolean("music_enabled", true));
        musicCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            prefs.putBoolean("music_enabled", newVal);
            com.tetris.util.SoundManager.getInstance().setMusicEnabled(newVal);
        });

        musicBox.getChildren().addAll(musicLabel, musicCheckBox);

        // Sound Effects Toggle
        HBox sfxBox = new HBox(15);
        sfxBox.setAlignment(Pos.CENTER_LEFT);
        Label sfxLabel = new Label("Sound Effects:");
        sfxLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
        sfxLabel.setTextFill(Color.WHITE);
        sfxLabel.setPrefWidth(150);

        CheckBox sfxCheckBox = new CheckBox();
        sfxCheckBox.setSelected(prefs.getBoolean("sound_effects_enabled", true));
        sfxCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            prefs.putBoolean("sound_effects_enabled", newVal);
            com.tetris.util.SoundManager.getInstance().setSoundEffectsEnabled(newVal);
        });

        sfxBox.getChildren().addAll(sfxLabel, sfxCheckBox);

        controls.getChildren().addAll(musicBox, sfxBox);

        audioPanel.getChildren().addAll(title, controls);

        return audioPanel;
    }

    private VBox createControlsPanel() {
        VBox controlsPanel = new VBox(15);
        controlsPanel.setPadding(new Insets(20));
        controlsPanel.setAlignment(Pos.TOP_CENTER);
        controlsPanel.setStyle("-fx-background-color: #2a2a2a;");

        Label title = new Label("Keyboard Controls");
        title.setFont(Font.font("System", FontWeight.BOLD, 18));
        title.setTextFill(Color.web("#00FFFF"));

        GridPane controlsGrid = new GridPane();
        controlsGrid.setHgap(20);
        controlsGrid.setVgap(12);
        controlsGrid.setAlignment(Pos.CENTER);

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
            actionLabel.setFont(Font.font("System", FontWeight.NORMAL, 13));
            actionLabel.setTextFill(Color.WHITE);

            Label keyLabel = new Label(controls[i][1]);
            keyLabel.setFont(Font.font("System", FontWeight.BOLD, 13));
            keyLabel.setTextFill(Color.web("#FFD700"));
            keyLabel.setStyle(
                "-fx-background-color: #333333; " +
                "-fx-padding: 5 10; " +
                "-fx-background-radius: 5;"
            );

            controlsGrid.add(actionLabel, 0, i);
            controlsGrid.add(keyLabel, 1, i);
        }

        Label note = new Label("Note: Key bindings are currently fixed");
        note.setFont(Font.font("System", FontWeight.NORMAL, 11));
        note.setTextFill(Color.web("#888888"));

        controlsPanel.getChildren().addAll(title, controlsGrid, note);

        return controlsPanel;
    }

    private void styleButton(Button button, String baseColor, String hoverColor) {
        button.setFont(Font.font("System", FontWeight.BOLD, 14));
        button.setPrefSize(120, 40);
        button.setStyle(
            "-fx-background-color: " + baseColor + "; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8;"
        );

        button.setOnMouseEntered(e -> button.setStyle(
            "-fx-background-color: " + hoverColor + "; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, " + baseColor + ", 10, 0.6, 0, 0);"
        ));

        button.setOnMouseExited(e -> button.setStyle(
            "-fx-background-color: " + baseColor + "; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8;"
        ));
    }

    private void saveSettings() {
        // Save theme
        ThemeManager.getInstance().setCurrentTheme(selectedTheme);
    }
}

