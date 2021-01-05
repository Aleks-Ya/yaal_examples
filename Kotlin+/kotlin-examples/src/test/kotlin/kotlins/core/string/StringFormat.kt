package kotlins.core.string

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StringFormat {
    @Test
    fun `format string`() {
        val name = "John"
        val greeting = "Hi, $name!"
        assertThat(greeting).isEqualTo("Hi, John!")
    }

    @Test
    fun `format string with braces`() {
        val code1 = "123"
        val code2 = "456"
        val greeting = "Position ${code1}.${code2}"
        assertThat(greeting).isEqualTo("Position 123.456")
    }
}