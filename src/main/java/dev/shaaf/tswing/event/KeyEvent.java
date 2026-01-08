package dev.shaaf.tswing.event;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import dev.shaaf.tswing.TComponent;

/**
 * An event which indicates that a keystroke occurred in a component.
 * Mirrors java.awt.event.KeyEvent
 */
public class KeyEvent extends TEvent {
    public static final int KEY_PRESSED = 1;
    public static final int KEY_RELEASED = 2;
    public static final int KEY_TYPED = 3;

    private final KeyStroke keyStroke;
    private final int eventType;

    public KeyEvent(TComponent source, int eventType, KeyStroke keyStroke) {
        super(source);
        this.eventType = eventType;
        this.keyStroke = keyStroke;
    }

    public KeyStroke getKeyStroke() {
        return keyStroke;
    }

    public int getEventType() {
        return eventType;
    }

    public KeyType getKeyType() {
        return keyStroke.getKeyType();
    }

    public Character getCharacter() {
        return keyStroke.getCharacter();
    }

    public boolean isCtrlDown() {
        return keyStroke.isCtrlDown();
    }

    public boolean isAltDown() {
        return keyStroke.isAltDown();
    }

    public boolean isShiftDown() {
        return keyStroke.isShiftDown();
    }

    @Override
    public String toString() {
        return getClass().getName() + "[source=" + source + ",keyStroke=" + keyStroke + ",eventType=" + eventType + "]";
    }
}