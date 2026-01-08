package dev.shaaf.tswing;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import dev.shaaf.tswing.event.KeyEvent;
import dev.shaaf.tswing.geom.Dimension;
import dev.shaaf.tswing.geom.Insets;
import dev.shaaf.tswing.layout.BorderLayout;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * A top-level window with a border and title.
 * Mirrors javax.swing.JFrame
 */
public class TFrame extends TContainer {
    public static final int EXIT_ON_CLOSE = 0;
    public static final int HIDE_ON_CLOSE = 1;
    public static final int DO_NOTHING_ON_CLOSE = 2;

    private String title;
    private int defaultCloseOperation;
    private boolean running;
    Screen screen; // Package-private for TDialog access
    private Terminal terminal;
    private boolean needsRepaint;

    public TFrame() {
        this("");
    }

    public TFrame(String title) {
        this.title = title != null ? title : "";
        this.defaultCloseOperation = HIDE_ON_CLOSE;
        this.running = false;
        this.needsRepaint = true;
        setLayout(new BorderLayout());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title != null ? title : "";
        repaint();
    }

    public int getDefaultCloseOperation() {
        return defaultCloseOperation;
    }

    public void setDefaultCloseOperation(int operation) {
        this.defaultCloseOperation = operation;
    }

    /**
     * Pack the frame to fit its preferred size.
     */
    public void pack() {
        Dimension preferred = getPreferredSize();
        setSize(preferred.width + 2, preferred.height + 3); // +2 for borders, +3 for title bar
    }

    /**
     * Make the frame visible and start the event loop.
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        if (visible && !running) {
            try {
                startEventLoop();
            } catch (IOException e) {
                throw new RuntimeException("Failed to start TFrame", e);
            }
        } else if (!visible && running) {
            stopEventLoop();
        }
    }

    @Override
    public void repaint() {
        needsRepaint = true;
    }

    private void startEventLoop() throws IOException {
        // Initialize terminal and screen
        terminal = new DefaultTerminalFactory().createTerminal();
        screen = new TerminalScreen(terminal);
        screen.startScreen();

        // Get terminal size and adjust frame size if needed
        TerminalSize termSize = screen.getTerminalSize();
        if (getWidth() == 0 || getHeight() == 0) {
            setSize(termSize.getColumns(), termSize.getRows());
        }

        running = true;

        // Set initial focus to first focusable component
        setInitialFocus();

        // Run event loop in current thread
        eventLoop();
    }

    private void stopEventLoop() {
        running = false;
        try {
            if (screen != null) {
                screen.stopScreen();
                screen.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void eventLoop() {
        try {
            while (running) {
                // Handle input
                KeyStroke keyStroke = screen.pollInput();
                if (keyStroke != null) {
                    handleInput(keyStroke);
                }

                // Render if needed
                if (needsRepaint) {
                    validate();
                    renderFrame();
                    needsRepaint = false;
                }

                // Small sleep to avoid busy waiting
                Thread.sleep(10);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            stopEventLoop();
        }
    }

    private void handleInput(KeyStroke keyStroke) {
        // Handle ESC key - close the frame
        if (keyStroke.getKeyType() == KeyType.Escape) {
            handleClose();
            return;
        }

        // Handle TAB - move focus to next focusable component
        if (keyStroke.getKeyType() == KeyType.Tab) {
            moveFocusForward();
            repaint();
            return;
        }

        // Get the focused component
        TComponent focused = getFocusedComponent();

        // Handle ENTER - activate focused component
        if (keyStroke.getKeyType() == KeyType.Enter) {
            if (focused instanceof TButton) {
                ((TButton) focused).doClick();
            }
            repaint();
            return;
        }

        // Dispatch key event to the focused component
        if (focused != null) {
            KeyEvent keyEvent = new KeyEvent(focused, KeyEvent.KEY_PRESSED, keyStroke);
            dispatchEventToComponent(keyEvent, focused);
            repaint();
        }
    }

    /**
     * Dispatch an event to a component and its ancestors.
     * Events bubble up the component hierarchy until consumed.
     */
    private void dispatchEventToComponent(KeyEvent event, TComponent target) {
        TComponent current = target;

        while (current != null && !event.isConsumed()) {
            current.processKeyEvent(event);
            current = current.getParent();
        }
    }

