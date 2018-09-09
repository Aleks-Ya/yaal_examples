package junit

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertThrows

class Logic2Test {
    Logic cut = new Logic()

    @Test
    void testGetString() {
        assertEquals('a string', cut.string)
    }

    @Test
    void testThrowException() {
        assertThrows(RuntimeException.class, { ->
            cut.throwException()
        } as Executable)
    }
}