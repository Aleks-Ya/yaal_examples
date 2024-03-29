package lang.inheritance.linkage.variables;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;

class VariablesTest {

    @Test
    void main() {
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