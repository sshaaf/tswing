package dev.shaaf.tswing;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import dev.shaaf.tswing.event.KeyEvent;
import dev.shaaf.tswing.geom.Dimension;
import dev.shaaf.tswing.geom.Insets;
import dev.shaaf.tswing.layout.BorderLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A dialog window that can be displayed modally or non-modally.
 * Mirrors javax.swing.JDialog
 */
public class TDialog extends TContainer {
    public static final int OK_OPTION = 0;
    public static final int CANCEL_OPTION = 1;
    public static final int CLOSED_OPTION = 2;

    private String title;
    private boolean modal;
    private boolean visible;
    private TFrame parentFrame;
    private int dialogResult;
    private boolean dialogClosed;

    public TDialog(TFrame parent, String title, boolean modal) {
        this.parentFrame = parent;
        this.title = title != null ? title : "";
        this.modal = modal;
        this.visible = false;
        this.dialogResult = CLOSED_OPTION;
        this.dialogClosed = false;
        setLayout(new BorderLayout());
    }

    public TDialog(TFrame parent, String title) {
        this(parent, title, true);
    }

    public TDialog(TFrame parent) {
        this(parent, "", true);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title != null ? title : "";
    }

    public boolean isModal() {
        return modal;
    }

    public void setModal(boolean modal) {
        this.modal = modal;
    }

    /**
     * Pack the dialog to fit its preferred size.
     */
    public void pack() {
        Dimension preferred = getPreferredSize();
        setSize(preferred.width + 2, preferred.height + 3); // +2 for borders, +3 for title bar
    }

