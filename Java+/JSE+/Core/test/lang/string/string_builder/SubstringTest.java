package lang.string.string_builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubstringTest {
    
    @Test
    void substring() {
        var sb = new StringBuilder("0123");
		assertEquals("1", sb.substring(1,2));
    }
}