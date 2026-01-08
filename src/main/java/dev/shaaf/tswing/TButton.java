package dev.shaaf.tswing;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import dev.shaaf.tswing.event.ActionEvent;
import dev.shaaf.tswing.event.ActionListener;
import dev.shaaf.tswing.geom.Dimension;
import dev.shaaf.tswing.geom.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * A push button component.
 * Mirrors javax.swing.JButton
 */
public class TButton extends TComponent {
    private String text;
    private String actionCommand;
    private final List<ActionListener> actionListeners;

    public TButton() {
        this("");
    }

    public TButton(String text) {
        this.text = text != null ? text : "";
        this.actionCommand = text;
        this.actionListeners = new ArrayList<>();
        setFocusable(true);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text != null ? text : "";
        invalidate();
        repaint();
    }

    public String getActionCommand() {
        return actionCommand;
    }

    public void setActionCommand(String actionCommand) {
        this.actionCommand = actionCommand;
    }

    public void addActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        actionListeners.remove(listener);
    }

    /**
     * Trigger the button action programmatically.
     */
    public void doClick() {
        if (isEnabled()) {
            fireActionPerformed();
        }
    }

    protected void fireActionPerformed() {
        ActionEvent event = new ActionEvent(this, actionCommand);
        for (ActionListener listener : actionListeners) {
            listener.actionPerformed(event);
        }
    }

    @Override
    protected Dimension calculatePreferredSize() {
        // Add brackets and padding: [ text ]
        return new Dimension(text.length() + 4, 1);
    }

    @Override
    public void render(Screen screen) {
        if (!isVisible()) {
            return;
        }

        Point absPos = getAbsoluteLocation();
        int width = getWidth();

        // Don't render if component hasn't been laid out yet or has invalid size
        if (width <= 0 || getHeight() <= 0) {
            return;
        }

        // Determine colors based on focus
        TColor fg = hasFocus() ? TColor.BLACK : getForeground();
        TColor bg = hasFocus() ? TColor.CYAN : getBackground();

        // Render button with brackets: [ text ]
        String buttonText = "[ " + text + " ]";
        int charsToRender = Math.min(buttonText.length(), width);

        for (int i = 0; i < charsToRender; i++) {
            screen.setCharacter(
                absPos.x + i,
                absPos.y,
                new TextCharacter(
                    buttonText.charAt(i),
                    fg.toLanternaColor(),
                    bg.toLanternaColor()
                )
            );
        }

        // Clear remaining space to avoid leftover characters
        for (int i = charsToRender; i < width; i++) {
            screen.setCharacter(
                absPos.x + i,
                absPos.y,
                new TextCharacter(
                    ' ',
                    fg.toLanternaColor(),
                    bg.toLanternaColor()
                )
            );
        }
    }
}
