package dev.shaaf.tswing.examples;

import dev.shaaf.tswing.*;
import dev.shaaf.tswing.layout.BorderLayout;
import dev.shaaf.tswing.event.*;

/**
 * A simple test demonstrating the event system with KeyListener and FocusListener.
 */
public class EventSystemTest {

    public static void main(String[] args) {
        // Create a frame
        TFrame frame = new TFrame("TSwing Event System Test");
        frame.setDefaultCloseOperation(TFrame.EXIT_ON_CLOSE);

        // Create a panel with BorderLayout
        TPanel panel = new TPanel(new BorderLayout());

        // Create components
        TLabel titleLabel = new TLabel("Event System Test - Try TAB, ENTER, and other keys");
        titleLabel.setForeground(TColor.GREEN);

        TLabel statusLabel = new TLabel("Status: Waiting for events...");
        statusLabel.setForeground(TColor.YELLOW);

        TButton button1 = new TButton("Button 1");
        TButton button2 = new TButton("Button 2");

        // Add ActionListener to buttons
        button1.addActionListener(new ActionListener() {
            private int clickCount = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                clickCount++;
                statusLabel.setText("Button 1 clicked " + clickCount + " time(s)!");
            }
        });

        button2.addActionListener(e -> {
            statusLabel.setText("Button 2 was clicked!");
        });

        // Add KeyListener to button1
        button1.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                statusLabel.setText("Key pressed on Button 1: " + e.getKeyStroke());
                // Let the event bubble up (don't consume it)
            }
        });

        // Add FocusListener to both buttons
        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String componentName = e.getSource() instanceof TButton ?
                    ((TButton) e.getSource()).getText() : "Unknown";
                statusLabel.setText("Focus gained: " + componentName);
            }

            @Override
            public void focusLost(FocusEvent e) {
                String componentName = e.getSource() instanceof TButton ?
                    ((TButton) e.getSource()).getText() : "Unknown";
                // Could update status here if needed
            }
        };

        button1.addFocusListener(focusListener);
        button2.addFocusListener(focusListener);

        // Create a panel for the buttons
        TPanel buttonPanel = new TPanel(new BorderLayout());
        buttonPanel.add(button1, BorderLayout.NORTH);
        buttonPanel.add(button2, BorderLayout.SOUTH);

        // Layout components
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(statusLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add panel to frame
        frame.add(panel, BorderLayout.CENTER);

        // Pack and display
        frame.pack();
        frame.setVisible(true);
    }
}
