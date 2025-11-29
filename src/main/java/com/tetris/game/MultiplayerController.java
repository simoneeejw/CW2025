package com.tetris.game;

import com.tetris.gui.MultiplayerGuiController;

/**
 * Controller for multiplayer Tetris game.
 * Manages two separate game instances for two players.
 */
public class MultiplayerController {

    public MultiplayerController(MultiplayerGuiController guiController) {
        // Simply initialize the GUI controller with two game controllers
        guiController.initializeGame();
    }
}
