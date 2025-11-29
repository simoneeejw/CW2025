:# Tetris Game Project

A fully-featured JavaFX Tetris implementation with classic light theme styling, progressive difficulty levels, power-up system, and comprehensive testing. Built with clean architecture using design patterns and refactored code following SOLID principles.

**🎮 Play Features**: 4 difficulty levels, ghost piece shadows, power-ups, hold mechanic, hard drop, progressive speed ramps  
**🎨 Visual Design**: Light classic theme with gradient backgrounds, rounded corners, drop shadows, and vibrant Tetris colors  
**🏗️ Architecture**: Refactored from monolithic design to SRP-compliant classes with Observer and Factory patterns  
**✅ Quality**: 60 passing unit tests, comprehensive error handling, persistent high scores and settings  

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
  - A total of 60 tests pass (increased from 37 baseline), ensuring reliability and preventing regressions.

- **Game Functionality**: The application provides a complete Tetris experience, including:
  - Brick movement (left, right, down) using keyboard controls.
  - Brick rotation with collision detection.
  - Line clearing when rows are filled.
  - Scoring system that increases with cleared lines and level multipliers.
  - Hold feature allowing players to store and swap bricks.
  - Ghost piece (shadow) showing where the active piece will land.
  - Progressive level system with increasing speed (4 levels).
  - Power-up system triggered by Tetris clears (4 lines).

- **Classic Light Theme GUI**: The game features a professionally redesigned interface with:
  - Light blue gradient background with subtle diagonal line pattern.
  - BorderPane layout with centered game board and sidebar.
  - 30x30px brick cells with rounded corners and stroke borders.
  - Vibrant classic Tetris colors (cyan, yellow, purple, green, red, orange, blue).
  - Real-time score, lines, and level display in styled sidebar boxes.
  - Orange-to-gold gradient "TETRIS" title with drop shadow effect.
  - Cyan "N-BLOCK" subtitle with italics.
  - Royal blue pause button with hover effects.

- **Application Launch**: The game launches successfully as a JavaFX desktop application, featuring a modern graphical user interface with smooth animations, responsive controls, and professional styling.

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
- **Direct Board Rendering**: Ghost piece is rendered directly on the game board's displayMatrix at 30% opacity
- **Position Calculation**: `getGhostYPosition()` method in GameController simulates piece drop using collision detection to find landing position
- **Visual Design**: Shadow rendered at 30% opacity in the same color as the active piece, only on empty cells
- **Real-time Updates**: Ghost position updates on every brick movement via `updateGhostPiece()` method in GuiController
- **Smart Overlay**: Ghost only draws on transparent cells to avoid overwriting locked pieces or the active piece
- **Performance**: Integrated rendering eliminates need for separate panels, improving performance

**User Benefit**: Players can now plan moves more effectively by seeing exactly where pieces will land, improving gameplay experience and reducing mistakes. The ghost piece disappears when the active piece reaches the landing position to avoid visual confusion.

### 4. Classic Light Theme GUI Redesign (Visual Appeal + User Experience)
**Description**: Complete redesign of the Tetris GUI with a professional light theme inspired by classic Tetris aesthetics.

**Implementation Details**:
- **Layout Architecture**: BorderPane root layout with three main sections:
  - **Top**: VBox containing "TETRIS" title (Arial 48px bold, orange-to-gold gradient) with drop shadow and "N-BLOCK" subtitle (Arial 24px italic, cyan)
  - **Center**: HBox with game board (300x600px StackPane) and sidebar (150px VBox)
  - **Bottom**: HBox with music icon (♫), separator, and pause button
