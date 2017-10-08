package core.lang.inheritance.initialization_sequence;

import org.junit.Test;

import static java.lang.System.out;

class Parent {
	private static int i1 = staticField();

    static {
		out.println("1 Parent static initializer");
	}

	private static int i2 = staticField();

	private static int staticField() {
		out.println("1 Parent static field ");
		return 0;
	}

    private static int field() {
		out.println("3 Parent field ");
		return 0;
	}

    Parent() {
        out.println("4 Parent constructor ");
    }

    private int o1 = field();

	{
		out.println("3 Parent object initializer");
	}
    private int o2 = field();
}

class Child extends Parent {
    private static int i1 = staticField();
	static {
		out.println("2 Child static initializer");
	}
    private static int i2 = staticField();

    Child() {
        out.println("6 Child constructor ");
    }

    private int o1 = field();
	{
		out.println("5 Child object initializer");
	}
    private int o2 = field();


    private static int staticField() {
        out.println("2 Child static field ");
        return 0;
    }

    private static int field() {
        out.println("5 Child field ");
        return 0;
    }
}

class ChildOfChild extends Child{
	static { 
		out.println("ChildOfChild static initializer");
	}
}

public class Main {
	@Test
	public void main() {
		Child m = new Child();
	}
}