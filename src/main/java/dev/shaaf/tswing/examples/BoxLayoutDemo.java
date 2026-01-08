package dev.shaaf.tswing.examples;

import dev.shaaf.tswing.*;
import dev.shaaf.tswing.layout.BorderLayout;
import dev.shaaf.tswing.layout.BoxLayout;

/**
 * Demonstrates BoxLayout - vertical or horizontal stacking.
 */
public class BoxLayoutDemo {

    public static void main(String[] args) {
        TFrame frame = new TFrame("BoxLayout Demo");
        frame.setDefaultCloseOperation(TFrame.EXIT_ON_CLOSE);

        TPanel mainPanel = new TPanel(new BorderLayout());

        // Title
        TLabel titleLabel = new TLabel("BoxLayout - Vertical (Y_AXIS)");
        titleLabel.setForeground(TColor.CYAN);

        // Vertical BoxLayout panel
        TPanel verticalPanel = new TPanel(new BoxLayout(BoxLayout.Y_AXIS, 1));

        TLabel statusLabel = new TLabel("Select an option");
        statusLabel.setForeground(TColor.YELLOW);

        // Add vertically stacked buttons
        TButton btn1 = new TButton("Option 1 - First Choice");
        TButton btn2 = new TButton("Option 2");
        TButton btn3 = new TButton("Option 3 - Another Option");
        TButton btn4 = new TButton("Option 4");

        btn1.addActionListener(e -> statusLabel.setText("Selected: Option 1"));
        btn2.addActionListener(e -> statusLabel.setText("Selected: Option 2"));
        btn3.addActionListener(e -> statusLabel.setText("Selected: Option 3"));
        btn4.addActionListener(e -> statusLabel.setText("Selected: Option 4"));

        verticalPanel.add(btn1);
        verticalPanel.add(btn2);
        verticalPanel.add(btn3);
        verticalPanel.add(btn4);

        // Help
        TLabel helpLabel = new TLabel("TAB=Navigate  ENTER=Select  ESC=Exit");
        helpLabel.setForeground(TColor.GREEN);

        // Layout main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(verticalPanel, BorderLayout.CENTER);

        TPanel bottomPanel = new TPanel(new BorderLayout());
        bottomPanel.add(statusLabel, BorderLayout.NORTH);
        bottomPanel.add(helpLabel, BorderLayout.SOUTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
