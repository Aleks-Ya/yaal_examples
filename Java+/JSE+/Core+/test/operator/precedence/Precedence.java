package operator.precedence;

import org.junit.Test;
import static org.junit.Assert.*;
import static java.lang.System.out;

public class Precedence {

	@Test
    public void main() {
        assertEquals(6, 2 + 2 * 2);
    }
    
    @Test
    public void orAnd() {
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		out.println(a++ == 0 || b++ < 0 && c++ < 0 || d++ < 0);
		out.println("a=" + a);
		out.println("b=" + b);
		out.println("c=" + c);
		out.println("d=" + d);
	}

	@Test
	public void newAndDot() {
		assertEquals(16, new StringBuilder().capacity());
	}

    @Test
    public void castAndDot() {
        Object o = new String();
        assertTrue(((String) o).isEmpty());
    }

	@Test
	public void constructor() {
		String s = "a";
		assertEquals("b", new String(s = "b"));
		assertEquals("b", s);
	}
}