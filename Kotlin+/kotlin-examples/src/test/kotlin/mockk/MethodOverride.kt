package mockk

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MethodOverride {

    @Test
    fun mock() {
        val mock = mockk<DataSource>()

        val data = "data"
        every { mock.getData() } returns data

        val dataString = "dataStr"
        every { mock.getData(any<String>()) } returns dataString

        val dataInt = "dataInt"
        every { mock.getData(any<Int>()) } returns dataInt

        assertThat(mock.getData()).isEqualTo(data)
        assertThat(mock.getData("abc")).isEqualTo(dataString)
        assertThat(mock.getData(1)).isEqualTo(dataInt)

        verify {
            mock.getData()
            mock.getData("abc")
            mock.getData(1)
        }
        confirmVerified(mock)
    }
}

internal interface DataSource {
    fun getData(): String
    fun getData(source: String): String
    fun getData(source: Int): String
}