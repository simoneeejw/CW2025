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
  - **Bottom**: HBox with music icon (♫) for audio indication
  - **Pause Button**: Positioned at the bottom of the sidebar below "Next Block" panel for easy access during gameplay
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
- **Pause/Resume**: ESC key or Pause button toggles game pause with proper Timeline management and overlay menu
- **Key Debouncing**: Prevents rapid repeated inputs from causing glitches
- **Control Guide**: Full keyboard layout available in the game interface

**Controls**:
- ← → (or A/D): Move left/right
- ↑ (or W): Rotate piece
- ↓ (or S): Soft drop (move down faster)
- SPACE: Hard drop (instant drop to bottom)
- R: Hold/swap piece
- ESC or Pause Button: Open pause menu
- N: New game (from pause menu)

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
- **Main Menu Selection**: Added horizontal toggle buttons in the main menu to select between Single Player and Multiplayer modes
  - Visual toggle with color-coded selection (bright green gradient for selected, dark gray for unselected)
  - Game mode selection placed prominently above the "Play Game" button
  - Buttons arranged horizontally with equal widths (145px each) matching the play button's total width
  - Smooth hover effects on unselected buttons
  - Fixed button sizing prevents layout shifts when switching modes
- **Consistent Window Sizing**: All three pages (Main Menu, Single Player, Multiplayer) use the same 820x700px window dimensions
  - Main Menu: 820x700px with centered content
  - Single Player: 820x700px with centered game board in the wider window
  - Multiplayer: 820x700px precisely sized for side-by-side boards and sidebars
  - Seamless transitions between pages without jarring size changes
- **Multiplayer Layout**: Created dedicated `multiplayerLayout.fxml` with optimized BorderPane layout:
  - **Left Sidebar**: Player 1 details (120px wide) with score, lines, level, held block, and next block
  - **Center**: Two game boards (250px each) positioned side-by-side with 20px spacing
  - **Right Sidebar**: Player 2 details (120px wide) with score, lines, level, held block, and next block
  - Game boards centered in the window for balanced visual appearance
  - Each player has independent game state and display
  - Total content width: 120 + 250 + 20 + 250 + 120 = 760px (fits perfectly in 820px with padding)
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
  - Clear, readable text explaining both control schemes
  - Automatically fades after 2 seconds to not obstruct gameplay
- **Property Binding System**: Implemented proper JavaFX property binding for real-time updates
  - Created IntegerProperty instances for each player's score, lines, and level
  - Labels bound to properties using Bindings.concat() for automatic updates
  - updateStatus() method sets property values instead of text directly
  - Eliminates "bound value cannot be set" errors
- **Dual Game Controllers**: Two independent GameController instances manage separate game states
  - Player1Listener and Player2Listener implement GameEventListener interface
  - Each listener updates its respective player's UI components
  - Separate game timelines for independent piece falling
  - Key handling routes inputs to the correct controller
- **Winner Detection**: Winner label at the bottom displays results when one or both players lose:
  - "PLAYER 1 WINS!" if Player 2's game ends first
  - "PLAYER 2 WINS!" if Player 1's game ends first
  - Score comparison for tie-breaking if implemented
- **Shared Controls**: ESC to pause both games, N to start new game
- **MultiplayerGuiController.java**: New controller class managing both player interfaces with proper property binding
- **MultiplayerController.java**: Lightweight coordinator that initializes the multiplayer controller

**User Experience**: Players can compete head-to-head in the same physical space, fostering competitive play and social interaction. The clear visual separation, distinct control schemes, and centered layout prevent confusion and enable smooth multiplayer gameplay. Consistent window sizing across all pages provides a polished, professional user experience.

**Innovation**: This feature transforms Tetris from a solitary experience into a social, competitive game mode, significantly enhancing replayability and player engagement. The split-screen implementation with independent game states demonstrates advanced JavaFX programming and proper MVC architecture.

### 9. Critical Bug Fixes and Game Stability Improvements
**Description**: Resolved multiple critical bugs that were preventing proper gameplay, including collision detection issues, spawn position problems, and rendering inconsistencies.

