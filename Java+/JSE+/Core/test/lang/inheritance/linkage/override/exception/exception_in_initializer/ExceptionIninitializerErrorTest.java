package lang.inheritance.linkage.override.exception.exception_in_initializer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Случаи бросания ExceptionInInitializerError.
 */
class ExceptionIninitializerErrorTest {

    /**
     * Initialization of a static variable
     */
    @Test
    void staticField() {
        assertThrows(ExceptionInInitializerError.class, StaticField::new);
    }

    /**
     * Execution of an anonymous static block
     */
    @Test
    void staticInitializer() {
        assertThrows(ExceptionInInitializerError.class, StaticInitializer::new);
    }

    /**
     * Execution of a static method
     */
    @Test
    void staticMethod() {
        assertThrows(ExceptionInInitializerError.class, StaticMethod::new);
    }

    /**
     * Исключения, брошенные в блоке инициализации объекта,
     * не оборачиваются в ExceptionInInitializerError.
     */
    @Test
    void initializer() {
        assertThrows(ArrayIndexOutOfBoundsException.class, Initializer::new);
    }
}