package lang.inheritance.derived_field_available;

import org.junit.jupiter.api.Test;

class Main {
    @Test
    void main() {
        Parent p = new Child();
        
        //compile error
        //out.println(p.name);
    }
}

class Parent {}

class Child extends Parent {
    String name = "Child";
}