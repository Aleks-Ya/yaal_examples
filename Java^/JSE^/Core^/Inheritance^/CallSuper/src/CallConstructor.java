package constructor;

import static java.lang.System.out;

public class CallConstructor {
    public static void main(String[] args) {
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