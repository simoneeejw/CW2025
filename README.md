:# Tetris Game Project

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
   - This runs all JUnit tests to verify functionality (60 tests).

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

## Additional Features (25 Marks)

### 1. Progressive Levels with Speed Ramps (New Playable Levels + Gameplay Enhancement)
**Description**: Implemented a 4-level progression system based on lines cleared, with adaptive speed and ghost rows.

**Implementation Details**:
- **LevelManager.java**: Added a new class that tracks total lines cleared and automatically advances levels at thresholds:
  - Level 1 (Beginner): 0-4 lines cleared, 500ms fall speed
  - Level 2 (Intermediate): 5-9 lines cleared, 400ms fall speed
  - Level 3 (Advanced): 10-19 lines cleared, 300ms fall speed
  - Level 4 (Expert): 20+ lines cleared, 200ms fall speed
- **Dynamic Speed Adjustment**: Fall speed reduces by 100ms per level, integrated into the Timeline loop via `updateGameSpeed()` method in GuiController
- **Ghost Rows**: At Level 4, occasional semi-transparent ghost rows appear randomly (30% chance on level-up) and vanish after 3 seconds using JavaFX PauseTransition
- **Score Multipliers**: Each level multiplies score by 1x, 2x, 3x, 4x respectively
- **UI Integration**: Level and lines cleared displayed in StatusPanel on the right sidebar
- **Unit Tests**: Comprehensive LevelManagerTest with 12 tests covering level advancement logic, threshold calculations, and edge cases

**Quality & Balance**: Thresholds were tuned through playtesting to ensure smooth progression. Speed ramp is challenging but achievable for players with practice.

### 2. Power-Up System (Innovative Feature Design)
**Description**: Introduced a strategic power-up system triggered by clearing 4 lines simultaneously (Tetris).

**Implementation Details**:
- **PowerUp.java**: Enum defining 4 power-up types:
  - **Slow Motion**: Reduces fall speed by 50% for 5 seconds
  - **Clear Bottom**: Instantly clears the bottom row
  - **Double Points**: Doubles score for 10 seconds
  - **Ghost Piece**: Permanently shows where piece will land (shadow)
- **PowerUpManager.java**: Manages active power-ups with timers and automatic expiration
- **Trigger Mechanism**: Random power-up awarded when player clears 4 lines at once
- **Visual Feedback**: LevelUpPanel displays animated power-up notifications with fade effects
- **Integration**: Power-up multipliers applied to scoring in GameController's onDownEvent()
- **Unit Tests**: PowerUpManagerTest with 11 tests covering activation, expiration, and multiplier logic

**Innovation**: This feature adds strategic depth by rewarding skilled play (4-line clears) and introduces timing-based decisions for power-up usage.

### 3. Ghost Piece (Shadow) (Gameplay Enhancement)
**Description**: Shows a semi-transparent shadow of where the current piece will land when dropped.

**Implementation Details**:
- **Ghost Panel**: Added ghostPanel GridPane with ghostRectangles array to display shadow
- **Position Calculation**: `getGhostYPosition()` method in TetrisBoard simulates piece drop using collision detection
- **Visual Design**: Shadow rendered at 30% opacity in the same color as the active piece
- **Real-time Updates**: Ghost position updates on every brick movement via `updateGhostPiece()` method
- **Integration**: Ghost panel positioned dynamically based on current piece X position and calculated landing Y position

**User Benefit**: Players can now plan moves more effectively by seeing exactly where pieces will land, improving gameplay experience and reducing mistakes.

### 4. Enhanced UI and Status Display
**Description**: Comprehensive status panel showing game state and score display.

**Implementation Details**:
- **StatusPanel.java**: New VBox component displaying:
  - Current level and difficulty name
  - Real-time score updates
  - Total lines cleared
  - Active power-ups with remaining time
  - Complete controls reference
- **Score Label**: Prominent "Score: X" label at top of game board, bound to score property for live updates
- **Visual Notifications**: LevelUpPanel with animated pop-ups for:
  - Level advancement ("LEVEL X!")
  - Power-up activation
  - Tetris clears (4 lines)
- **Styling**: Professional appearance with semi-transparent backgrounds, gold/cyan colors, and drop shadows

### 5. Improved Controls and Input Handling
**Description**: Fixed keyboard input issues and enhanced control responsiveness.

**Implementation Details**:
- **Fixed Multiple Key Handling**: Changed independent `if` statements to `else if` chain to prevent simultaneous key processing
- **Hard Drop**: SPACE key now properly drops piece to bottom without creating duplicates
- **Pause/Resume**: ESC key toggles game pause with proper Timeline management
- **Key Debouncing**: Prevents rapid repeated inputs from causing glitches
- **Control Guide**: Full keyboard layout displayed in StatusPanel

**Controls**:
- ← → (or A/D): Move left/right
- ↑ (or W): Rotate piece
- ↓ (or S): Soft drop (move down faster)
- SPACE: Hard drop (instant drop to bottom)
- C: Hold/swap piece
- ESC: Pause/resume game
- N: New game

### 6. Comprehensive Testing
**Total Tests**: 60 unit tests (increased from 37 baseline)
- LevelManagerTest: 12 tests for level progression
- PowerUpManagerTest: 11 tests for power-up mechanics
- Original test suite: 37 tests for core functionality

**Test Coverage**:
- Level calculation and advancement logic
- Power-up activation, expiration, and multipliers
- Score multiplier calculations
- Lines cleared tracking
- Ghost position calculation
- Board state management

All tests pass successfully, ensuring reliability and preventing regressions.

