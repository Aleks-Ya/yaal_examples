package withdrawal;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FocusTest {

    @Test
    public void testLeft() throws Exception {

    }

    @Test
    public void testRight() throws Exception {

    }

    @Test
    public void down() {
        Object[][] matrix = {
                {1, 2, 3},
                {4, 5, null},
                {null, 8, 9},
                {10, 11, 12}

        };
        Focus<Object> focus = new Focus<>(matrix);
        assertEquals(1, focus.selected());
        focus.down();
        assertEquals(4, focus.selected());
        focus.down();
        assertEquals(10, focus.selected());
        focus.down();
        assertEquals(10, focus.selected());
    }

    @Test
    public void testUp() throws Exception {

    }
}