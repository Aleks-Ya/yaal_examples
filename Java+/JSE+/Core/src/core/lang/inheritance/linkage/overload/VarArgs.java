package core.lang.inheritance.linkage.overload;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VarArgs {
	
	/**
	 * JVM выберет самый специфичный метод.
	 */
	@Test
	public void mostSpecific() {
		Assert.assertEquals(3, method(10, 20));
	}
	
   int method(int i, int... j){return 1;}
   int method(int... i ){return 2;}
   int method(int i, int j){return 3;}
}
