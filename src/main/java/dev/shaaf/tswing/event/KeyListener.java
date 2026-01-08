package dev.shaaf.tswing.event;

/**
 * The listener interface for receiving keyboard events.
 * Mirrors java.awt.event.KeyListener
 */
public interface KeyListener {
    /**
     * Invoked when a key has been pressed.
     */
    void keyPressed(KeyEvent e);

    /**
     * Invoked when a key has been released.
     */
    default void keyReleased(KeyEvent e) {
        // Default implementation does nothing
    }

    /**
     * Invoked when a key has been typed (pressed and released).
     */
    default void keyTyped(KeyEvent e) {
        // Default implementation does nothing
    }
}