    private void setInitialFocus() {
        List<TComponent> focusableComponents = new ArrayList<>();
        collectFocusableComponents(this, focusableComponents);

        if (!focusableComponents.isEmpty()) {
            focusableComponents.get(0).requestFocus();
        }
    }

    private void moveFocusForward() {
        List<TComponent> focusableComponents = new ArrayList<>();
        collectFocusableComponents(this, focusableComponents);

        if (focusableComponents.isEmpty()) {
            return;
        }

        TComponent currentFocus = getFocusedComponent();
        int currentIndex = focusableComponents.indexOf(currentFocus);

        // If current focus is not in the list (e.g., -1), start from beginning
        if (currentIndex < 0) {
            currentIndex = -1;
        }

        int nextIndex = (currentIndex + 1) % focusableComponents.size();

        focusableComponents.get(nextIndex).requestFocus();
        repaint();
    }

    private void collectFocusableComponents(TContainer container, List<TComponent> result) {
        for (TComponent comp : container.getComponents()) {
            if (comp.isFocusable() && comp.isVisible() && comp.isEnabled()) {
                result.add(comp);
            }
            if (comp instanceof TContainer) {
                collectFocusableComponents((TContainer) comp, result);
            }
        }
    }

    private void handleClose() {
        switch (defaultCloseOperation) {
            case EXIT_ON_CLOSE:
                running = false;
                System.exit(0);
                break;
            case HIDE_ON_CLOSE:
                running = false;
                setVisible(false);
                break;
            case DO_NOTHING_ON_CLOSE:
                // Do nothing
                break;
        }
    }

    private void renderFrame() throws IOException {
        screen.clear();

        // Draw border
        drawBorder();

        // Draw title
        if (!title.isEmpty()) {
            drawTitle();
        }

        // Render content (adjust for border and title)
        Insets frameInsets = new Insets(2, 1, 1, 1); // top=2 (title+border), left/right/bottom=1 (border)
        setInsets(frameInsets);

        // Render children
        super.render(screen);

        screen.refresh();
    }

    private void drawBorder() {
        int width = getWidth();
        int height = getHeight();

        // Corners
        screen.setCharacter(0, 0, new TextCharacter('┌', TColor.BLUE.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
        screen.setCharacter(width - 1, 0, new TextCharacter('┐', TColor.BLUE.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
        screen.setCharacter(0, height - 1, new TextCharacter('└', TColor.BLUE.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
        screen.setCharacter(width - 1, height - 1, new TextCharacter('┘', TColor.BLUE.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));

        // Horizontal lines
        for (int i = 1; i < width - 1; i++) {
            screen.setCharacter(i, 0, new TextCharacter('─', TColor.BLUE.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
            screen.setCharacter(i, height - 1, new TextCharacter('─', TColor.BLUE.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
        }

        // Vertical lines
        for (int i = 1; i < height - 1; i++) {
            screen.setCharacter(0, i, new TextCharacter('│', TColor.BLUE.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
            screen.setCharacter(width - 1, i, new TextCharacter('│', TColor.BLUE.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
        }

        // Title separator (line below title)
        screen.setCharacter(0, 1, new TextCharacter('├', TColor.BLUE.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
        screen.setCharacter(width - 1, 1, new TextCharacter('┤', TColor.BLUE.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
        for (int i = 1; i < width - 1; i++) {
            screen.setCharacter(i, 1, new TextCharacter('─', TColor.BLUE.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
        }
    }

    private void drawTitle() {
        String displayTitle = " " + title + " ";
        int startX = 2;

        for (int i = 0; i < displayTitle.length() && (startX + i) < getWidth() - 1; i++) {
            screen.setCharacter(
                startX + i,
                0,
                new TextCharacter(
                    displayTitle.charAt(i),
                    TColor.YELLOW.toLanternaColor(),
                    TColor.DEFAULT.toLanternaColor()
                )
            );
        }
    }

    @Override
    public void render(Screen screen) {
        // TFrame handles its own rendering in renderFrame()
        // This method is here to satisfy the abstract method requirement
        super.render(screen);
    }
}
