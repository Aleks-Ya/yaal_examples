package lang.literal;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;

class RadixTest {

	@Test
    public void numbers() {
		out.println("Разные системы счисления:");
		out.printf("Двоичная: 0b1001 -> %d%n", 0b1001);
		out.printf("Восмеричная: 07342 -> %d%n", 07342);
		out.printf("Десятичная: 30429 -> %d%n", 30429);
		out.printf("Шестнадцатеричная: 0xA3F -> %d%n%n", 0xA3F);
	}

	@Test
	public void chars() {
		char c1 = 'a';
		char c2 = '\u0122';
		char c3 = 122;
		char c4 = (char) -122;
		out.println("char:");
		out.printf("char c1 = 'a';         -> %s%n", c1);
		out.printf("char c2 = '\u0122';    -> %s%n", c2);
		out.printf("char c3 = 122;         -> %s%n", c3);
		out.printf("char c4 = (char) -122; -> %s%n%n", c4);
	}
}