**Implementation Details**:
- **Fixed Collision Detection**: Corrected matrix indexing bugs in `MatrixOperations.intersect()` and `merge()` methods
  - Fixed `brick[j][i]` to `brick[i][j]` for proper row-column access
  - Fixed coordinate offsets from `targetX = x + i; targetY = y + j` to `targetX = x + j; targetY = y + i`
  - Now pieces can properly detect collisions and reach board edges
- **Fixed Spawn Position**: Corrected piece spawning to start at row 0 (top of board) instead of row 10 (middle)
  - Updated `BrickManager.createNewBrick()` to set `currentOffset.setLocation(4, 0)`
  - Updated `BoardState` constructor and reset methods to spawn at `(4, 0)`
  - Pieces now appear at the top of the visible game area
- **Fixed Board Rendering**: Resolved game board display issues where pieces weren't visible
  - Updated `GuiController.initGameView()` to render from row 0 instead of row 2
  - Removed gaps between board cells by setting `hgap="0" vgap="0"`
  - Game board now fills the entire container from top to bottom
- **Fixed Property Binding**: Resolved "bound value cannot be set" errors in multiplayer mode
  - Created separate IntegerProperty instances for each player's stats
  - Used `Bindings.concat()` for proper label binding
  - updateStatus() methods now set property values instead of text directly
- **Fixed Window Sizing**: Standardized all windows to consistent dimensions
  - Main Menu: 820x700px
  - Single Player: 450x720px (optimized for content)
  - Multiplayer: 820x700px
  - Added proper min/max constraints to prevent layout issues
- **Fixed FXML Errors**: Resolved duplicate ID references and invalid attributes
  - Removed duplicate `fx:id="gamePanel"` elements
  - Added proper alignment attributes (`StackPane.alignment="TOP_LEFT"`)
  - Fixed inconsistent min/max width/height values

**Impact**: These fixes were essential for making the game playable. Before the fixes, pieces couldn't move properly, weren't visible, and spawned in wrong positions. After the fixes, all game mechanics work correctly with proper visual feedback and smooth gameplay.

### 10. CSS-Based Styling System (Maintainability + Extensibility)
**Description**: External CSS stylesheet for centralized styling and easy theme modifications.

**Implementation Details**:
- **styles.css**: Comprehensive stylesheet defining all visual properties:
  - Root background gradients with diagonal line patterns
  - Board container styling with transparency and borders
  - Piece rectangles with rounded corners and inner shadow glow
  - Sidebar info boxes with semi-transparent backgrounds
  - Typography for title, subtitle, and labels (Arial font family)
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

### 11. Consistent Window Sizing (UI Enhancement)
**Description**: Standardized the gameplay window dimensions to match the main menu for a seamless user experience.

**Implementation Details**:
- **Window Dimensions**: Both main menu and gameplay windows now use 650px width and 700px height
- **Minimum Size Constraints**: Added setMinWidth(500) and setMinHeight(600) to prevent window squeezing
- **Centering**: Windows automatically center on screen for optimal positioning
- **Consistency**: Eliminates jarring size changes when transitioning between menu and game

**User Benefit**: Provides a smooth, professional transition between application states without visual disruption.

### 12. Next Block Preview (Gameplay Enhancement)
**Description**: Added a preview box in the sidebar showing the next tetromino that will appear after the current piece locks.

**Implementation Details**:
- **UI Component**: Added "Next Block" label and 4x4 GridPane in the sidebar
- **Data Integration**: Utilizes ViewData.getNextBrickData() to display upcoming piece
- **Real-time Updates**: Preview updates automatically when new pieces are generated
- **Visual Design**: Matches game board styling with proper colors and rounded corners
- **Positioning**: Placed below the held block section for logical flow

**User Benefit**: Allows players to plan ahead by seeing what piece is coming next, improving strategic gameplay and reducing surprises.

### 13. Hold/Reserve Function with 'R' Key (Gameplay Enhancement)
**Description**: Implemented a hold mechanic where players can store the current falling piece and swap it with the held piece using the 'R' key.

**Implementation Details**:
- **Key Binding**: Changed hold key from 'C' to 'R' for better accessibility
- **UI Component**: Added "Held Block" label and 4x4 GridPane above the next block preview
- **Swap Logic**: Pressing 'R' swaps current piece with held piece, or holds current piece if none held
- **Visual Feedback**: Held piece displayed in sidebar with same styling as game board
- **State Management**: Integrated with existing ViewData.getHeldBrickData() system

