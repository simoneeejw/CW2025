package com.tetris.gui;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creates particle burst effects for line clears.
 * Generates fading dots that scatter outward from cleared lines.
 */
public class ParticleEffect {

    private final Group root;
    private final Random random = new Random();
    private final List<Circle> particles = new ArrayList<>();

    public ParticleEffect(Group root) {
        this.root = root;
    }

    /**
     * Creates a particle burst effect at the specified position.
     * @param centerX Center X coordinate
     * @param centerY Center Y coordinate
     * @param theme Current theme for particle colors
     */
    public void createBurst(double centerX, double centerY, Theme theme) {
        // Clear any existing particles
        clearParticles();

        // Create 20-30 particles
        int particleCount = 20 + random.nextInt(11);

        for (int i = 0; i < particleCount; i++) {
            Circle particle = new Circle(2 + random.nextDouble() * 3); // Size 2-5
            particle.setFill(Color.web(theme.getPieceGlowColor()));
            particle.setOpacity(0.8);

            // Position at center
            particle.setCenterX(centerX);
            particle.setCenterY(centerY);

            // Random direction and distance
            double angle = random.nextDouble() * 2 * Math.PI;
            double distance = 50 + random.nextDouble() * 100;
            double targetX = centerX + Math.cos(angle) * distance;
            double targetY = centerY + Math.sin(angle) * distance;

            // Add to scene
            root.getChildren().add(particle);
            particles.add(particle);

            // Create animations
            TranslateTransition move = new TranslateTransition(Duration.millis(800 + random.nextInt(400)), particle);
            move.setToX(targetX - centerX);
            move.setToY(targetY - centerY);

            FadeTransition fade = new FadeTransition(Duration.millis(600 + random.nextInt(400)), particle);
            fade.setToValue(0.0);

            ParallelTransition animation = new ParallelTransition(move, fade);
            animation.setOnFinished(e -> {
                root.getChildren().remove(particle);
                particles.remove(particle);
            });

            animation.play();
        }
    }

    /**
     * Clears all active particles.
     */
    public void clearParticles() {
        for (Circle particle : particles) {
            root.getChildren().remove(particle);
        }
        particles.clear();
    }
}
