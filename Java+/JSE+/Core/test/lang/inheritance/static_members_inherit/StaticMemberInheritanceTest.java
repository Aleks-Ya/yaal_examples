package lang.inheritance.static_members_inherit;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;

class StaticMemberInheritanceTest {

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

class Child extends Parent {
}