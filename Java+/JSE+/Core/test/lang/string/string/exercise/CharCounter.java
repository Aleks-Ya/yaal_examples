package lang.string.string.exercise;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Подсчитать количество данного символа в строке (разными способами).
 */
public class CharCounter {
	
	@Test
	public void count() {
		assertEquals(3, counter1("Semper Fidelis", 'e'));
		assertEquals(0, counter1("Semper Fidelis", 'a'));
		assertEquals(0, counter1(null, 'e'));
		assertEquals(0, counter1("", 'e'));
	}
	
	private int counter1(String s, char c) {
		if(s == null) {
			return 0;
		}
		int counter = 0;
		for(int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == c) {
				counter++;
			}
		}
		return counter;
	}
}