- **Background Styling**: Repeating linear gradient at 45° creating subtle diagonal lines (transparent to #A0D0E0 at 10% opacity), overlaid on a light blue to pale cyan gradient (#B0E0E6 to #E6F3FF)
- **Game Board Design**:
  - Light blue tinted background (rgba(176, 224, 230, 0.3)) for subtle depth
  - 3px cyan border (#00FFFF) with 5px rounded corners
  - Drop shadow for elevated appearance
  - 20x10 grid (30x30px cells) with gap of 1px
- **Brick Styling**: 
  - Classic Tetris colors with proper hex values (cyan #00FFFF, yellow #FFFF00, purple #8B00FF, green #00FF00, red #FF0000, orange #FF8C00, blue #0000FF)
  - 1px darker stroke on all colored bricks for definition
  - 2px rounded corners (arc-height/width: 4px)
  - Inner shadow glow effect via CSS (.piece-rect class)
- **Sidebar Info Boxes**:
  - Three stacked HBox containers with light white background (rgba(245, 245, 245, 0.8))
  - 5px border radius and 10px padding
  - Drop shadow for depth perception
  - Score, Lines, and Level labels in Arial Bold 14px
  - Values displayed in cyan (#00BFFF) for high contrast
  - Level difficulty name in orange (#FF8C00)
- **Pause Button**:
  - Royal blue background (#4169E1) with white text
  - 10px border radius for pill shape
  - Hover effect: scales to 105% with enhanced blue glow shadow
  - Pressed effect: scales to 98% for tactile feedback
  - Hand cursor on hover
- **CSS Integration**: External styles.css file linked in gameLayout.fxml containing all styling rules for maintainability
- **Property Binding**: Score, lines, and level labels bound to JavaFX properties for real-time updates without manual refresh
- **Theme Consistency**: All UI elements use coordinated color scheme (blues, cyans, oranges) for cohesive visual identity

**User Experience**: The light, airy design reduces eye strain during extended play sessions while maintaining high contrast for piece visibility. The classic Tetris aesthetic creates nostalgic appeal while modern styling elements (shadows, gradients, rounded corners) provide a polished, professional appearance.

### 5. Fixed Game Rendering and Mechanics (Critical Bug Fixes)
**Description**: Resolved major rendering issues where active pieces were not visible and game mechanics were broken.

**Problems Identified**:
- Active pieces were created but never displayed on the game board
- Separate brickPanel system was removed during refactoring but rendering logic wasn't updated
- Ghost piece used a disconnected GridPane that wasn't properly integrated
- Pieces appeared to "jump" or not move correctly due to display matrix not being refreshed

**Solutions Implemented**:
- **Unified Rendering System**: Completely rewrote `refreshBrick()` method to use the displayMatrix directly
  - First refreshes entire game background to show locked pieces
  - Then overlays active piece on top by updating cells at the piece's current position
  - Ensures pieces are always visible and positioned correctly
- **Ghost Piece Integration**: Reimplemented ghost rendering to work directly on the game board
  - Calculates landing position using existing collision detection
  - Renders ghost at 30% opacity only on empty cells
  - Avoids overwriting active or locked pieces
  - Disappears when active piece reaches landing position
- **Removed Obsolete Code**: Eliminated unused rendering components (separate rectangles arrays, ghost panels) that were causing confusion and bugs
- **Display Matrix as Single Source of Truth**: All visual updates now go through the displayMatrix, ensuring consistency between game state and visual representation

**Impact**: These fixes were critical for basic gameplay. Before the fixes, players couldn't see pieces moving, making the game unplayable. After the fixes, all game mechanics work correctly with proper visual feedback.

### 6. Improved Controls and Input Handling
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

### 7. Comprehensive Testing
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

### 8. Multiplayer Mode (Local Split-Screen) (New Playable Mode + Innovative Feature)
**Description**: Local 2-player competitive mode with split-screen display where players compete on separate boards simultaneously.

**Implementation Details**:
- **Main Menu Selection**: Added toggle buttons in the main menu to select between Single Player and Multiplayer modes
  - Visual toggle with color-coded selection (green for selected, gray for unselected)
  - Game mode selection placed prominently above the "Play Game" button
  - Smooth transition between mode selections with styled buttons
- **Multiplayer Layout**: Created dedicated `multiplayerLayout.fxml` with side-by-side game boards:
  - Player 1 board on the left with sidebar on the right
  - Player 2 board on the right with sidebar on the left
  - Each player has independent score, lines, level, held block, and next block displays
  - Window sized precisely at 820x700px to fit content without excessive empty space
  - Centered layout with proper spacing between elements
- **Separate Controls**: Each player uses different keyboard keys to avoid conflicts:
  - **Player 1 (WASD + Space + Tab)**:
    - A: Move left
    - D: Move right
    - W: Rotate
    - S: Soft drop
    - Space: Hard drop
    - Tab: Hold/reserve piece
  - **Player 2 (Arrow Keys + Enter + Backspace)**:
    - Left Arrow: Move left
    - Right Arrow: Move right
    - Up Arrow: Rotate
    - Down Arrow: Soft drop
    - Enter: Hard drop
    - Backspace: Hold/reserve piece
- **Instructions Display**: On game start, instructions are displayed for 2 seconds showing the control schemes for both players
- **Winner Detection**: Winner label at the bottom displays results when one or both players lose:
  - "PLAYER 1 WINS!" if Player 2's game ends first
  - "PLAYER 2 WINS!" if Player 1's game ends first
  - "IT'S A TIE!" if both end simultaneously (score comparison)
- **Shared Controls**: ESC to pause both games, N to start new game
- **MultiplayerGuiController.java**: New controller class managing both player interfaces
- **MultiplayerController.java**: Coordinator that initializes both game instances

**User Experience**: Players can compete head-to-head in the same physical space, fostering competitive play and social interaction. The clear visual separation and distinct control schemes prevent confusion and enable smooth multiplayer gameplay.

**Innovation**: This feature transforms Tetris from a solitary experience into a social, competitive game mode, significantly enhancing replayability and player engagement.

### 9. CSS-Based Styling System (Maintainability + Extensibility)
**Description**: External CSS stylesheet for centralized styling and easy theme modifications.

**Implementation Details**:
- **styles.css**: Comprehensive stylesheet defining all visual properties:
  - Root background gradients with diagonal line patterns
  - Board container styling with transparency and borders
  - Piece rectangles with rounded corners and inner shadow glow
  - Sidebar info boxes with semi-transparent backgrounds
  - Label and text styling (fonts, colors, sizes)
  - Button states (normal, hover, pressed) with scale transitions
  - Music icon and separator text styling
  - Multiplayer-specific styles (player labels, winner label, new game button)
- **Style Classes**: Semantic CSS classes applied via FXML:
  - `.title-text`: Main Tetris logo with gradient fill and drop shadow
  - `.subtitle-text`: N-Block subtitle with italic styling
  - `.game-board-container`: Board wrapper with light blue tint
  - `.piece-rect`: Individual brick cells with glow effects
  - `.info-box`: Sidebar status containers
  - `.bottom-bar`: Control panel at bottom
  - `.player-label`: Player identification in multiplayer mode
  - `.winner-label`: Winner announcement styling
  - `.new-game-button`: Green gradient button for restarting
- **Separation of Concerns**: Visual styling completely separated from Java logic, allowing designers to modify appearance without touching code
- **Maintainability**: Single source of truth for all visual properties; changes propagate throughout the application
- **Performance**: CSS styles compiled and cached by JavaFX for optimal rendering

**Technical Advantage**: This architecture allows for future theme expansion by creating alternate CSS files without modifying Java code. The current light classic theme could be easily complemented with dark, neon, or retro variants by swapping stylesheets.

### 9. Consistent Window Sizing (UI Enhancement)
**Description**: Standardized the gameplay window dimensions to match the main menu for a seamless user experience.

**Implementation Details**:
- **Window Dimensions**: Both main menu and gameplay windows now use 650px width and 700px height
- **Minimum Size Constraints**: Added setMinWidth(500) and setMinHeight(600) to prevent window squeezing
- **Centering**: Windows automatically center on screen for optimal positioning
- **Consistency**: Eliminates jarring size changes when transitioning between menu and game

**User Benefit**: Provides a smooth, professional transition between application states without visual disruption.

### 10. Next Block Preview (Gameplay Enhancement)
**Description**: Added a preview box in the sidebar showing the next tetromino that will appear after the current piece locks.

**Implementation Details**:
- **UI Component**: Added "Next Block" label and 4x4 GridPane in the sidebar
- **Data Integration**: Utilizes ViewData.getNextBrickData() to display upcoming piece
- **Real-time Updates**: Preview updates automatically when new pieces are generated
- **Visual Design**: Matches game board styling with proper colors and rounded corners
- **Positioning**: Placed below the held block section for logical flow

**User Benefit**: Allows players to plan ahead by seeing what piece is coming next, improving strategic gameplay and reducing surprises.

### 11. Hold/Reserve Function with 'R' Key (Gameplay Enhancement)
**Description**: Implemented a hold mechanic where players can store the current falling piece and swap it with the held piece using the 'R' key.

**Implementation Details**:
- **Key Binding**: Changed hold key from 'C' to 'R' for better accessibility
- **UI Component**: Added "Held Block" label and 4x4 GridPane above the next block preview
- **Swap Logic**: Pressing 'R' swaps current piece with held piece, or holds current piece if none held
- **Visual Feedback**: Held piece displayed in sidebar with same styling as game board
- **State Management**: Integrated with existing ViewData.getHeldBrickData() system

**User Benefit**: Adds strategic depth by allowing players to save pieces for later use, enabling better planning around difficult piece sequences.

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

- **Theme.java** (com.tetris.gui): Enumeration defining visual themes with color schemes for board gradients, piece glows, and UI accents. Currently implements a light classic theme system with support for extensibility.

- **ThemeManager.java** (com.tetris.gui): Singleton class managing theme persistence and application using Java Preferences API. Handles loading/saving user theme preferences across game sessions.

- **ThemeSelector.java** (com.tetris.gui): JavaFX modal dialog for theme selection with RadioButton previews (currently disabled in favor of fixed light classic theme).

- **ParticleEffect.java** (com.tetris.gui): Animation system for visual effects (currently not active in the light theme design but available for future enhancements).

- **HighScoresDialog.java** (com.tetris.gui): Dialog for displaying and managing high scores with TableView display and score persistence.

- **NotificationPanel.java** (com.tetris.gui): Animated panel for displaying score bonuses and game notifications with fade effects.

- **SettingsDialog.java** (com.tetris.gui): Dialog for adjusting game settings including sound volume and theme selection.

- **PauseMenuPanel.java** (com.tetris.gui): Overlay panel displayed when game is paused, providing options to resume, access settings, view help, or quit to main menu.

- **HelpDialog.java** (com.tetris.gui): Modal dialog displaying game controls, scoring system, and gameplay tips for new players.

- **MainMenu.java** (com.tetris.gui): Initial screen shown on application launch with options to start game, view high scores, adjust settings, or exit.

## New Resource Files

### CSS Stylesheets
- **styles.css** (src/main/resources): Main stylesheet defining the classic light theme with:
  - Root background gradient with diagonal line pattern
  - Game board container with light blue tint and cyan border
  - Piece rectangle styling with rounded corners and inner glow
  - Sidebar info boxes with semi-transparent white backgrounds
  - Typography for title, subtitle, and labels (Arial font family)
  - Button styling with hover and pressed states
  - Color scheme using classic Tetris colors and light blue palette

### FXML Layouts
- **gameLayout.fxml** (src/main/resources): Updated BorderPane layout structure:
  - Top section with title and subtitle VBox
  - Center section with game board StackPane and sidebar VBox
  - Bottom section with controls HBox (music icon, pause button)
  - CSS stylesheet links for window_style.css and styles.css
  - Style class assignments for all major components
  - Proper Insets for padding using JavaFX geometry

## Modified Java Classes
The following classes from the original codebase were modified to support the refactoring and new features:

- **SimpleBoard.java**: Originally a monolithic class handling multiple responsibilities. It was refactored into the three new classes (BoardState, BrickManager, TetrisBoard) to adhere to the Single Responsibility Principle. This change was necessary to improve code maintainability, testability, and scalability.

- **BrickGenerator.java**: Enhanced to fully implement the factory pattern, allowing for dynamic creation of different brick types. The modifications included adding abstract methods and concrete implementations, which were essential for decoupling brick creation from game logic.

- **GameController.java**: Updated to integrate the new refactored classes and implement the GameEventListener interface. Changes included adding references to LevelManager and modifying event handling methods. These modifications were necessary to support the new architecture and ensure proper event propagation.

- **GuiController.java**: Extensively modified to support the new GUI design and fix critical rendering issues:
  - **Rendering System Rewrite**: Completely overhauled `refreshBrick()` method to render pieces directly on displayMatrix instead of using separate panels
  - **Ghost Piece Integration**: Implemented `updateGhostPiece()` to render shadow pieces at 30% opacity on the main board
  - **CSS Style Application**: Added style class assignments ("piece-rect") to all Rectangle cells during initialization
  - **Property Binding**: Implemented real-time updates for score, lines, and level labels using JavaFX property binding
  - **Color Management**: Updated `getFillColor()` and added `getStrokeColor()` methods with proper hex color values for classic Tetris pieces
  - **Brick Styling**: Enhanced `setRectangleData()` to apply stroke, rounded corners, and fill colors to cells
  - **Level Display**: Added `showLevelUp()` and `updateStatus()` methods to refresh sidebar information
  - **Code Cleanup**: Removed obsolete rendering code (separate rectangles arrays, ghost panels) that were causing bugs
  - **Theme Support**: Integrated theme loading from Java Preferences API with `loadSavedTheme()` and `applyTheme()` methods
  
  These changes were critical for fixing the broken game mechanics where pieces were invisible and for implementing the new light classic theme design.

## Current Game State (November 2025)

### What's Working
The game is fully functional with the following features:
- ✅ **Core Gameplay**: All Tetris mechanics working correctly (movement, rotation, line clearing, scoring)
- ✅ **Visual Display**: Pieces are visible and render properly on a light blue classic-themed board
- ✅ **Ghost Piece**: Semi-transparent shadow shows landing position in real-time
- ✅ **Progressive Levels**: 4 difficulty levels with increasing speed (500ms → 200ms fall time)
- ✅ **Power-Ups**: Random power-ups awarded for Tetris clears (4 lines)
- ✅ **Hold Mechanic**: Players can store and swap pieces using the 'C' key
- ✅ **Hard Drop**: Space bar instantly drops pieces to bottom
- ✅ **Pause/Resume**: ESC key pauses game with proper state management
- ✅ **Score Tracking**: Real-time score, lines, and level display with property binding
- ✅ **Sound System**: Background music and sound effects for piece drops, line clears, level-ups
- ✅ **High Scores**: Persistent high score tracking with dialog display
- ✅ **Main Menu**: Start screen with game options and settings
- ✅ **CSS Styling**: Professional light classic theme with gradients, shadows, and rounded corners

### Known Visual Elements
- Light blue diagonal striped background (subtle repeating pattern)
- Orange-to-gold gradient "TETRIS" title with drop shadow
- Cyan "N-BLOCK" subtitle
- Cyan bordered game board (3px) with light blue tint background
- Classic Tetris colors: Cyan (I), Yellow (O), Purple (T), Green (S), Red (Z), Orange (L), Blue (J)
- White semi-transparent sidebar boxes showing Score, Lines, Level
- Royal blue pause button with hover effects

### Recent Fixes (Critical)
The game underwent major rendering fixes to resolve critical bugs:
- **Fixed invisible pieces**: Pieces now render correctly on the board
- **Fixed ghost piece**: Shadow pieces display at correct position with proper opacity
- **Fixed display updates**: Board refreshes properly after each move/lock
- **Fixed coordinate system**: Active pieces overlay correctly on the game board
- **Removed broken code**: Eliminated obsolete rendering systems causing conflicts

### Testing Status
- **60 unit tests** passing (100% success rate)
- All core mechanics verified through automated tests
- Manual playtesting confirms smooth gameplay experience
- No known game-breaking bugs

## Unexpected Problems
During the refactoring and feature implementation, several unexpected challenges were encountered:

- **Dependency Conflicts**: Maven dependency resolution issues arose when updating package structures, causing compilation failures. This was addressed by carefully reviewing and updating the pom.xml file to ensure compatible versions of JavaFX and JUnit dependencies.

- **Event Handling Complexity**: Implementing the observer pattern initially led to redundant event triggers and potential memory leaks. The problem was resolved by refactoring the event listener registration process, ensuring proper cleanup, and adding null checks to prevent exceptions.

- **Rendering System Breakdown**: During GUI redesign, the removal of the old brickPanel system broke piece rendering entirely, making pieces invisible. The root cause was that pieces were being created but never added to the display. This was fixed by completely rewriting the rendering logic to use displayMatrix as the single source of truth, with active pieces overlaid directly on the game board cells.

- **FXML Syntax Errors**: Initial FXML layout had invalid syntax including:
  - Direct RGBA color values in `fill` attributes (not supported in JavaFX)
  - Missing proper `xmlns` namespace declarations
  - Incorrect `padding` attribute format (needed `<Insets>` element)
  - Invalid gradient syntax for CSS linear-gradient in inline styles
  
  These were resolved by using proper JavaFX FXML syntax, moving color definitions to CSS, and using style attributes with correct JavaFX property formats.

- **CSS Linear Gradient Compatibility**: JavaFX CSS gradient syntax differs from standard CSS. The repeating-linear-gradient for diagonal lines required specific JavaFX format with explicit color stops and proper angle specification (45deg vs 45). Multiple iterations were needed to achieve the desired light blue diagonal line effect.

- **Ghost Piece Z-Order Issues**: Initially tried using a separate GridPane for ghost rendering, but this caused z-ordering problems where ghost appeared above active pieces. Solution was to render ghost directly on displayMatrix before active piece, only on transparent cells, ensuring proper layering.

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
