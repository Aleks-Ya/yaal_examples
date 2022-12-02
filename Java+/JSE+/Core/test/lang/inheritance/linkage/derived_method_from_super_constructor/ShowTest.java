package lang.inheritance.linkage.derived_method_from_super_constructor;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;

class ShowTest {
    @Test
    public void test() {
        new Child();
    }
}

class Parent {

    Parent() {
        test();
    }

    void test() {
        out.println("parent::test");
    }

}

class Child extends Parent {

    private String field = "Init";

    Child() {
        field = "Init in constructor";
    }

    void test() {
        out.println("child::test");
        out.println("field=" + field);
    }
}