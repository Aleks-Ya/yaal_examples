class Parent {
	static int i = initField();  
	{      
		System.out.println(" Parent object initializer");
	}
	static { 
		System.out.println(" Parent static initializer"); 
	}
	static int initField() {
		System.out.println(" Parent static field "); 
		return 0;
	}
}

class Child extends Parent {
	static {
		System.out.println(" Child static initializer");
	}
	{
		System.out.println(" Child object initializer");
	}
}

class ChildOfChild extends Child{
	static { 
		System.out.println("ChildOfChild static initializer"); 
	}
}

public class Main {    
	public static void main(String[] args) {
		Child m = new Child();
	}
}