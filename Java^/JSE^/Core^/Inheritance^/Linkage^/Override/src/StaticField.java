import java.io.IOException;

/**
 * Переопределение статического поля.
 */
public class StaticField {
    public static void main(String[] args) throws IOException, ReflectiveOperationException {
        System.out.println(new Parent().s);
        System.out.println(new Child().s);
    }


    private static class Parent {
        static String s = "Parent";
    }

    private static class Child extends Parent {
        static String s = "Child";
    }
}