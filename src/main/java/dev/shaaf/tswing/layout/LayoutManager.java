package dev.shaaf.tswing.layout;

import dev.shaaf.tswing.geom.Dimension;
import dev.shaaf.tswing.TContainer;
import dev.shaaf.tswing.TComponent;

/**
 * Interface for layout managers.
 * Mirrors java.awt.LayoutManager
 */
public interface LayoutManager {
    /**
     * Add a component to the layout with optional constraints.
     */
    void addLayoutComponent(String name, TComponent comp);

    /**
     * Remove a component from the layout.
     */
    void removeLayoutComponent(TComponent comp);

    /**
     * Calculate the preferred size of the container.
     */
    Dimension preferredLayoutSize(TContainer parent);

    /**
     * Calculate the minimum size of the container.
     */
    Dimension minimumLayoutSize(TContainer parent);

    /**
     * Lay out the container - set bounds for all child components.
     */
    void layoutContainer(TContainer parent);
}
