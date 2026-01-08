package dev.shaaf.tswing;

import dev.shaaf.tswing.layout.BorderLayout;
import dev.shaaf.tswing.layout.FlowLayout;

/**
 * Utility class for showing confirmation dialogs.
 * Similar to JOptionPane.showConfirmDialog()
 */
public class TConfirmDialog {
    public static final int YES_OPTION = 0;
    public static final int NO_OPTION = 1;
    public static final int CANCEL_OPTION = 2;

    /**
     * Show a Yes/No confirmation dialog.
     */
    public static int showYesNoDialog(TFrame parent, String message, String title) {
        TDialog dialog = new TDialog(parent, title, true);
        dialog.setLayout(new BorderLayout());

        // Message label
        TLabel messageLabel = new TLabel(message);
        messageLabel.setForeground(TColor.WHITE);

        // Button panel
        TPanel buttonPanel = new TPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));

        TButton yesButton = new TButton("Yes");
        yesButton.addActionListener(e -> dialog.closeDialog(YES_OPTION));

        TButton noButton = new TButton("No");
        noButton.addActionListener(e -> dialog.closeDialog(NO_OPTION));

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        // Layout
        TPanel contentPanel = new TPanel(new BorderLayout());
        contentPanel.add(messageLabel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(contentPanel, BorderLayout.CENTER);

        // Pack and show (focus is set automatically)
        dialog.pack();
        dialog.setVisible(true);

        return dialog.getDialogResult();
    }

    /**
     * Show a Yes/No/Cancel confirmation dialog.
     */
    public static int showYesNoCancelDialog(TFrame parent, String message, String title) {
        TDialog dialog = new TDialog(parent, title, true);
        dialog.setLayout(new BorderLayout());

        // Message label
        TLabel messageLabel = new TLabel(message);
        messageLabel.setForeground(TColor.WHITE);

        // Button panel
        TPanel buttonPanel = new TPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));

        TButton yesButton = new TButton("Yes");
        yesButton.addActionListener(e -> dialog.closeDialog(YES_OPTION));

        TButton noButton = new TButton("No");
        noButton.addActionListener(e -> dialog.closeDialog(NO_OPTION));

        TButton cancelButton = new TButton("Cancel");
        cancelButton.addActionListener(e -> dialog.closeDialog(CANCEL_OPTION));

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        buttonPanel.add(cancelButton);

        // Layout
        TPanel contentPanel = new TPanel(new BorderLayout());
        contentPanel.add(messageLabel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(contentPanel, BorderLayout.CENTER);

        // Pack and show (focus is set automatically)
        dialog.pack();
        dialog.setVisible(true);

        return dialog.getDialogResult();
    }

    /**
     * Show an OK/Cancel confirmation dialog.
     */
    public static int showOkCancelDialog(TFrame parent, String message, String title) {
        TDialog dialog = new TDialog(parent, title, true);
        dialog.setLayout(new BorderLayout());

        // Message label
        TLabel messageLabel = new TLabel(message);
        messageLabel.setForeground(TColor.WHITE);

        // Button panel
        TPanel buttonPanel = new TPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));

        TButton okButton = new TButton("OK");
        okButton.addActionListener(e -> dialog.closeDialog(TDialog.OK_OPTION));

        TButton cancelButton = new TButton("Cancel");
        cancelButton.addActionListener(e -> dialog.closeDialog(TDialog.CANCEL_OPTION));

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        // Layout
        TPanel contentPanel = new TPanel(new BorderLayout());
        contentPanel.add(messageLabel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(contentPanel, BorderLayout.CENTER);

        // Pack and show (focus is set automatically)
        dialog.pack();
        dialog.setVisible(true);

        return dialog.getDialogResult();
    }
}