**User Benefit**: Adds strategic depth by allowing players to save pieces for later use, enabling better planning around difficult piece sequences.

### 14. Adjustable Audio Volume Controls (Settings Enhancement)
**Description**: Enhanced audio settings from simple on/off toggles to fully adjustable volume sliders, providing users with granular control over game audio levels.

**Implementation Details**:
- **SoundManager.java Refactor**: Completely redesigned audio management system:
  - Removed boolean enabled flags in favor of volume-based control (0.0 to 1.0 range)
  - Added `setMusicVolume()` and `setSoundEffectsVolume()` methods for precise control
  - Added `getMusicVolume()` and `getSoundEffectsVolume()` getter methods
  - Modified `playBackgroundMusic()` and `playSoundEffect()` to check volume > 0 instead of enabled flags
  - Automatic preference loading on initialization for persistent volume settings
- **SettingsDialog.java Enhancement**: Replaced checkboxes with professional volume sliders:
  - 0-100% range sliders with tick marks at 25% intervals
  - Real-time percentage labels updating as sliders move
  - Immediate audio feedback when adjusting volume levels
  - Persistent storage using Java Preferences API
  - Default volumes: Background Music 30%, Sound Effects 70%
- **UI Design**: Clean, intuitive interface with:
  - Slider width: 200px for precise control
  - Value labels in golden color (#FFD700) for visibility
  - Smooth slider interaction with 5-unit increments
  - Consistent styling matching the game's light classic theme
- **User Experience**: Volume 0% effectively disables audio, maintaining off functionality while providing fine-tuned control
- **Technical Integration**: Seamless integration with existing sound system without breaking changes

**User Benefit**: Players can now customize audio levels to their environment and preferences, from complete silence to full volume, enhancing accessibility and user satisfaction. The professional slider interface provides precise control with immediate feedback.

### 15. Pause Menu System (UI Enhancement + User Experience)
**Description**: Implemented a comprehensive pause menu overlay that appears when the game is paused, providing three key options: Resume, Options (settings), and Quit.

**Implementation Details**:
- **PauseMenuPanel.java**: Custom VBox component that serves as an overlay menu:
  - Semi-transparent blue background (rgba(100, 120, 180, 0.95)) with border and rounded corners
  - "PAUSED" title in large bold font (48px) with drop shadow effect
  - Three menu buttons with distinct styling and visual hierarchy
  - Automatically hidden by default, shown only when game is paused
  - Positioned and centered over the game board using StackPane alignment
- **Button Hierarchy**:
  - **Resume Button**: Largest button (340x90px, 36px font) with prominent styling to emphasize primary action
    - Light background with darker border for maximum visibility
    - Hover effect: brightens to white with enhanced shadow
    - Direct action: resumes game and hides menu
  - **Options Button**: Standard size (320x80px, 32px font) for secondary actions
    - Opens SettingsDialog for adjusting volume and key bindings
    - Lighter styling than Resume to show secondary importance
    - Hover effect: subtle color change and shadow
  - **Quit Button**: Standard size matching Options button
    - Returns player to main menu (ends current game)
    - Same styling as Options for consistency
    - Hover effect matches Options button
- **Activation Methods**:
  - ESC key: Toggles pause state (pause/resume)
  - Pause button: Positioned below "Next Block" panel in sidebar for easy access
  - Both methods trigger the same togglePause() method
- **Lazy Initialization**: Menu panel created on first pause to optimize performance
  - Checks for scene availability before initialization
  - Sets up action handlers during first creation
  - Subsequent pauses simply show/hide the existing panel
- **Timeline Management**: Properly pauses game Timeline when menu is shown
  - Prevents pieces from falling while paused
  - Resumes Timeline when game continues
  - Maintains game state integrity during pause
- **UI Integration**: Seamlessly integrated into existing layout
  - Added to center StackPane of BorderPane root
  - Positioned on top of game board with proper z-ordering
  - No interference with game board or sidebar elements
  - Menu automatically focuses to accept input

**User Experience**: 
- **Intuitive Access**: ESC key is a universal pause shortcut, supplemented by a visible button
- **Clear Visual Hierarchy**: Resume button stands out as the primary action
- **Convenient Settings**: Players can adjust audio without leaving the game
- **Safe Exit**: Quit option provides a clear way to return to menu
- **Non-Intrusive**: Menu appears only when explicitly paused, doesn't obstruct gameplay
- **Professional Polish**: Smooth transitions, consistent styling, and proper visual feedback

**Technical Implementation**:
- Pause button moved from bottom bar to sidebar (below Next Block panel) for better positioning
- Added null checks in togglePause() to prevent crashes before initialization
- Implemented show() and hide() methods for clean visibility management
- Action handlers use Runnable callbacks for flexible integration
- Settings dialog opens modally on top of pause menu

**Quality**: 
- Clean separation of concerns (PauseMenuPanel is self-contained)
- Reusable component that could be extended for additional menu items
- Properly integrated with existing game loop and UI architecture
- No performance impact when not visible (hidden by default)

### 16. Tutorial System (User Experience Enhancement)
**Description**: Added a comprehensive tutorial accessible from the main menu to guide new players through game mechanics, controls, and strategies.

**Implementation Details**:
- **HelpDialog.java**: Modal dialog providing step-by-step tutorial content:
  - **Game Controls Section**: Grid layout displaying all keyboard controls with clear action-key pairings
  - **How to Play Section**: Numbered step-by-step instructions covering basic to advanced gameplay
  - **Tips Section**: Strategic advice for better performance and avoiding common mistakes
  - **Scrollable Interface**: ScrollPane wrapper allows content to extend beyond dialog height
  - **Visual Design**: Dark blue background (#2A3A6A) with white title text and white content boxes for high contrast
  - **Accessibility**: Text wrapping and proper font sizing (14-18px) for readability
- **Main Menu Integration**: "❓ HELP" button added to quick actions section
  - Styled consistently with other menu buttons (green gradient, hover effects)
  - Positioned between High Scores and Settings for logical flow
  - Opens HelpDialog modally when clicked
- **Content Structure**:
  - Introduction to Tetris mechanics
  - Complete control reference (movement, rotation, special actions)
  - Scoring and progression explanation
  - Power-up and hold feature descriptions
  - Strategic tips for line clearing and planning
- **User Experience**: Provides immediate guidance for beginners without leaving the main menu

## Recent Updates

### Sound Effects Implementation
**Description**: Added comprehensive sound effects for movement, rotation, and all button clicks throughout the application.

**Implementation Details**:
- **SoundManager.java Enhancement**: Extended the audio system with new sound effect methods:
  - `playMoveSound()`: Plays when moving pieces left or right
  - `playRotateSound()`: Plays when rotating pieces
  - `playButtonClickSound()`: Plays for all button interactions
- **Movement Sounds**: Integrated into `GuiController.java` key event handlers for left/right movement and rotation
- **Button Click Sounds**: Added to every button in the application:
  - **Main Menu**: Play Game, High Scores, Settings, Help, Exit buttons
  - **Theme Selector**: All theme selection buttons + Start Game button
  - **Settings Dialog**: Audio/Controls tabs, Save, Cancel, all key change buttons
  - **Pause Menu**: Resume, Options, Quit buttons
  - **High Scores Dialog**: Close button
  - **Help Dialog**: Close button
  - **Game Screen**: Pause button
  - **Game Over Dialog**: Play Again, Main Menu buttons
- **Sound Files Required**: Place these WAV files in `src/main/resources/sounds/`:
  - `move.wav`: Sound for left/right piece movement
  - `rotate.wav`: Sound for piece rotation
  - `button_click.wav`: Sound for all button clicks
- **Volume Control**: All sounds respect the existing volume settings in the Settings dialog

**User Benefit**: Provides immediate audio feedback for all user interactions, enhancing the gaming experience with satisfying sound effects that respond to every action.

### Custom Font Implementation
**Description**: Enhanced the main menu with a custom Google Font for improved visual appeal and branding.

**Implementation Details**:
- **Font Selection**: Implemented "Bungee Spice" font from Google Fonts for a bold, playful appearance
- **Font Loading**: Added `Font.loadFont()` in MainMenu.java to load `BungeeSpice-Regular.ttf` from resources
- **UI Application**: Applied the custom font to all main menu buttons and labels:
  - Title label (48px bold)
  - All buttons: High Scores, Settings, Help, Play Game, Exit (24px bold)
  - Credits label (11px normal)
- **Fallback Handling**: Graceful fallback to system fonts if custom font fails to load

**User Benefit**: The custom font gives the main menu a unique, professional look that enhances the game's visual identity.

### Enhanced Background Layers
**Description**: Added layered backgrounds with overlays for depth and visual interest.

**Implementation Details**:
- **Background Overlay**: Semi-transparent black overlay (40% opacity) on top of background images
- **Layered Rendering**: Used `Background` with both `BackgroundImage` and `BackgroundFill` for proper layering
- **Fallback Gradient**: Improved fallback gradient to a vibrant blue: `linear-gradient(to bottom, #1a1a2e, #16213e, #0f3460)`
- **Visual Depth**: Creates a cinematic effect with better contrast for text and buttons

**User Benefit**: The layered backgrounds add visual depth and make the interface more engaging and professional.

### Background Music Duplication Fix
**Description**: Resolved issue where background music would duplicate when transitioning from main menu to game.

**Implementation Details**:
- **Stop Before Play**: Added `SoundManager.getInstance().stopBackgroundMusic()` before playing new music in `loadGame()`
- **Clean Transitions**: Ensures smooth audio transitions without overlapping tracks
- **Proper Timing**: Music stops immediately when entering game, preventing duplication

**User Benefit**: Eliminates annoying audio duplication, providing a clean audio experience during gameplay transitions.

### High Scores Window Enhancements
**Description**: Improved the high scores dialog with better visuals and layout.

**Implementation Details**:
- **White Text**: Changed all text to white color for better contrast on dark backgrounds
- **Front Layer Positioning**: Ensured names and scores appear on the front layer, not hidden behind backgrounds
- **Rank and Level Display**: Added white color to rank and level indicators for consistency
- **Visual Hierarchy**: Improved readability and professional appearance

**User Benefit**: The high scores window is now more readable and visually appealing.

### Settings Page Improvements
**Description**: Enhanced the settings dialog with better layout and visual elements.

**Implementation Details**:
- **Sidebar Enhancement**: Added pretty containers for "Audio" and "Control" labels
- **Upper Sidebar Styling**: Enhanced the appearance of the upper sidebar options
- **Container Removal**: Removed cyan containers and repositioned elements for better usability
- **Visual Consistency**: Improved overall look to match the high scores window styling

**User Benefit**: The settings page now has a more polished and user-friendly interface.

### Countdown Timer Implementation
**Description**: Added a 3-second countdown before game start for better user experience.

**Implementation Details**:
- **Countdown Display**: Visual countdown in the game board area showing "3", "2", "1", "GO!"
- **UI Integration**: Countdown appears in the small area of the game board container
- **Pretty Visualization**: Styled with attractive fonts and colors for engagement
- **Timing**: Exactly 3 seconds before game pieces start falling

**User Benefit**: Gives players a moment to prepare, enhancing the gaming experience with anticipation.

### Font Upload and Implementation
**Description**: Successfully implemented a custom font uploaded by the user.

**Implementation Details**:
- **Font File**: Used the uploaded `AlfaSlabOne-Regular.ttf` font file
- **Resource Placement**: Placed in `src/main/resources` for proper loading
- **Font Loading**: Updated font loading code to use the new font file
- **Family Name**: Applied "Alfa Slab One" family name to all UI elements

**User Benefit**: The custom font personalizes the game's appearance and improves visual branding.

## Conclusion

This Tetris implementation represents a comprehensive, feature-rich game with professional-quality UI, robust testing, and innovative gameplay enhancements. The codebase follows SOLID principles, utilizes design patterns, and provides an excellent foundation for further development. All features are fully functional and tested, delivering a complete Tetris experience that rivals commercial implementations.

**Total Features Implemented**: 16 major enhancements plus core game functionality
**Test Coverage**: 60 passing unit tests
**Code Quality**: Refactored architecture with proper separation of concerns
**User Experience**: Polished interface with smooth gameplay and comprehensive controls
