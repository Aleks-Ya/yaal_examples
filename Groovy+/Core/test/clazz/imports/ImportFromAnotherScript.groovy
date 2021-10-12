package clazz.imports


import clazz.imports.lib2.Library2
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class ImportFromAnotherScript {

    @Test
    void staticMethodFromClassSamePackage() {
        def greet = Library.greet()
        assertEquals('Hi!', greet)
    }

    @Test
    void staticMethodFromClassAnotherPackage() {
        def person = Library2.person()
        assertEquals('John', person)
    }

    @Test
    void functionAsFileAnotherPackage() {
        def shell = new GroovyShell()
        def functions = shell.parse(new File('src/clazz/imports/lib2/Functions.groovy'))
        def city = functions.city()
        assertEquals('Moscow', city)
    }

    @Test
    void functionAsTextAnotherPackage() {
        def scriptText = new File('src/clazz/imports/lib2/Functions.groovy').text
        def shell = new GroovyShell()
        def functions = shell.parse(scriptText)
        def city = functions.city()
        assertEquals('Moscow', city)
    }
}