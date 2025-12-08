package com.tetris.model;

/**
 * Movement event with type and source.
 */
public final class MoveEvent {
    /** The type of the event. */
    private final EventType eventType;
    /** The source of the event. */
    private final EventSource eventSource;

    /**
     * Constructs a MoveEvent with type and source.
     * @param eventType the event type
     * @param eventSource the event source
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * Gets the event type.
     * @return the event type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Gets the event source.
     * @return the event source
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}
