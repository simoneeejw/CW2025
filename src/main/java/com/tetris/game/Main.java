package com.tetris.game;

import com.tetris.gui.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main entry point for the Tetris application, launching the JavaFX GUI.
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application by showing the main menu.
     * @param primaryStage the primary stage for the application
     * @throws Exception if an error occurs during startup
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Show main menu - it will handle loading the game
        MainMenu mainMenu = new MainMenu(primaryStage);
        mainMenu.show();
    }

    /**
     * Main method to launch the application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
