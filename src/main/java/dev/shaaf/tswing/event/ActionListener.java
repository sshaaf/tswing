package dev.shaaf.tswing.event;

/**
 * The listener interface for receiving action events.
 * Mirrors java.awt.event.ActionListener
 */
@FunctionalInterface
public interface ActionListener {
    /**
     * Invoked when an action occurs.
     */
    void actionPerformed(ActionEvent e);
}
