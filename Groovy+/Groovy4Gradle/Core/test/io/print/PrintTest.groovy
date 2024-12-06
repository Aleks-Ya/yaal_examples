package io.print

import org.junit.jupiter.api.Test

class PrintTest {
    @Test
    void format() {
        printf('Size is %d\n', 42)
    }
}
