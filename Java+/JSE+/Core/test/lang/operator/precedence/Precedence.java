package lang.operator.precedence;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Precedence {

	@Test
    public void main() {
        assertEquals(6, 2 + 2 * 2);
    }
    
    @Test
    void orAnd() {
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
    void castAndDot() {
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