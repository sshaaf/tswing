package dev.shaaf.tswing.layout;

import dev.shaaf.tswing.geom.Dimension;
import dev.shaaf.tswing.geom.Insets;
import dev.shaaf.tswing.TContainer;
import dev.shaaf.tswing.TComponent;

import java.util.HashMap;
import java.util.Map;

/**
 * A layout manager that arranges components in five regions: NORTH, SOUTH, EAST, WEST, and CENTER.
 * Mirrors java.awt.BorderLayout
 */
public class BorderLayout implements LayoutManager {
    public static final String NORTH = "North";
    public static final String SOUTH = "South";
    public static final String EAST = "East";
    public static final String WEST = "West";
    public static final String CENTER = "Center";

    private int hgap;
    private int vgap;

    private final Map<String, TComponent> components;

    public BorderLayout() {
        this(0, 0);
    }

    public BorderLayout(int hgap, int vgap) {
        this.hgap = hgap;
        this.vgap = vgap;
        this.components = new HashMap<>();
    }

    @Override
    public void addLayoutComponent(String name, TComponent comp) {
        if (name == null) {
            name = CENTER;
        }
        components.put(name, comp);
    }

    @Override
    public void removeLayoutComponent(TComponent comp) {
        components.values().remove(comp);
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
        int width = insets.left + insets.right;
        int height = insets.top + insets.bottom;

        TComponent north = components.get(NORTH);
        TComponent south = components.get(SOUTH);
        TComponent east = components.get(EAST);
        TComponent west = components.get(WEST);
        TComponent center = components.get(CENTER);

        if (north != null) {
            Dimension d = preferred ? north.getPreferredSize() : north.getMinimumSize();
            height += d.height + vgap;
            width = Math.max(width, d.width + insets.left + insets.right);
        }

        if (south != null) {
            Dimension d = preferred ? south.getPreferredSize() : south.getMinimumSize();
            height += d.height + vgap;
            width = Math.max(width, d.width + insets.left + insets.right);
        }

        if (east != null) {
            Dimension d = preferred ? east.getPreferredSize() : east.getMinimumSize();
            width += d.width + hgap;
        }

        if (west != null) {
            Dimension d = preferred ? west.getPreferredSize() : west.getMinimumSize();
            width += d.width + hgap;
        }

        if (center != null) {
            Dimension d = preferred ? center.getPreferredSize() : center.getMinimumSize();
            width += d.width;
            height += d.height;
        }

        return new Dimension(width, height);
    }

    @Override
    public void layoutContainer(TContainer parent) {
        Insets insets = parent.getInsets();
        int top = insets.top;
        int bottom = parent.getHeight() - insets.bottom;
        int left = insets.left;
        int right = parent.getWidth() - insets.right;

        TComponent north = components.get(NORTH);
        TComponent south = components.get(SOUTH);
        TComponent east = components.get(EAST);
        TComponent west = components.get(WEST);
        TComponent center = components.get(CENTER);

        // Layout NORTH
        if (north != null && north.isVisible()) {
            Dimension d = north.getPreferredSize();
            north.setBounds(left, top, right - left, d.height);
            top += d.height + vgap;
        }

        // Layout SOUTH
        if (south != null && south.isVisible()) {
            Dimension d = south.getPreferredSize();
            south.setBounds(left, bottom - d.height, right - left, d.height);
            bottom -= d.height + vgap;
        }

        // Layout EAST
        if (east != null && east.isVisible()) {
            Dimension d = east.getPreferredSize();
            east.setBounds(right - d.width, top, d.width, bottom - top);
            right -= d.width + hgap;
        }

        // Layout WEST
        if (west != null && west.isVisible()) {
            Dimension d = west.getPreferredSize();
            west.setBounds(left, top, d.width, bottom - top);
            left += d.width + hgap;
        }

        // Layout CENTER
        if (center != null && center.isVisible()) {
            center.setBounds(left, top, right - left, bottom - top);
        }
    }

    public int getHgap() {
        return hgap;
    }

    public void setHgap(int hgap) {
        this.hgap = hgap;
    }

    public int getVgap() {
        return vgap;
    }

    public void setVgap(int vgap) {
        this.vgap = vgap;
    }
}
