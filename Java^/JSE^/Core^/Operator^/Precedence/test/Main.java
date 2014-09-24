import org.junit.Test;
import static org.junit.Assert.*;
import static java.lang.System.out;

public class Main {

	@Test
    public void main() {
		out.println(2 + 2 * 2);
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
		assertTrue(new String().isEmpty());
	}

	@Test
	public void constructor() {
		String s = "a";
		assertEquals("b", new String(s = "b"));
		assertEquals("b", s);
	}
}