package clazz.members.method

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class StaticMethodTest {
    @Test
    void main() {
        assertEquals(StaticMethod.getOne(), 1)
    }
}
