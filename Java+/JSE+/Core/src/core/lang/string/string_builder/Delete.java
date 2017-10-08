package core.lang.string.string_builder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Delete {
    
    @Test
    public void delete() {
		StringBuilder sb = new StringBuilder("0123");
		sb.delete(1,2);
		Assert.assertEquals("023", sb.toString());
    }

    @Test
    public void deleteAll() {
		StringBuilder sb = new StringBuilder("0123");
		sb.delete(0, sb.length());
		Assert.assertTrue(sb.toString().isEmpty());
    }
}