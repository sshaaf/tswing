# TSwing - Terminal-Swing

An experiment to bring the Java Swing's API UX and mental model to Terminal User Interfaces (TUI).

## Concept

TSwing mirrors Java Swing's component architecture for building terminal applications:
- `TFrame` instead of `JFrame`
- `TButton` instead of `JButton`
- Same layout managers: `BorderLayout`, `FlowLayout`, `GridLayout`
- Same event model: `ActionListener`, `KeyListener`

## Quick Start

### Run TSwing Demo Applications

**Hello World Demo:**
```bash
mvn compile exec:java -Dexec.mainClass="examples.dev.shaaf.tswing.HelloTSwing"
```

**Multi-Button Demo:**
```bash
mvn compile exec:java -Dexec.mainClass="examples.dev.shaaf.tswing.MultiButtonDemo"
```

**Controls:**
- `TAB` - Move focus to next component
- `ENTER` - Activate focused button
- `ESC` - Close the application

Detailed examples can be found in the [examples](examples) directory.

```java
// Create a frame
TFrame frame = new TFrame("My Application");
frame.setDefaultCloseOperation(TFrame.EXIT_ON_CLOSE);

// Create a panel with layout
TPanel panel = new TPanel(new BorderLayout());

// Add components
TLabel label = new TLabel("Hello TSwing!");
label.setForeground(TColor.GREEN);

TButton button = new TButton("Click Me");
button.addActionListener(e -> {
    label.setText("Button clicked!");
});

// Layout and display
panel.add(label, BorderLayout.NORTH);
panel.add(button, BorderLayout.SOUTH);
frame.add(panel);
frame.pack();
frame.setVisible(true);
```


