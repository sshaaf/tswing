package dev.shaaf.tswing;

import com.googlecode.lanterna.screen.Screen;
import dev.shaaf.tswing.event.FocusEvent;
import dev.shaaf.tswing.event.FocusListener;
import dev.shaaf.tswing.event.KeyEvent;
import dev.shaaf.tswing.event.KeyListener;
import dev.shaaf.tswing.geom.Dimension;
import dev.shaaf.tswing.geom.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all TSwing components.
 * Mirrors javax.swing.JComponent and java.awt.Component
 */
public abstract class TComponent {
    private TContainer parent;
    private Point location;
    private Dimension size;
    private Dimension preferredSize;
    private Dimension minimumSize;
    private Dimension maximumSize;

    private TColor foreground;
    private TColor background;

    private boolean visible;
    private boolean enabled;
    private boolean focusable;
    private boolean hasFocus;

    private final List<KeyListener> keyListeners;
    private final List<FocusListener> focusListeners;

    public TComponent() {
        this.location = new Point(0, 0);
        this.size = new Dimension(0, 0);
        this.foreground = TColor.WHITE;
        this.background = TColor.BLACK;
        this.visible = true;
        this.enabled = true;
        this.focusable = false;
        this.hasFocus = false;
        this.keyListeners = new ArrayList<>();
        this.focusListeners = new ArrayList<>();
    }

    // Geometry methods
    public Point getLocation() {
        return new Point(location);
    }

    public void setLocation(int x, int y) {
        this.location.setLocation(x, y);
    }

    public void setLocation(Point p) {
        this.location.setLocation(p);
    }

    public int getX() {
        return location.x;
    }

    public int getY() {
        return location.y;
    }

    public Dimension getSize() {
        return new Dimension(size);
    }

    public void setSize(int width, int height) {
        this.size.setSize(width, height);
    }

    public void setSize(Dimension d) {
        this.size.setSize(d);
    }

    public int getWidth() {
        return size.width;
    }

    public int getHeight() {
        return size.height;
    }

    public void setBounds(int x, int y, int width, int height) {
        setLocation(x, y);
        setSize(width, height);
    }

    // Preferred size methods
    public Dimension getPreferredSize() {
        if (preferredSize != null) {
            return new Dimension(preferredSize);
        }
        return calculatePreferredSize();
    }

    public void setPreferredSize(Dimension preferredSize) {
        this.preferredSize = preferredSize;
    }

    public Dimension getMinimumSize() {
        if (minimumSize != null) {
            return new Dimension(minimumSize);
        }
        return new Dimension(1, 1);
    }

    public void setMinimumSize(Dimension minimumSize) {
        this.minimumSize = minimumSize;
    }

    public Dimension getMaximumSize() {
        if (maximumSize != null) {
            return new Dimension(maximumSize);
        }
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public void setMaximumSize(Dimension maximumSize) {
        this.maximumSize = maximumSize;
    }

    /**
     * Calculate the preferred size of this component.
     * Subclasses should override this to provide meaningful sizes.
     */
    protected Dimension calculatePreferredSize() {
        return new Dimension(10, 1);
    }

    // Color methods
    public TColor getForeground() {
        return foreground;
    }

    public void setForeground(TColor foreground) {
        this.foreground = foreground;
    }

    public TColor getBackground() {
        return background;
    }

    public void setBackground(TColor background) {
        this.background = background;
    }

    // Visibility and state methods
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isFocusable() {
        return focusable;
    }

    public void setFocusable(boolean focusable) {
        this.focusable = focusable;
    }

    public boolean hasFocus() {
        return hasFocus;
    }

    public void requestFocus() {
        if (!focusable || parent == null) {
            return;
        }

        // Set focus in immediate parent
        parent.setFocusedComponent(this);

        // Propagate up the hierarchy so all ancestors know
        // which child container has the focused component
        TContainer ancestor = parent.getParent();
        TContainer childWithFocus = parent;

        while (ancestor != null) {
            ancestor.setFocusedComponent(childWithFocus);
            childWithFocus = ancestor;
            ancestor = ancestor.getParent();
        }
    }

    protected void setFocus(boolean focus) {
        this.hasFocus = focus;
    }

    // Hierarchy methods
    public TContainer getParent() {
        return parent;
    }

    protected void setParent(TContainer parent) {
        this.parent = parent;
    }

    /**
     * Get the absolute position of this component on the screen.
     * This traverses up the parent hierarchy.
     */
    public Point getAbsoluteLocation() {
        Point abs = new Point(location);
        TContainer p = parent;
        while (p != null) {
            abs.translate(p.getX(), p.getY());
            p = p.getParent();
        }
        return abs;
    }

    // Rendering
    /**
     * Render this component to the screen.
     * Subclasses must implement this to draw themselves.
     */
    public abstract void render(Screen screen);

    /**
     * Validate the component and ensure it's laid out properly.
     */
    public void validate() {
        // Base implementation does nothing
        // Containers will override this
    }

    /**
     * Mark this component as needing layout recalculation.
     */
    public void invalidate() {
        if (parent != null) {
            parent.invalidate();
        }
    }

    public void repaint() {
        // Trigger a repaint in the parent frame
        TContainer p = parent;
        while (p != null && !(p instanceof TFrame)) {
            p = p.getParent();
        }
        if (p instanceof TFrame) {
            ((TFrame) p).repaint();
        }
    }

    // Event listener management
    public void addKeyListener(KeyListener listener) {
        if (listener != null) {
            keyListeners.add(listener);
        }
    }

    public void removeKeyListener(KeyListener listener) {
        keyListeners.remove(listener);
    }

    public void addFocusListener(FocusListener listener) {
        if (listener != null) {
            focusListeners.add(listener);
        }
    }

    public void removeFocusListener(FocusListener listener) {
        focusListeners.remove(listener);
    }

    // Event dispatching methods
    protected void processKeyEvent(KeyEvent e) {
        if (!isEnabled() || e.isConsumed()) {
            return;
        }

        for (KeyListener listener : new ArrayList<>(keyListeners)) {
            switch (e.getEventType()) {
                case KeyEvent.KEY_PRESSED:
                    listener.keyPressed(e);
                    break;
                case KeyEvent.KEY_RELEASED:
                    listener.keyReleased(e);
                    break;
                case KeyEvent.KEY_TYPED:
                    listener.keyTyped(e);
                    break;
            }
            if (e.isConsumed()) {
                break;
            }
        }
    }

    protected void processFocusEvent(FocusEvent e) {
        for (FocusListener listener : new ArrayList<>(focusListeners)) {
            switch (e.getEventType()) {
                case FocusEvent.FOCUS_GAINED:
                    listener.focusGained(e);
                    break;
                case FocusEvent.FOCUS_LOST:
                    listener.focusLost(e);
                    break;
            }
        }
    }
}
