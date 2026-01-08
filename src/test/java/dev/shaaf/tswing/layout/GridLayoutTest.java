package dev.shaaf.tswing.layout;

import dev.shaaf.tswing.TButton;
import dev.shaaf.tswing.TPanel;
import dev.shaaf.tswing.geom.Dimension;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GridLayoutTest {

    @Test
    void testInvalidConstruction() {
        assertThatThrownBy(() -> new GridLayout(0, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("rows and cols cannot both be zero");
    }

    @Test
    void testValidConstruction() {
        GridLayout layout1 = new GridLayout(2, 3);
        assertThat(layout1.getRows()).isEqualTo(2);
        assertThat(layout1.getColumns()).isEqualTo(3);

        GridLayout layout2 = new GridLayout(0, 3); // Auto rows
        assertThat(layout2.getColumns()).isEqualTo(3);

        GridLayout layout3 = new GridLayout(2, 0); // Auto cols
        assertThat(layout3.getRows()).isEqualTo(2);
    }

    @Test
    void testLayoutWithFixedRowsAndCols() {
        GridLayout layout = new GridLayout(2, 2);
        TPanel container = new TPanel(layout);
        container.setSize(100, 50);

        // Add 4 buttons (exactly fills 2x2 grid)
        for (int i = 0; i < 4; i++) {
            container.add(new TButton("Button " + i));
        }

        layout.layoutContainer(container);

        // All components should have equal size
        TButton first = (TButton) container.getComponents()[0];
        assertThat(first.getWidth()).isGreaterThan(0);
        assertThat(first.getHeight()).isGreaterThan(0);
    }

    @Test
    void testLayoutWithAutoRows() {
        GridLayout layout = new GridLayout(0, 3); // 3 columns, auto rows
        TPanel container = new TPanel(layout);
        container.setSize(90, 60);

        // Add 6 buttons (should create 2 rows)
        for (int i = 0; i < 6; i++) {
            container.add(new TButton("B" + i));
        }

        layout.layoutContainer(container);

        // First button should be at (0,0)
        TButton first = (TButton) container.getComponents()[0];
        assertThat(first.getX()).isEqualTo(0);
        assertThat(first.getY()).isEqualTo(0);
    }

    @Test
    void testPreferredSize() {
        GridLayout layout = new GridLayout(2, 2, 5, 5);
        TPanel container = new TPanel(layout);

        for (int i = 0; i < 4; i++) {
            container.add(new TButton("Btn" + i));
        }

        Dimension preferred = layout.preferredLayoutSize(container);
        assertThat(preferred).isNotNull();
        assertThat(preferred.width).isGreaterThan(0);
        assertThat(preferred.height).isGreaterThan(0);
    }

    @Test
    void testGaps() {
        GridLayout layout = new GridLayout(2, 2, 10, 5);
        assertThat(layout.getHgap()).isEqualTo(10);
        assertThat(layout.getVgap()).isEqualTo(5);

        layout.setHgap(15);
        layout.setVgap(8);
        assertThat(layout.getHgap()).isEqualTo(15);
        assertThat(layout.getVgap()).isEqualTo(8);
    }

    @Test
    void testEqualCellSizes() {
        GridLayout layout = new GridLayout(2, 2);
        TPanel container = new TPanel(layout);
        container.setSize(100, 100);

        TButton btn1 = new TButton("1");
        TButton btn2 = new TButton("2");
        TButton btn3 = new TButton("3");
        TButton btn4 = new TButton("4");

        container.add(btn1);
        container.add(btn2);
        container.add(btn3);
        container.add(btn4);

        layout.layoutContainer(container);

        // All buttons should have same size
        assertThat(btn1.getWidth()).isEqualTo(btn2.getWidth());
        assertThat(btn1.getHeight()).isEqualTo(btn2.getHeight());
        assertThat(btn2.getWidth()).isEqualTo(btn3.getWidth());
        assertThat(btn2.getHeight()).isEqualTo(btn3.getHeight());
    }
}
