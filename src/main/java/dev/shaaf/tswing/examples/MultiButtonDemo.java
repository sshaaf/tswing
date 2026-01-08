package dev.shaaf.tswing.examples;

import dev.shaaf.tswing.*;
import dev.shaaf.tswing.layout.BorderLayout;

/**
 * A more complex demo showing multiple buttons and components.
 */
public class MultiButtonDemo {

    public static void main(String[] args) {
        TFrame frame = new TFrame("TSwing - Multi Button Demo");
        frame.setDefaultCloseOperation(TFrame.EXIT_ON_CLOSE);

        TPanel mainPanel = new TPanel(new BorderLayout());

        // Top panel with title
        TLabel titleLabel = new TLabel("Multiple Button Example");
        titleLabel.setForeground(TColor.CYAN);

        // Center panel with status
        TLabel statusLabel = new TLabel("Ready - Select a button and press ENTER");
        statusLabel.setForeground(TColor.WHITE);

        // Bottom panel with buttons
        TPanel buttonPanel = new TPanel(new BorderLayout());

        TButton button1 = new TButton("Option A");
        button1.addActionListener(e -> statusLabel.setText("You selected Option A!"));

        TButton button2 = new TButton("Option B");
        button2.addActionListener(e -> statusLabel.setText("You selected Option B!"));

        TButton button3 = new TButton("Option C");
        button3.addActionListener(e -> statusLabel.setText("You selected Option C!"));

        buttonPanel.add(button1, BorderLayout.WEST);
        buttonPanel.add(button2, BorderLayout.CENTER);
        buttonPanel.add(button3, BorderLayout.EAST);

        // Help label
        TLabel helpLabel = new TLabel("TAB=Next  ENTER=Select  ESC=Exit");
        helpLabel.setForeground(TColor.YELLOW);

        // Layout main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(statusLabel, BorderLayout.CENTER);

        TPanel bottomContainer = new TPanel(new BorderLayout());
        bottomContainer.add(buttonPanel, BorderLayout.NORTH);
        bottomContainer.add(helpLabel, BorderLayout.SOUTH);

        mainPanel.add(bottomContainer, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