### 7. Dynamic Visual Themes with Piece Glows and Board Gradients (Visual Appeal + Immersion)
**Description**: Implemented swappable visual themes with gradient backgrounds, glowing piece effects, and particle burst animations for line clears.

**Implementation Details**:
- **Theme.java**: Enum defining three distinct themes:
  - **Classic Retro**: Black-to-gray gradient, white glows, yellow accents
  - **Neon Night**: Dark blue-to-darker blue gradient, cyan glows, magenta accents
  - **Zen Minimal**: Light gray-to-white gradient, dark gray glows, gray accents
- **ThemeManager.java**: Singleton managing theme persistence using Java Preferences API. Automatically loads saved theme on startup and saves selections.
- **ThemeSelector.java**: Modal dialog shown at game start with RadioButtons for theme selection. Displays theme previews with color-coded buttons.
- **Board Gradients**: Full-screen LinearGradient background applied to root pane, creating immersive space/neon environments.
- **Piece Glow Effects**: DropShadow effects with theme-matched colors and pulsing opacity animation (0.5s cycle) for active pieces.
- **Particle Bursts**: Line clears trigger 20-30 fading circles that scatter outward using TranslateTransition and FadeTransition animations.
- **Integration**: Press 'T' during gameplay to open theme selector. Theme persists across game sessions.

**Visual Impact**: Transforms the game from flat 2D to immersive 3D-like experience with dynamic lighting effects and atmospheric backgrounds. Each theme provides unique visual identity while maintaining gameplay clarity.

## Implemented but Not Working Properly
None. All implemented features are functioning correctly, including the recently added progressive levels, power-ups, and ghost piece.

## Features Not Implemented
None. All required refactoring and additional features have been successfully implemented.

## New Java Classes

### Refactoring Classes
The following new Java classes were introduced during the refactoring process:

- **BoardState.java** (com.tetris.game): This class encapsulates the state of the game board, including the matrix representation and current brick position. It provides methods for updating the board state and resetting the matrix, ensuring separation of concerns.

- **BrickManager.java** (com.tetris.game): Responsible for managing brick-related operations such as generation, rotation, and holding. It uses the factory pattern to create bricks and handles the logic for swapping held bricks.

- **TetrisBoard.java** (com.tetris.game): The refactored main board class that orchestrates game logic by coordinating between BoardState and BrickManager. It implements the Board interface and handles high-level game operations like line clearing and brick placement.

- **GameEventListener.java** (com.tetris.game): An interface defining the observer pattern for game events. It specifies methods for handling user inputs and game state changes, allowing for loose coupling between the game logic and UI components.

- **TetrisBoardTest.java** (src/test/java/com/tetris/game): A unit test class containing JUnit tests for the TetrisBoard functionality. It verifies board initialization, brick placement, and game state transitions to ensure correctness.

### Additional Feature Classes
The following new Java classes were introduced for the additional features:

- **LevelManager.java** (com.tetris.game): Manages automatic level progression based on lines cleared. Tracks total lines, calculates current level (1-4), provides fall speeds (500-200ms), and score multipliers (1x-4x). Includes reset functionality for new games.

- **PowerUp.java** (com.tetris.game): Enum defining four power-up types (Slow Motion, Clear Bottom, Double Points, Ghost Piece) with names and descriptions for UI display.

- **PowerUpManager.java** (com.tetris.game): Manages power-up state including activation, timers, and expiration. Provides methods to trigger random power-ups, calculate speed/score multipliers, and handle timed effects with JavaFX properties for UI binding.

- **LevelUpPanel.java** (com.tetris.gui): JavaFX StackPane component for displaying animated notifications. Shows level-up messages, power-up activations, and Tetris (4-line clear) notifications with scale and fade animations.

- **StatusPanel.java** (com.tetris.gui): JavaFX VBox component for comprehensive game status display. Shows current level, difficulty, score, lines cleared, active power-ups with timers, and complete control reference.

- **LevelManagerTest.java** (src/test/java/com/tetris/game): Unit test class with 12 tests covering level advancement logic, threshold calculations, speed changes, score multipliers, and reset functionality.

- **PowerUpManagerTest.java** (src/test/java/com/tetris/game): Unit test class with 11 tests covering power-up activation, expiration timing, multiplier calculations, property binding, and enum properties.

- **Theme.java** (com.tetris.gui): Enumeration defining three visual themes (Classic Retro, Neon Night, Zen Minimal) with color schemes for board gradients, piece glows, and UI accents.

- **ThemeManager.java** (com.tetris.gui): Singleton class managing theme persistence and application. Handles loading/saving user theme preferences and applying themes to the GUI controller.

- **ThemeSelector.java** (com.tetris.gui): JavaFX modal dialog for theme selection. Displays theme previews with RadioButtons and applies selected theme to the game.

- **ParticleEffect.java** (com.tetris.gui): Animation system for line clear particle bursts. Creates 20-30 fading circles that scatter outward from cleared lines using Translate and Fade transitions.

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

## Summary
This Tetris game project demonstrates successful application of software engineering principles including:
- **Clean Architecture**: Package restructuring and SRP implementation
- **Design Patterns**: Observer and Factory patterns for maintainable code
- **Test-Driven Development**: 60 comprehensive unit tests ensuring reliability
- **Enhanced Gameplay**: Progressive levels, power-ups, and improved user experience
- **Quality Implementation**: Professional UI, responsive controls, and balanced difficulty progression

The project has evolved from a basic Tetris clone to a feature-rich, well-architected game with strategic depth and polished presentation.
