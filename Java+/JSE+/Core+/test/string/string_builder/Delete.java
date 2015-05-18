package string.string_builder;

import org.junit.Test;
import static org.junit.Assert.*;

public class Delete {
    
    @Test
    public void delete() {
		StringBuilder sb = new StringBuilder("0123");
		sb.delete(1,2);
		assertEquals("023", sb.toString());
    }

    @Test
    public void deleteAll() {
		StringBuilder sb = new StringBuilder("0123");
		sb.delete(0, sb.length());
		assertTrue(sb.toString().isEmpty());
    }
}