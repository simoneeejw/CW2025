package com.comp2042.tetris.game;

import com.comp2042.tetris.model.DownData;
import com.comp2042.tetris.model.event.MoveEvent;
import com.comp2042.tetris.model.ViewData;

// Listener for player input events during gameplay
public interface InputEventListener {

    // Handle downward movement of brick
    DownData onDownEvent(MoveEvent event);

    // Move brick to the left
    ViewData onLeftEvent(MoveEvent event);

    // Move brick to the right
    ViewData onRightEvent(MoveEvent event);

    // Rotate brick clockwise
    ViewData onRotateEvent(MoveEvent event);

    // Swap current brick with held brick
    ViewData onHoldEvent(MoveEvent event);

    // Start a new game
    void createNewGame();
}
