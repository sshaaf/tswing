package dev.shaaf.tswing;

import com.googlecode.lanterna.screen.Screen;
import dev.shaaf.tswing.layout.BorderLayout;
import dev.shaaf.tswing.layout.LayoutManager;

/**
 * A generic lightweight container.
 * Mirrors javax.swing.JPanel
 */
public class TPanel extends TContainer {

    public TPanel() {
        this(new BorderLayout());
    }

    public TPanel(LayoutManager layout) {
        setLayout(layout);
    }

    @Override
    public void render(Screen screen) {
        // TPanel doesn't draw itself, just its children
        super.render(screen);
    }
}
