package access;

/**
 * Модификатор доступа переопределяющего метода может быть менее строгим.
 */
public class AccessModifier {
    public static void main(String[] args) {
        System.out.println(new Child().makeString());
    }
}

class Parent {
    String makeString() {
        return "Parent";
    }
}

class Child extends Parent {
    @Override
    protected String makeString() {
        return "Child";
    }
}