package dev.shaaf.tswing;

import com.googlecode.lanterna.TextColor;

/**
 * Represents colors for TSwing components.
 * Provides common color constants and conversion to Lanterna colors.
 */
public class TColor {
    private final TextColor.ANSI ansiColor;

    private TColor(TextColor.ANSI ansiColor) {
        this.ansiColor = ansiColor;
    }

    public TextColor.ANSI toLanternaColor() {
        return ansiColor;
    }

    // Common color constants
    public static final TColor BLACK = new TColor(TextColor.ANSI.BLACK);
    public static final TColor RED = new TColor(TextColor.ANSI.RED);
    public static final TColor GREEN = new TColor(TextColor.ANSI.GREEN);
    public static final TColor YELLOW = new TColor(TextColor.ANSI.YELLOW);
    public static final TColor BLUE = new TColor(TextColor.ANSI.BLUE);
    public static final TColor MAGENTA = new TColor(TextColor.ANSI.MAGENTA);
    public static final TColor CYAN = new TColor(TextColor.ANSI.CYAN);
    public static final TColor WHITE = new TColor(TextColor.ANSI.WHITE);
    public static final TColor DEFAULT = new TColor(TextColor.ANSI.DEFAULT);

    @Override
    public String toString() {
        return "TColor[" + ansiColor + "]";
    }
}
