package core.lang.string.string_builder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Substring {
    
    @Test
    public void substring() {
		StringBuilder sb = new StringBuilder("0123");
		Assert.assertEquals("1", sb.substring(1,2));
    }
}