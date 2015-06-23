package operator.assingnment;

import org.junit.Test;

public class ChainingDeclaration {

	@Test
    public void chain1() {
		int a, b, c;
		a = b = c = 1;
    }
    
    @Test
    public void chain2() {
		int b = 0, c = 0;
		int a = b = c = 100;
    }
    
    @Test
    public void chain3() {
		int b, c;
		int a = b = c = 100;
    }
    
    /**
     * Нельзя совмещать цепочку декларации и инициализации.
     */
    @Test
    public void main() {
		//Ошибка компиляции 
		//int a = b = c = 1;
    }
}
