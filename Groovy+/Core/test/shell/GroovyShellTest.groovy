package shell

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

/**
 * Using GroovyShell to execute Groovy scripts.
 */
class GroovyShellTest {

    @Test
    void evaluateString() {
        def shell = new GroovyShell()
        def value = shell.evaluate('1 + 1')
        assertEquals(2, value)
    }

    @Test
    void evaluateFile() {
        def shell = new GroovyShell()
        def value = shell.evaluate(new File("test/shell/IncludedFile.groovy"))
        assertEquals('abc', value)
    }

    @Test
    void evaluateWithParameters() {
        def values = [num1: 1, num2: 2, message: 'Result ']
        def binding = new Binding(values)
        def shell = new GroovyShell(binding)
        def value = shell.evaluate('num1 + num2')
        assertEquals(3, value)
    }
}