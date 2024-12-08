package flow

import org.junit.jupiter.api.Test

class WhileTest {
    @Test
    void whileLoop() {
        def i = 0
        while (i < 5) {
            println(i)
            i++
        }
    }
}
