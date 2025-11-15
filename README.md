# COMP2042 Tetris Game Maintenance and Extension

## GitHub
https://github.com/simoneeejw/CW2025

## Compilation Instructions
1. Ensure Java JDK (version 21 or later) and Maven are installed.
2. Clone the repository.
3. Run `mvn clean compile` in the project root to resolve any cached issues.
4. Run `mvn javafx:run` to start the game.

## Implemented and Working Properly
- Basic Tetris gameplay (maintained and refactored for stability)
- Hold feature: Players can press 'C' to hold the current falling brick and swap it with a previously held brick, adding strategic depth to the gameplay.

## Implemented but Not Working Properly
- None

## Features Not Implemented
- Other advanced features (e.g., power-ups, levels, adaptive difficulty) as extensions were limited to the hold mechanic.

## New Java Classes
- None

## Modified Java Classes
- **ViewData.java**: Added `heldBrickData` field and corresponding methods to support displaying the held brick.
- **Board.java**: Added `holdBrick()` method to the interface.
- **SimpleBoard.java**: Implemented `holdBrick()` method, added `heldBrick` field, updated `getViewData()` and `newGame()` to handle held bricks.
- **GameController.java**: Added `onHoldEvent()` method to handle hold input.
- **InputEventListener.java**: Added `onHoldEvent()` method declaration.
- **EventType.java**: Added `HOLD` enum value.
- **GuiController.java**: Added key handling for 'C' key, FXML injection for `heldPanel`, initialization of held rectangles, and updated `refreshBrick()` to display held bricks.
- **gameLayout.fxml**: Added `heldPanel` GridPane for UI display of held bricks.

## Unexpected Problems
- Encountered duplicate class compilation errors due to cached compiled files. Resolved by running `mvn clean` to clear the target directory.
- Initial runtime error with ClassNotFoundException for GameOverPanel was fixed by cleaning the build, as it was caused by outdated class files.
