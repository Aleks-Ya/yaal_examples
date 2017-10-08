package core.lang.inheritance.linkage.override.exception;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Нет ограничений на непроверяемые исключения, бросаемые переопределяемым методом.
 */
public class Unchecked {

    @Test
    public void main() throws IOException, ReflectiveOperationException {
        Assert.assertEquals("Child", new Child().makeString());
    }


    private static class Parent {
        String makeString() throws NullPointerException {
            return "Parent";
        }
    }

    private static class Child extends Parent {
        @Override
        String makeString() throws IllegalArgumentException {
            return "Child";
        }
    }
}