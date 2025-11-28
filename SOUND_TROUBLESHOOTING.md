# Sound Effects Troubleshooting Guide

## Current Sound System Status

The game has been configured with the following sounds:

### Required Sound Files (in `src/main/resources/sounds/`)
1. ✅ **background_music.mp3** - Background music (looping)
2. ✅ **line_clear.wav** - When clearing 1-3 lines
3. ✅ **tetris_clear.wav** - When clearing 4 lines (Tetris!)
4. ✅ **level_up.wav** - When leveling up
5. ✅ **game_over.mp3** - When game ends

### How to Test Sound Effects

1. **Run the game** using `mvn javafx:run`
2. **Check the console output** - You should see debug messages like:
   - "Attempting to play line clear sound..."
   - "Looking for sound file: sounds/line_clear.wav"
   - "Sound file found at: ..."
   - "Sound playing: line_clear.wav"

### Common Issues and Solutions

#### Issue 1: "Sound effects are disabled!"
**Solution:** Go to Settings → Audio tab → Enable "Sound Effects" checkbox

#### Issue 2: "Sound effect file not found"
**Solution:** 
- Make sure your sound files are in `src/main/resources/sounds/`
- Check the file names match exactly (case-sensitive)
- Rebuild the project: `mvn clean compile`

#### Issue 3: Sound files found but no audio playing
**Possible causes:**
- **File format issues:** WAV files should be PCM format (not compressed)
- **File corruption:** Try playing the files in a media player first
- **JavaFX Media support:** Some audio codecs may not be supported
- **System volume:** Check your system audio is not muted

#### Issue 4: Background music plays but not sound effects
**Solutions:**
1. Check if sound effects are enabled in Settings
2. The sound effect files might be too short or invalid
3. Try converting your WAV files to a different format (PCM 16-bit, 44100 Hz)

### Testing Individual Sounds

To trigger each sound effect:
- **Line Clear:** Clear 1, 2, or 3 lines
- **Tetris Clear:** Clear 4 lines at once
- **Level Up:** Reach the next level (clear required lines)
- **Game Over:** Let blocks reach the top

### File Format Recommendations

- **Background Music:** MP3, 128-320 kbps
- **Sound Effects:** WAV (PCM 16-bit, 44100 Hz, Mono or Stereo)

### Debug Mode

The game is currently in debug mode with console logging. After testing, you can remove the debug messages from:
- `SoundManager.java` (lines with `System.out.println`)

### Next Steps

1. Run the game with `mvn javafx:run`
2. Watch the console for debug messages
3. Try to clear lines and level up
4. Report back with the console output to diagnose further

