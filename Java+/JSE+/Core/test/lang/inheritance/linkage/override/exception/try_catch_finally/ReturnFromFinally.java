package lang.inheritance.linkage.override.exception.try_catch_finally;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class ReturnFromFinally {
	@Test
	public void test() {
		assertEquals(10, getInt1());
		assertEquals(20, getInt2());
	}
	
	int getInt1() {
		int i = 0;
		try {
			i = 10;
		} finally {
			return i;
		}
	}
	
	int getInt2() {
		int i = 0;
		try {
			throw new Exception();
		} catch(Exception e) {
			i = 20;
		} finally {
			return i;
		}
	}
}
