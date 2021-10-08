package io.file


import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

class ReadTextFileTest {

    @Test
    void fileToString() {
        def path = getClass().getResource('/io/file/data.txt').path
        def content = new File(path).text
        assertEquals('abc\nefg', content)
    }

    @Test
    void fileToLineList() {
        def path = getClass().getResource('/io/file/data.txt').path
        def lines = new File(path).readLines()
        assertTrue(lines.size() == 2)
        assertEquals(['abc', 'efg'], lines)
    }
}
