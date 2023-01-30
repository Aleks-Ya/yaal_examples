package io.print

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class StringFormatTest {
    @Test
    void format() {
        def template = 'Size is %d'
        def formatted = String.format(template, 42)
        assertEquals("Size is 42", formatted)
    }

    @Test
    void gstring() {
        def size = 42
        def formatted = "Size is $size"
        assertEquals("Size is 42", formatted.toString())
    }
}
