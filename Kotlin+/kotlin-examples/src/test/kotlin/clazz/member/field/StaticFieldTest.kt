package clazz.member.field

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class StaticFieldTest {

    @Test
    fun `read a static field`() {
        assertThat(StaticField.MY_STATIC_FILED).isEqualTo(20)
    }
}