package dev.shaaf.tswing.event;

import dev.shaaf.tswing.TComponent;

/**
 * An event which indicates that a component has gained or lost focus.
 * Mirrors java.awt.event.FocusEvent
 */
public class FocusEvent extends TEvent {
    public static final int FOCUS_GAINED = 1;
    public static final int FOCUS_LOST = 2;

    private final int eventType;
    private final TComponent oppositeComponent;

    public FocusEvent(TComponent source, int eventType, TComponent oppositeComponent) {
        super(source);
        this.eventType = eventType;
        this.oppositeComponent = oppositeComponent;
    }

    public FocusEvent(TComponent source, int eventType) {
        this(source, eventType, null);
    }

    public int getEventType() {
        return eventType;
    }

    public TComponent getOppositeComponent() {
        return oppositeComponent;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[source=" + source + ",eventType=" +
               (eventType == FOCUS_GAINED ? "FOCUS_GAINED" : "FOCUS_LOST") +
               ",opposite=" + oppositeComponent + "]";
    }
}