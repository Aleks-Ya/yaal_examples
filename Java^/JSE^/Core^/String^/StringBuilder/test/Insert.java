import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Применение метода StringBuilder#insert.
 */
public class Insert {
    
    @Test
    public void insert() {
		StringBuilder sb = new StringBuilder("0123");

        // into the middle
		sb.insert(2,7);
		assertEquals("01723", sb.toString());

        //into the begin
        sb.insert(0, 5);
		assertEquals("501723", sb.toString());

        //into the end
        sb.insert(sb.length(), 9);
		assertEquals("5017239", sb.toString());
    }

    @Test
    public void insertString() {
        StringBuilder sb = new StringBuilder("0123");

        // into the middle
        sb.insert(2,"987");
        assertEquals("0198723", sb.toString());
    }
}