package dev.shaaf.tswing;

import dev.shaaf.tswing.layout.BorderLayout;

/**
 * Utility class for showing message dialogs.
 * Similar to JOptionPane.showMessageDialog()
 */
public class TMessageDialog {

    /**
     * Show a simple message dialog with an OK button.
     */
    public static void showMessageDialog(TFrame parent, String message) {
        showMessageDialog(parent, message, "Message");
    }

    /**
     * Show a message dialog with a custom title.
     */
    public static void showMessageDialog(TFrame parent, String message, String title) {
        TDialog dialog = new TDialog(parent, title, true);
        dialog.setLayout(new BorderLayout());

        // Message label
        TLabel messageLabel = new TLabel(message);
        messageLabel.setForeground(TColor.WHITE);

        // OK button
        TButton okButton = new TButton("OK");
        okButton.addActionListener(e -> dialog.closeDialog(TDialog.OK_OPTION));

        // Layout
        TPanel contentPanel = new TPanel(new BorderLayout());
        contentPanel.add(messageLabel, BorderLayout.CENTER);
        contentPanel.add(okButton, BorderLayout.SOUTH);

        dialog.add(contentPanel, BorderLayout.CENTER);

        // Pack and show (focus is set automatically)
        dialog.pack();
        dialog.setVisible(true);
    }

    /**
     * Show an information message.
     */
    public static void showInformationDialog(TFrame parent, String message, String title) {
        TDialog dialog = new TDialog(parent, title, true);
        dialog.setLayout(new BorderLayout());

        TLabel infoLabel = new TLabel("[INFO] " + message);
        infoLabel.setForeground(TColor.CYAN);

        TButton okButton = new TButton("OK");
        okButton.addActionListener(e -> dialog.closeDialog(TDialog.OK_OPTION));

        TPanel contentPanel = new TPanel(new BorderLayout());
        contentPanel.add(infoLabel, BorderLayout.CENTER);
        contentPanel.add(okButton, BorderLayout.SOUTH);

        dialog.add(contentPanel, BorderLayout.CENTER);

        dialog.pack();
        dialog.setVisible(true);
    }

    /**
     * Show a warning message.
     */
    public static void showWarningDialog(TFrame parent, String message, String title) {
        TDialog dialog = new TDialog(parent, title, true);
        dialog.setLayout(new BorderLayout());

        TLabel warnLabel = new TLabel("[WARN] " + message);
        warnLabel.setForeground(TColor.YELLOW);

        TButton okButton = new TButton("OK");
        okButton.addActionListener(e -> dialog.closeDialog(TDialog.OK_OPTION));

        TPanel contentPanel = new TPanel(new BorderLayout());
        contentPanel.add(warnLabel, BorderLayout.CENTER);
        contentPanel.add(okButton, BorderLayout.SOUTH);

        dialog.add(contentPanel, BorderLayout.CENTER);

        dialog.pack();
        dialog.setVisible(true);
    }

    /**
     * Show an error message.
     */
    public static void showErrorDialog(TFrame parent, String message, String title) {
        TDialog dialog = new TDialog(parent, title, true);
        dialog.setLayout(new BorderLayout());

        TLabel errorLabel = new TLabel("[ERROR] " + message);
        errorLabel.setForeground(TColor.RED);

        TButton okButton = new TButton("OK");
        okButton.addActionListener(e -> dialog.closeDialog(TDialog.OK_OPTION));

        TPanel contentPanel = new TPanel(new BorderLayout());
        contentPanel.add(errorLabel, BorderLayout.CENTER);
        contentPanel.add(okButton, BorderLayout.SOUTH);

        dialog.add(contentPanel, BorderLayout.CENTER);

        dialog.pack();
        dialog.setVisible(true);
    }
}
