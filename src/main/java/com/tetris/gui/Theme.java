package com.tetris.gui;

/**
 * Enumeration of available visual themes for the Tetris game.
 * Each theme defines colors for board background, piece effects, and UI elements.
 */
public enum Theme {
    CLASSIC_RETRO("Classic Retro", "#000000", "#333333", "#FFFFFF", "#FFFF00"),
    NEON_NIGHT("Neon Night", "#000033", "#000066", "#00FFFF", "#FF00FF"),
    ZEN_MINIMAL("Zen Minimal", "#F5F5F5", "#E0E0E0", "#333333", "#666666");

    private final String displayName;
    private final String boardGradientStart;
    private final String boardGradientEnd;
    private final String pieceGlowColor;
    private final String uiAccentColor;

    Theme(String displayName, String boardGradientStart, String boardGradientEnd,
          String pieceGlowColor, String uiAccentColor) {
        this.displayName = displayName;
        this.boardGradientStart = boardGradientStart;
        this.boardGradientEnd = boardGradientEnd;
        this.pieceGlowColor = pieceGlowColor;
        this.uiAccentColor = uiAccentColor;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getBoardGradientStart() {
        return boardGradientStart;
    }

    public String getBoardGradientEnd() {
        return boardGradientEnd;
    }

    public String getPieceGlowColor() {
        return pieceGlowColor;
    }

    public String getUiAccentColor() {
        return uiAccentColor;
    }
}
