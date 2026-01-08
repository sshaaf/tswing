package dev.shaaf.tswing.examples;

import dev.shaaf.tswing.*;
import dev.shaaf.tswing.layout.BorderLayout;
import dev.shaaf.tswing.event.*;

/**
 * Debug version with focus logging
 */
public class FocusDebugTest {

    public static void main(String[] args) {
        // Create a frame
        TFrame frame = new TFrame("Focus Debug Test");
        frame.setDefaultCloseOperation(TFrame.EXIT_ON_CLOSE);

        // Create a panel with BorderLayout
        TPanel panel = new TPanel(new BorderLayout());

        // Create components
        TLabel titleLabel = new TLabel("Press TAB to change focus");
        titleLabel.setForeground(TColor.GREEN);

        TButton button1 = new TButton("Button 1");
        TButton button2 = new TButton("Button 2");

        // Add focus listeners with debug output
        button1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                System.err.println("DEBUG: Button 1 GAINED focus. hasFocus=" + button1.hasFocus());
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.err.println("DEBUG: Button 1 LOST focus. hasFocus=" + button1.hasFocus());
            }
        });

        button2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                System.err.println("DEBUG: Button 2 GAINED focus. hasFocus=" + button2.hasFocus());
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.err.println("DEBUG: Button 2 LOST focus. hasFocus=" + button2.hasFocus());
            }
        });

        // Create a panel for the buttons
        TPanel buttonPanel = new TPanel(new BorderLayout());
        buttonPanel.add(button1, BorderLayout.NORTH);
        buttonPanel.add(button2, BorderLayout.SOUTH);

        // Layout components
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Add panel to frame
        frame.add(panel, BorderLayout.CENTER);

        // Pack and display
        frame.pack();
        frame.setVisible(true);
    }
}
