package dev.shaaf.tswing.event;

import dev.shaaf.tswing.TComponent;

/**
 * An event which indicates that a component-defined action occurred.
 * Mirrors java.awt.event.ActionEvent
 */
public class ActionEvent extends TEvent {
    private final String command;

    public ActionEvent(TComponent source, String command) {
        super(source);
        this.command = command;
    }

    public String getActionCommand() {
        return command;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[source=" + source + ",command=" + command + "]";
    }
}
