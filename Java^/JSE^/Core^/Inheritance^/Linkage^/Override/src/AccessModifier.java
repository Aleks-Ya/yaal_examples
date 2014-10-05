/**
 * Модификатор доступа переопределяющего метода может быть менее строгим.
 */
public class AccessModifier {
    public static void main(String[] args) {
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