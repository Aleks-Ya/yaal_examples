package swing.Container.JComponent.Focus.withdrawal;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FocusTest {

    @Test
    void right() {
        var matrix = new Object[][]{
                {1, 2, null, 3, 4, null, 5},
                {4, 5, null}
        };
        var focus = new Focus<>(matrix);
        assertThat(focus.prevSelected()).isEqualTo(1);
        assertThat(focus.selected()).isEqualTo(1);
        focus.right();
        assertThat(focus.prevSelected()).isEqualTo(1);
        assertThat(focus.selected()).isEqualTo(2);
        focus.right();
        assertThat(focus.prevSelected()).isEqualTo(2);
        assertThat(focus.selected()).isEqualTo(3);
        focus.right();
        assertThat(focus.prevSelected()).isEqualTo(3);
        assertThat(focus.selected()).isEqualTo(4);
        focus.right();
        assertThat(focus.prevSelected()).isEqualTo(4);
        assertThat(focus.selected()).isEqualTo(5);
        focus.right();
        assertThat(focus.prevSelected()).isEqualTo(4);
        assertThat(focus.selected()).isEqualTo(5);
    }

    @Test
    void left() {
        var matrix = new Object[][]{
                {0, 1, null, 3, 4, null, 6},
                {4, 5, null}
        };
        var focus = new Focus<>(matrix);
        focus.right();
        focus.right();
        focus.right();
        focus.right();
        assertThat(focus.prevSelected()).isEqualTo(4);
        assertThat(focus.selected()).isEqualTo(6);
        focus.left();
        assertThat(focus.prevSelected()).isEqualTo(6);
        assertThat(focus.selected()).isEqualTo(4);
        focus.left();
        assertThat(focus.prevSelected()).isEqualTo(4);
        assertThat(focus.selected()).isEqualTo(3);
        focus.left();
        assertThat(focus.prevSelected()).isEqualTo(3);
        assertThat(focus.selected()).isEqualTo(1);
        focus.left();
        assertThat(focus.prevSelected()).isEqualTo(1);
        assertThat(focus.selected()).isEqualTo(0);
        focus.left();
        assertThat(focus.prevSelected()).isEqualTo(1);
        assertThat(focus.selected()).isEqualTo(0);
    }

    @Test
    void down() {
        var matrix = new Object[][]{
                {1, 2, 3},
                {4, 5, null},
                {null, 8, 9},
                {10},
                {11},
                {null, 12},
                {13, 14}

        };
        var focus = new Focus<>(matrix);
        assertThat(focus.prevSelected()).isEqualTo(1);
        assertThat(focus.selected()).isEqualTo(1);
        focus.down();
        assertThat(focus.prevSelected()).isEqualTo(1);
        assertThat(focus.selected()).isEqualTo(4);
        focus.down();
        assertThat(focus.prevSelected()).isEqualTo(4);
        assertThat(focus.selected()).isEqualTo(10);
        focus.down();
        assertThat(focus.prevSelected()).isEqualTo(10);
        assertThat(focus.selected()).isEqualTo(11);
        focus.down();
        assertThat(focus.prevSelected()).isEqualTo(11);
        assertThat(focus.selected()).isEqualTo(13);
        focus.down();
        assertThat(focus.prevSelected()).isEqualTo(11);
        assertThat(focus.selected()).isEqualTo(13);
    }

    @Test
    void up() {
        var matrix = new Object[][]{
                {0, 12, 13},
                {1, 15, null},
                {null, 16, 17},
                {3},
                {4},
                {null, 18},
                {6, 19}

        };
        var focus = new Focus<>(matrix);
        focus.down();
        focus.down();
        focus.down();
        focus.down();
        assertThat(focus.prevSelected()).isEqualTo(4);
        assertThat(focus.selected()).isEqualTo(6);
        focus.up();
        assertThat(focus.prevSelected()).isEqualTo(6);
        assertThat(focus.selected()).isEqualTo(4);
        focus.up();
        assertThat(focus.prevSelected()).isEqualTo(4);
        assertThat(focus.selected()).isEqualTo(3);
        focus.up();
        assertThat(focus.prevSelected()).isEqualTo(3);
        assertThat(focus.selected()).isEqualTo(1);
        focus.up();
        assertThat(focus.prevSelected()).isEqualTo(1);
        assertThat(focus.selected()).isEqualTo(0);
        focus.up();
        assertThat(focus.prevSelected()).isEqualTo(1);
        assertThat(focus.selected()).isEqualTo(0);
    }

    @Test
    void rightDown() {
        var matrix = new Object[][]{
                {1, 2, null},
                {3, 4, null},
                {5, 6, null},
        };
        var focus = new Focus<>(matrix);
        assertThat(focus.prevSelected()).isEqualTo(1);
        assertThat(focus.selected()).isEqualTo(1);
        focus.right();
        assertThat(focus.prevSelected()).isEqualTo(1);
        assertThat(focus.selected()).isEqualTo(2);
        focus.down();
        assertThat(focus.prevSelected()).isEqualTo(2);
        assertThat(focus.selected()).isEqualTo(4);
        focus.down();
        assertThat(focus.prevSelected()).isEqualTo(4);
        assertThat(focus.selected()).isEqualTo(6);
        focus.down();
        assertThat(focus.prevSelected()).isEqualTo(4);
        assertThat(focus.selected()).isEqualTo(6);
    }
}