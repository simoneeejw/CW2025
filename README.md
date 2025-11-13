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

## Implemented but Not Working Properly
- None

## Features Not Implemented
- New gameplay features (e.g., power-ups, levels, adaptive difficulty) as extensions were not implemented in this submission.

## New Java Classes
- None

## Modified Java Classes
- **SimpleBoard.java**: Refactored to reduce code duplication by introducing a `tryMove(int dx, int dy)` helper method for brick movement logic. Updated `createNewBrick()` to return a success status for clearer game-over handling. This improves maintainability and readability without changing functionality.
- **GameController.java**: Adjusted the logic in `onDownEvent()` to properly check the return value of `createNewBrick()` for game-over conditions, ensuring consistent behavior.

## Unexpected Problems
- Encountered duplicate class compilation errors due to cached compiled files. Resolved by running `mvn clean` to clear the target directory.
- Initial runtime error with ClassNotFoundException for GameOverPanel was fixed by cleaning the build, as it was caused by outdated class files.
