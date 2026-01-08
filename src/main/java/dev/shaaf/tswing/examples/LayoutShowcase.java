package dev.shaaf.tswing.examples;

import dev.shaaf.tswing.*;
import dev.shaaf.tswing.layout.*;

/**
 * Comprehensive showcase demonstrating all TSwing layout managers.
 */
public class LayoutShowcase {

    public static void main(String[] args) {
        TFrame frame = new TFrame("TSwing Layout Showcase");
        frame.setDefaultCloseOperation(TFrame.EXIT_ON_CLOSE);

        TPanel mainPanel = new TPanel(new BorderLayout());

        // Title
        TLabel titleLabel = new TLabel("TSwing Layout Managers - All in One");
        titleLabel.setForeground(TColor.CYAN);

        // Content area using BoxLayout to stack different layout examples
        TPanel contentPanel = new TPanel(new BoxLayout(BoxLayout.Y_AXIS, 1));

        // 1. BorderLayout section
        TLabel borderTitle = new TLabel("1. BorderLayout (NORTH/SOUTH/EAST/WEST/CENTER)");
        borderTitle.setForeground(TColor.GREEN);
        contentPanel.add(borderTitle);

        TPanel borderDemo = new TPanel(new BorderLayout());
        borderDemo.add(new TLabel("N"), BorderLayout.NORTH);
        borderDemo.add(new TLabel("S"), BorderLayout.SOUTH);
        borderDemo.add(new TLabel("E"), BorderLayout.EAST);
        borderDemo.add(new TLabel("W"), BorderLayout.WEST);
        borderDemo.add(new TLabel("CENTER"), BorderLayout.CENTER);
        contentPanel.add(borderDemo);

        // 2. FlowLayout section
        TLabel flowTitle = new TLabel("2. FlowLayout (flows left-to-right, wraps)");
        flowTitle.setForeground(TColor.GREEN);
        contentPanel.add(flowTitle);

        TPanel flowDemo = new TPanel(new FlowLayout(FlowLayout.CENTER, 1, 0));
        flowDemo.add(new TButton("A"));
        flowDemo.add(new TButton("B"));
        flowDemo.add(new TButton("C"));
        flowDemo.add(new TButton("D"));
        contentPanel.add(flowDemo);

        // 3. GridLayout section
        TLabel gridTitle = new TLabel("3. GridLayout (2x3 grid, equal cells)");
        gridTitle.setForeground(TColor.GREEN);
        contentPanel.add(gridTitle);

        TPanel gridDemo = new TPanel(new GridLayout(2, 3, 1, 0));
        gridDemo.add(new TButton("1"));
        gridDemo.add(new TButton("2"));
        gridDemo.add(new TButton("3"));
        gridDemo.add(new TButton("4"));
        gridDemo.add(new TButton("5"));
        gridDemo.add(new TButton("6"));
        contentPanel.add(gridDemo);

        // 4. BoxLayout section
        TLabel boxTitle = new TLabel("4. BoxLayout (vertical stacking)");
        boxTitle.setForeground(TColor.GREEN);
        contentPanel.add(boxTitle);

        TPanel boxDemo = new TPanel(new BoxLayout(BoxLayout.Y_AXIS, 0));
        boxDemo.add(new TLabel("First Item"));
        boxDemo.add(new TLabel("Second Item"));
        boxDemo.add(new TLabel("Third Item"));
        contentPanel.add(boxDemo);

        // Status and help
        TLabel statusLabel = new TLabel("Try different layout demos!");
        statusLabel.setForeground(TColor.YELLOW);

        TLabel helpLabel = new TLabel("TAB=Navigate  ENTER=Click  ESC=Exit");
        helpLabel.setForeground(TColor.WHITE);

        // Layout main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        TPanel bottomPanel = new TPanel(new BorderLayout());
        bottomPanel.add(statusLabel, BorderLayout.NORTH);
        bottomPanel.add(helpLabel, BorderLayout.SOUTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
