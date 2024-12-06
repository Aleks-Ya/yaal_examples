package flow

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class IfTest {
    @Test
    void ternary() {
        def result = (1 > 2) ? 'abc' : 'xyz'
        assertEquals('xyz', result)
    }
}
