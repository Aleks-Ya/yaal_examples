package lang.inheritance.linkage.override.exception.exception_in_initializer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Случаи бросания ExceptionInInitializerError.
 */
class ExceptionInInitializerErrorTest {

    /**
     * Initialization of a static variable
     */
    @Test
    void staticField() {
        assertThatThrownBy(StaticField::new).isInstanceOf(ExceptionInInitializerError.class);
    }

    /**
     * Execution of an anonymous static block
     */
    @Test
    void staticInitializer() {
        assertThatThrownBy(StaticInitializer::new).isInstanceOf(ExceptionInInitializerError.class);
    }

    /**
     * Execution of a static method
     */
    @Test
    void staticMethod() {
        assertThatThrownBy(StaticMethod::new).isInstanceOf(ExceptionInInitializerError.class);
    }

    /**
     * Исключения, брошенные в блоке инициализации объекта,
     * не оборачиваются в ExceptionInInitializerError.
     */
    @Test
    void initializer() {
        assertThatThrownBy(Initializer::new).isInstanceOf(ArrayIndexOutOfBoundsException.class);
    }
}