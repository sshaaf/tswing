package dev.shaaf.tswing.event;

import dev.shaaf.tswing.TComponent;

/**
 * Base class for all TSwing events.
 * Mirrors java.awt.AWTEvent
 */
public abstract class TEvent {
    protected final TComponent source;
    protected final long when;
    protected boolean consumed;

    public TEvent(TComponent source) {
        this.source = source;
        this.when = System.currentTimeMillis();
        this.consumed = false;
    }

    public TComponent getSource() {
        return source;
    }

    public long getWhen() {
        return when;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public void consume() {
        this.consumed = true;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[source=" + source + ",when=" + when + "]";
    }
}