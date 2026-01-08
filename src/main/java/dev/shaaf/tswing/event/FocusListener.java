package dev.shaaf.tswing.event;

/**
 * The listener interface for receiving focus events.
 * Mirrors java.awt.event.FocusListener
 */
public interface FocusListener {
    /**
     * Invoked when a component gains focus.
     */
    void focusGained(FocusEvent e);

    /**
     * Invoked when a component loses focus.
     */
    void focusLost(FocusEvent e);
}