    /**
     * Show the dialog. If modal, this blocks until the dialog is closed.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;

        if (visible && modal && parentFrame != null) {
            // Set initial focus before showing
            setInitialFocus();
            showModalDialog();
        }
    }

    /**
     * Set initial focus to the first focusable component.
     */
    private void setInitialFocus() {
        List<TComponent> focusableComponents = new ArrayList<>();
        collectFocusableComponents(this, focusableComponents);

        if (!focusableComponents.isEmpty()) {
            focusableComponents.get(0).requestFocus();
        }
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    /**
     * Close the dialog with the specified result.
     */
    public void closeDialog(int result) {
        this.dialogResult = result;
        this.dialogClosed = true;
        this.visible = false;
    }

    /**
     * Get the result of the dialog after it's closed.
     */
    public int getDialogResult() {
        return dialogResult;
    }

    /**
     * Show the dialog modally (blocks until closed).
     */
    private void showModalDialog() {
        dialogClosed = false;
        dialogResult = CLOSED_OPTION;

        try {
            // Validate that parent has an active screen
            if (parentFrame == null || parentFrame.screen == null) {
                System.err.println("Warning: Cannot show dialog - parent frame has no active screen");
                return;
            }

            // Center the dialog on the parent frame
            centerOnParent();

            // Render the dialog and run event loop
            while (!dialogClosed) {
                // Get parent's screen with validation
                Screen screen = parentFrame.screen;
                if (screen == null) {
                    // Parent screen was closed
                    dialogClosed = true;
                    break;
                }

                try {
                    // Handle input
                    KeyStroke keyStroke = screen.pollInput();
                    if (keyStroke != null) {
                        handleDialogInput(keyStroke);
                    }

                    // Render dialog only if still open
                    if (!dialogClosed) {
                        renderDialog(screen);
                    }
                } catch (Exception e) {
                    // Screen might have been closed
                    dialogClosed = true;
                    break;
                }

                // Small sleep to avoid busy waiting
                Thread.sleep(10);
            }

            // Trigger repaint of parent when dialog closes (if parent still active)
            if (parentFrame.screen != null) {
                parentFrame.repaint();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("Error in modal dialog: " + e.getMessage());
        }
    }

    /**
     * Center the dialog on the parent frame.
     */
    private void centerOnParent() {
        if (parentFrame == null) {
            return;
        }

        int parentWidth = parentFrame.getWidth();
        int parentHeight = parentFrame.getHeight();
        int dialogWidth = getWidth();
        int dialogHeight = getHeight();

        int x = (parentWidth - dialogWidth) / 2;
        int y = (parentHeight - dialogHeight) / 2;

        setLocation(Math.max(0, x), Math.max(0, y));
    }

    /**
     * Handle input for the dialog.
     */
    protected void handleDialogInput(KeyStroke keyStroke) {
        // Handle ESC key - close the dialog
        if (keyStroke.getKeyType() == KeyType.Escape) {
            closeDialog(CANCEL_OPTION);
            return;
        }

        // Handle TAB - move focus to next focusable component
        if (keyStroke.getKeyType() == KeyType.Tab) {
            moveFocusForward();
            return;
        }

        // Get the focused component
        TComponent focused = getFocusedComponent();

        // Handle ENTER - activate focused component
        if (keyStroke.getKeyType() == KeyType.Enter) {
            if (focused instanceof TButton) {
                ((TButton) focused).doClick();
            }
            return;
        }

        // Dispatch key event to the focused component
        if (focused != null) {
            KeyEvent keyEvent = new KeyEvent(focused, KeyEvent.KEY_PRESSED, keyStroke);
            dispatchEventToComponent(keyEvent, focused);
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

        if (currentIndex < 0) {
            currentIndex = -1;
        }

        int nextIndex = (currentIndex + 1) % focusableComponents.size();
        focusableComponents.get(nextIndex).requestFocus();
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

    private void dispatchEventToComponent(KeyEvent event, TComponent target) {
        TComponent current = target;
        while (current != null && !event.isConsumed()) {
            current.processKeyEvent(event);
            current = current.getParent();
        }
    }

    /**
     * Render the dialog on the screen.
     */
    protected void renderDialog(Screen screen) throws IOException {
        if (screen == null) {
            return;
        }

        try {
            // Validate layout
            validate();

            // Draw border
            drawBorder(screen);

            // Draw title
            if (!title.isEmpty()) {
                drawTitle(screen);
            }

            // Render content (adjust for border and title)
            Insets dialogInsets = new Insets(2, 1, 1, 1); // top=2 (title+border), left/right/bottom=1 (border)
            setInsets(dialogInsets);

            // Render children
            render(screen);

            screen.refresh();
        } catch (Exception e) {
            // Silently handle rendering errors during cleanup
            throw new IOException("Error rendering dialog", e);
        }
    }

    private void drawBorder(Screen screen) {
        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();

        // Corners
        screen.setCharacter(x, y, new TextCharacter('┌', TColor.YELLOW.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
        screen.setCharacter(x + width - 1, y, new TextCharacter('┐', TColor.YELLOW.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
        screen.setCharacter(x, y + height - 1, new TextCharacter('└', TColor.YELLOW.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
        screen.setCharacter(x + width - 1, y + height - 1, new TextCharacter('┘', TColor.YELLOW.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));

        // Horizontal lines
        for (int i = 1; i < width - 1; i++) {
            screen.setCharacter(x + i, y, new TextCharacter('─', TColor.YELLOW.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
            screen.setCharacter(x + i, y + height - 1, new TextCharacter('─', TColor.YELLOW.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
        }

        // Vertical lines
        for (int i = 1; i < height - 1; i++) {
            screen.setCharacter(x, y + i, new TextCharacter('│', TColor.YELLOW.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
            screen.setCharacter(x + width - 1, y + i, new TextCharacter('│', TColor.YELLOW.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
        }

        // Title separator
        screen.setCharacter(x, y + 1, new TextCharacter('├', TColor.YELLOW.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
        screen.setCharacter(x + width - 1, y + 1, new TextCharacter('┤', TColor.YELLOW.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
        for (int i = 1; i < width - 1; i++) {
            screen.setCharacter(x + i, y + 1, new TextCharacter('─', TColor.YELLOW.toLanternaColor(), TColor.DEFAULT.toLanternaColor()));
        }
    }

    private void drawTitle(Screen screen) {
        String displayTitle = " " + title + " ";
        int x = getX() + 2;
        int y = getY();

        for (int i = 0; i < displayTitle.length() && (x + i) < getX() + getWidth() - 1; i++) {
            screen.setCharacter(
                x + i,
                y,
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
        // Dialog handles its own rendering in renderDialog()
        super.render(screen);
    }
}
