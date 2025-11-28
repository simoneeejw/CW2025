package com.tetris.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;

/**
 * Manages sound effects and background music for the Tetris game.
 */
public class SoundManager {

    private static SoundManager instance;
    private MediaPlayer backgroundMusicPlayer;
    private boolean musicEnabled = true;
    private boolean soundEffectsEnabled = true;

    private SoundManager() {
        // Private constructor for singleton
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    /**
     * Sets whether background music is enabled.
     */
    public void setMusicEnabled(boolean enabled) {
        musicEnabled = enabled;
        if (!enabled && backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        } else if (enabled && backgroundMusicPlayer != null) {
            backgroundMusicPlayer.play();
        }
    }

    /**
     * Sets whether sound effects are enabled.
     */
    public void setSoundEffectsEnabled(boolean enabled) {
        soundEffectsEnabled = enabled;
    }

    /**
     * Plays background music in a loop.
     */
    public void playBackgroundMusic() {
        if (!musicEnabled) return;

        try {
            URL musicUrl = getClass().getClassLoader().getResource("sounds/background_music.mp3");
            if (musicUrl != null) {
                Media media = new Media(musicUrl.toString());
                backgroundMusicPlayer = new MediaPlayer(media);
                backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                backgroundMusicPlayer.setVolume(0.3); // Set volume to 30%
                backgroundMusicPlayer.play();
            } else {
                System.out.println("Background music file not found.");
            }
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
        if (!soundEffectsEnabled) {
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
                player.setVolume(0.7); // Set volume to 70%

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
}
