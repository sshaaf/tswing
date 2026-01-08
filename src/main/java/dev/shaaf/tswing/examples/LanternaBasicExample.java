package dev.shaaf.tswing.examples;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/**
 * Basic Lanterna example showing:
 * - Terminal initialization
 * - Drawing text at positions
 * - Handling keyboard input
 * - Screen buffering and refresh
 */
public class LanternaBasicExample {

    public static void main(String[] args) {
        try {
            // 1. Create a terminal
            Terminal terminal = new DefaultTerminalFactory().createTerminal();

            // 2. Wrap it in a Screen (provides buffering and easier drawing)
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();

            // 3. Get terminal size
            TerminalSize size = screen.getTerminalSize();

            // 4. Draw some content
            drawUI(screen, size);

            // 5. Handle input loop
            boolean running = true;
            while (running) {
                KeyStroke keyStroke = screen.pollInput();

                if (keyStroke != null) {
                    if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'q') {
                        running = false;
                    } else if (keyStroke.getKeyType() == KeyType.Escape) {
                        running = false;
                    }
                }

                Thread.sleep(10);
            }

            // 6. Clean up
            screen.stopScreen();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void drawUI(Screen screen, TerminalSize size) throws IOException {
        screen.clear();

        // Draw a border
        drawBox(screen, 0, 0, size.getColumns(), size.getRows(), TextColor.ANSI.BLUE);

        // Draw a title
        String title = "[ Lanterna Basic Example ]";
        int titleX = (size.getColumns() - title.length()) / 2;
        drawText(screen, titleX, 0, title, TextColor.ANSI.YELLOW);

        // Draw some labels
        drawText(screen, 2, 2, "Welcome to Lanterna!", TextColor.ANSI.GREEN);
        drawText(screen, 2, 4, "This is the low-level API that TSwing will abstract.", TextColor.ANSI.WHITE);

        // Draw a "button" representation
        drawBox(screen, 2, 6, 20, 3, TextColor.ANSI.CYAN);
        drawText(screen, 4, 7, "[ Click Me ]", TextColor.ANSI.CYAN);

        // Draw instructions
        drawText(screen, 2, size.getRows() - 2, "Press 'q' or ESC to quit", TextColor.ANSI.RED);

        screen.refresh();
    }

    private static void drawText(Screen screen, int x, int y, String text, TextColor color) {
        for (int i = 0; i < text.length(); i++) {
            screen.setCharacter(x + i, y,
                new com.googlecode.lanterna.TextCharacter(
                    text.charAt(i),
                    color,
                    TextColor.ANSI.DEFAULT
                )
            );
        }
    }

    private static void drawBox(Screen screen, int x, int y, int width, int height, TextColor color) {
        // Corners
        screen.setCharacter(x, y, new com.googlecode.lanterna.TextCharacter('┌', color, TextColor.ANSI.DEFAULT));
        screen.setCharacter(x + width - 1, y, new com.googlecode.lanterna.TextCharacter('┐', color, TextColor.ANSI.DEFAULT));
        screen.setCharacter(x, y + height - 1, new com.googlecode.lanterna.TextCharacter('└', color, TextColor.ANSI.DEFAULT));
        screen.setCharacter(x + width - 1, y + height - 1, new com.googlecode.lanterna.TextCharacter('┘', color, TextColor.ANSI.DEFAULT));

        // Horizontal lines
        for (int i = 1; i < width - 1; i++) {
            screen.setCharacter(x + i, y, new com.googlecode.lanterna.TextCharacter('─', color, TextColor.ANSI.DEFAULT));
            screen.setCharacter(x + i, y + height - 1, new com.googlecode.lanterna.TextCharacter('─', color, TextColor.ANSI.DEFAULT));
        }

        // Vertical lines
        for (int i = 1; i < height - 1; i++) {
            screen.setCharacter(x, y + i, new com.googlecode.lanterna.TextCharacter('│', color, TextColor.ANSI.DEFAULT));
            screen.setCharacter(x + width - 1, y + i, new com.googlecode.lanterna.TextCharacter('│', color, TextColor.ANSI.DEFAULT));
        }
    }
}
