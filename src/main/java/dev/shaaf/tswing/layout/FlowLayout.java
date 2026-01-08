package dev.shaaf.tswing.layout;

import dev.shaaf.tswing.TComponent;
import dev.shaaf.tswing.TContainer;
import dev.shaaf.tswing.geom.Dimension;
import dev.shaaf.tswing.geom.Insets;

import java.util.ArrayList;
import java.util.List;

/**
 * FlowLayout arranges components in a left-to-right flow, much like lines of text.
 * When one row fills up, components wrap to the next row.
 * Mirrors java.awt.FlowLayout
 */
public class FlowLayout implements LayoutManager {
    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;

    private int alignment;
    private int hgap;
    private int vgap;
    private final List<TComponent> components;

    public FlowLayout() {
        this(CENTER, 1, 1);
    }

    public FlowLayout(int alignment) {
        this(alignment, 1, 1);
    }

    public FlowLayout(int alignment, int hgap, int vgap) {
        this.alignment = alignment;
        this.hgap = hgap;
        this.vgap = vgap;
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
        int maxWidth = parent.getWidth() - insets.left - insets.right;
        if (maxWidth <= 0) {
            maxWidth = Integer.MAX_VALUE; // During initial sizing
        }

        int width = 0;
        int height = 0;
        int rowWidth = 0;
        int rowHeight = 0;

        for (TComponent comp : components) {
            if (!comp.isVisible()) {
                continue;
            }

            Dimension d = preferred ? comp.getPreferredSize() : comp.getMinimumSize();

            // Check if we need to wrap to next row
            if (rowWidth > 0 && rowWidth + hgap + d.width > maxWidth) {
                // Finish current row
                width = Math.max(width, rowWidth);
                height += rowHeight + vgap;
                // Start new row
                rowWidth = d.width;
                rowHeight = d.height;
            } else {
                // Add to current row
                if (rowWidth > 0) {
                    rowWidth += hgap;
                }
                rowWidth += d.width;
                rowHeight = Math.max(rowHeight, d.height);
            }
        }

        // Add last row
        width = Math.max(width, rowWidth);
        height += rowHeight;

        width += insets.left + insets.right;
        height += insets.top + insets.bottom;

        return new Dimension(width, height);
    }

    @Override
    public void layoutContainer(TContainer parent) {
        Insets insets = parent.getInsets();
        int maxWidth = parent.getWidth() - insets.left - insets.right;
        int x = insets.left;
        int y = insets.top;

        List<RowInfo> rows = calculateRows(parent, maxWidth);

        // Layout each row
        for (RowInfo row : rows) {
            int startX = x;

            // Calculate alignment offset
            if (alignment == CENTER) {
                startX = x + (maxWidth - row.width) / 2;
            } else if (alignment == RIGHT) {
                startX = x + maxWidth - row.width;
            }

            // Position components in this row
            int currentX = startX;
            for (TComponent comp : row.components) {
                Dimension d = comp.getPreferredSize();
                comp.setBounds(currentX, y, d.width, d.height);
                currentX += d.width + hgap;
            }

            y += row.height + vgap;
        }
    }

    private List<RowInfo> calculateRows(TContainer parent, int maxWidth) {
        List<RowInfo> rows = new ArrayList<>();
        RowInfo currentRow = new RowInfo();

        for (TComponent comp : components) {
            if (!comp.isVisible()) {
                continue;
            }

            Dimension d = comp.getPreferredSize();

            // Check if we need to wrap to next row
            if (!currentRow.components.isEmpty() &&
                currentRow.width + hgap + d.width > maxWidth) {
                // Save current row and start new one
                rows.add(currentRow);
                currentRow = new RowInfo();
            }

            // Add to current row
            currentRow.add(comp, d.width, d.height);
        }

        // Add last row
        if (!currentRow.components.isEmpty()) {
            rows.add(currentRow);
        }

        return rows;
    }

    private static class RowInfo {
        List<TComponent> components = new ArrayList<>();
        int width = 0;
        int height = 0;

        void add(TComponent comp, int w, int h) {
            if (!components.isEmpty()) {
                width += 1; // hgap
            }
            components.add(comp);
            width += w;
            height = Math.max(height, h);
        }
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
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
