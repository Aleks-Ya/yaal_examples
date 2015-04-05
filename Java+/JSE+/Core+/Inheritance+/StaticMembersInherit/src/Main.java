import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
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