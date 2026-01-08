package dev.shaaf.tswing.examples;

import dev.shaaf.tswing.*;
import dev.shaaf.tswing.event.ActionEvent;
import dev.shaaf.tswing.event.ActionListener;

/**
 * Simple test to verify event handling works without UI
 */
public class EventTest {

    public static void main(String[] args) {
        System.out.println("Testing TSwing Event System...");

        // Create a label
        TLabel label = new TLabel("Initial text");
        System.out.println("Initial label text: " + label.getText());

        // Create a button with an action listener
        TButton button = new TButton("Test Button");
        final int[] clickCount = {0};

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickCount[0]++;
                System.out.println("ActionListener fired! Click count: " + clickCount[0]);
                String newText = "Button clicked " + clickCount[0] + " time(s)!";
                label.setText(newText);
                System.out.println("Label text updated to: " + label.getText());
            }
        });

        // Simulate button clicks
        System.out.println("\nSimulating button clicks...");
        button.doClick();
        button.doClick();
        button.doClick();

        System.out.println("\nFinal click count: " + clickCount[0]);
        System.out.println("Final label text: " + label.getText());

        if (clickCount[0] == 3 && label.getText().equals("Button clicked 3 time(s)!")) {
            System.out.println("\n✓ Event system is working correctly!");
        } else {
            System.out.println("\n✗ Event system has issues!");
            System.out.println("  Expected count: 3, got: " + clickCount[0]);
            System.out.println("  Expected text: 'Button clicked 3 time(s)!', got: '" + label.getText() + "'");
        }
    }
}
