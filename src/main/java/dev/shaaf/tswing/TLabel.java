package dev.shaaf.tswing;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import dev.shaaf.tswing.geom.Dimension;
import dev.shaaf.tswing.geom.Point;

/**
 * A display area for a short text string.
 * Mirrors javax.swing.JLabel
 */
public class TLabel extends TComponent {
    private String text;

    public TLabel() {
        this("");
    }

    public TLabel(String text) {
        this.text = text != null ? text : "";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text != null ? text : "";
        invalidate();
        repaint();
    }

    @Override
    protected Dimension calculatePreferredSize() {
        return new Dimension(text.length(), 1);
    }

    @Override
    public void render(Screen screen) {
        if (!isVisible()) {
            return;
        }

        Point absPos = getAbsoluteLocation();
        int maxWidth = getWidth();

        // Don't render if component hasn't been laid out yet or has invalid size
        if (maxWidth <= 0 || getHeight() <= 0) {
            return;
        }

        // Render text, truncating if necessary
        int charsToRender = Math.min(text.length(), maxWidth);
        for (int i = 0; i < charsToRender; i++) {
            screen.setCharacter(
                absPos.x + i,
                absPos.y,
                new TextCharacter(
                    text.charAt(i),
                    getForeground().toLanternaColor(),
                    getBackground().toLanternaColor()
                )
            );
        }

        // Clear remaining space to avoid leftover characters
        for (int i = charsToRender; i < maxWidth; i++) {
            screen.setCharacter(
                absPos.x + i,
                absPos.y,
                new TextCharacter(
                    ' ',
                    getForeground().toLanternaColor(),
                    getBackground().toLanternaColor()
                )
            );
        }
    }
}
