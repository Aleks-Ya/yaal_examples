package clazz

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

data class Person2(val name: String, val age: Int)

class DataTest {
    @Test
    fun `data class overrides equals() method`() {
        val name = "John"
        val age = 30
        val p1 = Person2(name, age)
        val p2 = Person2(name, age)
        assertThat(p1).isEqualTo(p2)
    }
}