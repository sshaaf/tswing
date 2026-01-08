package dev.shaaf.tswing.examples;

import dev.shaaf.tswing.*;
import dev.shaaf.tswing.layout.BorderLayout;
import dev.shaaf.tswing.event.ActionEvent;
import dev.shaaf.tswing.event.ActionListener;

/**
 * A simple "Hello World" demo of TSwing.
 * This demonstrates the Swing-like API for building terminal UIs.
 */
public class HelloTSwing {

    public static void main(String[] args) {
        // Create a frame (like JFrame)
        TFrame frame = new TFrame("TSwing Demo - Hello World");
        frame.setDefaultCloseOperation(TFrame.EXIT_ON_CLOSE);

        // Create a panel with BorderLayout
        TPanel panel = new TPanel(new BorderLayout());

        // Create components
        TLabel titleLabel = new TLabel("Welcome to TSwing!");
        titleLabel.setForeground(TColor.GREEN);

        TLabel descLabel = new TLabel("A Swing-like API for Terminal UIs");
        descLabel.setForeground(TColor.WHITE);

        TLabel instructionLabel = new TLabel("Press TAB to focus button, ENTER to click, ESC to quit");
        instructionLabel.setForeground(TColor.YELLOW);

        TButton clickButton = new TButton("Click Me");
        clickButton.addActionListener(new ActionListener() {
            private int clickCount = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                clickCount++;
                descLabel.setText("Button clicked " + clickCount + " time(s)!");
            }
        });

        // Layout components using BorderLayout
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(descLabel, BorderLayout.CENTER);
        panel.add(clickButton, BorderLayout.SOUTH);

        // Add instruction label to a separate panel
        TPanel bottomPanel = new TPanel(new BorderLayout());
        bottomPanel.add(clickButton, BorderLayout.NORTH);
        bottomPanel.add(instructionLabel, BorderLayout.SOUTH);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Add panel to frame
        frame.add(panel, BorderLayout.CENTER);

        // Pack and display
        frame.pack();
        frame.setVisible(true);
    }
}
