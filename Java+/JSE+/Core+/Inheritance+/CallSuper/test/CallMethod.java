package method;

import org.junit.Test;
import static org.junit.Assert.*;
import static java.lang.System.out;

public class CallMethod {
	@Test
    public void main() {
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
