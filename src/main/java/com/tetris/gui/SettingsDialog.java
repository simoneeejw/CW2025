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
 * Settings dialog for volume and key bindings.
 */
public class SettingsDialog {

    private Preferences prefs;

    public SettingsDialog() {
        prefs = Preferences.userNodeForPackage(SettingsDialog.class);
    }

    /**
     * Shows the settings dialog.
     * @param owner The owner stage
     */
    public void show(Stage owner) {
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

        // Audio Tab
        Tab audioTab = new Tab("Audio");
        audioTab.setContent(createAudioPanel());

        // Controls Tab
        Tab controlsTab = new Tab("Controls");
        controlsTab.setContent(createControlsPanel());

        tabPane.getTabs().addAll(audioTab, controlsTab);

        // Buttons
        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(15, 0, 0, 0));

        Button saveButton = new Button("SAVE");
        styleButton(saveButton, "#4CAF50", "#66BB6A");
        saveButton.setOnAction(e -> {
            dialog.close();
        });

        Button cancelButton = new Button("CANCEL");
        styleButton(cancelButton, "#F44336", "#EF5350");
        cancelButton.setOnAction(e -> dialog.close());

        buttons.getChildren().addAll(saveButton, cancelButton);

        mainLayout.getChildren().addAll(title, tabPane, buttons);

        Scene scene = new Scene(mainLayout, 550, 550);
        dialog.setScene(scene);
        dialog.showAndWait();
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

        // Background Music Volume
        HBox musicBox = new HBox(15);
        musicBox.setAlignment(Pos.CENTER_LEFT);
        Label musicLabel = new Label("Background Music:");
        musicLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
        musicLabel.setTextFill(Color.WHITE);
        musicLabel.setPrefWidth(150);

        Label musicValueLabel = new Label();
        musicValueLabel.setTextFill(Color.web("#FFD700"));

        Slider musicSlider = new Slider(0, 100, prefs.getDouble("music_volume", 30));
        musicSlider.setPrefWidth(200);
        musicSlider.setShowTickLabels(true);
        musicSlider.setShowTickMarks(true);
        musicSlider.setMajorTickUnit(25);
        musicSlider.setBlockIncrement(5);
        musicSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            prefs.putDouble("music_volume", newVal.doubleValue());
            com.tetris.util.SoundManager.getInstance().setMusicVolume(newVal.doubleValue() / 100.0);
            musicValueLabel.setText(String.format("%.0f%%", newVal));
        });

        musicValueLabel.setText(String.format("%.0f%%", musicSlider.getValue()));

        musicBox.getChildren().addAll(musicLabel, musicSlider, musicValueLabel);

        // Sound Effects Volume
        HBox sfxBox = new HBox(15);
        sfxBox.setAlignment(Pos.CENTER_LEFT);
        Label sfxLabel = new Label("Sound Effects:");
        sfxLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
        sfxLabel.setTextFill(Color.WHITE);
        sfxLabel.setPrefWidth(150);

        Label sfxValueLabel = new Label();
        sfxValueLabel.setTextFill(Color.web("#FFD700"));

        Slider sfxSlider = new Slider(0, 100, prefs.getDouble("sound_effects_volume", 70));
        sfxSlider.setPrefWidth(200);
        sfxSlider.setShowTickLabels(true);
        sfxSlider.setShowTickMarks(true);
        sfxSlider.setMajorTickUnit(25);
        sfxSlider.setBlockIncrement(5);
        sfxSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            prefs.putDouble("sound_effects_volume", newVal.doubleValue());
            com.tetris.util.SoundManager.getInstance().setSoundEffectsVolume(newVal.doubleValue() / 100.0);
            sfxValueLabel.setText(String.format("%.0f%%", newVal));
        });

        sfxValueLabel.setText(String.format("%.0f%%", sfxSlider.getValue()));

        sfxBox.getChildren().addAll(sfxLabel, sfxSlider, sfxValueLabel);

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
}
