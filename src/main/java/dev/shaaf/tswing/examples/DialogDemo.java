package dev.shaaf.tswing.examples;

import dev.shaaf.tswing.*;
import dev.shaaf.tswing.layout.BorderLayout;
import dev.shaaf.tswing.layout.BoxLayout;

/**
 * Demonstrates various types of dialogs in TSwing.
 */
public class DialogDemo {

    public static void main(String[] args) {
        TFrame frame = new TFrame("TSwing Dialog Demo");
        frame.setDefaultCloseOperation(TFrame.EXIT_ON_CLOSE);

        TPanel mainPanel = new TPanel(new BorderLayout());

        // Title
        TLabel titleLabel = new TLabel("Dialog Examples - Click buttons to see dialogs");
        titleLabel.setForeground(TColor.CYAN);

        // Status label
        TLabel statusLabel = new TLabel("Status: Ready");
        statusLabel.setForeground(TColor.YELLOW);

        // Button panel with vertical layout
        TPanel buttonPanel = new TPanel(new BoxLayout(BoxLayout.Y_AXIS, 1));

        // Message Dialog buttons
        TButton infoButton = new TButton("Show Information Dialog");
        infoButton.addActionListener(e -> {
            TMessageDialog.showInformationDialog(frame, "This is an information message", "Information");
            statusLabel.setText("Status: Information dialog closed");
        });

        TButton warningButton = new TButton("Show Warning Dialog");
        warningButton.addActionListener(e -> {
            TMessageDialog.showWarningDialog(frame, "This is a warning message!", "Warning");
            statusLabel.setText("Status: Warning dialog closed");
        });

        TButton errorButton = new TButton("Show Error Dialog");
        errorButton.addActionListener(e -> {
            TMessageDialog.showErrorDialog(frame, "An error has occurred!", "Error");
            statusLabel.setText("Status: Error dialog closed");
        });

        TButton messageButton = new TButton("Show Simple Message");
        messageButton.addActionListener(e -> {
            TMessageDialog.showMessageDialog(frame, "Hello from TSwing!");
            statusLabel.setText("Status: Message dialog closed");
        });

        // Confirm Dialog buttons
        TButton yesNoButton = new TButton("Show Yes/No Dialog");
        yesNoButton.addActionListener(e -> {
            int result = TConfirmDialog.showYesNoDialog(frame, "Do you like TSwing?", "Question");
            if (result == TConfirmDialog.YES_OPTION) {
                statusLabel.setText("Status: You clicked Yes!");
            } else if (result == TConfirmDialog.NO_OPTION) {
                statusLabel.setText("Status: You clicked No");
            } else {
                statusLabel.setText("Status: Dialog was cancelled");
            }
        });

        TButton yesNoCancelButton = new TButton("Show Yes/No/Cancel Dialog");
        yesNoCancelButton.addActionListener(e -> {
            int result = TConfirmDialog.showYesNoCancelDialog(frame,
                "Save changes before closing?", "Confirm");
            if (result == TConfirmDialog.YES_OPTION) {
                statusLabel.setText("Status: Changes saved");
            } else if (result == TConfirmDialog.NO_OPTION) {
                statusLabel.setText("Status: Changes discarded");
            } else {
                statusLabel.setText("Status: Operation cancelled");
            }
        });

        TButton okCancelButton = new TButton("Show OK/Cancel Dialog");
        okCancelButton.addActionListener(e -> {
            int result = TConfirmDialog.showOkCancelDialog(frame,
                "Proceed with operation?", "Confirm");
            if (result == TDialog.OK_OPTION) {
                statusLabel.setText("Status: OK clicked");
            } else {
                statusLabel.setText("Status: Cancelled");
            }
        });

        // Add all buttons
        buttonPanel.add(infoButton);
        buttonPanel.add(warningButton);
        buttonPanel.add(errorButton);
        buttonPanel.add(messageButton);
        buttonPanel.add(yesNoButton);
        buttonPanel.add(yesNoCancelButton);
        buttonPanel.add(okCancelButton);

        // Help label
        TLabel helpLabel = new TLabel("TAB=Navigate  ENTER=Click  ESC=Close Dialog");
        helpLabel.setForeground(TColor.GREEN);

        // Layout main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        TPanel bottomPanel = new TPanel(new BorderLayout());
        bottomPanel.add(statusLabel, BorderLayout.NORTH);
        bottomPanel.add(helpLabel, BorderLayout.SOUTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
