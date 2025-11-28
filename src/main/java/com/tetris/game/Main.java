package com.tetris.game;

import com.tetris.gui.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Show main menu - it will handle loading the game
        MainMenu mainMenu = new MainMenu(primaryStage);
        mainMenu.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
