package io.file

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class ReplaceInFileTest {

    @Test
    void replaceSubString() {
        def file = File.createTempFile(ReplaceInFileTest.class.simpleName, '.tmp')
        file.text = 'abc'
        assertEquals('abc', new File(file.absolutePath).text)
        replace(file.absolutePath, "b", "x")
        assertEquals('axc', new File(file.absolutePath).text)
    }

    private static def replace(String file, String from, String to) {
        def f = new File(file)
        f.text = f.text.replace(from, to)
    }
}
