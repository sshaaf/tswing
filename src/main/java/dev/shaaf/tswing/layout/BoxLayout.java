package dev.shaaf.tswing.layout;

import dev.shaaf.tswing.TComponent;
import dev.shaaf.tswing.TContainer;
import dev.shaaf.tswing.geom.Dimension;
import dev.shaaf.tswing.geom.Insets;

import java.util.ArrayList;
import java.util.List;

/**
 * BoxLayout arranges components either vertically or horizontally.
 * Similar to javax.swing.BoxLayout
 */
public class BoxLayout implements LayoutManager {
    public static final int X_AXIS = 0;  // Horizontal
    public static final int Y_AXIS = 1;  // Vertical

    private final int axis;
    private final List<TComponent> components;
    private int gap;

    public BoxLayout(int axis) {
        this(axis, 0);
    }

    public BoxLayout(int axis, int gap) {
        if (axis != X_AXIS && axis != Y_AXIS) {
            throw new IllegalArgumentException("Invalid axis");
        }
        this.axis = axis;
        this.gap = gap;
        this.components = new ArrayList<>();
    }

    @Override
    public void addLayoutComponent(String name, TComponent comp) {
        components.add(comp);
    }

    @Override
    public void removeLayoutComponent(TComponent comp) {
        components.remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(TContainer parent) {
        return layoutSize(parent, true);
    }

    @Override
    public Dimension minimumLayoutSize(TContainer parent) {
        return layoutSize(parent, false);
    }

    private Dimension layoutSize(TContainer parent, boolean preferred) {
        Insets insets = parent.getInsets();
        int width = 0;
        int height = 0;
        int visibleCount = 0;

        for (TComponent comp : components) {
            if (!comp.isVisible()) {
                continue;
            }

            visibleCount++;
            Dimension d = preferred ? comp.getPreferredSize() : comp.getMinimumSize();

            if (axis == X_AXIS) {
                // Horizontal: sum widths, max height
                width += d.width;
                height = Math.max(height, d.height);
            } else {
                // Vertical: max width, sum heights
                width = Math.max(width, d.width);
                height += d.height;
            }
        }

        // Add gaps between components
        if (visibleCount > 1) {
            if (axis == X_AXIS) {
                width += gap * (visibleCount - 1);
            } else {
                height += gap * (visibleCount - 1);
            }
        }

        width += insets.left + insets.right;
        height += insets.top + insets.bottom;

        return new Dimension(width, height);
    }

    @Override
    public void layoutContainer(TContainer parent) {
        Insets insets = parent.getInsets();
        int x = insets.left;
        int y = insets.top;
        int availWidth = parent.getWidth() - insets.left - insets.right;
        int availHeight = parent.getHeight() - insets.top - insets.bottom;

        for (TComponent comp : components) {
            if (!comp.isVisible()) {
                continue;
            }

            Dimension d = comp.getPreferredSize();

            if (axis == X_AXIS) {
                // Horizontal layout
                int componentWidth = d.width;
                int componentHeight = Math.min(d.height, availHeight);
                comp.setBounds(x, y, componentWidth, componentHeight);
                x += componentWidth + gap;
            } else {
                // Vertical layout
                int componentWidth = Math.min(d.width, availWidth);
                int componentHeight = d.height;
                comp.setBounds(x, y, componentWidth, componentHeight);
                y += componentHeight + gap;
            }
        }
    }

    public int getAxis() {
        return axis;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }
}
