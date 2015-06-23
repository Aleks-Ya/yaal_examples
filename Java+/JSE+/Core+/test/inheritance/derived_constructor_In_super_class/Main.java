package inheritance.derived_constructor_In_super_class;

import org.junit.Test;

import static java.lang.System.out;

public class Main {

    @Test
    public void main() {
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