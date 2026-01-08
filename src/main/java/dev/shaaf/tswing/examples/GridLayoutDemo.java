package dev.shaaf.tswing.examples;

import dev.shaaf.tswing.*;
import dev.shaaf.tswing.layout.BorderLayout;
import dev.shaaf.tswing.layout.GridLayout;

/**
 * Demonstrates GridLayout - components arranged in a grid with equal-sized cells.
 */
public class GridLayoutDemo {

    public static void main(String[] args) {
        TFrame frame = new TFrame("GridLayout Demo");
        frame.setDefaultCloseOperation(TFrame.EXIT_ON_CLOSE);

        TPanel mainPanel = new TPanel(new BorderLayout());

        // Title
        TLabel titleLabel = new TLabel("GridLayout - 3 rows x 3 columns");
        titleLabel.setForeground(TColor.CYAN);

        // Panel with GridLayout (3 rows, 3 cols, 1 hgap, 1 vgap)
        TPanel gridPanel = new TPanel(new GridLayout(3, 3, 1, 1));

        // Create a calculator-like grid
        String[] labels = {"7", "8", "9", "4", "5", "6", "1", "2", "3"};
        TLabel statusLabel = new TLabel("Click a number");
        statusLabel.setForeground(TColor.YELLOW);

        for (String label : labels) {
            TButton btn = new TButton(label);
            final String number = label;
            btn.addActionListener(e -> statusLabel.setText("You clicked: " + number));
            gridPanel.add(btn);
        }

        // Help
        TLabel helpLabel = new TLabel("TAB=Navigate  ENTER=Click  ESC=Exit");
        helpLabel.setForeground(TColor.GREEN);

        // Layout main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        TPanel bottomPanel = new TPanel(new BorderLayout());
        bottomPanel.add(statusLabel, BorderLayout.NORTH);
        bottomPanel.add(helpLabel, BorderLayout.SOUTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
