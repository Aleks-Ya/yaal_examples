package lang.inheritance.linkage.variables;

import org.junit.Test;

import static java.lang.System.out;

public class Main {

    @Test
    public void main() {
        Parent parent = new Parent();
        Parent child = new Child();
        out.println(parent.name);
        out.println(child.name);
        out.println(parent.getName());
        out.println(child.getName());
    }
}

class Parent {
    String name = "Parent";
    String getName() {
        return name;
    }
}

class Child extends Parent {
    String name = "Child";
    
    String getName() {
        return name;
    }
}