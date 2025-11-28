package com.tetris.game;

import com.tetris.gui.GuiController;
import com.tetris.gui.Theme;
import com.tetris.gui.ThemeManager;
import com.tetris.gui.ThemeSelector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Show theme selector first
        ThemeSelector themeSelector = new ThemeSelector();
        Theme selectedTheme = themeSelector.showAndWait(primaryStage);

        if (selectedTheme != null) {
            // Apply selected theme
            ThemeManager.getInstance().setCurrentTheme(selectedTheme);

            // Load the main game
            URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
            ResourceBundle resources = null;
            FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
            Parent root = fxmlLoader.load();
            GuiController c = fxmlLoader.getController();

            primaryStage.setTitle("TetrisJFX");
            Scene scene = new Scene(root, 300, 510);
            primaryStage.setScene(scene);
            primaryStage.show();

            // Initialize game controller
            GameController gameController = new GameController(c);

            // Apply theme to GUI
            ThemeManager.getInstance().applyTheme(c);
        } else {
            // User cancelled theme selection, exit
            System.exit(0);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
