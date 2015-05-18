package exception.exception_in_initializer;

import org.junit.Test;

/**
 * Случаи бросания ExceptionInInitializerError.
 */
public class ExceptionIninitializerErrorTest {

    /**
     * Initialization of a static variable
     */
    @Test(expected = ExceptionInInitializerError.class)
    public void staticField() {
        new StaticField();
    }

    /**
     * Execution of an anonymous static block
     */
    @Test(expected = ExceptionInInitializerError.class)
    public void staticInitializer() {
        new StaticInitializer();
    }

    /**
     * Execution of a static method
     */
    @Test(expected = ExceptionInInitializerError.class)
    public void staticMethod() {
        new StaticMethod();
    }

    /**
     * Исключения, брошенные в блоке инициализации объекта,
     * не оборачиваются в ExceptionInInitializerError.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void initializer() {
        new Initializer();
    }
}