package lang.inheritance.linkage.overload;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VarArgsTest {
	
	/**
	 * JVM выберет самый специфичный метод.
	 */
	@Test
	public void mostSpecific() {
		assertEquals(3, method(10, 20));
	}
	
   int method(int i, int... j){return 1;}
   int method(int... i ){return 2;}
   int method(int i, int j){return 3;}
}