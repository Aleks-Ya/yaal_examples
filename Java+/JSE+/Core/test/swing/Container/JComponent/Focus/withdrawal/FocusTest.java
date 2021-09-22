package swing.Container.JComponent.Focus.withdrawal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FocusTest {

    @Test
    void right() {
        var matrix = new Object[][]{
                {1, 2, null, 3, 4, null, 5},
                {4, 5, null}
        };
        var focus = new Focus<Object>(matrix);
        assertEquals(1, focus.prevSelected());
        assertEquals(1, focus.selected());
        focus.right();
        assertEquals(1, focus.prevSelected());
        assertEquals(2, focus.selected());
        focus.right();
        assertEquals(2, focus.prevSelected());
        assertEquals(3, focus.selected());
        focus.right();
        assertEquals(3, focus.prevSelected());
        assertEquals(4, focus.selected());
        focus.right();
        assertEquals(4, focus.prevSelected());
        assertEquals(5, focus.selected());
        focus.right();
        assertEquals(4, focus.prevSelected());
        assertEquals(5, focus.selected());
    }

    @Test
    void left() {
        var matrix = new Object[][]{
                {0, 1, null, 3, 4, null, 6},
                {4, 5, null}
        };
        var focus = new Focus<Object>(matrix);
        focus.right();
        focus.right();
        focus.right();
        focus.right();
        assertEquals(4, focus.prevSelected());
        assertEquals(6, focus.selected());
        focus.left();
        assertEquals(6, focus.prevSelected());
        assertEquals(4, focus.selected());
        focus.left();
        assertEquals(4, focus.prevSelected());
        assertEquals(3, focus.selected());
        focus.left();
        assertEquals(3, focus.prevSelected());
        assertEquals(1, focus.selected());
        focus.left();
        assertEquals(1, focus.prevSelected());
        assertEquals(0, focus.selected());
        focus.left();
        assertEquals(1, focus.prevSelected());
        assertEquals(0, focus.selected());
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
        var focus = new Focus<Object>(matrix);
        assertEquals(1, focus.prevSelected());
        assertEquals(1, focus.selected());
        focus.down();
        assertEquals(1, focus.prevSelected());
        assertEquals(4, focus.selected());
        focus.down();
        assertEquals(4, focus.prevSelected());
        assertEquals(10, focus.selected());
        focus.down();
        assertEquals(10, focus.prevSelected());
        assertEquals(11, focus.selected());
        focus.down();
        assertEquals(11, focus.prevSelected());
        assertEquals(13, focus.selected());
        focus.down();
        assertEquals(11, focus.prevSelected());
        assertEquals(13, focus.selected());
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
        var focus = new Focus<Object>(matrix);
        focus.down();
        focus.down();
        focus.down();
        focus.down();
        assertEquals(4, focus.prevSelected());
        assertEquals(6, focus.selected());
        focus.up();
        assertEquals(6, focus.prevSelected());
        assertEquals(4, focus.selected());
        focus.up();
        assertEquals(4, focus.prevSelected());
        assertEquals(3, focus.selected());
        focus.up();
        assertEquals(3, focus.prevSelected());
        assertEquals(1, focus.selected());
        focus.up();
        assertEquals(1, focus.prevSelected());
        assertEquals(0, focus.selected());
        focus.up();
        assertEquals(1, focus.prevSelected());
        assertEquals(0, focus.selected());
    }

    @Test
    void rightDown() {
        var matrix = new Object[][]{
                {1, 2, null},
                {3, 4, null},
                {5, 6, null},
        };
        var focus = new Focus<Object>(matrix);
        assertEquals(1, focus.prevSelected());
        assertEquals(1, focus.selected());
        focus.right();
        assertEquals(1, focus.prevSelected());
        assertEquals(2, focus.selected());
        focus.down();
        assertEquals(2, focus.prevSelected());
        assertEquals(4, focus.selected());
        focus.down();
        assertEquals(4, focus.prevSelected());
        assertEquals(6, focus.selected());
        focus.down();
        assertEquals(4, focus.prevSelected());
        assertEquals(6, focus.selected());
    }
}