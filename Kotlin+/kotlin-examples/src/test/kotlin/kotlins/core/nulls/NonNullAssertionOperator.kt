package kotlins.core.nulls

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Using the "Non-null assertion operator" (!!).
 * */
class NonNullAssertionOperator {

    @Test
    fun `convert Nullable Reference to Non-nullable Reference`() {
        val nullableReference: String? = "John"
        val nonNullableReference: String = nullableReference!!
        assertThat(nonNullableReference).isEqualTo(nullableReference)
    }
}