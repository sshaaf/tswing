package dev.shaaf.tswing.layout;

import dev.shaaf.tswing.TButton;
import dev.shaaf.tswing.TLabel;
import dev.shaaf.tswing.TPanel;
import dev.shaaf.tswing.geom.Dimension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BorderLayoutTest {

    private BorderLayout layout;
    private TPanel container;

    @BeforeEach
    void setUp() {
        layout = new BorderLayout();
        container = new TPanel(layout);
        container.setSize(100, 50);
    }

    @Test
    void testEmptyLayout() {
        layout.layoutContainer(container);
        // Should not throw exception with no components
    }

    @Test
    void testNorthComponent() {
        TLabel north = new TLabel("North");
        container.add(north, BorderLayout.NORTH);

        layout.layoutContainer(container);

        assertThat(north.getY()).isEqualTo(0);
        assertThat(north.getWidth()).isEqualTo(100);
    }

    @Test
    void testAllRegions() {
        TLabel north = new TLabel("N");
        TLabel south = new TLabel("S");
        TLabel east = new TLabel("E");
        TLabel west = new TLabel("W");
        TLabel center = new TLabel("C");

        container.add(north, BorderLayout.NORTH);
        container.add(south, BorderLayout.SOUTH);
        container.add(east, BorderLayout.EAST);
        container.add(west, BorderLayout.WEST);
        container.add(center, BorderLayout.CENTER);

        layout.layoutContainer(container);

        // North should be at top
        assertThat(north.getY()).isEqualTo(0);

        // South should be at bottom
        assertThat(south.getY()).isGreaterThan(0);

        // West should be on left
        assertThat(west.getX()).isEqualTo(0);

        // East should be on right
        assertThat(east.getX()).isGreaterThan(0);
    }

    @Test
    void testPreferredSize() {
        TButton button = new TButton("Test");
        container.add(button, BorderLayout.CENTER);

        Dimension preferred = layout.preferredLayoutSize(container);
        assertThat(preferred).isNotNull();
        assertThat(preferred.width).isGreaterThan(0);
        assertThat(preferred.height).isGreaterThan(0);
    }

    @Test
    void testAddWithoutConstraint() {
        TLabel label = new TLabel("Default");
        container.add(label); // Should default to CENTER

        layout.layoutContainer(container);
        assertThat(label.getWidth()).isGreaterThan(0);
    }

    @Test
    void testGaps() {
        BorderLayout layoutWithGaps = new BorderLayout(5, 3);
        assertThat(layoutWithGaps.getHgap()).isEqualTo(5);
        assertThat(layoutWithGaps.getVgap()).isEqualTo(3);

        layoutWithGaps.setHgap(10);
        layoutWithGaps.setVgap(8);
        assertThat(layoutWithGaps.getHgap()).isEqualTo(10);
        assertThat(layoutWithGaps.getVgap()).isEqualTo(8);
    }
}
