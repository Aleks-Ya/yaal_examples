package string.string_builder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Replace {
    
    @Test
    public void replace() {
		StringBuilder sb = new StringBuilder("0123456");
		sb.replace(1,2,"abcde");
		assertEquals("0abcde23456", sb.toString());
    }
}