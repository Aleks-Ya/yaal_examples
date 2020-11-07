package kotlins.core.nulls

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Using the "Safe call operator" (?.).
 * */
class SafeCallOperator {

    @Test
    fun `prevent NPE on a null reference`() {
        val nullableReference: String? = null
        val length = nullableReference?.length
        assertThat(length).isNull()
    }
}