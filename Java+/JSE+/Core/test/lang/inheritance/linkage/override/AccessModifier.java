package lang.inheritance.linkage.override;

import org.junit.jupiter.api.Test;

/**
 * Модификатор доступа переопределяющего метода может быть менее строгим.
 */
class AccessModifier {
    @Test
    void main() {
        System.out.println(new Child().makeString());
    }

    private static class Parent {
        String makeString() {
            return "Parent";
        }
    }

    private static class Child extends Parent {
        @Override
        protected String makeString() {
            return "Child";
        }
    }
}