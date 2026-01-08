package dev.shaaf.tswing;

import com.googlecode.lanterna.screen.Screen;
import dev.shaaf.tswing.geom.Dimension;
import dev.shaaf.tswing.geom.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class TComponentTest {

    private TestComponent component;

    // Concrete implementation for testing abstract TComponent
    private static class TestComponent extends TComponent {
        @Override
        public void render(Screen screen) {
            // Test implementation
        }

        @Override
        protected Dimension calculatePreferredSize() {
            return new Dimension(100, 50);
        }
    }

    @BeforeEach
    void setUp() {
        component = new TestComponent();
    }

    @Test
    void testDefaultState() {
        assertThat(component.isVisible()).isTrue();
        assertThat(component.isEnabled()).isTrue();
        assertThat(component.isFocusable()).isFalse();
        assertThat(component.hasFocus()).isFalse();
    }

    @Test
    void testLocationManagement() {
        component.setLocation(10, 20);
        assertThat(component.getX()).isEqualTo(10);
        assertThat(component.getY()).isEqualTo(20);

        Point location = component.getLocation();
        assertThat(location.x).isEqualTo(10);
        assertThat(location.y).isEqualTo(20);
    }

    @Test
    void testSizeManagement() {
        component.setSize(200, 100);
        assertThat(component.getWidth()).isEqualTo(200);
        assertThat(component.getHeight()).isEqualTo(100);

        Dimension size = component.getSize();
        assertThat(size.width).isEqualTo(200);
        assertThat(size.height).isEqualTo(100);
    }

    @Test
    void testBoundsManagement() {
        component.setBounds(5, 10, 150, 75);
        assertThat(component.getX()).isEqualTo(5);
        assertThat(component.getY()).isEqualTo(10);
        assertThat(component.getWidth()).isEqualTo(150);
        assertThat(component.getHeight()).isEqualTo(75);
    }

    @Test
    void testPreferredSize() {
        Dimension preferred = component.getPreferredSize();
        assertThat(preferred.width).isEqualTo(100);
        assertThat(preferred.height).isEqualTo(50);

        // Test explicit setting
        component.setPreferredSize(new Dimension(300, 200));
        preferred = component.getPreferredSize();
        assertThat(preferred.width).isEqualTo(300);
        assertThat(preferred.height).isEqualTo(200);
    }

    @Test
    void testColorManagement() {
        assertThat(component.getForeground()).isEqualTo(TColor.WHITE);
        assertThat(component.getBackground()).isEqualTo(TColor.BLACK);

        component.setForeground(TColor.RED);
        component.setBackground(TColor.BLUE);

        assertThat(component.getForeground()).isEqualTo(TColor.RED);
        assertThat(component.getBackground()).isEqualTo(TColor.BLUE);
    }

    @Test
    void testVisibilityToggle() {
        component.setVisible(false);
        assertThat(component.isVisible()).isFalse();

        component.setVisible(true);
        assertThat(component.isVisible()).isTrue();
    }

    @Test
    void testEnabledToggle() {
        component.setEnabled(false);
        assertThat(component.isEnabled()).isFalse();

        component.setEnabled(true);
        assertThat(component.isEnabled()).isTrue();
    }

    @Test
    void testFocusManagement() {
        component.setFocusable(true);
        assertThat(component.isFocusable()).isTrue();
        assertThat(component.hasFocus()).isFalse();
    }

    @Test
    void testAbsoluteLocationWithoutParent() {
        component.setLocation(10, 20);
        Point absolute = component.getAbsoluteLocation();
        assertThat(absolute.x).isEqualTo(10);
        assertThat(absolute.y).isEqualTo(20);
    }

    @Test
    void testMinimumSize() {
        Dimension min = component.getMinimumSize();
        assertThat(min).isNotNull();

        component.setMinimumSize(new Dimension(50, 25));
        min = component.getMinimumSize();
        assertThat(min.width).isEqualTo(50);
        assertThat(min.height).isEqualTo(25);
    }

    @Test
    void testMaximumSize() {
        Dimension max = component.getMaximumSize();
        assertThat(max).isNotNull();

        component.setMaximumSize(new Dimension(500, 300));
        max = component.getMaximumSize();
        assertThat(max.width).isEqualTo(500);
        assertThat(max.height).isEqualTo(300);
    }
}
