package dev.shaaf.tswing.examples;

import dev.shaaf.tswing.*;
import dev.shaaf.tswing.layout.BorderLayout;
import dev.shaaf.tswing.layout.FlowLayout;

/**
 * Demonstrates FlowLayout - components flow left-to-right, wrapping to next line.
 */
public class FlowLayoutDemo {

    public static void main(String[] args) {
        TFrame frame = new TFrame("FlowLayout Demo");
        frame.setDefaultCloseOperation(TFrame.EXIT_ON_CLOSE);

        TPanel mainPanel = new TPanel(new BorderLayout());

        // Title
        TLabel titleLabel = new TLabel("FlowLayout - Components wrap like text");
        titleLabel.setForeground(TColor.CYAN);

        // Panel with FlowLayout
        TPanel flowPanel = new TPanel(new FlowLayout(FlowLayout.CENTER, 2, 1));

        // Add various sized buttons
        TButton btn1 = new TButton("First");
        TButton btn2 = new TButton("Second Button");
        TButton btn3 = new TButton("Third");
        TButton btn4 = new TButton("Button 4");
        TButton btn5 = new TButton("Fifth Button");
        TButton btn6 = new TButton("Six");
        TButton btn7 = new TButton("Button Number Seven");
        TButton btn8 = new TButton("Eight");

        flowPanel.add(btn1);
        flowPanel.add(btn2);
        flowPanel.add(btn3);
        flowPanel.add(btn4);
        flowPanel.add(btn5);
        flowPanel.add(btn6);
        flowPanel.add(btn7);
        flowPanel.add(btn8);

        // Status label
        TLabel statusLabel = new TLabel("Components flow and wrap automatically");
        statusLabel.setForeground(TColor.YELLOW);

        // Add action listeners
        btn1.addActionListener(e -> statusLabel.setText("First button clicked!"));
        btn2.addActionListener(e -> statusLabel.setText("Second Button clicked!"));
        btn3.addActionListener(e -> statusLabel.setText("Third button clicked!"));
        btn4.addActionListener(e -> statusLabel.setText("Button 4 clicked!"));
        btn5.addActionListener(e -> statusLabel.setText("Fifth Button clicked!"));
        btn6.addActionListener(e -> statusLabel.setText("Six clicked!"));
        btn7.addActionListener(e -> statusLabel.setText("Button Number Seven clicked!"));
        btn8.addActionListener(e -> statusLabel.setText("Eight clicked!"));

        // Help
        TLabel helpLabel = new TLabel("TAB=Navigate  ENTER=Click  ESC=Exit");
        helpLabel.setForeground(TColor.GREEN);

        // Layout main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(flowPanel, BorderLayout.CENTER);

        TPanel bottomPanel = new TPanel(new BorderLayout());
        bottomPanel.add(statusLabel, BorderLayout.NORTH);
        bottomPanel.add(helpLabel, BorderLayout.SOUTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
