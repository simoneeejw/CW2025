package com.tetris.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private KeyCode moveLeftKey = KeyCode.LEFT;
    private KeyCode moveRightKey = KeyCode.RIGHT;
    private KeyCode rotateKey = KeyCode.UP;
    private KeyCode softDropKey = KeyCode.DOWN;
    private KeyCode hardDropKey = KeyCode.SPACE;
    private KeyCode holdKey = KeyCode.R;
    private String changingAction = null;
    private Button currentButton = null;
    private Label moveLeftLabel, moveRightLabel, rotateLabel, softDropLabel, hardDropLabel, holdLabel;

    public SettingsDialog() {
        prefs = Preferences.userNodeForPackage(SettingsDialog.class);
    }

    /**
     * Shows the settings dialog.
     * @param owner The owner stage
     */
    public void show(Stage owner) {
        // Load current key bindings
        moveLeftKey = KeyCode.valueOf(prefs.get("moveLeft", "LEFT"));
        moveRightKey = KeyCode.valueOf(prefs.get("moveRight", "RIGHT"));
        rotateKey = KeyCode.valueOf(prefs.get("rotate", "UP"));
        softDropKey = KeyCode.valueOf(prefs.get("softDrop", "DOWN"));
        hardDropKey = KeyCode.valueOf(prefs.get("hardDrop", "SPACE"));
        holdKey = KeyCode.valueOf(prefs.get("hold", "R"));

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle("Settings");
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
        Label title = new Label("⚙ SETTINGS ⚙");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        title.setTextFill(Color.web("#FFD700"));
        title.setStyle("-fx-effect: dropshadow(gaussian, #FFA500, 20, 0.8, 2, 2); " +
                "-fx-background-color: rgba(0, 0, 0, 0.5); " +
                "-fx-padding: 10 20; " +
                "-fx-background-radius: 10;");

        // Option buttons
        HBox optionButtons = new HBox(20);
        optionButtons.setAlignment(Pos.CENTER);
        optionButtons.setPadding(new Insets(10));

        Button audioButton = new Button("🔊 Audio");
        audioButton.setFont(Font.font("System", FontWeight.BOLD, 18));
        audioButton.setPrefSize(150, 40);
        audioButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #000000; -fx-background-radius: 10; -fx-border-color: #FFA500; -fx-border-width: 2; -fx-border-radius: 10;");
        audioButton.setOnMouseEntered(e -> audioButton.setStyle("-fx-background-color: #FFECB3; -fx-text-fill: #000000; -fx-background-radius: 10; -fx-border-color: #FFA500; -fx-border-width: 2; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 5, 0.5, 0, 0);"));
        audioButton.setOnMouseExited(e -> audioButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #000000; -fx-background-radius: 10; -fx-border-color: #FFA500; -fx-border-width: 2; -fx-border-radius: 10;"));

        Button controlsButton = new Button("🎮 Controls");
        controlsButton.setFont(Font.font("System", FontWeight.BOLD, 18));
        controlsButton.setPrefSize(150, 40);
        controlsButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-text-fill: #FFD700; -fx-background-radius: 10; -fx-border-color: #FFD700; -fx-border-width: 2; -fx-border-radius: 10;");
        controlsButton.setOnMouseEntered(e -> controlsButton.setStyle("-fx-background-color: rgba(255, 215, 0, 0.3); -fx-text-fill: #FFD700; -fx-background-radius: 10; -fx-border-color: #FFD700; -fx-border-width: 2; -fx-border-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 5, 0.5, 0, 0);"));
        controlsButton.setOnMouseExited(e -> controlsButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-text-fill: #FFD700; -fx-background-radius: 10; -fx-border-color: #FFD700; -fx-border-width: 2; -fx-border-radius: 10;"));

        optionButtons.getChildren().addAll(audioButton, controlsButton);

        // Content area
        VBox contentArea = new VBox();
        contentArea.setPrefSize(500, 350);
        contentArea.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); " +
                "-fx-border-color: #FFD700; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 10; " +
                "-fx-background-radius: 10;");

        // Default to audio
        contentArea.getChildren().setAll(createAudioPanelContent());

        // Button actions
        audioButton.setOnAction(e -> {
            com.tetris.util.SoundManager.getInstance().playButtonClickSound();
            contentArea.getChildren().setAll(createAudioPanelContent());
            audioButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #000000; -fx-background-radius: 10; -fx-border-color: #FFA500; -fx-border-width: 2; -fx-border-radius: 10;");
            controlsButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-text-fill: #FFD700; -fx-background-radius: 10; -fx-border-color: #FFD700; -fx-border-width: 2; -fx-border-radius: 10;");
        });

        controlsButton.setOnAction(e -> {
            com.tetris.util.SoundManager.getInstance().playButtonClickSound();
            contentArea.getChildren().setAll(createControlsPanelContent());
            controlsButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #000000; -fx-background-radius: 10; -fx-border-color: #FFA500; -fx-border-width: 2; -fx-border-radius: 10;");
            audioButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-text-fill: #FFD700; -fx-background-radius: 10; -fx-border-color: #FFD700; -fx-border-width: 2; -fx-border-radius: 10;");
        });

        // Buttons
        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(15, 0, 0, 0));

        Button saveButton = new Button("SAVE");
        styleButton(saveButton, "#4CAF50", "#66BB6A");
        saveButton.setOnAction(e -> {
            com.tetris.util.SoundManager.getInstance().playButtonClickSound();
            dialog.close();
        });

        Button cancelButton = new Button("CANCEL");
        styleButton(cancelButton, "#F44336", "#EF5350");
        cancelButton.setOnAction(e -> {
            com.tetris.util.SoundManager.getInstance().playButtonClickSound();
            dialog.close();
        });

        buttons.getChildren().addAll(saveButton, cancelButton);

        mainLayout.getChildren().addAll(title, optionButtons, contentArea, buttons);

        Scene scene = new Scene(mainLayout, 550, 550);
        dialog.setScene(scene);

        // Request focus on the scene to ensure key events are captured
        scene.getRoot().requestFocus();

        // Handle key binding changes
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (changingAction != null) {
                KeyCode newKey = event.getCode();
                switch (changingAction) {
                    case "moveLeft":
                        moveLeftKey = newKey;
                        prefs.put("moveLeft", newKey.name());
                        moveLeftLabel.setText("Move Left: " + newKey.getName());
                        break;
                    case "moveRight":
                        moveRightKey = newKey;
                        prefs.put("moveRight", newKey.name());
                        moveRightLabel.setText("Move Right: " + newKey.getName());
                        break;
                    case "rotate":
                        rotateKey = newKey;
                        prefs.put("rotate", newKey.name());
                        rotateLabel.setText("Rotate: " + newKey.getName());
                        break;
                    case "softDrop":
                        softDropKey = newKey;
                        prefs.put("softDrop", newKey.name());
                        softDropLabel.setText("Soft Drop: " + newKey.getName());
                        break;
                    case "hardDrop":
                        hardDropKey = newKey;
                        prefs.put("hardDrop", newKey.name());
                        hardDropLabel.setText("Hard Drop: " + newKey.getName());
                        break;
                    case "hold":
                        holdKey = newKey;
                        prefs.put("hold", newKey.name());
                        holdLabel.setText("Hold Piece: " + newKey.getName());
                        break;
                }
                changingAction = null;
                if (currentButton != null) {
                    currentButton.setText("Change");
                    currentButton = null;
                }
                event.consume();
            }
        });

        dialog.showAndWait();
    }

    /**
     * Creates the content for the audio settings panel, including volume sliders.
     * @return VBox containing the audio panel UI elements
     */
    private VBox createAudioPanelContent() {
        VBox audioPanel = new VBox(20);
        audioPanel.setPadding(new Insets(20));
        audioPanel.setAlignment(Pos.TOP_CENTER);
        audioPanel.setStyle("-fx-background-color: #2a2a2a;");

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

        audioPanel.getChildren().addAll(musicBox, sfxBox);

        return audioPanel;
    }

    /**
     * Creates the content for the controls settings panel, including key binding options.
     * @return VBox containing the controls panel UI elements
     */
    private VBox createControlsPanelContent() {
        VBox controlsPanel = new VBox(15);
        controlsPanel.setPadding(new Insets(20));
        controlsPanel.setAlignment(Pos.TOP_CENTER);
        controlsPanel.setStyle("-fx-background-color: #2a2a2a;");

        VBox keyBindings = new VBox(10);
        keyBindings.setAlignment(Pos.TOP_LEFT);

        // Move Left
        HBox moveLeftRow = createKeyBindingRow("Move Left", moveLeftKey, "moveLeft");
        moveLeftLabel = (Label) moveLeftRow.getChildren().get(0);
        keyBindings.getChildren().add(moveLeftRow);

        // Move Right
        HBox moveRightRow = createKeyBindingRow("Move Right", moveRightKey, "moveRight");
        moveRightLabel = (Label) moveRightRow.getChildren().get(0);
        keyBindings.getChildren().add(moveRightRow);

        // Rotate
        HBox rotateRow = createKeyBindingRow("Rotate", rotateKey, "rotate");
        rotateLabel = (Label) rotateRow.getChildren().get(0);
        keyBindings.getChildren().add(rotateRow);

        // Soft Drop
        HBox softDropRow = createKeyBindingRow("Soft Drop", softDropKey, "softDrop");
        softDropLabel = (Label) softDropRow.getChildren().get(0);
        keyBindings.getChildren().add(softDropRow);

        // Hard Drop
        HBox hardDropRow = createKeyBindingRow("Hard Drop", hardDropKey, "hardDrop");
        hardDropLabel = (Label) hardDropRow.getChildren().get(0);
        keyBindings.getChildren().add(hardDropRow);

        // Hold Piece
        HBox holdRow = createKeyBindingRow("Hold Piece", holdKey, "hold");
        holdLabel = (Label) holdRow.getChildren().get(0);
        keyBindings.getChildren().add(holdRow);

        controlsPanel.getChildren().addAll(keyBindings);

        return controlsPanel;
    }

    /**
     * Creates a row for key binding configuration.
     * @param labelText The text for the label
     * @param currentKey The current key code
     * @param action The action string for changing the key
     * @return HBox containing the label and change button
     */
    private HBox createKeyBindingRow(String labelText, KeyCode currentKey, String action) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        Label label = new Label(labelText + ": " + currentKey.getName());
        label.setTextFill(Color.WHITE);
        label.setPrefWidth(150);
        label.setFont(Font.font("System", FontWeight.NORMAL, 14));

        Button changeBtn = new Button("Change");
        changeBtn.setPrefSize(100, 30);
        changeBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-background-radius: 5;");
        changeBtn.setOnMouseEntered(e -> changeBtn.setStyle("-fx-background-color: #42A5F5; -fx-text-fill: white; -fx-background-radius: 5; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 5, 0.5, 0, 0);"));
        changeBtn.setOnMouseExited(e -> changeBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-background-radius: 5;"));
        changeBtn.setOnAction(e -> {
            com.tetris.util.SoundManager.getInstance().playButtonClickSound();
            changingAction = action;
            currentButton = changeBtn;
            changeBtn.setText("Press key...");
        });

        row.getChildren().addAll(label, changeBtn);

        return row;
    }

    /**
     * Applies styling to a button with base and hover colors.
     * @param button The button to style
     * @param baseColor The base background color
     * @param hoverColor The hover background color
     */
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
