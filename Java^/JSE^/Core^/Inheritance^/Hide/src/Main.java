import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
		Parent p = new Parent();
		p.field++;
		p.method();
		
		Child c = new Child();
		// Поле и метод недоступны:
		//c.field++;
		//c.method();
    }
}

class Parent {
	public int field;
	public void method() {}
}

class Child extends Parent {
	private int field;
	public void method() {}
}