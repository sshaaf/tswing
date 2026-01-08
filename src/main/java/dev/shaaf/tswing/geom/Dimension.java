package dev.shaaf.tswing.geom;

/**
 * Encapsulates the width and height of a component.
 * Mirrors java.awt.Dimension
 */
public class Dimension {
    public int width;
    public int height;

    public Dimension() {
        this(0, 0);
    }

    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Dimension(Dimension d) {
        this(d.width, d.height);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setSize(Dimension d) {
        setSize(d.width, d.height);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Dimension) {
            Dimension d = (Dimension) obj;
            return width == d.width && height == d.height;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return width ^ height;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[width=" + width + ",height=" + height + "]";
    }
}
