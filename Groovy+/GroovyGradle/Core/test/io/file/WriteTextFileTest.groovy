package io.file

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class WriteTextFileTest {

    @Test
    void stringToFile() {
        def content = "abc"
        def file = File.createTempFile(WriteTextFileTest.class.simpleName, '.tmp')
        file.text = content
        assertEquals(content, new File(file.absolutePath).text)
    }
}
