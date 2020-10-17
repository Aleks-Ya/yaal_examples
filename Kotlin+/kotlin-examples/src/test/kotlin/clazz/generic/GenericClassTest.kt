package clazz.generic

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

data class Pair<K, V>(val key: K, val value: V)

class GenericClassTest {

    @Test
    fun `generic data class`() {
        val key = "John"
        val value = 30
        val pair = Pair(key, value)
        assertThat(pair.key).isEqualTo(key)
        assertThat(pair.value).isEqualTo(value)
    }
}