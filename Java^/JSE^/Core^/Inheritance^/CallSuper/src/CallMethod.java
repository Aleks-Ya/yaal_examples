package method;

import static java.lang.System.out;

public class CallMethod {
    public static void main(String[] args) {
		new Child().print();
	}
}

class Parent {
    void print() {
		out.println("Parent's print");
	}
}

class Child extends Parent {
	void print() {
		super.print();
		out.println("Child's print");
	}
}