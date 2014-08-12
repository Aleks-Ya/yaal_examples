package staticfield;

import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Переопределение статического поля.
 */
public class StaticField {
    public static void main(String[] args) throws IOException, ReflectiveOperationException {
		System.out.println(new Parent().s);
		System.out.println(new Child().s);
	}
}

class Parent {
    static String s = "Parent";
}

class Child extends Parent {
    static String s = "Child";
}