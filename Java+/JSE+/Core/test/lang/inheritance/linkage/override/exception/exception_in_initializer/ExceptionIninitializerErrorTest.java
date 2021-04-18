package lang.inheritance.linkage.override.exception.exception_in_initializer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Случаи бросания ExceptionInInitializerError.
 */
public class ExceptionIninitializerErrorTest {

    /**
     * Initialization of a static variable
     */
    @Test
    public void staticField() {
        assertThrows(ExceptionInInitializerError.class, StaticField::new);
    }

    /**
     * Execution of an anonymous static block
     */
    @Test
    public void staticInitializer() {
        assertThrows(ExceptionInInitializerError.class, StaticInitializer::new);
    }

    /**
     * Execution of a static method
     */
    @Test
    public void staticMethod() {
        assertThrows(ExceptionInInitializerError.class, StaticMethod::new);
    }

    /**
     * Исключения, брошенные в блоке инициализации объекта,
     * не оборачиваются в ExceptionInInitializerError.
     */
    @Test
    public void initializer() {
        assertThrows(ArrayIndexOutOfBoundsException.class, Initializer::new);
    }
}