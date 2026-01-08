package dev.shaaf.tswing.geom;

/**
 * Specifies the insets (padding) of a container.
 * Mirrors java.awt.Insets
 */
public class Insets {
    public int top;
    public int left;
    public int bottom;
    public int right;

    public Insets(int top, int left, int bottom, int right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    public void set(int top, int left, int bottom, int right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Insets) {
            Insets insets = (Insets) obj;
            return top == insets.top && left == insets.left &&
                   bottom == insets.bottom && right == insets.right;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return top + left + bottom + right;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[top=" + top + ",left=" + left +
               ",bottom=" + bottom + ",right=" + right + "]";
    }

    @Override
    public Object clone() {
        return new Insets(top, left, bottom, right);
    }
}
