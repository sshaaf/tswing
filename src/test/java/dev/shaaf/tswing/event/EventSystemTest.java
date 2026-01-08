package dev.shaaf.tswing.event;

import dev.shaaf.tswing.TButton;
import dev.shaaf.tswing.TLabel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.*;

class EventSystemTest {

    private TButton button;
    private TLabel label;

    @BeforeEach
    void setUp() {
        button = new TButton("Test Button");
        label = new TLabel("Initial");
    }

    @Test
    void testActionListenerFires() {
        AtomicInteger clickCount = new AtomicInteger(0);

        button.addActionListener(e -> clickCount.incrementAndGet());

        button.doClick();
        assertThat(clickCount.get()).isEqualTo(1);

        button.doClick();
        assertThat(clickCount.get()).isEqualTo(2);
    }

    @Test
    void testMultipleActionListeners() {
        AtomicInteger count1 = new AtomicInteger(0);
        AtomicInteger count2 = new AtomicInteger(0);

        button.addActionListener(e -> count1.incrementAndGet());
        button.addActionListener(e -> count2.incrementAndGet());

        button.doClick();

        assertThat(count1.get()).isEqualTo(1);
        assertThat(count2.get()).isEqualTo(1);
    }

    @Test
    void testActionEventContainsSource() {
        AtomicReference<ActionEvent> capturedEvent = new AtomicReference<>();

        button.addActionListener(capturedEvent::set);
        button.doClick();

        assertThat(capturedEvent.get()).isNotNull();
        assertThat(capturedEvent.get().getSource()).isEqualTo(button);
    }

    @Test
    void testActionCommand() {
        button.setActionCommand("custom-command");

        AtomicReference<String> capturedCommand = new AtomicReference<>();
        button.addActionListener(e -> capturedCommand.set(e.getActionCommand()));

        button.doClick();

        assertThat(capturedCommand.get()).isEqualTo("custom-command");
    }

    @Test
    void testDisabledButtonDoesNotFire() {
        AtomicInteger clickCount = new AtomicInteger(0);
        button.addActionListener(e -> clickCount.incrementAndGet());

        button.setEnabled(false);
        button.doClick();

        assertThat(clickCount.get()).isEqualTo(0);
    }

    @Test
    void testRemoveActionListener() {
        AtomicInteger clickCount = new AtomicInteger(0);
        ActionListener listener = e -> clickCount.incrementAndGet();

        button.addActionListener(listener);
        button.doClick();
        assertThat(clickCount.get()).isEqualTo(1);

        button.removeActionListener(listener);
        button.doClick();
        assertThat(clickCount.get()).isEqualTo(1); // Should not increment
    }

    @Test
    void testFocusListenerRegistration() {
        AtomicInteger gainedCount = new AtomicInteger(0);

        button.setFocusable(true);
        FocusListener listener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                gainedCount.incrementAndGet();
            }

            @Override
            public void focusLost(FocusEvent e) {
                // No-op for this test
            }
        };

        button.addFocusListener(listener);
        assertThat(button.isFocusable()).isTrue();

        // Remove listener
        button.removeFocusListener(listener);
        // If we could trigger focus events, count should not change
    }

    @Test
    void testEventTimestamp() {
        AtomicReference<Long> timestamp = new AtomicReference<>();

        button.addActionListener(e -> timestamp.set(e.getWhen()));

        long before = System.currentTimeMillis();
        button.doClick();
        long after = System.currentTimeMillis();

        assertThat(timestamp.get()).isBetween(before, after);
    }
}
