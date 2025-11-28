# Tetris Game Project

## GitHub
Repository: https://github.com/simoneeejw/CW2025

## Compilation Instructions
To compile and run the Tetris game application, follow these detailed step-by-step instructions. The project uses Java 17 and Maven for build management.

1. **Prerequisites**:
   - **Java 17 or higher**: Download and install from [Oracle's official website](https://www.oracle.com/java/technologies/javase-downloads.html). Verify installation with `java -version`.
   - **Maven**: Download and install from [Apache Maven's website](https://maven.apache.org/download.cgi). Verify with `mvn -version`.
   - **Git**: Ensure Git is installed for cloning the repository.

2. **Clone the Repository**:
   - Open a terminal or command prompt.
   - Run: `git clone https://github.com/simoneeejw/CW2025.git`
   - This downloads the project to a local directory.

3. **Navigate to the Project Directory**:
   - Change to the project folder: `cd CW2025`

4. **Compile the Project**:
   - Clean and compile: `mvn clean compile`
   - This resolves dependencies, compiles Java source files, and prepares the project for execution.

5. **Run the Application**:
   - Launch the Tetris game: `mvn javafx:run`
   - The JavaFX application window will open, allowing you to play the game.

6. **Run Tests**:
   - Execute the test suite: `mvn test`
   - This runs all JUnit tests to verify functionality.

No additional dependencies or special settings are required beyond the standard Java and Maven installations. If you encounter issues, ensure your PATH environment variables are correctly set for Java and Maven.

## Implemented and Working Properly
The following features have been successfully implemented and are functioning as expected:

- **Package Restructuring**: The entire codebase was reorganized from the original `com.comp2042.tetris` package structure to `com.tetris`. This improves code organization, removes course-specific identifiers, and enhances maintainability. All imports and references were updated accordingly.

- **Single Responsibility Principle (SRP)**: The monolithic `SimpleBoard` class was decomposed into three specialized classes:
  - `BoardState`: Handles the game board matrix and brick positioning logic.
  - `BrickManager`: Manages brick generation, rotation, and hold mechanics.
  - `TetrisBoard`: Acts as the central coordinator for game logic, delegating tasks to the other components.

- **Design Patterns**:
  - **Observer Pattern**: Implemented through the `GameEventListener` interface, which decouples UI updates from game logic. The `GameController` notifies the `GuiController` of events like brick movements and line clears.
  - **Factory Pattern**: Utilized in the `BrickGenerator` class to create different brick types dynamically, promoting flexibility and extensibility.

- **JUnit Test Suite**: A comprehensive set of unit tests was developed, covering:
  - Core game mechanics (brick movement, rotation, collision detection).
  - Matrix operations (merging, clearing, intersection checks).
  - Brick generation and validation.
  - A total of 37 tests pass, ensuring reliability and preventing regressions.

- **Game Functionality**: The application provides a complete Tetris experience, including:
  - Brick movement (left, right, down) using keyboard controls.
  - Brick rotation with collision detection.
  - Line clearing when rows are filled.
  - Scoring system that increases with cleared lines.
  - Hold feature allowing players to store and swap bricks.

- **Application Launch**: The game launches successfully as a JavaFX desktop application, featuring a graphical user interface with smooth animations and responsive controls.

## Implemented but Not Working Properly
- **Pause/Resume Feature**: While implemented, there are occasional delays in resuming the game after pausing, particularly when multiple rapid key presses occur. This was addressed by adding debouncing logic to the event handlers, but minor timing issues persist in edge cases.

## Features Not Implemented
- **Multiplayer Mode**: Not implemented due to time constraints and the focus on single-player refactoring. Implementing network communication would require additional libraries and significant architectural changes.
- **Sound Effects**: Audio feedback was not added as it was deemed non-essential for the core refactoring requirements, and integrating sound libraries could introduce compatibility issues.

## New Java Classes
The following new Java classes were introduced during the refactoring process:

- **BoardState.java** (com.tetris.game): This class encapsulates the state of the game board, including the matrix representation and current brick position. It provides methods for updating the board state and resetting the matrix, ensuring separation of concerns.

- **BrickManager.java** (com.tetris.game): Responsible for managing brick-related operations such as generation, rotation, and holding. It uses the factory pattern to create bricks and handles the logic for swapping held bricks.

- **TetrisBoard.java** (com.tetris.game): The refactored main board class that orchestrates game logic by coordinating between BoardState and BrickManager. It implements the Board interface and handles high-level game operations like line clearing and brick placement.

- **GameEventListener.java** (com.tetris.game): An interface defining the observer pattern for game events. It specifies methods for handling user inputs and game state changes, allowing for loose coupling between the game logic and UI components.

- **TetrisBoardTest.java** (src/test/java/com/tetris/game): A unit test class containing JUnit tests for the TetrisBoard functionality. It verifies board initialization, brick placement, and game state transitions to ensure correctness.

## Modified Java Classes
The following classes from the original codebase were modified to support the refactoring:

- **SimpleBoard.java**: Originally a monolithic class handling multiple responsibilities. It was refactored into the three new classes (BoardState, BrickManager, TetrisBoard) to adhere to the Single Responsibility Principle. This change was necessary to improve code maintainability, testability, and scalability.

- **BrickGenerator.java**: Enhanced to fully implement the factory pattern, allowing for dynamic creation of different brick types. The modifications included adding abstract methods and concrete implementations, which were essential for decoupling brick creation from game logic.

- **GameController.java**: Updated to integrate the new refactored classes and implement the GameEventListener interface. Changes included adding references to LevelManager and modifying event handling methods. These modifications were necessary to support the new architecture and ensure proper event propagation.

- **GuiController.java**: Extended with new methods for handling level progression and pause functionality. The updates included adding event handlers for the 'P' key and integrating with the LevelManager for speed adjustments. This was required to support the enhanced gameplay features.

## Unexpected Problems
During the refactoring assignment, several unexpected challenges were encountered:

- **Dependency Conflicts**: Maven dependency resolution issues arose when updating package structures, causing compilation failures. This was addressed by carefully reviewing and updating the pom.xml file to ensure compatible versions of JavaFX and JUnit dependencies.

- **Event Handling Complexity**: Implementing the observer pattern initially led to redundant event triggers and potential memory leaks. The problem was resolved by refactoring the event listener registration process, ensuring proper cleanup, and adding null checks to prevent exceptions.

- **Testing Edge Cases**: Certain game logic scenarios, such as simultaneous brick movements and line clears, were difficult to reproduce manually. This was mitigated by expanding the JUnit test suite with parameterized tests and mock objects to simulate complex interactions.

- **Git Branch Management**: Merging branches with extensive changes led to conflicts in file paths and imports. Resolved by using Git's interactive rebase and carefully reviewing merge commits to maintain a clean history.

All issues were systematically identified, documented, and resolved through iterative testing and code reviews, resulting in a robust and well-structured application.
