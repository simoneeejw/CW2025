package com.tetris.model;

/**
 * Data returned when brick moves down.
 */
public final class DownData {
    /** The clear row data if lines were cleared. */
    private final ClearRow clearRow;
    /** The updated view data. */
    private final ViewData viewData;

    /**
     * Constructs DownData with clear row and view data.
     * @param clearRow the clear row data
     * @param viewData the view data
     */
    public DownData(ClearRow clearRow, ViewData viewData) {
        this.clearRow = clearRow;
        this.viewData = viewData;
    }

    /**
     * Gets the clear row data.
     * @return the clear row
     */
    public ClearRow getClearRow() {
        return clearRow;
    }

    /**
     * Gets the view data.
     * @return the view data
     */
    public ViewData getViewData() {
        return viewData;
    }
}
