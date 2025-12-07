package com.tetris.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.prefs.Preferences;

/**
 * Manages sound effects and background music for the Tetris game.
 */
public class SoundManager {

    /** Singleton instance. */
    private static SoundManager instance;
    /** Media player for background music. */
    private MediaPlayer backgroundMusicPlayer;
    /** Background music media. */
    private static Media backgroundMedia;
    /** Music volume level. */
    private double musicVolume = 0.3;
    /** Sound effects volume level. */
    private double soundEffectsVolume = 0.7;

    private SoundManager() {
        Preferences prefs = Preferences.userNodeForPackage(SoundManager.class);
        musicVolume = prefs.getDouble("music_volume", 0.3);
        soundEffectsVolume = prefs.getDouble("sound_effects_volume", 0.7);
        preloadBackgroundMusic();
    }

    private void preloadBackgroundMusic() {
        try {
            URL musicUrl = getClass().getClassLoader().getResource("sounds/background_music.mp3");
            if (musicUrl != null) {
                backgroundMedia = new Media(musicUrl.toString());
            }
        } catch (Exception e) {
            System.out.println("Error preloading background music: " + e.getMessage());
        }
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    /**
     * Sets the music volume (0.0 to 1.0).
     */
    public void setMusicVolume(double volume) {
        musicVolume = Math.max(0.0, Math.min(1.0, volume));
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(musicVolume);
        }
    }

    /**
     * Sets the sound effects volume (0.0 to 1.0).
     */
    public void setSoundEffectsVolume(double volume) {
        soundEffectsVolume = Math.max(0.0, Math.min(1.0, volume));
    }

    /**
     * Gets the current music volume.
     */
    public double getMusicVolume() {
        return musicVolume;
    }

    /**
     * Gets the current sound effects volume.
     */
    public double getSoundEffectsVolume() {
        return soundEffectsVolume;
    }

    /**
     * Plays background music in a loop.
     */
    public void playBackgroundMusic() {
        if (musicVolume <= 0) return;

        // Stop any existing music to prevent duplication
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }

        try {
            if (backgroundMedia == null) {
                URL musicUrl = getClass().getClassLoader().getResource("sounds/background_music.mp3");
                if (musicUrl != null) {
                    backgroundMedia = new Media(musicUrl.toString());
                } else {
                    System.out.println("Background music file not found.");
                    return;
                }
            }
            backgroundMusicPlayer = new MediaPlayer(backgroundMedia);
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundMusicPlayer.setVolume(musicVolume); // Set volume to 30%
            backgroundMusicPlayer.play();
        } catch (Exception e) {
            System.out.println("Error playing background music: " + e.getMessage());
        }
    }

    /**
     * Stops background music.
     */
    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }

    /**
     * Plays a sound effect once.
     */
    private void playSoundEffect(String soundFileName) {
        if (soundEffectsVolume <= 0) {
            System.out.println("Sound effects are disabled!");
            return;
        }

        try {
            System.out.println("Looking for sound file: sounds/" + soundFileName);
            URL soundUrl = getClass().getClassLoader().getResource("sounds/" + soundFileName);
            if (soundUrl != null) {
                System.out.println("Sound file found at: " + soundUrl.toString());
                Media media = new Media(soundUrl.toString());
                MediaPlayer player = new MediaPlayer(media);
                player.setVolume(soundEffectsVolume); // Set volume to 70%

                // Dispose of player after sound finishes to prevent memory leaks
                player.setOnEndOfMedia(() -> player.dispose());

                player.play();
                System.out.println("Sound playing: " + soundFileName);
            } else {
                System.out.println("Sound effect file not found: " + soundFileName);
            }
        } catch (Exception e) {
            System.out.println("Error playing sound effect: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Plays the line clear sound effect.
     */
    public void playLineClearSound() {
        System.out.println("Attempting to play line clear sound...");
        playSoundEffect("line_clear.wav");
    }

    /**
     * Plays the Tetris (4-line clear) sound effect.
     */
    public void playTetrisSound() {
        System.out.println("Attempting to play tetris sound...");
        playSoundEffect("tetris_clear.wav");
    }

    /**
     * Plays the level up sound effect.
     */
    public void playLevelUpSound() {
        System.out.println("Attempting to play level up sound...");
        playSoundEffect("level_up.wav");
    }

    /**
     * Plays the game over sound effect.
     */
    public void playGameOverSound() {
        System.out.println("Attempting to play game over sound...");
        playSoundEffect("game_over.mp3");
    }

    /**
     * Plays the piece drop/lock sound effect.
     */
    public void playPieceDropSound() {
        System.out.println("Attempting to play piece drop sound...");
        playSoundEffect("piece_drop.wav");
    }

    /**
     * Plays the move sound effect (left/right).
     */
    public void playMoveSound() {
        System.out.println("Attempting to play move sound...");
        playSoundEffect("move.wav");
    }

    /**
     * Plays the rotate sound effect.
     */
    public void playRotateSound() {
        System.out.println("Attempting to play rotate sound...");
        playSoundEffect("rotate.wav");
    }

    /**
     * Plays the button click sound effect.
     */
    public void playButtonClickSound() {
        System.out.println("Attempting to play button click sound...");
        playSoundEffect("button_click.wav");
    }
}
