package kotlins.core.collection

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Maps {

    @Test
    fun `immutable map literal`() {
        val map = mapOf("a" to 2, "b" to 7)
        assertThat(map).containsEntry("a", 2).containsEntry("b", 7)
    }
}