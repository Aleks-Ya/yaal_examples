package clazz

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PojoTest {

    @Test
    fun `instantiate Person class`() {
        val name = "John"
        val age = 30
        val p = Person(name, age)
        assertThat(p.name).isEqualTo(name)
        assertThat(p.age).isEqualTo(age)
    }
}