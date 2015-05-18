package literal;

import org.junit.Test;
import static org.junit.Assert.*;

public class Suffix {

    /**
     * Все существующие суффиксы числовых литералов.
     */
    @Test
    public void allSuffixed() {
        long l1 = 2l;
        long l2 = 2L;
        float f1 = 3f;
        float f2 = 3F;
        double d1 = 4d;
        double d2 = 4D;
    }

    /**
	 * Литералы в двоичной системе (0b) не могут содержать суффиксы f,F,d,D. 
	 * Они автоматически приводятся к типу переменной, даже если не помещаются в ее диапазон.
	 */
	@Test
	public void binary() {
		byte b = 0b10_000;
		short s = 0b10_000;
		int i = 0b10_000;
		long l = 0b10_000;
		float f = 0b10_000;
		double d = 0b10_000;
		
		//Ошибка компиляции
		//double d = 0b10_000D;
		//float x = 0b10_000f;		
	}
	
	/**
	 * Note that a floating point number cannot be written in Octal. 
	 * Therefore, float x = 010_000f; is valid but it is not octal 
	 * even though it starts with a 0. It is interpreted in decimal.
	 */
	@Test
	public void fpOctal() {
		float f1 = 010_000f;
		float f2 = 10_000f;
		int i = 10_000;
		assertEquals(i, f1, 0);
		assertEquals(i, f2, 0);
	}
}
