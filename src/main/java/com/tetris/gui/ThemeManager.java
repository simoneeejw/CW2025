package com.tetris.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Manages the current visual theme and provides theme switching functionality.
 */
public class ThemeManager {

    private static ThemeManager instance;
    private final ObjectProperty<Theme> currentTheme;

    private ThemeManager() {
        // Default to Neon Night theme
        this.currentTheme = new SimpleObjectProperty<>(Theme.NEON_NIGHT);
    }

    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    public Theme getCurrentTheme() {
        return currentTheme.get();
    }

    public void setCurrentTheme(Theme theme) {
        this.currentTheme.set(theme);
    }

    public ObjectProperty<Theme> currentThemeProperty() {
        return currentTheme;
    }

    /**
     * Applies the current theme to the GUI controller.
     * @param guiController The GUI controller to update
     */
    public void applyTheme(GuiController guiController) {
        guiController.applyTheme(getCurrentTheme());
    }
}

