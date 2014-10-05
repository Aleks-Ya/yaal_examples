package exercise;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Подсчитать количество подстроки в строку (разными способами).
 */
public class SubstringCounter {

    /**
     * Алгоритм на основе String#indexOf.
     */
	@Test
	public void count1() {
		assertEquals(3, counter1("doremefadosolliasido", "do"));
		assertEquals(0, counter1("Semper Fidelis", "not"));
		assertEquals(0, counter1("Semper Fidelis", ""));
		assertEquals(0, counter1(null, "no"));
		assertEquals(0, counter1("", "a"));
		assertEquals(0, counter1("Semper Fidelis", null));
	}
	
	private int counter1(String src, String substring) {
		if (src == null || substring == null || substring.isEmpty()) {
			return 0;
		}
		int counter = 0;
		int index = 0;
		while((index = src.indexOf(substring, index)) != -1) {
			counter++;
			index++;
		}
		return counter;
	}
}
