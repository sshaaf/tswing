package dev.shaaf.tswing;

import com.googlecode.lanterna.screen.Screen;
import dev.shaaf.tswing.event.FocusEvent;
import dev.shaaf.tswing.geom.Dimension;
import dev.shaaf.tswing.geom.Insets;
import dev.shaaf.tswing.layout.LayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for components that can contain other components.
 * Mirrors java.awt.Container
 */
public abstract class TContainer extends TComponent {
    private final List<TComponent> components;
    private LayoutManager layout;
    private Insets insets;
    private TComponent focusedComponent;

    public TContainer() {
        this.components = new ArrayList<>();
        this.insets = new Insets(0, 0, 0, 0);
    }

    // Component management
    public TComponent add(TComponent comp) {
        return add(comp, null);
    }

    public TComponent add(TComponent comp, Object constraints) {
        components.add(comp);
        comp.setParent(this);

        if (layout != null) {
            String constraintString = constraints != null ? constraints.toString() : null;
            layout.addLayoutComponent(constraintString, comp);
        }

        invalidate();
        return comp;
    }

    public void remove(TComponent comp) {
        if (components.remove(comp)) {
            comp.setParent(null);
            if (layout != null) {
                layout.removeLayoutComponent(comp);
            }
            invalidate();
        }
    }

    public void removeAll() {
        for (TComponent comp : components) {
            comp.setParent(null);
            if (layout != null) {
                layout.removeLayoutComponent(comp);
            }
        }
        components.clear();
        invalidate();
    }

    public TComponent[] getComponents() {
        return components.toArray(new TComponent[0]);
    }

    public int getComponentCount() {
        return components.size();
    }

    // Layout management
    public LayoutManager getLayout() {
        return layout;
    }

    public void setLayout(LayoutManager layout) {
        this.layout = layout;
        invalidate();
    }

    public Insets getInsets() {
        return new Insets(insets.top, insets.left, insets.bottom, insets.right);
    }

    public void setInsets(Insets insets) {
        this.insets = insets;
        invalidate();
    }

    @Override
    protected Dimension calculatePreferredSize() {
        if (layout != null) {
            return layout.preferredLayoutSize(this);
        }
        return super.calculatePreferredSize();
    }

    // Focus management
    protected void setFocusedComponent(TComponent comp) {
        TComponent oldFocus = focusedComponent;

        if (oldFocus != null && oldFocus != comp) {
            oldFocus.setFocus(false);
            // Only fire focus events on focusable components (not containers)
            if (oldFocus.isFocusable()) {
                FocusEvent lostEvent = new FocusEvent(oldFocus, FocusEvent.FOCUS_LOST, comp);
                oldFocus.processFocusEvent(lostEvent);
            }
        }

        focusedComponent = comp;

        if (focusedComponent != null && focusedComponent != oldFocus) {
            focusedComponent.setFocus(true);
            // Only fire focus events on focusable components (not containers)
            if (focusedComponent.isFocusable()) {
                FocusEvent gainedEvent = new FocusEvent(focusedComponent, FocusEvent.FOCUS_GAINED, oldFocus);
                focusedComponent.processFocusEvent(gainedEvent);
            }
        }
    }

    public TComponent getFocusedComponent() {
        // Recursively find the actual focused leaf component
        if (focusedComponent instanceof TContainer) {
            TComponent deepFocus = ((TContainer) focusedComponent).getFocusedComponent();
            return deepFocus != null ? deepFocus : focusedComponent;
        }
        return focusedComponent;
    }

    // Validation and layout
    @Override
    public void validate() {
        if (layout != null) {
            layout.layoutContainer(this);
        }
        for (TComponent comp : components) {
            comp.validate();
        }
    }

    // Rendering
    @Override
    public void render(Screen screen) {
        // Render all child components
        for (TComponent comp : components) {
            if (comp.isVisible()) {
                comp.render(screen);
            }
        }
    }
}
