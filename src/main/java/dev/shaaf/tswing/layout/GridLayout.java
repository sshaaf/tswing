package dev.shaaf.tswing.layout;

import dev.shaaf.tswing.TComponent;
import dev.shaaf.tswing.TContainer;
import dev.shaaf.tswing.geom.Dimension;
import dev.shaaf.tswing.geom.Insets;

import java.util.ArrayList;
import java.util.List;

/**
 * GridLayout arranges components in a rectangular grid.
 * All components are given equal size.
 * Mirrors java.awt.GridLayout
 */
public class GridLayout implements LayoutManager {
    private int rows;
    private int cols;
    private int hgap;
    private int vgap;
    private final List<TComponent> components;

    /**
     * Creates a grid layout with the specified number of rows and columns.
     * At least one of rows or cols must be non-zero.
     */
    public GridLayout(int rows, int cols) {
        this(rows, cols, 0, 0);
    }

    public GridLayout(int rows, int cols, int hgap, int vgap) {
        if (rows == 0 && cols == 0) {
            throw new IllegalArgumentException("rows and cols cannot both be zero");
        }
        this.rows = rows;
        this.cols = cols;
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
        int ncomponents = getVisibleComponentCount();

        if (ncomponents == 0) {
            return new Dimension(insets.left + insets.right, insets.top + insets.bottom);
        }

        int[] dims = calculateGridDimensions(ncomponents);
        int nrows = dims[0];
        int ncols = dims[1];

        // Find the maximum preferred/minimum size
        int maxWidth = 0;
        int maxHeight = 0;

        for (TComponent comp : components) {
            if (!comp.isVisible()) {
                continue;
            }

            Dimension d = preferred ? comp.getPreferredSize() : comp.getMinimumSize();
            maxWidth = Math.max(maxWidth, d.width);
            maxHeight = Math.max(maxHeight, d.height);
        }

        int width = ncols * maxWidth + (ncols - 1) * hgap + insets.left + insets.right;
        int height = nrows * maxHeight + (nrows - 1) * vgap + insets.top + insets.bottom;

        return new Dimension(width, height);
    }

    @Override
    public void layoutContainer(TContainer parent) {
        Insets insets = parent.getInsets();
        int ncomponents = getVisibleComponentCount();

        if (ncomponents == 0) {
            return;
        }

        int[] dims = calculateGridDimensions(ncomponents);
        int nrows = dims[0];
        int ncols = dims[1];

        int availWidth = parent.getWidth() - (insets.left + insets.right);
        int availHeight = parent.getHeight() - (insets.top + insets.bottom);

        int cellWidth = (availWidth - (ncols - 1) * hgap) / ncols;
        int cellHeight = (availHeight - (nrows - 1) * vgap) / nrows;

        int componentIndex = 0;
        for (int r = 0; r < nrows; r++) {
            for (int c = 0; c < ncols; c++) {
                if (componentIndex >= components.size()) {
                    return;
                }

                TComponent comp = components.get(componentIndex);
                while (!comp.isVisible() && componentIndex < components.size() - 1) {
                    componentIndex++;
                    comp = components.get(componentIndex);
                }

                if (comp.isVisible()) {
                    int x = insets.left + c * (cellWidth + hgap);
                    int y = insets.top + r * (cellHeight + vgap);
                    comp.setBounds(x, y, cellWidth, cellHeight);
                }

                componentIndex++;
            }
        }
    }

    private int getVisibleComponentCount() {
        int count = 0;
        for (TComponent comp : components) {
            if (comp.isVisible()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculate the actual number of rows and columns based on the component count.
     * Returns [rows, cols]
     */
    private int[] calculateGridDimensions(int ncomponents) {
        int nrows = rows;
        int ncols = cols;

        if (nrows > 0) {
            ncols = (ncomponents + nrows - 1) / nrows;
        } else {
            nrows = (ncomponents + ncols - 1) / ncols;
        }

        return new int[]{nrows, ncols};
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return cols;
    }

    public void setColumns(int cols) {
        this.cols = cols;
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
