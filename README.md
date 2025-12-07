# Tetris Game Project

A fully-featured JavaFX Tetris implementation with classic light theme styling, progressive difficulty levels, power-up system, and comprehensive testing. Built with clean architecture using design patterns and refactored code following SOLID principles.

**🎮 Play Features**: 8 difficulty levels, ghost piece shadows, power-ups, hold mechanic, hard drop, progressive speed ramps  
**🎨 Visual Design**: Light classic theme with gradient backgrounds, rounded corners, drop shadows, and vibrant Tetris colors  
**🏗️ Architecture**: Refactored from monolithic design to SRP-compliant classes with Observer and Factory patterns  
**✅ Quality**: 60 passing unit tests, comprehensive error handling, persistent high scores and settings  

## Table of Contents
- [GitHub](#github)
- [Compilation Instructions](#compilation-instructions)
- [Implemented and Working Properly](#implemented-and-working-properly)
- [Implemented but Not Working Properly](#implemented-but-not-working-properly)
- [Features Not Implemented](#features-not-implemented)
- [New Java Classes](#new-java-classes)
- [Modified Java Classes](#modified-java-classes)
- [Unexpected Problems](#unexpected-problems)

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

- **Single Responsibility Principle (SRP) Refactoring**: The monolithic `SimpleBoard` class was decomposed into three specialized classes: `BoardState` for board matrix and positioning, `BrickManager` for brick generation and hold mechanics, and `TetrisBoard` as the central coordinator. This improves modularity and testability.

- **Design Patterns Implementation**: 
  - **Observer Pattern**: Implemented through `GameEventListener` interface for decoupling UI updates from game logic.
  - **Factory Pattern**: Used in `BrickGenerator` for dynamic brick creation.

- **Comprehensive JUnit Test Suite**: Developed 60 unit tests (increased from 37), covering core mechanics, matrix operations, brick generation, level progression, and power-up systems.

- **Core Game Functionality**: Complete Tetris experience with brick movement, rotation, collision detection, line clearing, scoring, hold mechanic, ghost piece, and progressive levels.

- **Progressive Levels System**: 8-level progression with adaptive speed ramps, ghost rows, and score multipliers based on lines cleared.

- **Power-Up System**: Strategic power-ups triggered by Tetris clears, including slow motion, clear bottom row, double points, and permanent ghost piece.

- **Ghost Piece (Shadow)**: Semi-transparent shadow showing where the active piece will land, improving planning and reducing mistakes.

- **Classic Light Theme GUI Redesign**: Professional interface with light gradients, rounded corners, drop shadows, and coordinated color scheme.

- **Unified Rendering System**: Fixed rendering bugs by using displayMatrix directly, ensuring pieces are always visible and properly positioned.

- **Improved Controls and Input Handling**: Fixed key handling issues, added hard drop, pause functionality, and customizable key bindings.

- **Multiplayer Mode**: Local split-screen competitive mode with independent controls, game states, and winner detection.

- **CSS-Based Styling System**: External stylesheet for centralized styling, enabling easy theme modifications.

- **Consistent Window Sizing**: Standardized dimensions across all application states for seamless transitions.

- **Next Block Preview**: Sidebar display of upcoming tetromino for strategic planning.

- **Hold/Reserve Function**: Ability to store and swap current piece using 'R' key.

- **Adjustable Audio Volume Controls**: Volume sliders replacing on/off toggles for granular audio control.

- **Pause Menu System**: Overlay menu with resume, options, and quit options.

- **Tutorial System**: Comprehensive help dialog accessible from main menu, covering controls, gameplay, and tips.

- **Custom TETRIS Logo**: Image-based logo replacing text for enhanced visual identity.

- **Level Up Effects**: Audio and visual feedback (screen shake, label animation) on level advancement.

- **Music Preloading**: Optimized audio loading to eliminate startup delays.

## Implemented but Not Working Properly
None. All implemented features are functioning correctly as verified by the comprehensive test suite and manual testing.

## Features Not Implemented
- **Online Multiplayer**: Not implemented due to time constraints and complexity of network programming beyond the scope of this coursework.
- **Advanced AI Opponent**: Not implemented as the focus was on local multiplayer and single-player enhancements.
- **Custom Tetromino Shapes**: Not implemented to maintain standard Tetris gameplay rules.
- **Save/Load Game State**: Not implemented as it would require additional file I/O complexity not essential for core functionality.

## New Java Classes
The following new Java classes were introduced for the assignment:

- **BoardState.java**: Handles the game board matrix and brick positioning logic. Location: `src/main/java/com/tetris/game/BoardState.java`. Purpose: Manages board state separately from game logic for better SRP compliance.

- **BrickManager.java**: Manages brick generation, rotation, and hold mechanics. Location: `src/main/java/com/tetris/game/BrickManager.java`. Purpose: Centralizes brick-related operations to reduce complexity in the main game controller.

- **TetrisBoard.java**: Acts as the central coordinator for game logic, delegating tasks to BoardState and BrickManager. Location: `src/main/java/com/tetris/game/TetrisBoard.java`. Purpose: Replaces the monolithic SimpleBoard with a clean interface following SRP.

- **GameEventListener.java**: Interface for observer pattern implementation. Location: `src/main/java/com/tetris/game/GameEventListener.java`. Purpose: Decouples UI updates from game logic for better maintainability.

- **LevelManager.java**: Manages level progression based on lines cleared. Location: `src/main/java/com/tetris/game/LevelManager.java`. Purpose: Implements progressive difficulty system with speed ramps and ghost rows.

- **PowerUp.java**: Enum defining available power-up types. Location: `src/main/java/com/tetris/game/PowerUp.java`. Purpose: Defines power-up types and their properties.

- **PowerUpManager.java**: Manages active power-ups with timers and effects. Location: `src/main/java/com/tetris/game/PowerUpManager.java`. Purpose: Handles power-up activation, expiration, and application to game mechanics.

- **Level.java**: Enum defining level thresholds and properties. Location: `src/main/java/com/tetris/game/Level.java`. Purpose: Encapsulates level data for the progressive system.

- **HelpDialog.java**: Modal dialog providing tutorial content. Location: `src/main/java/com/tetris/gui/HelpDialog.java`. Purpose: Guides new players through game mechanics and controls.

- **PauseMenuPanel.java**: Custom overlay component for pause menu. Location: `src/main/java/com/tetris/gui/PauseMenuPanel.java`. Purpose: Provides pause functionality with resume, options, and quit options.

- **ThemeManager.java**: Manages application themes. Location: `src/main/java/com/tetris/gui/ThemeManager.java`. Purpose: Enables theme switching and customization.

- **ThemeSelector.java**: UI component for theme selection. Location: `src/main/java/com/tetris/gui/ThemeSelector.java`. Purpose: Allows users to choose between available themes.

- **Theme.java**: Enum defining available themes. Location: `src/main/java/com/tetris/gui/Theme.java`. Purpose: Defines theme properties and styling.

- **SoundManager.java**: Enhanced audio management system. Location: `src/main/java/com/tetris/util/SoundManager.java`. Purpose: Handles background music and sound effects with volume control.

## Modified Java Classes
The following Java classes were modified from the provided codebase:

- **GuiController.java**: Modified to implement unified rendering system, integrate ghost piece, add pause menu, level up effects, and custom logo. Location: `src/main/java/com/tetris/gui/GuiController.java`. Changes: Added methods for refreshBrick(), ghost piece calculation, pause menu management, and UI enhancements. Rationale: To fix critical rendering bugs where pieces weren't visible and to add new gameplay features like ghost piece and pause functionality.

- **GameController.java**: Modified to integrate level progression, power-up system, and improved event handling. Location: `src/main/java/com/tetris/game/GameController.java`. Changes: Added level management, power-up triggers, and enhanced collision detection. Rationale: To support progressive difficulty and strategic power-ups, enhancing gameplay depth.

- **Main.java**: Modified to support multiplayer mode and consistent window sizing. Location: `src/main/java/com/tetris/game/Main.java`. Changes: Added mode selection logic and window dimension standardization. Rationale: To enable the new multiplayer feature and improve UI consistency.

- **ViewData.java**: Modified to include held brick and next brick data. Location: `src/main/java/com/tetris/model/ViewData.java`. Changes: Added fields and methods for hold mechanic and next block preview. Rationale: To support the hold feature and next block preview for better player planning.

- **MatrixOperations.java**: Modified to fix collision detection bugs. Location: `src/main/java/com/tetris/util/matrix/MatrixOperations.java`. Changes: Corrected matrix indexing from `brick[j][i]` to `brick[i][j]` and coordinate offsets. Rationale: Critical bug fix to ensure pieces detect collisions properly and can reach board edges.

- **SettingsDialog.java**: Modified to include volume sliders instead of checkboxes. Location: `src/main/java/com/tetris/gui/SettingsDialog.java`. Changes: Replaced boolean toggles with 0-100% sliders for music and sound effects. Rationale: To provide granular audio control and improve user experience.

- **MainMenu.java**: Modified to add multiplayer toggle and help button. Location: `src/main/java/com/tetris/gui/MainMenu.java`. Changes: Added mode selection buttons and tutorial access. Rationale: To support the new multiplayer mode and provide user guidance.

- **GameLayout.fxml**: Modified to include new UI elements like pause menu, multiplayer layout, and custom logo. Location: `src/main/resources/GameLayout.fxml`. Changes: Added ImageView for logo, StackPane for pause overlay, and multiplayer-specific panes. Rationale: To implement the redesigned GUI with new features.

- **MultiplayerLayout.fxml**: New FXML file for multiplayer mode. Location: `src/main/resources/MultiplayerLayout.fxml`. Changes: Created dedicated layout with side-by-side boards and dual sidebars. Rationale: To support local competitive multiplayer.

- **Styles.css**: Modified to include comprehensive styling for all new UI elements. Location: `src/main/resources/Styles.css`. Changes: Added classes for pause menu, multiplayer elements, and theme support. Rationale: To centralize styling and enable easy theme modifications.

## Unexpected Problems
Several unexpected challenges were encountered during development:

- **Rendering Issues**: Active pieces were not visible due to disconnected rendering systems from refactoring. Resolved by implementing a unified rendering approach using the displayMatrix directly, ensuring all visual updates go through a single source of truth.

- **Collision Detection Bugs**: Pieces could not move to board edges or detect collisions properly due to incorrect matrix indexing in MatrixOperations. Fixed by correcting array access patterns and coordinate calculations, which was critical for basic gameplay.

- **Spawn Position Errors**: Pieces appeared in the middle of the board instead of the top due to incorrect offset initialization. Corrected by setting spawn position to row 0, column 4, restoring proper Tetris mechanics.

- **Property Binding Failures**: "Bound value cannot be set" errors in multiplayer mode due to improper JavaFX property usage. Resolved by using Bindings.concat() and separate IntegerProperty instances for each player's stats.

- **Window Sizing Inconsistencies**: Jarring transitions between menu and game due to varying window dimensions. Standardized all windows to consistent sizes (820x700px for main/multiplayer, 450x720px for single player) with proper constraints.

- **Audio Loading Delays**: Background music caused startup delays due to repeated Media instantiation. Optimized by preloading Media objects once and reusing them.

- **Key Handling Conflicts**: Simultaneous key presses caused erratic behavior. Fixed by changing independent if-statements to else-if chains and adding debouncing.

- **FXML Parsing Errors**: Duplicate IDs and invalid attributes caused loading failures. Resolved by removing duplicates and ensuring proper element alignment.

These issues were addressed through systematic debugging, code reviews, and extensive testing, ensuring the final application is stable and fully functional.

## Summary
This coursework project successfully transformed a basic Tetris implementation into a comprehensive, feature-rich game through extensive maintenance and extension work. The development process involved careful refactoring, bug fixing, and innovative feature additions while maintaining code quality and adhering to software engineering principles.

### Overall Changes
- **System Maintenance**: Restructured the entire codebase from `com.comp2042.tetris` to `com.tetris` package, improving organization and removing course-specific dependencies. Refactored the monolithic `SimpleBoard` class into three SRP-compliant components (`BoardState`, `BrickManager`, `TetrisBoard`) for better modularity and testability.
- **Feature Extensions**: Added 8-level progressive difficulty system with speed ramps and ghost rows, strategic power-up system triggered by Tetris clears, ghost piece shadows for better planning, local split-screen multiplayer mode, comprehensive pause menu, tutorial system, adjustable audio controls, and professional light theme GUI redesign.
- **Quality Improvements**: Expanded test suite from 37 to 60 unit tests, implemented unified rendering system to fix critical display bugs, standardized window sizing for seamless transitions, and optimized audio loading for better performance.
- **Code Enhancements**: Introduced design patterns (Observer and Factory), improved controls with customizable key bindings, added visual effects like level-up animations, and centralized styling through external CSS.

### Design Patterns Implemented
- **Observer Pattern**: Utilized through the `GameEventListener` interface to decouple UI updates from game logic, enabling clean separation of concerns and easier maintenance.
- **Factory Pattern**: Applied in the `BrickGenerator` class for dynamic creation of different brick types, promoting flexibility and extensibility in brick management.

### Unexpected Problems and Resolutions
Throughout development, several critical issues emerged that required immediate attention:
- **Rendering Failures**: Pieces became invisible after refactoring due to disconnected display systems; resolved by implementing a unified rendering approach using the displayMatrix as the single source of truth.
- **Collision Detection Errors**: Incorrect matrix indexing prevented proper piece movement; fixed by correcting array access patterns and coordinate calculations.
- **Spawn Position Bugs**: Pieces appeared mid-board instead of top; corrected by resetting initialization offsets to standard Tetris positions.
- **UI Binding Issues**: Property binding errors in multiplayer mode; resolved using proper JavaFX property management with separate IntegerProperty instances.
- **Window Transition Problems**: Inconsistent sizing caused jarring effects; standardized dimensions across all application states.
- **Audio Performance**: Startup delays from repeated media loading; optimized through preloading and reuse of Media objects.
- **Input Handling Conflicts**: Simultaneous key presses caused erratic behavior; fixed with debounced else-if chains.
- **FXML Parsing Errors**: Duplicate elements caused loading failures; resolved by cleaning up invalid attributes and IDs.

All problems were systematically addressed through debugging, code reviews, and comprehensive testing, resulting in a stable, fully functional application that exceeds the original requirements.

### Additional Notes on Processing
The development followed an iterative approach, starting with core maintenance tasks (package restructuring, SRP refactoring) before progressing to feature extensions. Each new feature was implemented with corresponding unit tests to ensure reliability. The project demonstrates effective application of software engineering principles, including SOLID design, design patterns, and thorough testing. The final product provides both single-player and multiplayer experiences with professional polish, comprehensive documentation, and robust error handling.
