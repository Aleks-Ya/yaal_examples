package core.lang.string.string.exercise;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Подсчитать количество подстроки в строку (разными способами).
 */
public class SubstringCounter {

    /**
     * Алгоритм на основе String#indexOf.
     */
	@Test
	public void count1() {
		Assert.assertEquals(3, counter1("doremefadosolliasido", "do"));
		Assert.assertEquals(0, counter1("Semper Fidelis", "not"));
		Assert.assertEquals(0, counter1("Semper Fidelis", ""));
		Assert.assertEquals(0, counter1(null, "no"));
		Assert.assertEquals(0, counter1("", "a"));
		Assert.assertEquals(0, counter1("Semper Fidelis", null));
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
	
	/**
     * Алгоритм на основе String#startsWith.
     * todo Переписать на основе параметризованных тестов.
     */
	@Test
	public void count2() {
		Assert.assertEquals(3, counter2("doremefadosolliasido", "do"));
		Assert.assertEquals(0, counter2("Semper Fidelis", "not"));
		Assert.assertEquals(0, counter2("Semper Fidelis", ""));
		Assert.assertEquals(0, counter2(null, "no"));
		Assert.assertEquals(0, counter2("", "a"));
		Assert.assertEquals(0, counter2("Semper Fidelis", null));
	}
	
	private int counter2(String src, String substring) {
		if (src == null || substring == null || substring.isEmpty()) {
			return 0;
		}
		int counter = 0;
		int index = 0;
		while(index < src.length()) {
			if (src.startsWith(substring, index)) {
				counter++;
				index += substring.length();
			} else {
				index++;
			}
		}
		return counter;
	}
}
