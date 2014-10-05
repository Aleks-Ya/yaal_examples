package exception;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Декларируемые исключения должны быть подклассами первоначального.
 */
public class ThrowsType {
    public static void main(String[] args) throws IOException, ReflectiveOperationException {
        System.out.println(new Child().makeString());
    }


    private static class Parent {
        String makeString() throws IOException, ReflectiveOperationException {
            return "Parent";
        }
    }

    private static class Child extends Parent {
        @Override
        String makeString() throws FileNotFoundException, ClassNotFoundException {
            return "Child";
        }
    }
}