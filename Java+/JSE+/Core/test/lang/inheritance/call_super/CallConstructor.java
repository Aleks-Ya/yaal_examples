package lang.inheritance.call_super;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;

public class CallConstructor {
	
	@Test
    public void main() {
		new Child();
	}
}

class Parent {
    Parent(String s) {
		out.println("Parent's constructor: " + s);
	}
}

class Child extends Parent {
	Child() {
		super("hello from Child");
		out.println("Child's constructor");
	}
}
