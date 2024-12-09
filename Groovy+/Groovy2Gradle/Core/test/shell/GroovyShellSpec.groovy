package shell

import spock.lang.Specification

class GroovyShellSpec extends Specification {

    def "test evaluate string"() {
        given: "a GroovyShell instance"
        def shell = new GroovyShell()

        when: "evaluating a simple expression"
        def value = shell.evaluate('1 + 1')

        then: "the result is correct"
        value == 2
    }

    def "test evaluate file"() {
        given: "a GroovyShell instance"
        def shell = new GroovyShell()

        when: "evaluating a script from a file"
        def value = shell.evaluate(new File("test/shell/IncludedFile.groovy"))

        then: "the result is correct"
        value == 'abc'
    }

    def "test evaluate with parameters"() {
        given: "a GroovyShell instance with bindings"
        def values = [num1: 1, num2: 2, message: 'Result ']
        def binding = new Binding(values)
        def shell = new GroovyShell(binding)

        when: "evaluating an expression using parameters"
        def value = shell.evaluate('num1 + num2')

        then: "the result is correct"
        value == 3
    }
}
