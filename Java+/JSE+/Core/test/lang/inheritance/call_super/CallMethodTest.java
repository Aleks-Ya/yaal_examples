package lang.inheritance.call_super;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;

class CallMethodTest {
    @Test
    void main() {
        new Child().print();
    }

    static class Parent {
        void print() {
            out.println("Parent's print");
        }
    }

    static class Child extends Parent {
        void print() {
            super.print();
            out.println("Child's print");
        }
    }
}

