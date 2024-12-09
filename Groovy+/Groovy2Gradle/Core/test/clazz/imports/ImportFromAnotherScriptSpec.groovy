package clazz.imports

import clazz.imports.lib2.Library2
import spock.lang.Specification

class ImportFromAnotherScriptSpec extends Specification {

    def "staticMethodFromClassSamePackage"() {
        expect:
        Library.greet() == 'Hi!'
    }

    def "staticMethodFromClassAnotherPackage"() {
        expect:
        Library2.person() == 'John'
    }

    def "functionAsFileAnotherPackage"() {
        given:
        def shell = new GroovyShell()
        def functions = shell.parse(new File('src/clazz/imports/lib2/Functions.groovy'))

        expect:
        functions.city() == 'Moscow'
    }

    def "functionAsTextAnotherPackage"() {
        given:
        def scriptText = new File('src/clazz/imports/lib2/Functions.groovy').text
        def shell = new GroovyShell()
        def functions = shell.parse(scriptText)

        expect:
        functions.city() == 'Moscow'
    }
}
