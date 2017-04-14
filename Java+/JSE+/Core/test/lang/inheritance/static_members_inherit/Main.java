package lang.inheritance.static_members_inherit;

import org.junit.Test;

import static java.lang.System.out;

public class Main {

	@Test
    public void main() {
		Child c = new Child();
		out.println(c.field);
		out.println(c.method());
	}
}

class Parent {
	static int field = 1;
	
	static String method() {
		return "parent";
	}
}

class Child extends Parent {}