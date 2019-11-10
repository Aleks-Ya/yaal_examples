package kotlintest.matcher

import io.kotlintest.specs.StringSpec
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ExceptionMatcherTest : StringSpec({
    "exception is expected" {
        assertFailsWith<IllegalArgumentException> { throw IllegalArgumentException() }
    }

    "assert exception message" {
        val message = "fail"
        val e = assertFailsWith<IllegalArgumentException> { throw IllegalArgumentException(message) }
        assertEquals(message, e.message)
    }

    "assert exception cause" {
        val e = assertFailsWith<IllegalArgumentException> { throw IllegalArgumentException(NullPointerException()) }
        assertTrue(e.cause is IndexOutOfBoundsException)
    }
})