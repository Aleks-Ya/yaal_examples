package lang.inheritance.derived_constructor_In_super_class;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;

class DerivedConstructorTest {

    @Test
    void main() {
        Child ch = new Child();
        Parent p = new Parent(ch);
        out.println(ch);
        out.println(p);
    }
  }

class Parent {
    Parent(){}
    Parent(Child ch) {}
}

class Child extends Parent {}