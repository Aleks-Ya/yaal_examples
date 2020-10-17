package mockk

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MockkAsserts {

    @Test
    fun mock() {
        val mock = mockk<Person>()
        val name = "Mock Name"
        every { mock.getName() } returns name
        assertThat(mock.getName()).isEqualTo(name)
        verify { mock.getName() }
        confirmVerified(mock)
    }
}

internal class Person {
    fun getName(): String = "John"
}