package lang.string.string.substring;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

/**
 * String#substring(int fromInclusive, int toExclusive)
 * String#substring(int fromInclusive)
 */
public class Substring {

    @Test
    public void substring() {
        String s = "0123456789";
        assertEquals("12",   s.substring(1, 3));
        assertEquals("6789", s.substring(6));
        assertEquals("",     s.substring(10));
    }

    @Test(expected=StringIndexOutOfBoundsException.class)
    public void substringException1() {
    	String s = "0";
    	assertEquals("", s.substring(1));
    	s.substring(2);
    }

    @Test(expected=StringIndexOutOfBoundsException.class)
    public void substringException2() {
    	String s = "012345";
    	assertEquals("012345", s.substring(0, 6));
    	s.substring(0, 7);
    }
}