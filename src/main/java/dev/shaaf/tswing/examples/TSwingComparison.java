package dev.shaaf.tswing.examples;

/**
 * This is what the SAME example would look like with TSwing
 * (This is aspirational code - we haven't built TSwing yet!)
 *
 * Compare this to LanternaBasicExample.java to see the abstraction value.
 */
public class TSwingComparison {

    public static void main(String[] args) {
        // This is what we WANT to enable for developers:

        /*
        // Create a frame (like JFrame)
        TFrame frame = new TFrame("Lanterna Basic Example");
        frame.setDefaultCloseOperation(TFrame.EXIT_ON_CLOSE);

        // Create a panel with layout
        TPanel panel = new TPanel();
        panel.setLayout(new BorderLayout());

        // Add components
        TLabel welcomeLabel = new TLabel("Welcome to Lanterna!");
        welcomeLabel.setForeground(TColor.GREEN);

        TLabel descLabel = new TLabel("This is the low-level API that TSwing will abstract.");

        TButton button = new TButton("Click Me");
        button.addActionListener(e -> {
            System.out.println("Button clicked!");
        });

        // Layout components
        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(descLabel, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);

        // Add panel to frame and display
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        */
    }
}

/*
 * KEY DIFFERENCES:
 *
 * LANTERNA (Low-level):
 * - Manual positioning: drawText(screen, 2, 2, ...)
 * - Manual drawing: drawBox(), character-by-character
 * - Manual input loop: while(running) { pollInput() }
 * - Manual screen refresh
 * - No layout management
 * - No event system
 *
 * TSWING (High-level):
 * - Declarative components: new TLabel(), new TButton()
 * - Automatic layout: BorderLayout handles positioning
 * - Event-driven: addActionListener()
 * - Automatic rendering
 * - Familiar Swing patterns
 * - Component hierarchy and reusability
 */
